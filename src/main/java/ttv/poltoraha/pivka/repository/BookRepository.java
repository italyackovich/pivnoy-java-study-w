package ttv.poltoraha.pivka.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query("SELECT b FROM book b JOIN b.author a WHERE a.id = (" +
            "SELECT a2.id FROM author a2 WHERE a2.fullName LIKE CONCAT(:lastname, ' %') " +
            "OR a2.fullName LIKE CONCAT('% ', :lastname) " +
            "ORDER BY a2.avgRating DESC, a2.id ASC LIMIT 1)")
    List<Book> findBooksByAuthorTopRating(@Param("lastname") String lastname);
}
