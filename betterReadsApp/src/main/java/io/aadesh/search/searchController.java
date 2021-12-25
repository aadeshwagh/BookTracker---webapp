package io.aadesh.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Controller
public class searchController {

    private final WebClient webCliet;
    private final String COVER_IMAGE_ROOT = "https://covers.openlibrary.org/b/id/";

    public searchController(WebClient.Builder webClientBuilder) {
        this.webCliet = webClientBuilder.exchangeStrategies((ExchangeStrategies) ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build())
                .baseUrl("http://openlibrary.org/search.json").build();
    }

    @GetMapping("/search")
    public String getSearchResults(@RequestParam String query, Model model) {
        Mono<SearchResults> resultsMono = this.webCliet.get()
                .uri("?q={query}", query)
                .retrieve().bodyToMono(SearchResults.class);
        SearchResults result = resultsMono.block();

        List<SearchResultBook> books = result.getDocs()
                .stream()
                .limit(10)
                .map(bookResult -> {
                    bookResult.setKey(bookResult.getKey().replace("/works/", ""));
                    String coverId = bookResult.getCover_i();
                    if (StringUtils.hasText(coverId)) {
                        coverId = COVER_IMAGE_ROOT + coverId + "-M.jpg";
                    }

                    bookResult.setCover_i(coverId);
                    return bookResult;
                })
                .collect(Collectors.toList());

        model.addAttribute("searchResult", books);
        return "search";

    }
}
