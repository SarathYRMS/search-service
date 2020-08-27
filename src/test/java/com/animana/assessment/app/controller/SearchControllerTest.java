package com.animana.assessment.app.controller;

import com.animana.assessment.app.controller.SearchController;
import com.animana.assessment.app.dto.SearchResponse;
import com.animana.assessment.app.exception.SearchException;
import com.animana.assessment.app.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = SearchController.class)
public class SearchControllerTest {

    @MockBean
    private SearchService searchService;
    @Autowired
    private WebTestClient webClient;

    @Test
    public void test_fetch_search_results() {
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setType("Book");
        searchResponse.setTitle("Hello, Goodbye");
        searchResponse.setAuthors(List.of());

        Mockito.when(searchService.fetchSearchResults("hello")).thenReturn(
                Flux.just(searchResponse)
        );
        webClient.get().uri("/search?q=hello")
                .exchange()
                .expectStatus().isOk().expectBodyList(SearchResponse.class)
                .value(List::size, equalTo(1))
                .value(resultSearchResponse -> resultSearchResponse.get(0).getTitle(), equalTo("Hello, Goodbye"));

        Mockito.verify(searchService, times(1)).fetchSearchResults("hello");
    }

    @Test
    public void test_fetch_search_results_with_empty(){
        webClient.get().uri("/search?q=")
                .exchange()
                .expectStatus().isOk().expectBodyList(SearchException.class);

        Mockito.verify(searchService, times(1)).fetchSearchResults("");
    }
}
