package com.movieflix.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.dto.MovieDto;
import com.movieflix.exception.MovieNotFoundException;
import com.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/movie/")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MovieController {

    private final MovieService movieService;

    @PostMapping("add-movie/")
    public ResponseEntity<String> addMovie(@RequestPart MultipartFile file,
                                           @RequestPart String movieDto) throws IOException {

        MovieDto response = movieService.addMovie(mapToMovieDto(movieDto),file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response.toString());
    }

    private MovieDto mapToMovieDto(String movieDto) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        MovieDto converted = mapper.readValue(movieDto,MovieDto.class);
        return converted;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<String> getMovieById(@PathVariable("id") Integer id) throws MovieNotFoundException {
        MovieDto response = movieService.getMovie(id);
        return ResponseEntity.ok(response.toString());
    }
}
