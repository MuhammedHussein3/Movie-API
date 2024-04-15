package com.movieflix.service;

import com.movieflix.dto.MovieDto;
import com.movieflix.exception.MovieNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;

    MovieDto getMovie(Integer movieId) throws MovieNotFoundException;

    List<MovieDto> getAllMovies();

    MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException, MovieNotFoundException;

    String deleteMovie(Integer movieId) throws MovieNotFoundException, IOException;
}
