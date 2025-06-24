package ttv.poltoraha.pivka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "review_ratings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"reader_username", "review_id"}, name = "unique_reader_review")
        })
public class ReviewRating extends BaseRating {
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
