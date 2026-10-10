package dev.eder.estudiantes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EstudianteRequest(

        @NotBlank(message = "El codigo es obligatorio") @Pattern(regexp = "^U\\d{8}$", message = "El formato del codigo debe de ser U y seguido 8 digitos") String codigo,
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 80, message = "El nombre debe de ser maximo de 80 caracteress") String nombres,
        @NotBlank(message = "El apellido es obligatorio") @Size(max = 80, message = "El apellido debe de ser maximo de 80 caracteres") String apellidos,
        @NotBlank(message = "El correo es obligatorio") @Email(message = "El formato del correo no es valido") @Size(max = 100, message = "El email debe de tener maximo de 100 caracteres") String email,
        @NotBlank(message = "La carrera es obligatorio") @Size(max = 60, message = "La carrera debe de tener maximo de 60 caracteres") String carrera,
        @NotNull(message = "El ciclo es obligatorio") @Min(value = 1, message = "El ciclo minimo es de 1") @Max(value = 10, message = "El ciclo maximo es de 10") Integer ciclo

) {

}
