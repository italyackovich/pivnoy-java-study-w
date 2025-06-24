package ttv.poltoraha.pivka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import ttv.poltoraha.pivka.enums.RatingValue;

@Entity
@Getter
@Setter
@Table(name = "quote_ratings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"reader_username", "quote_id"}, name = "unique_reader_quote")
        })
public class QuoteRating extends BaseRating {
    @ManyToOne
    @JoinColumn(name = "quote_id")
    private Quote quote;
}
