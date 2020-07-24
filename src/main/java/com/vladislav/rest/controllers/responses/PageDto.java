package com.vladislav.rest.controllers.responses;

import lombok.Value;

import java.util.List;

@Value
public class PageDto<T> {

    List<T> content;
    Integer totalPages;
    Long totalElements;
    Integer pageSize;
    Integer pageNumber;
    Boolean first;
    Boolean last;
    Integer numberOfElements;
    Boolean isEmpty;

}
