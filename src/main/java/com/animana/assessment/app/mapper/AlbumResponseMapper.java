package com.animana.assessment.app.mapper;

import com.animana.assessment.app.dto.SearchResponse;
import com.animana.assessment.app.model.AlbumResultResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlbumResponseMapper {
    @Mapping(target = "title", source = "collectionName")
    @Mapping(target = "authors", expression = "java(java.util.Collections.singletonList(albumResultResponse.getArtistName()))")
    SearchResponse mapToDto(AlbumResultResponse albumResultResponse);
}
