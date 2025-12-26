package clinica.api.service;

import clinica.api.model.Consulta;
import clinica.api.model.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoEmailService {

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private ConsultaRepository consultaRepository;

    // Executa todos os dias às 9:00 da manhã
    @Scheduled(cron = "0 0 9 * * ?")
    public void enviarLembretesDiarios() {
        try {
            System.out.println("Iniciando envio de lembretes automáticos...");
            
            // Buscar consultas para o próximo dia
            LocalDateTime amanha = LocalDateTime.now().plusDays(1);
            LocalDateTime inicioDia = amanha.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime fimDia = amanha.withHour(23).withMinute(59).withSecond(59);
            
            List<Consulta> consultasAmanha = consultaRepository.findByDataBetween(inicioDia, fimDia);
            
            int emailsEnviados = 0;
            for (Consulta consulta : consultasAmanha) {
                try {
                    emailService.enviarEmailLembrete(consulta);
                    emailsEnviados++;
                    System.out.println("Lembrete enviado para: " + consulta.getPaciente().getEmail());
                } catch (Exception e) {
                    System.err.println("Erro ao enviar lembrete para " + consulta.getPaciente().getEmail() + ": " + e.getMessage());
                }
            }
            
            System.out.println("Lembretes automáticos concluídos. Enviados: " + emailsEnviados + " de " + consultasAmanha.size());
            
        } catch (Exception e) {
            System.err.println("Erro no agendamento de lembretes: " + e.getMessage());
        }
    }

    // Executa todas as segundas-feiras às 8:00 da manhã
    @Scheduled(cron = "0 0 8 * * MON")
    public void enviarLembretesSemanais() {
        try {
            System.out.println("Iniciando envio de lembretes semanais...");
            
            // Buscar consultas para os próximos 7 dias
            LocalDateTime agora = LocalDateTime.now();
            LocalDateTime em7Dias = agora.plusDays(7);
            
            List<Consulta> consultasSemana = consultaRepository.findByDataBetween(agora, em7Dias);
            
            int emailsEnviados = 0;
            for (Consulta consulta : consultasSemana) {
                try {
                    emailService.enviarEmailLembrete(consulta);
                    emailsEnviados++;
                    System.out.println("Lembrete semanal enviado para: " + consulta.getPaciente().getEmail());
                } catch (Exception e) {
                    System.err.println("Erro ao enviar lembrete semanal para " + consulta.getPaciente().getEmail() + ": " + e.getMessage());
                }
            }
            
            System.out.println("Lembretes semanais concluídos. Enviados: " + emailsEnviados + " de " + consultasSemana.size());
            
        } catch (Exception e) {
            System.err.println("Erro no agendamento de lembretes semanais: " + e.getMessage());
        }
    }
}
