package ttv.poltoraha.pivka.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.MyUser;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, String> {
}
