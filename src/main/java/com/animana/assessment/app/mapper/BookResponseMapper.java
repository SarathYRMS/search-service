package com.animana.assessment.app.mapper;

import com.animana.assessment.app.dto.SearchResponse;
import com.animana.assessment.app.model.VolumeInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookResponseMapper {
    @Mapping(expression = "java(java.util.Optional.ofNullable(bookResponse.getAuthors()).orElse(java.util.List.of()))", target = "authors")
    SearchResponse mapToDto(VolumeInfo bookResponse);
}
