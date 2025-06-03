package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/authors/{username}")
    public ResponseEntity<List<Author>> getRecommendAuthor(@PathVariable String username) {
        return ResponseEntity.ok(recommendationService.recommendAuthor(username));
    }

    @GetMapping("/books/{username}")
    public ResponseEntity<List<Book>> getRecommendBook(@PathVariable String username) {
        return ResponseEntity.ok(recommendationService.recommendBook(username));
    }

    @GetMapping("/quotes/{id}")
    public ResponseEntity<List<Quote>> getRecommendQuoteByBook(@PathVariable Integer id) {
        return ResponseEntity.ok(recommendationService.recommendQuoteByBook(id));
    }
}
