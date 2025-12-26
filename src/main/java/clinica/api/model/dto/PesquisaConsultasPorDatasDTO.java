package clinica.api.model.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PesquisaConsultasPorDatasDTO(

        @NotNull
        LocalDateTime dataInicio,

        @NotNull
        LocalDateTime dataFim,

        @NotNull
        Long idUsuario




) {




}
