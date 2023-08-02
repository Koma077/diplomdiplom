package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.FindNoEntityException;
import ru.skypro.homework.mapping.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class CommentServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private AdsService adsService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper mapper;

    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentServiceImpl(userService, adsService, commentRepository, mapper);
    }

    @Test
    void testGetComments() {
        int adId = 1;
        Comment commentEntity = new Comment();
        CommentDto comment = new CommentDto();
        when(commentRepository.findAllByAd_Pk(adId)).thenReturn(List.of(commentEntity));
        when(mapper.entityToCommentDto(commentEntity)).thenReturn(comment);

        ResponseWrapperCommentDto response = commentService.getComments(adId);

        assertEquals(1, response.getCount());
        assertEquals(List.of(comment), response.getResults());
        verify(commentRepository).findAllByAd_Pk(adId);
        verify(mapper).entityToCommentDto(commentEntity);
    }

    @Test
    void testAdd() {
        int adId = 1;
        String name = "Xxx Xxx";
        CreateCommentDto createComment = new CreateCommentDto();
        Comment commentEntity = new Comment();
        CommentDto comment = new CommentDto();
        when(adsService.getEntity(adId)).thenReturn(new Ads());
        when(userService.getEntity(name)).thenReturn(new User());
        when(mapper.createCommentToEntity(createComment, new Ads(), new User())).thenReturn(commentEntity);
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        when(mapper.entityToCommentDto(commentEntity)).thenReturn(comment);

        CommentDto result = commentService.add(adId, createComment, name);

        assertSame(comment, result);
        verify(adsService).getEntity(adId);
        verify(userService).getEntity(name);
        verify(mapper).createCommentToEntity(createComment, new Ads(), new User());
        verify(commentRepository).save(commentEntity);
        verify(mapper).entityToCommentDto(commentEntity);
    }

    @Test
    void testDelete() {
        int commentId = 1;

        commentService.delete(commentId);

        verify(commentRepository).deleteById(commentId);
    }


    @Test
    void testGetEntity() {
        int commentId = 1;
        Comment commentEntity = new Comment();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));

        Comment result = commentService.getEntity(commentId);

        assertSame(commentEntity, result);
        verify(commentRepository).findById(commentId);
    }

    @Test
    void testGetEntity_ThrowsFindNoEntityException() {
        int commentId = 1;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        assertThrows(FindNoEntityException.class, () -> commentService.getEntity(commentId));

        verify(commentRepository).findById(commentId);
    }
}