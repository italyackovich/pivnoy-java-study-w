package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.dao.response.BookResponseDto;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.mapping.MappingUtil;
import ttv.poltoraha.pivka.repository.BookRepository;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> findBooksByAuthorTopRating(@RequestParam String lastname) {
        List<Book> bookList = bookRepository.findBooksByAuthorTopRating(lastname);
        List<BookResponseDto> bookResponseDtoList = bookList.stream().map(MappingUtil::toBookResponseDto).toList();
        return ResponseEntity.ok(bookResponseDtoList);
    }
}
