package com.example.dto;

import com.example.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int id;
    private String content;
    private String writer;
    private String writerPhotoUrl;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getName(),
                comment.getUser().getPhotourl()
        );
    }
}
