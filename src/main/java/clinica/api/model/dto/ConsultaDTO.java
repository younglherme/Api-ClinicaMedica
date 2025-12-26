package clinica.api.model.dto;

import clinica.api.model.Consulta;
import clinica.api.model.enums.MotivoCancelamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDTO {
    private Long id;
    private String medico;
    private String paciente;
    private LocalDateTime data;
    private MotivoCancelamento motivoCancelamento;
    private String observacoes;

    public ConsultaDTO(Consulta consulta) {
        this.id = consulta.getId();
        this.medico = consulta.getMedico().getId()+ " - " + consulta.getMedico().getNome();
        this.paciente = consulta.getPaciente().getId() + " - " + consulta.getPaciente().getNome();
        this.data = consulta.getData();
        this.motivoCancelamento = consulta.getMotivoCancelamento();
        this.observacoes = consulta.getObservacoes();
    }


}
