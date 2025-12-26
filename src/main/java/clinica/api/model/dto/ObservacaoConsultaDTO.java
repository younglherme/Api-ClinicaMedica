package clinica.api.model.dto;

import jakarta.validation.constraints.NotNull;

public record ObservacaoConsultaDTO(
        @NotNull
        Long idConsulta,

        @NotNull
        String observacao

) {
}
