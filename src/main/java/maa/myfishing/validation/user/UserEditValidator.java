package maa.myfishing.validation.user;

import maa.myfishing.data.models.User;
import maa.myfishing.data.reposipories.UserRepository;
import maa.myfishing.constants.validation.UserValidationConstants;
import maa.myfishing.validation.annotation.Validator;
import maa.myfishing.web.models.user.UserEditModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;

@Validator
public class UserEditValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserEditValidator(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserEditModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserEditModel userEditModel = (UserEditModel) o;

        User user = this.userRepository.findByUsername(userEditModel.getUsername()).orElse(null);

        if (!this.bCryptPasswordEncoder.matches(userEditModel.getOldPassword(), user.getPassword())) {
            errors.rejectValue(
                    "oldPassword",
                    UserValidationConstants.WRONG_PASSWORD,
                    UserValidationConstants.WRONG_PASSWORD
            );
        }

        if (userEditModel.getPassword() != null && !userEditModel.getPassword().equals(userEditModel.getConfirmPassword())) {
            errors.rejectValue(
                    "password",
                    UserValidationConstants.PASSWORDS_DO_NOT_MATCH,
                    UserValidationConstants.PASSWORDS_DO_NOT_MATCH
            );
        }

        if (!user.getEmail().equals(userEditModel.getEmail()) && this.userRepository.findByEmail(userEditModel.getEmail()).isPresent()) {
            errors.rejectValue(
                    "email",
                    String.format(UserValidationConstants.EMAIL_ALREADY_EXISTS, userEditModel.getEmail()),
                    String.format(UserValidationConstants.EMAIL_ALREADY_EXISTS, userEditModel.getEmail())
            );
        }
    }
}
