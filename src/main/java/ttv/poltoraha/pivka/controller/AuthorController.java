package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.metrics.CustomMetrics;
import ttv.poltoraha.pivka.service.AuthorService;

import java.util.List;

// Контроллеры - это классы для создания внешних http ручек. Чтобы к нам могли прийти по http, например, через постман
// Или если у приложухи есть веб-морда, каждое действие пользователя - это http запросы
@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {
    private final AuthorService authorService;
    private final CustomMetrics metrics;

    @PostMapping("/create")
    public void createAuthor(@RequestBody Author author) {
        log.info("Received request to create author: {}", author);
        metrics.incrementRequestCounter();
        authorService.create(author);
        log.info("Completed request to create author: {}", author);
    }

    @PostMapping("/delete")
    public void deleteAuthorById(@RequestParam Integer id) {
        log.info("Received request to delete author: {}", id);
        metrics.incrementRequestCounter();
        authorService.delete(id);
        log.info("Completed request to delete author: {}", id);
    }

    @PostMapping("/add/books")
    public void addBooksToAuthor(@RequestParam Integer id, @RequestBody List<Book> books) {
        log.info("Received request to add books {} to author: {}", books, id);
        metrics.incrementRequestCounter();
        authorService.addBooks(id, books);
        log.info("Completed request to add books {} to author: {}", books, id);
    }
}
