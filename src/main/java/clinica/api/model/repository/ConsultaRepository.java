package clinica.api.model.repository;

import clinica.api.model.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
    @Query("""
    SELECT c FROM Consulta c 
    INNER JOIN Medico m ON m.idusuario = :idUsuario AND c.medico.id = m.id
    WHERE c.data BETWEEN :primeiroHorario AND :ultimoHorario
""")
    List<Consulta> findByPacienteIdAndDataBetween(LocalDateTime primeiroHorario, LocalDateTime ultimoHorario,long idUsuario);

    @Query("""
    SELECT c FROM Consulta c 
    INNER JOIN Medico m ON c.medico.id = m.id
    WHERE c.data BETWEEN :primeiroHorario AND :ultimoHorario
""")
    List<Consulta> findByDataBetween(LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);

    boolean existsByMedicoIdAndData(Long medicoId, LocalDateTime data);

    Page<Consulta> findByPacienteId(Long pacienteId, Pageable pageable);
}
