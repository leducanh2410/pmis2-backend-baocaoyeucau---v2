package com.pmis.services.service;

import com.pmis.constant.Constant;
import com.pmis.util.Util;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class EmailService {
//    @Autowired
//    private JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Integer sendOtpEmail(String to, String otp) {
        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(to);
//            message.setSubject(new String(Constant.MAIL_SUBJECT.getBytes(), StandardCharsets.UTF_8));
//            message.setText(new String(Constant.MAIL_BODY.getBytes(), StandardCharsets.UTF_8) + otp);
//
//            javaMailSender.send(message);

            String sql = "SELECT [FROM_ADDRESS], [IP_MAIL_SERVER], [PORT_MAIL_SERVER], [PASS_EMAIL] FROM S_MAIL_ACCOUNT WHERE ID = 'EMAIL_EMS'";
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, new MapSqlParameterSource());
            if (result.isEmpty()) {
                return 0;
            }
            String ip = result.get("IP_MAIL_SERVER").toString();
            String from = result.get("FROM_ADDRESS").toString();
            Integer port = (Integer) result.get("PORT_MAIL_SERVER");
            String pwd = Util.decrypt(result.get("PASS_EMAIL").toString());

            Properties prop = new Properties();
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.host", ip);
            prop.put("mail.smtp.port", port);

            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, pwd);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(Constant.MAIL_SUBJECT, "utf-8");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(Constant.MAIL_BODY + otp, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String findEmailByUserId(String userId) {
        try {
            String sql = "SELECT EMAIL FROM Q_USER WHERE USERID = :userId";
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, new MapSqlParameterSource(params));
            return result.get("EMAIL").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
