package engine.Dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record RegisterDto(
        @NotBlank @Pattern(regexp = ".*@.*[.].*") String email,
        @NotBlank @Pattern(regexp = "^.{5,}$+") String password) {
}
