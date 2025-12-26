package clinica.api.model.dto;

import clinica.api.model.Consulta;

import java.time.LocalDateTime;

public record ListagemConsultaDTO(Long id, String medico, String paciente, LocalDateTime data, String motivo_cancelamento, String observacoes, String telefone) {

    public ListagemConsultaDTO(Consulta consulta) {
        this(consulta.getId(),
            consulta.getMedico().getNome(),
            consulta.getPaciente().getNome(),
            consulta.getData(),
            consulta.getMotivoCancelamento() != null ? consulta.getMotivoCancelamento().toString() : null,
            consulta.getObservacoes(),
            consulta.getPaciente().getTelefone() != null ? consulta.getPaciente().getTelefone() : null);
    }

}