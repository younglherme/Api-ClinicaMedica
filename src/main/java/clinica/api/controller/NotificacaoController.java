package clinica.api.controller;

import clinica.api.model.Consulta;
import clinica.api.model.repository.ConsultaRepository;
import clinica.api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("notificacoes")
public class NotificacaoController {

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping("/lembrete/{idConsulta}")
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

    @PostMapping("/email-simples")
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

    @PostMapping("/lembretes-automaticos")
    public ResponseEntity<String> enviarLembretesAutomaticos() {
        try {
            // Buscar consultas para os próximos 2 dias
            LocalDateTime agora = LocalDateTime.now();
            LocalDateTime em2Dias = agora.plusDays(2);
            
            List<Consulta> consultas = consultaRepository.findByDataBetween(agora, em2Dias);
            
            int emailsEnviados = 0;
            for (Consulta consulta : consultas) {
                try {
                    emailService.enviarEmailLembrete(consulta);
                    emailsEnviados++;
                } catch (Exception e) {
                    System.err.println("Erro ao enviar lembrete para consulta " + consulta.getId() + ": " + e.getMessage());
                }
            }
            
            return ResponseEntity.ok("Lembretes automáticos enviados: " + emailsEnviados + " de " + consultas.size() + " consultas");
        } catch (Exception e) {
            System.err.println("Erro ao enviar lembretes automáticos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar lembretes automáticos: " + e.getMessage());
        }
    }

    @PostMapping("/teste-email")
    public ResponseEntity<String> testarEmail(@RequestParam String destinatario) {
        try {
            String assunto = "Teste de Email - Clínica Médica";
            String corpo = "Este é um email de teste para verificar se o sistema de notificações está funcionando corretamente.\n\n" +
                         "Data/Hora do teste: " + LocalDateTime.now() + "\n\n" +
                         "Se você recebeu este email, o sistema está funcionando perfeitamente!";
            
            emailService.enviarEmailSimples(destinatario, assunto, corpo);
            return ResponseEntity.ok("Email de teste enviado com sucesso para: " + destinatario);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email de teste: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar email de teste: " + e.getMessage());
        }
    }
}
