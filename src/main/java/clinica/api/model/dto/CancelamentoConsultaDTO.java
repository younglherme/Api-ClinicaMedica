package clinica.api.model.dto;

import clinica.api.model.enums.MotivoCancelamento;
import jakarta.validation.constraints.NotNull;

public record CancelamentoConsultaDTO(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo) {
}
