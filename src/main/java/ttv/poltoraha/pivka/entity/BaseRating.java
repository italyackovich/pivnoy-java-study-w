package ttv.poltoraha.pivka.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import ttv.poltoraha.pivka.enums.RatingValue;

@MappedSuperclass
@Getter
@Setter
public class BaseRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RatingValue value;

    @ManyToOne
    @JoinColumn(name = "reader_username", nullable = false)
    private Reader reader;
}
