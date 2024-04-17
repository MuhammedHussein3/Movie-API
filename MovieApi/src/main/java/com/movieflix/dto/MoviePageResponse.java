package com.movieflix.dto;

import java.util.List;

public record MoviePageResponse(List<MovieDto> movieDots,
                                Integer pageNumber,
                                Integer pageSize,
                                int totalPages,
                                long totalElements,
                                boolean isLast){
}
