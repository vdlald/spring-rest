package com.vladislav.rest.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestBody extends PageRequest {
    public PageRequestBody(
            @JsonProperty(value = "page", required = true) int page,
            @JsonProperty(value = "size", required = true) int size
    ) {
        super(page, size, Sort.unsorted());
    }
}
