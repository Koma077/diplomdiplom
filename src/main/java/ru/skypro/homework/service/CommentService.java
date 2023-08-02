package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Comment;


public interface CommentService {
    ResponseWrapperCommentDto getComments(int id);

    CommentDto add(int id, CreateCommentDto comment, String name);

    void delete(int commentId);

    CommentDto update(CommentDto newComment, int commentId, String email);

    Comment getEntity(int commentId);
}
