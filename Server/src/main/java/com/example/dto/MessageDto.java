package com.example.dto;

import com.example.domain.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String content;
    private String senderEmail;
    private String receiverEmail;
    private LocalDateTime regDate;

    public static MessageDto toDto(Message message) {
        return new MessageDto(
                message.getContent(),
                message.getSender().getEmail(),
                message.getReceiver().getEmail(),
                message.getRegDate()
        );
    }
}
