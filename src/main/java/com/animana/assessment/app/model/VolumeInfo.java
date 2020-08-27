package com.animana.assessment.app.model;

import lombok.Data;

import java.util.List;

@Data
public class VolumeInfo {
    private String title;
    private List<String> authors;
    private String type = "Book";
}
