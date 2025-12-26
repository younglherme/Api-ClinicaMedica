package clinica.api.controller;

import clinica.api.model.*;
import clinica.api.model.dto.AtualizacaoPacienteDTO;
import clinica.api.model.dto.CadastroPacienteDTO;
import clinica.api.model.dto.ListagemPacienteDTO;
import clinica.api.model.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public void cadastrarPaciente(@RequestBody @Valid CadastroPacienteDTO dados) {
        Paciente paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
    }

    @GetMapping
    public Page<ListagemPacienteDTO> listarPaciente(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return pacienteRepository.findAllByAtivoTrue(paginacao).map(ListagemPacienteDTO::new);
    }

    @PutMapping
    @Transactional
    public void atualizarPaciente(@RequestBody @Valid AtualizacaoPacienteDTO dados) {
        var paciente = pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        pacienteRepository.save(paciente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluirPaciente(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
        pacienteRepository.save(paciente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getMedicoById(@PathVariable Long id) {
        try {
            var paciente = pacienteRepository.findById(id);
            if (paciente.isPresent()) {
                return ResponseEntity.ok().body(paciente.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Paciente> getPacienteByNome(@PathVariable String nome) {
        try {
            Optional<Paciente> paciente = pacienteRepository.findByNome(nome);

            if (paciente.isPresent()) {
                return ResponseEntity.ok().body(paciente.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Paciente> getPacienteByCpf(@PathVariable String cpf) {
        try {
            Optional<Paciente> paciente = pacienteRepository.findByCpf(cpf);

            if (paciente.isPresent()) {
                return ResponseEntity.ok().body(paciente.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
