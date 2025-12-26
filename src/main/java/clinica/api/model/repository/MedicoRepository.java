package clinica.api.model.repository;

import clinica.api.model.enums.Especialidade;
import clinica.api.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByNome(String nome);  // Busca um médico com o nome
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            select m from Medico m
            where
            m.ativo = true
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.data = :data
                and
                c.motivoCancelamento is null
            )
            order by rand()
            limit 1
        """)
    Medico escolherMedicoAleatorio(Especialidade especialidade, LocalDateTime data);

    Page<Medico> findAll(Pageable paginacao);

    Optional<Medico> findByCrm(String crm);



}
