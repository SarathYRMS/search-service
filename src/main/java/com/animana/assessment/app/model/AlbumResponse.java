package com.animana.assessment.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponse {
    private Integer resultCount;
    private List<AlbumResultResponse> results;
}
