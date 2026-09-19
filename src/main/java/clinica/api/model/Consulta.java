package clinica.api.model;

import clinica.api.model.enums.MotivoCancelamento;
import clinica.api.model.converter.MotivoCancelamentoConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime data;

    @Column(name = "motivo_cancelamento")
    @Convert(converter = MotivoCancelamentoConverter.class)
    private MotivoCancelamento motivoCancelamento;

    @Column(name = "observacoes_medicas")
    private String observacoes;

    @Column(name = "convenio")
    private String convenio;

    @Column(name = "cancelada", nullable = false)
    private boolean cancelada = false;

    public void cancelar(MotivoCancelamento motivo) {
        this.motivoCancelamento = motivo;
        this.cancelada = true;
    }

    public void inserirObservacao(String observacoes) {
        this.observacoes = observacoes;
    }

}
