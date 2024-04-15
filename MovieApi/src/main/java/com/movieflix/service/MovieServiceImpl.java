package com.movieflix.service;

import com.movieflix.dto.MovieDto;
import com.movieflix.entities.Movie;
import com.movieflix.exception.MovieNotFoundException;
import com.movieflix.mapper.Mapper;
import com.movieflix.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileServiceImpl fileService;

    private Mapper mapper;
    @Value("${base.url}")
    private String baseUrl;
    @Value("${project.poster}")
    private String path;
    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {

        //1. upload file get the filename
        if (Files.exists(Paths.get(path+ File.separator+file.getOriginalFilename())))
            throw new RuntimeException("File already exists");

        String fileName = fileService.uploadFile(path,file);

        //2. set the value of the poster as fileName
        movieDto.setPoster(fileName);

        //3. map movieDto to movie
        //Movie movie = mapper.mapFromMovieDtoToMovie(movieDto);
        Movie movie = Movie.
                builder()
                .movieId(movieDto.getMovieId())
                .movieCast(movieDto.getMovieCast())
                .studio(movieDto.getStudio())
                .title(movieDto.getTitle())
                .director(movieDto.getDirector())
                .poster(movieDto.getPoster())
                .releaseYear(movieDto.getReleaseYear())
                .build();
        //4. sava movie in database
        Movie movieSaved = movieRepository.save(movie);

        //5. generate the url
        String posterUrl = baseUrl + "/api/file/getFile/" + fileName;

        //6. map MovieDto to Movie
        MovieDto response = new MovieDto(
                movieSaved.getMovieId(),
                movieSaved.getTitle(),
                movieSaved.getStudio(),
                movieSaved.getDirector(),
                movieSaved.getMovieCast(),
                movieSaved.getReleaseYear(),
                movieSaved.getPoster(),
                posterUrl

        );


        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) throws MovieNotFoundException {
        //1. check the data in DB and if exists, fetch the dta
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            throw new MovieNotFoundException(String.format("Movie with id %s not found", movieId));
        }

        //2. generate posterUrl
        String posterUrl = baseUrl + "/api/file/getFile/" + movie.getPoster();

        //3. map movie to movieDto
        MovieDto response = new MovieDto(
          movie.getMovieId(),
          movie.getTitle(),
          movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<MovieDto> dtos = new ArrayList<>();

        movieRepository.findAll().forEach(movie -> {
            String posterUrl = baseUrl + "/api/file/getFile/" + movie.getPoster();
            dtos.add(new MovieDto(
               movie.getMovieId(),
               movie.getTitle(),
               movie.getDirector(),
               movie.getStudio(),
               movie.getMovieCast(),
               movie.getReleaseYear(),
               movie.getPoster(),
                     posterUrl
            ));
        });
        return dtos;
    }


    @Transactional()
    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException, MovieNotFoundException {

       Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException(String.format("Movie with id %s not found in DB",movieId)));

       String fileName = movie.getPoster();

       if (file != null)
       {
           //1. delete file if exist
           Files.deleteIfExists(Paths.get(path+movie.getPoster()));
           //2.upload new file
           fileName = fileService.uploadFile(path,file);

      }

      movieDto.setPoster(fileName);

      Movie movieU = new Movie(
              movieDto.getMovieId(),
              movieDto.getTitle(),
              movieDto.getDirector(),
              movieDto.getStudio(),
              movieDto.getMovieCast(),
              movieDto.getReleaseYear(),
              movieDto.getPoster()
      );

      //how save work
        //save is update when id is exists in DB
        //save is insert when id is null or empty or not exists in DB
      Movie updatedMovie = movieRepository.save(movieU);


      String posterUrl = baseUrl + "/api/file/getFile/" + fileName;

      MovieDto response = new MovieDto(
              updatedMovie.getMovieId(),
              updatedMovie.getTitle(),
              updatedMovie.getDirector(),
              updatedMovie.getStudio(),
              updatedMovie.getMovieCast(),
              updatedMovie.getReleaseYear(),
              updatedMovie.getPoster(),
              posterUrl
      );

        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws MovieNotFoundException, IOException {
        //1. if movie exist in DB
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException(String.format("Movie with id %s not found in DB",movieId)));

        //2. delete file from server
        String pathFile = path+File.separator+movie.getPoster();
        try {
            Files.deleteIfExists(Paths.get(pathFile));

        }catch (IOException e)
        {
            throw new IOException("File Not deleted from server with path "+pathFile);
        }

        movieRepository.delete(movie);

        return String.format("Successfully, movie and file are deleted! with %s, id",movieId);
    }
}
