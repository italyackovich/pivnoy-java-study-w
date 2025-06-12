package ttv.poltoraha.pivka.service;

import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.entity.Reader;

import java.util.List;

public interface RecommendationService {
    List<Author> recommendAuthor(String username);
    List<Book> recommendBook(String username);
    List<Quote> recommendQuoteByBook(Integer book_id);
}
