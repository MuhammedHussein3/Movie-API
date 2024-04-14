package com.movieflix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;
    @Column(nullable = false,length = 200)
    @NotBlank(message = "Please provide movie's title!")
    private String title;
    @Column(nullable = false)
    @NotBlank(message = "Please provide movie's director!")
    private String director;
    @Column(nullable = false)
    @NotBlank(message = "Please provide movie's studio!")
    private String studio;
    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;
    @Column(nullable = false)
    @NotNull(message = "Please provide movie's releaseYear!")
    private Integer releaseYear;
    @Column(nullable = false)
    @NotBlank(message = "Please provide movie's Poster!")
    private String poster;
}
