package com.animana.assessment.app.service;

import com.animana.assessment.app.dto.SearchResponse;
import reactor.core.publisher.Flux;

public interface SearchService {
    Flux<SearchResponse> fetchSearchResults(final String query);
}
