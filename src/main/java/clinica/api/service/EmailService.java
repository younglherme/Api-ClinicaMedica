package clinica.api.service;

import clinica.api.config.MailConfig;
import clinica.api.model.Consulta;
import clinica.api.model.Medico;
import clinica.api.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailConfig mailConfig;

    public void enviarEmailAgendamento(Consulta consulta) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Configurar destinatários
            helper.setFrom(mailConfig.getDefaultFromAddress());
            helper.setTo(consulta.getPaciente().getEmail());
            helper.setSubject("Confirmação de Agendamento - Clínica Médica");

            // Preparar dados para o template
            Map<String, Object> variables = new HashMap<>();
            variables.put("paciente", consulta.getPaciente());
            variables.put("medico", consulta.getMedico());
            variables.put("consulta", consulta);
            variables.put("dataFormatada", consulta.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            variables.put("horaFormatada", consulta.getData().format(DateTimeFormatter.ofPattern("HH:mm")));

            Context context = new Context();
            context.setVariables(variables);

            String htmlContent = templateEngine.process("email-agendamento", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("Email de agendamento enviado para: " + consulta.getPaciente().getEmail());

        } catch (MessagingException e) {
            System.err.println("Erro ao enviar email de agendamento: " + e.getMessage());
        }
    }

    public void enviarEmailCancelamento(Consulta consulta, String motivo) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(mailConfig.getDefaultFromAddress());
            helper.setTo(consulta.getPaciente().getEmail());
            helper.setSubject("Cancelamento de Consulta - Clínica Médica");

            Map<String, Object> variables = new HashMap<>();
            variables.put("paciente", consulta.getPaciente());
            variables.put("medico", consulta.getMedico());
            variables.put("consulta", consulta);
            variables.put("motivo", motivo);
            variables.put("dataFormatada", consulta.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            variables.put("horaFormatada", consulta.getData().format(DateTimeFormatter.ofPattern("HH:mm")));

            Context context = new Context();
            context.setVariables(variables);

            String htmlContent = templateEngine.process("email-cancelamento", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("Email de cancelamento enviado para: " + consulta.getPaciente().getEmail());

        } catch (MessagingException e) {
            System.err.println("Erro ao enviar email de cancelamento: " + e.getMessage());
        }
    }

    public void enviarEmailLembrete(Consulta consulta) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(mailConfig.getDefaultFromAddress());
            helper.setTo(consulta.getPaciente().getEmail());
            helper.setSubject("Lembrete de Consulta - Clínica Médica");

            Map<String, Object> variables = new HashMap<>();
            variables.put("paciente", consulta.getPaciente());
            variables.put("medico", consulta.getMedico());
            variables.put("consulta", consulta);
            variables.put("dataFormatada", consulta.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            variables.put("horaFormatada", consulta.getData().format(DateTimeFormatter.ofPattern("HH:mm")));

            Context context = new Context();
            context.setVariables(variables);

            String htmlContent = templateEngine.process("email-lembrete", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("Email de lembrete enviado para: " + consulta.getPaciente().getEmail());

        } catch (MessagingException e) {
            System.err.println("Erro ao enviar email de lembrete: " + e.getMessage());
        }
    }

    // Método para envio de email simples (sem template)
    public void enviarEmailSimples(String destinatario, String assunto, String corpo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailConfig.getDefaultFromAddress());
            message.setTo(destinatario);
            message.setSubject(assunto);
            message.setText(corpo);
            
            mailSender.send(message);
            System.out.println("Email simples enviado para: " + destinatario);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email simples: " + e.getMessage());
        }
    }
}
