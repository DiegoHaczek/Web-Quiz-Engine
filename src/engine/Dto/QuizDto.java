package engine.Dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public record QuizDto (
        @NotBlank String title,
        @NotBlank String text,
        @NotNull @Size(min = 2) List<String> options,
        List<Integer> answer) {
}


