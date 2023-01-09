package kl.project.webApp.web.dto;

import jakarta.validation.constraints.NotBlank;
import kl.project.webApp.validators.ValidEmail;
import kl.project.webApp.validators.ValidName;
import kl.project.webApp.validators.ValidPassword;
import kl.project.webApp.validators.ValidSurname;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    private Long id;
    @ValidName(message = "Name should contain at least three chars and first one should be big letter")
    @NotBlank(message = "Firstname must not be null or empty")
    private String firstName;
    @ValidSurname(message = "Surname should contain at least three chars and first one should be big letter")
    @NotBlank(message = "Lastname must not be null or empty")
    private String lastName;
    @ValidEmail(message = "It has to be properly formatted email address")
    @NotBlank(message = "Email must not be null or empty")
    private String email;
    @ValidPassword
    @NotBlank(message = "Password must not be null or empty")
    private String password;
}
