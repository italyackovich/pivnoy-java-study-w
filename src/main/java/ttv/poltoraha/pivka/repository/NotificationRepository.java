package ttv.poltoraha.pivka.repository;

import org.springframework.data.repository.CrudRepository;
import ttv.poltoraha.pivka.entity.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {
}
