package clinica.api.model.dto;

import clinica.api.model.enums.Especialidade;
import jakarta.validation.constraints.NotNull;


public record AtualizacaoMedicoDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        String email,
        Especialidade especialidade,
        EnderecoDTO endereco) {
}
