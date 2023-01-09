package kl.project.webApp.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {

    private Long id;
    @Length(max = 50, message = "Maximum size is 50 characters")
    @NotBlank(message = "Title must not be empty")
    private String title;
    @Length(max = 100, message = "Maximum size is 100 characters")
    @NotBlank(message = "Body must not be empty")
    private String body;
    @NotBlank(message = "You must set deadline date")
    private String deadline;
}
