package com.animana.assessment.app.e2e;


import com.animana.assessment.app.dto.SearchResponse;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

public class E2ESearchSpec extends BaseSpec {

    @Test
    public void test_with_valid_term() {
        searchConfig.setAlbumsUrl("https://itunes.apple.com/search?term=SEARCH_TERM&entity=album&limit=MAX_RESULTS");
        searchConfig.setBooksUrl("https://www.googleapis.com/books/v1/volumes?q=SEARCH_TERM&&maxResults=MAX_RESULTS");
        webTestClient.get()
                .uri("http://localhost:"+port+"/search?q=hello")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isOk()
                .expectBodyList(SearchResponse.class)
                .hasSize(10);
    }

    @Test
    public void test_with_invalid_term() {
        searchConfig.setAlbumsUrl("https://itunes.apple.com/search?term=SEARCH_TERM&entity=album&limit=MAX_RESULTS");
        searchConfig.setBooksUrl("https://www.googleapis.com/books/v1/volumes?q=SEARCH_TERM&&maxResults=MAX_RESULTS");
        webTestClient.get()
                .uri("http://localhost:"+port+"/search?q="+ UUID.randomUUID().toString())
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().json("[]");
    }

    @Test
    public void test_when_one_upstream_is_down_when_term_is_valid() {
        // mimicing the invalid upstream or error while
        searchConfig.setAlbumsUrl("https://itunesasdas.apple.com/search?term=SEARCH_TERM&entity=album&limit=MAX_RESULTS");
        searchConfig.setBooksUrl("https://www.googleapis.com/books/v1/volumes?q=SEARCH_TERM&&maxResults=MAX_RESULTS");
        webTestClient.get()
                .uri("http://localhost:"+port+"/search?q=hello")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(SearchResponse.class)
                .hasSize(5);
    }

    @Test
    public void test_when_both_upstream_is_down_when_term_is_valid() {
        // mimicing the invalid upstream or error while
        searchConfig.setAlbumsUrl("https://itunesasdas.apple.com/search?term=SEARCH_TERM&entity=album&limit=MAX_RESULTS");
        searchConfig.setBooksUrl("https://www.googleapisadsas.com/books/v1/volumes?q=SEARCH_TERM&&maxResults=MAX_RESULTS");

        webTestClient.get()
                .uri("http://localhost:"+port+"/search?q=hello")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().json("[]");
    }
}
