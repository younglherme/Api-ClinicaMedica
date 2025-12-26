package clinica.api.model.dto;

import clinica.api.model.enums.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroMedicoDTO(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String crm,
        @NotNull
        Especialidade especialidade,

        @NotNull
        String login,

        @NotNull
        String senha,

        String telefone,

        @NotNull @Valid EnderecoDTO endereco
)
{}
