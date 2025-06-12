package ttv.poltoraha.pivka.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.repository.MyUserRepository;
import ttv.poltoraha.pivka.service.MyUserPasswordService;

@Service
@RequiredArgsConstructor
public class MyUserPasswordServiceImpl implements MyUserPasswordService {
    private final MyUserRepository myUserRepository;

    @Override
    public void changePassword(String newPassword, MyUser user) {
        user.setPassword(newPassword);
        user.setNew(true);
        myUserRepository.save(user);
    }
}
