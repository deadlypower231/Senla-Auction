package eu.senla.auction.chat.util.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Properties;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@ComponentScan("eu.senla.auction.chat.util.mail")
@PropertySource("classpath:application.properties")
public class MailConfig {

    @Value("${mail.debug}")
    private String mailDebugProperty;

    @Value("${mail.smtp.auth}")
    private String mailSmtpAuthProperty;

    @Value("${mail.smtp.starttls.enable}")
    private String mailSmtpStarttlsEnableProperty;

    @Value("${mail.transport.protocol}")
    private String mailTransportProtocolProperty;

    @Value("${mail.sender.set.host}")
    private String mailSenderSetHost;

    @Value("${mail.sender.set.port}")
    private String mailSenderSetPort;

    @Value("${mail.sender.set.username}")
    private String mailSenderSetUsername;

    @Value("${mail.sender.set.password}")
    private String mailSenderSetPassword;

    @Value("${input.encoding}")
    private String velocityInputEncodingProperty;

    @Value("${output.encoding}")
    private String velocityOutputEncodingProperty;

    @Value("${resource.loader}")
    private String velocityResourceLoaderProperty;

    @Value("${class.resource.loader.class}")
    private String velocityClassResourceLoaderClassProperty;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSenderSetHost);
        mailSender.setPort(Integer.parseInt(mailSenderSetPort));
        mailSender.setUsername(mailSenderSetUsername);
        mailSender.setPassword(mailSenderSetPassword);
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnableProperty);
        javaMailProperties.put("mail.smtp.auth", mailSmtpAuthProperty);
        javaMailProperties.put("mail.transport.protocol", mailTransportProtocolProperty);
        javaMailProperties.put("mail.debug", mailDebugProperty);
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean
    public VelocityEngine velocityEngine() {
        Properties velocityProperties = new Properties();
        velocityProperties.setProperty("input.encoding", velocityInputEncodingProperty);
        velocityProperties.setProperty("output.encoding", velocityOutputEncodingProperty);
        velocityProperties.setProperty("resource.loader", velocityResourceLoaderProperty);
        velocityProperties.setProperty("class.resource.loader.class", velocityClassResourceLoaderClassProperty);
        return new VelocityEngine(velocityProperties);
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }



}
