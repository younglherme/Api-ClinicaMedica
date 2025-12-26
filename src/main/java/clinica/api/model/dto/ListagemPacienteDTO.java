package clinica.api.model.dto;

import clinica.api.model.Endereco;
import clinica.api.model.Paciente;

public record ListagemPacienteDTO(Long id, String nome, String email, String cpf, Endereco endereco, String telefone) {

    public ListagemPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getEndereco(), paciente.getTelefone());
    }

}
