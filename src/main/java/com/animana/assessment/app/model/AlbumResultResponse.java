package com.animana.assessment.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumResultResponse {
    private String collectionName;
    private String artistName;
    private String type = "Album";
}
