package com.animana.assessment.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    private String title;
    private List<String> authors;
    private String type;
}
