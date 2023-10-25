package com.demo1010.researcherv2.service;

import com.amazonaws.services.simpleemail.model.*;
import com.demo1010.researcherv2.config.MessageConfig;
import com.demo1010.researcherv2.entity.Code;
import com.demo1010.researcherv2.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {


    private MessageConfig messageConfig;
    private final CodeRepository codeRepository;

    @Autowired
    public MessageServiceImpl(MessageConfig messageConfig, CodeRepository codeRepository) {
        this.messageConfig = messageConfig;
        this.codeRepository = codeRepository;
    }

    @Override
    public void sendEmail(String to, String subject, String content) {
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(content)))
                        .withSubject(new Content().withCharset("UTF-8").withData(subject)))
                .withSource("zombil8731@1day2days.com")
                .withConfigurationSetName("my-first-configuration-set");
        SendEmailResult result = messageConfig.amazonSimpleEmailService().sendEmail(request);
        log.info("Email sent to: " + to + " with result: " + result.getMessageId());
    }

    @Override
    public SingleMessageSentResponse sendPhone(String from, String to, String text) {
        net.nurigo.sdk.message.model.Message message = new net.nurigo.sdk.message.model.Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);

        SingleMessageSentResponse response = messageConfig.defaultMessageService().sendOne(new SingleMessageSendingRequest(message));
        log.info("response: {}", response);
        return response;
    }

    @Override
    public void sendCodeToEmail(String email) {
        Code code = new Code();
        code.setEp(email);
        code.defaultSetCode();
        code.setEmail(true);
        if(codeRepository.findByEp(email) != null) {
            codeRepository.delete(codeRepository.findByEp(email));
        }
        codeRepository.save(code);
        String subject = "1day2days.com 인증 코드";
        String content = "<h1>1day2days.com 인증 코드</h1><br><h2>인증 코드: " + code.getCode() + "</h2>";
        sendEmail(email, subject, content);
    }

    @Override
    public void sendCodeToPhone(String phone) {
        Code code = new Code();
        code.setEp(phone);
        code.defaultSetCode();
        code.setPhone(true);
        if(codeRepository.findByEp(phone) != null) {
            codeRepository.delete(codeRepository.findByEp(phone));
        }
        codeRepository.save(code);
        String text = "1day2days.com 인증 코드: " + code.getCode();
        sendPhone("01090281679", phone, text);
    }

    @Override
    public boolean checkCode(String ep, String code) {
        Code codeEntity = codeRepository.findByEp(ep);
        if(codeEntity == null) {
            return false;
        }
        if(codeEntity.getCode().equals(code)) {
            codeEntity.setVerified(true);
            codeRepository.save(codeEntity);
            return true;
        }
        return false;
    }


}
