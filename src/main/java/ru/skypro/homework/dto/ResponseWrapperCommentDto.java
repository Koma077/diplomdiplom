package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@AllArgsConstructor
@Data
public class ResponseWrapperCommentDto {
    private int count;
    private List<CommentDto> results;
}
