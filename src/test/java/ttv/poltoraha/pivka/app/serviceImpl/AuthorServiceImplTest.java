package ttv.poltoraha.pivka.app.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.serviceImpl.AuthorServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Используйте H2 вместо реальной БД
@Transactional
public class AuthorServiceImplTest {
    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    private Author author;
    private Book book;
    private List<Book> bookList;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        author = new Author();
        author.setBooks(new ArrayList<>());
        book = new Book();
        bookList = List.of(new Book(), new Book());
    }

    @Test
    public void testCreate() {
        val beforeCreateNewAuthor = authorRepository.findAll().size();
        authorService.create(author);
        val afterCreateNewAuthor = authorRepository.findAll().size();

        assertNotEquals(beforeCreateNewAuthor, afterCreateNewAuthor);
    }

    @Test
    public void testDelete() {
        val authorId = authorRepository.save(author).getId();
        authorService.delete(authorId);

        assertFalse(authorRepository.existsById(authorId));
    }

    @Test
    public void testAddBooks_Success() {
        val authorId = authorRepository.save(author).getId();
        author.setId(authorId);
        val authorBooksSizeBeforeUpdate = author.getBooks().size();
        authorService.addBooks(author.getId(), bookList);
        val authorBooksSizeAfterUpdate = authorRepository.findById(author.getId()).get().getBooks().size();

        assertNotEquals(authorBooksSizeBeforeUpdate, authorBooksSizeAfterUpdate);
    }

    @Test
    public void testAddBooks_AuthorNotFound() {
        author.setId(999);

        assertThrows(RuntimeException.class, () -> {
            authorService.addBooks(author.getId(), bookList);
        });
    }

    @Test
    public void testAddBook_Success() {
        val authorId = authorRepository.save(author).getId();
        author.setId(authorId);
        val authorBooksSizeBeforeUpdate = author.getBooks().size();
        authorService.addBook(author.getId(), book);
        val authorBooksSizeAfterUpdate = authorRepository.findById(author.getId()).get().getBooks().size();

        assertNotEquals(authorBooksSizeBeforeUpdate, authorBooksSizeAfterUpdate);
    }

    @Test
    public void testAddBook_AuthorNotFound() {
        author.setId(999);

        assertThrows(RuntimeException.class, () -> {
            authorService.addBook(author.getId(), book);
        });
    }

    @Test
    public void testGetTopAuthorsByTag_Success() {
        Book book1 = new Book();
        Book book2 = new Book();
        book1.setTags("W");
        book2.setTags("W");
        book1.setAuthor(author);
        book2.setAuthor(author);
        author.setBooks(new ArrayList<>(List.of(book1, book2)));
        Author savedAuthor = authorRepository.save(author);

        List<Author> authorList = authorService.getTopAuthorsByTag("W", 1);

        assertEquals(authorList.get(0).getId(), savedAuthor.getId());
    }
}
