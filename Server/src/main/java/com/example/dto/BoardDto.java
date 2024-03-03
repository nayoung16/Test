package com.example.dto;

import com.example.domain.post.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private int id;
    private String title;
    private String content;
    private String writer;
    private String writerPhotoUrl;
    private LocalDateTime createdAt;

    public static BoardDto toDto(Board board) {
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getName(),
                board.getUser().getPhotourl(),
                board.getCreatedAt());
    }

}
