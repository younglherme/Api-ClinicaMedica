package clinica.api.model.dto;

import jakarta.validation.constraints.NotNull;


public record AtualizacaoPacienteDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        String email,
        String cpf,
        EnderecoDTO endereco) {
}
