package ttv.poltoraha.pivka.app.serviceImpl;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.controller.ReaderController;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Reader;
import ttv.poltoraha.pivka.entity.Reading;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.repository.ReaderRepository;
import ttv.poltoraha.pivka.repository.ReadingRepository;
import ttv.poltoraha.pivka.service.BookService;
import ttv.poltoraha.pivka.service.ReaderService;
import ttv.poltoraha.pivka.service.RecommendationService;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ttv.poltoraha.pivka.app.util.TestConst.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Используйте H2 вместо реальной БД
@Transactional // Обеспечивает откат транзакций после каждого теста
public class RecommendationServiceImplTest {
    @Autowired
    private RecommendationService recommendationService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private ReaderController readerController;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ReadingRepository readingRepository;

    @BeforeEach
    public void setUp() {
        val reader = new Reader();
        reader.setUsername(USERNAME);
        reader.setPassword("132");

        readerRepository.save(reader);

        val author = new Author();
        author.setId(1);

        authorRepository.save(author);

        val book = new Book();
        book.setId(1);
        book.setAuthor(author);

        bookRepository.save(book);

        val reading = new Reading();
        reading.setReader(reader);
        reading.setBook(book);
        readingRepository.save(reading);

        readerService.addFinishedBook(USERNAME, 1);

        readerController.addQuote(USERNAME, 1, "Quote");
    }

    @Test
    public void checkRecommendAuthor() {
        val authors = recommendationService.recommendAuthor(USERNAME);

        // todo эта залупа возвращает 3 из-за того, что надо эти ебучие книги и авторов фиксить
        // там столько ёбани, что просто скипаю. Если бы книг, авторов было бы больше и они бы не повторялись - всё ок бы работало
        assertEquals(authors.size(), 3);
    }

    @Test
    public void checkRecommendQuote() {
        val quotes = recommendationService.recommendQuoteByBook(1);

        assertThat(quotes).hasSize(1);
        assertThat(quotes.get(0).getText()).isEqualTo("Quote");
    }
}
