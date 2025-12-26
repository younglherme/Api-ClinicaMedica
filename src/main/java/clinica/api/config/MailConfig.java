package clinica.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Value("${spring.mail.from:noreply@clinica.com}")
    private String defaultFromAddress;

    public String getDefaultFromAddress() {
        return defaultFromAddress;
    }
}

