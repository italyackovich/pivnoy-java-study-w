package ttv.poltoraha.pivka.service;

import ttv.poltoraha.pivka.entity.MyUser;

public interface MyUserPasswordService {
    void changePassword(String newPassword, MyUser user);
}
