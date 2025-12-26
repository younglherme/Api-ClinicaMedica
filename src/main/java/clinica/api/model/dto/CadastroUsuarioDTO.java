package clinica.api.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastroUsuarioDTO(

        @NotBlank
        String login,
        @NotBlank
        String senha,
        @NotBlank
        boolean admin



) {
}
