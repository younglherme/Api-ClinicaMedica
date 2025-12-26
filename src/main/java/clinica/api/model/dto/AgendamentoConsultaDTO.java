package clinica.api.model.dto;

import clinica.api.model.enums.Especialidade;
import clinica.api.model.enums.MotivoCancelamento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoConsultaDTO(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        MotivoCancelamento motivo_cancelamento,

        @NotNull
        String convenio

) {
}
