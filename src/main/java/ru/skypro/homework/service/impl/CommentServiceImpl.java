package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exception.FindNoEntityException;
import ru.skypro.homework.mapping.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final UserService userService;
    private final AdsService adsService;
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;

    @Override
    public ResponseWrapperCommentDto getComments(int id) {
        List<CommentDto> result = new LinkedList<>();
        commentRepository.findAllByAd_Pk(id).forEach(entity -> result.add(mapper.entityToCommentDto(entity)));
        return new ResponseWrapperCommentDto(result.size(), result);
    }

    @Override
    public CommentDto add(int id, CreateCommentDto comment, String name) {
        Comment entity = mapper.createCommentToEntity(comment, adsService.getEntity(id), userService.getEntity(name));
        return mapper.entityToCommentDto(commentRepository.save(entity));
    }

    @Override
    public void delete(int commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDto update(CommentDto comment, int commentId, String email) {
        Comment entity = getEntity(commentId);
        entity.setText(comment.getText() + "(отредактировал(а) " + userService.getEntity(email).getFirstName() +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(" dd MMMM yyyy в HH:mm:ss)")));
        return mapper.entityToCommentDto(commentRepository.save(entity));
    }

    @Override
    public Comment getEntity(int commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new FindNoEntityException("комментарий"));
    }
}
