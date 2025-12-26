package clinica.api.controller;


import clinica.api.model.Consulta;
import clinica.api.model.Paciente;
import clinica.api.model.dto.*;
import clinica.api.model.repository.ConsultaRepository;
import clinica.api.model.service.AgendaDeConsultas;
import clinica.api.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private EmailService emailService;


    @PostMapping
    @Transactional
    public ResponseEntity agendarConsulta(@RequestBody @Valid AgendamentoConsultaDTO dados) {
        var dto = agenda.agendarConsulta(dados);
        
        // Enviar email de confirmação
        try {
            var consulta = consultaRepository.findById(dto.id()).orElse(null);
            if (consulta != null) {
                emailService.enviarEmailAgendamento(consulta);
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar email de agendamento: " + e.getMessage());
        }
        
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/pesquisaMinhasConsultas")
    @Transactional
    public ResponseEntity<List<ListagemConsultaDTO>> pesquisaConsultasIndividuais(@RequestBody @Valid PesquisaConsultasPorDatasDTO dados) {
        List<Consulta> consultas;
        if(dados.idUsuario() == 0){
            consultas = consultaRepository.findByDataBetween(dados.dataInicio(), dados.dataFim());
        }else{
            consultas = consultaRepository.findByPacienteIdAndDataBetween(dados.dataInicio(), dados.dataFim(),dados.idUsuario());
        }
        if(consultas.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<ListagemConsultaDTO> listagemConsultaDTOs = consultas.stream()
                .map(consulta -> new ListagemConsultaDTO(consulta)) // Assuming you have a ListagemConsultaDTO constructor that takes a Consulta object
                .collect(Collectors.toList());

        return ResponseEntity.ok(listagemConsultaDTOs);
    }

    @PutMapping
    @Transactional
    public void cancelarConsulta(@RequestBody @Valid CancelamentoConsultaDTO dados) {
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
        consultaRepository.save(consulta);
        
        // Enviar email de cancelamento
        try {
            emailService.enviarEmailCancelamento(consulta, dados.motivo().toString());
        } catch (Exception e) {
            System.err.println("Erro ao enviar email de cancelamento: " + e.getMessage());
        }
    }

    @GetMapping
    public Page<ListagemConsultaDTO> listarConsulta(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        return consultaRepository.findAll(paginacao).map(ListagemConsultaDTO::new);
    }

    @GetMapping("/individual/{idBusca}")
    public Page<ListagemConsultaDTO> listarConsultasPorPacienteEConsulta(
            @PageableDefault(size = 30, sort = {"id"}) Pageable paginacao,
            @PathVariable Long idBusca) {

        var consulta = consultaRepository.getReferenceById(idBusca);
        return consultaRepository.findByPacienteId(consulta.getPaciente().getId(), paginacao)
                .map(ListagemConsultaDTO::new);
    }

    @GetMapping("/individualPaciente/{idBusca}")
    public Page<ListagemConsultaDTO> listarConsultasPorPaciente(
            @PageableDefault(size = 30, sort = {"id"}) Pageable paginacao,
            @PathVariable Long idBusca) {

        return consultaRepository.findByPacienteId(idBusca, paginacao)
                .map(ListagemConsultaDTO::new);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> getConsulta(@PathVariable Long id) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consulta consulta = consultaOptional.get();
            ConsultaDTO consultaDTO = new ConsultaDTO(consulta);
            return ResponseEntity.ok(consultaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/inserirObservacao")
    @Transactional
    public void inserirObservacao(@RequestBody @Valid ObservacaoConsultaDTO dados) {
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.inserirObservacao(dados.observacao());
        consultaRepository.save(consulta);
    }

    @PostMapping("/enviarLembrete/{idConsulta}")
    @Transactional
    public ResponseEntity<String> enviarLembrete(@PathVariable Long idConsulta) {
        try {
            var consulta = consultaRepository.findById(idConsulta);
            if (consulta.isPresent()) {
                emailService.enviarEmailLembrete(consulta.get());
                return ResponseEntity.ok("Lembrete enviado com sucesso!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar lembrete: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar lembrete: " + e.getMessage());
        }
    }

    @PostMapping("/enviarEmailSimples")
    public ResponseEntity<String> enviarEmailSimples(@RequestParam String destinatario, 
                                                   @RequestParam String assunto, 
                                                   @RequestParam String corpo) {
        try {
            emailService.enviarEmailSimples(destinatario, assunto, corpo);
            return ResponseEntity.ok("Email enviado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao enviar email simples: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar email: " + e.getMessage());
        }
    }

}
