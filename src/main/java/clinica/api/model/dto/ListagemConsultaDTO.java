package clinica.api.model.dto;

import clinica.api.model.Consulta;

import java.time.LocalDateTime;

public record ListagemConsultaDTO(Long id, String medico, String paciente, LocalDateTime data, String convenio, String motivo_cancelamento, String observacoes, String telefone, boolean cancelada) {

    public ListagemConsultaDTO(Consulta consulta) {
        this(consulta.getId(),
            consulta.getMedico().getNome(),
            consulta.getPaciente().getNome(),
            consulta.getData(),
            consulta.getConvenio(),
            consulta.getMotivoCancelamento() != null ? consulta.getMotivoCancelamento().toString() : null,
            consulta.getObservacoes(),
            consulta.getPaciente().getTelefone() != null ? consulta.getPaciente().getTelefone() : null,
            consulta.isCancelada());
    }

}