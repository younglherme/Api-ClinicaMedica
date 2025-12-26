package clinica.api.model.service;

import clinica.api.model.Consulta;
import clinica.api.model.Medico;
import clinica.api.model.dto.AgendamentoConsultaDTO;
import clinica.api.model.dto.CancelamentoConsultaDTO;
import clinica.api.model.dto.DetalhamentoConsultaDTO;
import clinica.api.model.exception.AgendamentoConflitanteException;
import clinica.api.model.exception.ValidacaoException;
import clinica.api.model.repository.ConsultaRepository;
import clinica.api.model.repository.MedicoRepository;
import clinica.api.model.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public DetalhamentoConsultaDTO agendarConsulta(AgendamentoConsultaDTO dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente nao existe!");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico não existe!");
        }

        // Verifica se já existe uma consulta para o mesmo médico e horário
        if (dados.idMedico() != null && consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data())) {
            throw new AgendamentoConflitanteException("Já existe uma consulta agendada para esse médico nesse horário!");
        }

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = medicoRepository.getReferenceById(dados.idMedico());

        var consulta = new Consulta(null, medico, paciente, dados.data(), dados.motivo_cancelamento(),"",dados.convenio());
        consultaRepository.save(consulta);

        return new DetalhamentoConsultaDTO(consulta);
    }

    public void cancelarConsulta(CancelamentoConsultaDTO dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }


//    private Medico escolherMedico(AgendamentoConsultaDTO dados) {
//        if (dados.idMedico() != null) {
//            return medicoRepository.getReferenceById(dados.idMedico());
//        }
//
//        if (dados.especialidade() == null) {
//            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
//        }
//
//        return medicoRepository.escolherMedicoAleatorio(dados.especialidade(), dados.data());
//    }



}
