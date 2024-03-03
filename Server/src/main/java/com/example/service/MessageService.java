package com.example.service;

import com.example.domain.message.Message;
import com.example.domain.message.MessageRepository;
import com.example.domain.user.User;
import com.example.domain.user.UserRepository;
import com.example.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageDto write(MessageDto messageDto) {
        User receiver = userRepository.findByEmail(messageDto.getReceiverEmail());
        User sender = userRepository.findByEmail(messageDto.getSenderEmail());

        Message message = new Message();
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setContent(messageDto.getContent());
        message.setRegDate(LocalDateTime.now());
        messageRepository.save(message);

        return MessageDto.toDto(message);
    }


    @Transactional(readOnly = true)
    public List<MessageDto> receivedMessage(User user) {
        // 받은 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        // 추후 JWT를 이용해서 재구현 예정
        List<Message> messages = messageRepository.findAllByReceiver(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(Message message : messages) {
            messageDtos.add(MessageDto.toDto(message));
        }
        return messageDtos;
    }


    @Transactional(readOnly = true)
    public List<MessageDto> sentMessage(User user) {
        // 보낸 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        // 추후 JWT를 이용해서 재구현 예정
        List<Message> messages = messageRepository.findAllBySender(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(Message message : messages) {
            messageDtos.add(MessageDto.toDto(message));
        }
        return messageDtos;
    }

    //두 유저간 주고받은 쪽지들 불러오기
    @Transactional(readOnly = true)
    public List<MessageDto> bothReceivedMessage(User sender, User receiver) {
        List<Message> senderSentMessages = messageRepository.findAllBySender(sender);
        List<Message> senderReceivedMessages = messageRepository.findAllByReceiver(sender);
        List<Message> receiverSentMessages = messageRepository.findAllBySender(receiver);
        List<Message> receiverReceivedMessages = messageRepository.findAllByReceiver(receiver);

        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : senderSentMessages) {
            if (receiverReceivedMessages.contains(message))
                messageDtos.add(MessageDto.toDto(message));
        }
        for (Message message : senderReceivedMessages) {
            if (receiverSentMessages.contains(message))
                messageDtos.add(MessageDto.toDto(message));
        }
        // regDate를 기준으로 시간순으로 정렬
        messageDtos.sort(Comparator.comparing(MessageDto::getRegDate));

        return messageDtos;
    }
}
