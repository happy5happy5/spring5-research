package com.demo1010.researcherv2.service;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SMSService {

    private final DefaultMessageService messageService;

//    @Value("${sms.api-key}")
//    private String API_KEY;
//
//    @Value("${sms.api-secret-key}")
//    private String API_SECRET_KEY;

    public SMSService() {
        this.messageService = NurigoApp.INSTANCE.initialize("NCSFMXD4IW7RM3JR", "GJPKUB9QRL9F9GSQWAQGL3UXUWX4VGUV", "https://api.coolsms.co.kr");
    }


    public SingleMessageSentResponse sendOne(String from, String to, String text) {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        log.info("response: {}", response);

        return response;
    }


    /**
     * 여러 메시지 발송 예제
     * 한 번 실행으로 최대 10,000건 까지의 메시지가 발송 가능합니다.
     */
    public MultipleDetailMessageSentResponse sendMany(List<String> froms, List<String> tos, List<String> texts) {
        ArrayList<Message> messageList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom(froms.get(i));
            message.setTo(tos.get(i));
            message.setText(texts.get(i));
            messageList.add(message);
        }

        try {
           MultipleDetailMessageSentResponse response = this.messageService.send(messageList, false, true);
            return response;
        } catch (Exception exception) {
            log.error("exception: ", exception);
        }
        return null;
    }


    public MultipleDetailMessageSentResponse sendScheduledMessages(List<String> froms, List<String> tos, List<String> texts, String scheduledDate) {
        ArrayList<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom(froms.get(i));
            message.setTo(tos.get(i));
            message.setText(texts.get(i));
            messageList.add(message);
        }

        try {
            // 과거 시간으로 예약 발송을 진행할 경우 즉시 발송처리 됩니다.     2022-11-26 00:00:00
            LocalDateTime localDateTime = LocalDateTime.parse(scheduledDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(localDateTime);
            Instant instant = localDateTime.toInstant(zoneOffset);
            // 단일 발송도 지원하여 ArrayList<Message> 객체가 아닌 Message 단일 객체만 넣어도 동작합니다!
            MultipleDetailMessageSentResponse response = this.messageService.send(messageList, instant);
            // 중복 수신번호를 허용하고 싶으실 경우 위 코드 대신 아래코드로 대체해 사용해보세요!
            //MultipleDetailMessageSentResponse response = this.messageService.send(messageList, instant, true);
            return response;
        } catch (NurigoMessageNotReceivedException exception) {
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

}
