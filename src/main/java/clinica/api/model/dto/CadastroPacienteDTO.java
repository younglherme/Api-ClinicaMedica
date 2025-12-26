package clinica.api.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record CadastroPacienteDTO(
        @NotBlank
        String nome,
        String telefone,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        String cpf,

        @NotNull @Valid EnderecoDTO endereco
        ) {
}
