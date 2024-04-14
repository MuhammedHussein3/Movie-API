package com.movieflix.mapper;

import com.movieflix.dto.MovieDto;
import com.movieflix.entities.Movie;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    @Autowired
    private ModelMapper modelMapper;

    public Movie mapFromMovieDtoToMovie(MovieDto movieDto) {
        return modelMapper.map(movieDto,Movie.class);
    }
}
