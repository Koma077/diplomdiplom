package ru.skypro.homework.mapping;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CommentMapperTest {

    private final CommentMapper commentMapper = new CommentMapper();

    @Test
    void testEntityToCommentDto() {
        User author = new User();
        author.setId(1);
        author.setImage(new Image());
        author.setFirstName("Xxx Xxx");
        author.setLastName("Xxx");

        LocalDateTime createdAt = LocalDateTime.now();

        Comment commentEntity = new Comment();
        commentEntity.setAuthor(author);
        commentEntity.setCreatedAt(createdAt);
        commentEntity.setPk(1);
        commentEntity.setText("This is a comment");

        CommentDto expectedComment = new CommentDto(1, "/users/image/1", "Xxx Xxx",
                createdAt.toInstant(ZoneOffset.ofHours(5)).toEpochMilli(), 1, "This is a comment");

        CommentDto result = commentMapper.entityToCommentDto(commentEntity);

        assertEquals(expectedComment, result);
    }

    @Test
    void testCreateCommentToEntity() {
        CreateCommentDto createComment = new CreateCommentDto();
        createComment.setText("New comment");

        Ads ad = new Ads();
        User author = new User();

        Comment result = commentMapper.createCommentToEntity(createComment, ad, author);

        assertEquals(createComment.getText(), result.getText());
        assertEquals(ad, result.getAd());
        assertEquals(author, result.getAuthor());
    }

}