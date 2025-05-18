package ttv.poltoraha.pivka.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="quote")
@Getter
@Setter
@ToString(of = "text")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "reader_username")
    private Reader reader;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private String text;
}
