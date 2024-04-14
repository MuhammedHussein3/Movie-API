package com.movieflix.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;
@Data
@Builder
@NotBlank
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieDto {
    private Integer movieId;
    @NotBlank(message = "Please provide movie's title!")
    private String title;
    @NotBlank(message = "Please provide movie's director!")
    private String director;
    @NotBlank(message = "Please provide movie's studio!")
    private String studio;
    private Set<String> movieCast;
    @NotNull(message = "Please provide movie's releaseYear!")
    private Integer releaseYear;
    @NotBlank(message = "Please provide movie's Poster!")
    private String poster;
    @NotBlank(message = "Please provide movie's PosterUrl!")
    private String posterUrl;


}
