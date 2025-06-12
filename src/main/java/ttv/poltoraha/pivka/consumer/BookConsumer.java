package ttv.poltoraha.pivka.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class BookConsumer {
    private final RateLimiter rateLimiter = RateLimiter.create(15);
    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "book-updates", groupId = "book_group")
    public void consume(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            Book book = parseBook(record.value());
            rateLimiter.acquire();
            bookRepository.save(book);
            ack.acknowledge();
        } catch (Exception e) {
            ack.acknowledge();
        }
    }

    private Book parseBook(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Book.class);

    }
}
