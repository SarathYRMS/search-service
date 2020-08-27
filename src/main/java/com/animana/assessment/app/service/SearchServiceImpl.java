package com.animana.assessment.app.service;

import com.animana.assessment.app.config.SearchConfig;
import com.animana.assessment.app.dto.SearchResponse;
import com.animana.assessment.app.exception.SearchException;
import com.animana.assessment.app.mapper.AlbumResponseMapper;
import com.animana.assessment.app.mapper.BookResponseMapper;
import com.animana.assessment.app.model.AlbumResponse;
import com.animana.assessment.app.model.BookResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService {
    private SearchConfig searchConfig;
    private WebClient webClient;
    private AlbumResponseMapper albumResponseMapper;
    private BookResponseMapper bookResponseMapper;

    private <T> Mono<T> constructEndpointCall(String url, String query, Class<T> convertTo) {
        final String endpoint =
                url.replace("SEARCH_TERM", query)
                        .replace("MAX_RESULTS", searchConfig.getMaxResults().toString());
        log.info("Fetching results for : " + endpoint);
        return webClient.get().uri(endpoint)
                .retrieve()
                .bodyToMono(convertTo)
                .doOnError(throwable -> log.error("Error while connecting upstream service: {}", throwable.getMessage()))
                .name(convertTo.getSimpleName()).metrics();
    }

    public Flux<SearchResponse> fetchSearchResults(final String query) {
        validateQueryString(query);
        final Mono<BookResponse> bookResponseMono =
                constructEndpointCall(searchConfig.getBooksUrl(), query, BookResponse.class);
        final Mono<AlbumResponse> albumResponseMono =
                constructEndpointCall(searchConfig.getAlbumsUrl(), query, AlbumResponse.class);

        final Flux<SearchResponse> booksFlux = bookResponseMono
                .onErrorReturn(getEmptyBookResponse())
                .filter(bookResponse -> bookResponse.getTotalItems() > 0)
                .flatMapIterable(BookResponse::getItems)
                .map(bookItemResponse -> bookResponseMapper.mapToDto(bookItemResponse.getVolumeInfo()));

        final Flux<SearchResponse> albumsFlux = albumResponseMono
                .onErrorReturn(getEmptyAlbumResponse())
                .filter(albumResponse -> albumResponse.getResultCount() > 0)
                .flatMapIterable(AlbumResponse::getResults)
                .map(albumResultResponse -> albumResponseMapper.mapToDto(albumResultResponse));

        return Flux.merge(booksFlux, albumsFlux).sort(Comparator.comparing(SearchResponse::getTitle));
    }

    private AlbumResponse getEmptyAlbumResponse() {
        return new AlbumResponse(0, List.of());
    }

    private BookResponse getEmptyBookResponse() {
        return new BookResponse(0, List.of());
    }

    @SneakyThrows
    private void validateQueryString(final String query) {
        if (query.isEmpty()){
            throw new SearchException("Query parameter should not be empty. Please try with other query string.");
        }
    }
}
