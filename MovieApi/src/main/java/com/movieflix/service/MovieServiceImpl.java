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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return List.of();
    }
}
