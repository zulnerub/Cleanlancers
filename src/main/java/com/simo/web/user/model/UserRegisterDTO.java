package com.simo.web.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterDTO {

    @Email(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
            message = "Invalid email (UserRegisterDTO validation)")
    private String email;

    @Length(min = 3, max = 50, message = "Password must be between 3 and 50 characters.")
    private String password;
    private String confirmPassword;

    @Length(min = 2, max = 50, message = "Please enter a name with length form 2 to 50 characters.")
    private String firstName;

    @Length(min = 2, max = 50, message = "Please enter a name with length form 2 to 50 characters.")
    private String lastName;

    private String role;

}
