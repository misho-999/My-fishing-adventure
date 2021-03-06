package maa.myfishing.service.serices;

import maa.myfishing.service.models.DestinationServiceModel;
import maa.myfishing.service.models.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUserName(String username);

//    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

    List<UserServiceModel> getAllUsers();

    void setUserRole(String id, String role);
}
