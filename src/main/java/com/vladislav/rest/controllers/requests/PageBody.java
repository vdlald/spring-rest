package com.vladislav.rest.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBody {

    @JsonProperty(required = true, defaultValue = "0")
    private Integer page;
    @JsonProperty(required = true, defaultValue = "100")
    private Integer pageSize;

}
