package com.movieflix.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.dto.MovieDto;
import com.movieflix.dto.MoviePageResponse;
import com.movieflix.exception.EmptyFileException;
import com.movieflix.exception.MovieNotFoundException;
import com.movieflix.exception.PageSizeException;
import com.movieflix.service.MovieService;
import com.movieflix.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie/")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MovieController {

    private final MovieService movieService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add-movie/")
    public ResponseEntity<String> addMovie(@RequestPart MultipartFile file,
                                           @RequestPart String movieDto) throws IOException {


        if (file.isEmpty()) {
            throw new EmptyFileException("Please enter another file and try again");
        }

        MovieDto response = movieService.addMovie(mapToMovieDto(movieDto),file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response.toString());
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/id/{id}")
    public ResponseEntity<String> getMovieById(@PathVariable("id") Integer id) throws MovieNotFoundException {
        MovieDto response = movieService.getMovie(id);
        return ResponseEntity.ok(response.toString());
    }

    @GetMapping("get-movies/")
    public ResponseEntity<List<MovieDto>> getMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("allMoviesPage/")
    public ResponseEntity<MoviePageResponse> allMoviesPage(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
    ) throws PageSizeException {
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber,pageSize));
    }
    @GetMapping("allMoviesPageSort/")
    public ResponseEntity<MoviePageResponse> allMoviesPage(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR,required = false) String sortDir

    ) throws PageSizeException {
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber,pageSize,sortBy,sortDir));
    }

    @PutMapping("update/{movieId}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Integer movieId,
                                                @RequestPart MultipartFile file,
                                                @RequestPart String stringMovieDto) throws IOException, MovieNotFoundException {
       if (file.isEmpty()) file = null;

       MovieDto response = movieService.updateMovie(movieId,mapToMovieDto(stringMovieDto),file);
       return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("delete/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable Integer movieId) throws MovieNotFoundException, IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }

    private MovieDto mapToMovieDto(String movieDto) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        MovieDto converted = mapper.readValue(movieDto,MovieDto.class);
        return converted;
    }
}
