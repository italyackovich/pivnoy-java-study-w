package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.metrics.CustomMetrics;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.service.AuthorService;

import java.util.List;

// Имплементации интерфейсов с бизнес-логикой
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CustomMetrics metrics;

    @Override
    public void create(Author author) {
        log.info("Request to author repository to create new author {}", author);
        metrics.recordDbCallTimer(() -> authorRepository.save(author));
        log.info("Completed request to author repository to create new author {}", author);
    }

    @Override
    public void delete(Integer id) {
        log.info("Request to author repository to delete author by id {}", id);
        metrics.recordDbCallTimer(() -> authorRepository.deleteById(id));
        log.info("Completed request to author repository to delete author by id {}", id);
    }

    @Override
    public void addBooks(Integer id, List<Book> books) {
        val author = getOrThrow(id);

        author.getBooks().addAll(books);
    }

    @Override
    public void addBook(Integer id, Book book) {
        val author = getOrThrow(id);

        author.getBooks().add(book);
    }

    @Override
    public List<Author> getTopAuthorsByTag(String tag, int count) {
        Pageable pageable = PageRequest.of(0, count);

        log.info("Request to author repository to find top authors by tag {}", tag);
        val authors = metrics.recordDbCallTimer(() -> authorRepository.findTopAuthorsByTag(tag, pageable));
        log.info("Completed request to author repository to find top authors by tag {}", tag);

        return authors;
    }

    private Author getOrThrow(Integer id) {
        log.info("Request to author repository to find author by id {}", id);
        val author = metrics.recordDbCallTimer(() -> authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id = " + id + " not found")));
        log.info("Completed request to author repository to find author by id {}", id);

        return author;
    }
}
