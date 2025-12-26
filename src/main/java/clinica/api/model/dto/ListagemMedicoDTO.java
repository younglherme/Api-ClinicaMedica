package clinica.api.model.dto;

import clinica.api.model.Endereco;
import clinica.api.model.enums.Especialidade;
import clinica.api.model.Medico;

public record ListagemMedicoDTO(Long id, String nome, String email, String crm, Especialidade especialidade, Endereco endereco, String telefone) {

    public ListagemMedicoDTO(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco(), medico.getTelefone());
    }

}
