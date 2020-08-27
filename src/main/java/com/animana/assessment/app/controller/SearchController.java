package com.animana.assessment.app.controller;

import com.animana.assessment.app.dto.SearchResponse;
import com.animana.assessment.app.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@AllArgsConstructor
public class SearchController {
    private SearchService searchService;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @GetMapping(value="/search")
    public Flux<SearchResponse> fetchSearchResults(@RequestParam(name = "q") String query){
        log.info("Searching for : " + query);
        return searchService.fetchSearchResults(query);
    }
}
