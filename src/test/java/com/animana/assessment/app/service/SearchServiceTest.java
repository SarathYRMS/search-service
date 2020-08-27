package com.animana.assessment.app.service;

import com.animana.assessment.app.config.SearchConfig;
import com.animana.assessment.app.dto.SearchResponse;
import com.animana.assessment.app.mapper.AlbumResponseMapper;
import com.animana.assessment.app.mapper.BookResponseMapper;
import com.animana.assessment.app.model.*;
import com.animana.assessment.app.service.SearchService;
import com.animana.assessment.app.service.SearchServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class SearchServiceTest {
    final WebClient webClientMock = mock(WebClient.class);
    final WebClient.RequestHeadersUriSpec uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
    final WebClient.RequestHeadersSpec headersSpecMock = mock(WebClient.RequestHeadersSpec.class);
    final WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
    final WebClient.RequestHeadersSpec bookHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
    final WebClient.ResponseSpec bookResponseSpecMock = mock(WebClient.ResponseSpec.class);

    @Mock
    private SearchConfig searchConfig;
    @Mock
    private AlbumResponseMapper albumResponseMapper;
    @Mock
    private BookResponseMapper bookResponseMapper;

    private SearchService searchService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        given(searchConfig.getAlbumsUrl())
                .willReturn("https://itunes.apple.com/search?term=SEARCH_TERM&entity=album&limit=MAX_RESULTS");
        given(searchConfig.getBooksUrl())
                .willReturn("https://www.googleapis.com/books/v1/volumes?q=SEARCH_TERM&maxResults=MAX_RESULTS");

        when(webClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(ArgumentMatchers.contains("https://itunes.apple.com/search"))).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<AlbumResponse>>notNull()))
                .thenReturn(Mono.just(new AlbumResponse(0, List.of())));

        when(uriSpecMock.uri(ArgumentMatchers.contains("https://www.googleapis.com/books/v1/volumes"))).thenReturn(bookHeadersSpecMock);
        when(bookHeadersSpecMock.retrieve()).thenReturn(bookResponseSpecMock);
        when(bookResponseSpecMock.bodyToMono(ArgumentMatchers.<Class<BookResponse>>notNull()))
                .thenReturn(Mono.just(new BookResponse(0, List.of())));

        SearchResponse albumResponse = new SearchResponse();
        albumResponse.setTitle("Hello");
        albumResponse.setAuthors(List.of("Karmin"));
        albumResponse.setType("Album");

        when(albumResponseMapper.mapToDto(any(AlbumResultResponse.class))).thenReturn(albumResponse);

        SearchResponse bookResponse = new SearchResponse();
        bookResponse.setTitle("Hello, Goodbye");
        bookResponse.setAuthors(List.of("Lora Heller"));
        bookResponse.setType("Book");

        when(bookResponseMapper.mapToDto(any(VolumeInfo.class))).thenReturn(bookResponse);

        searchService = new SearchServiceImpl(
                searchConfig, webClientMock, albumResponseMapper, bookResponseMapper
        );
    }

    @Test
    public void test_zero_results_fetching_term() {
        StepVerifier.create(searchService.fetchSearchResults("invalid_search_goes_here"))
                .expectNextCount(0)
                .verifyComplete();

        verify(webClientMock, times(2)).get();
    }

    @Test
    public void test_valid_term() {
        when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<AlbumResponse>>notNull()))
                .thenReturn(Mono.just(new AlbumResponse(3, List.of(
                        mock(AlbumResultResponse.class),
                        mock(AlbumResultResponse.class),
                        mock(AlbumResultResponse.class)
                ))));
        when(bookResponseSpecMock.bodyToMono(ArgumentMatchers.<Class<BookResponse>>notNull()))
                .thenReturn(Mono.just(new BookResponse(2, List.of(
                        new BookItemResponse(mock(VolumeInfo.class)),
                        new BookItemResponse(mock(VolumeInfo.class))
                ))));

        StepVerifier.create(searchService.fetchSearchResults("hello"))
                .expectNextCount(5)
                .verifyComplete();
    }
}
