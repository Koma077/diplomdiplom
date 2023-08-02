package ru.skypro.homework.controller;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getComments(@PathVariable int id) {
        return ResponseEntity.ok(commentService.getComments(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable int id, @RequestBody CreateCommentDto comment,
                                                 Authentication authentication) {
        return ResponseEntity.ok(commentService.add(id, comment, authentication.getName()));
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("@commentServiceImpl.getEntity(#commentId).author.email.equals(#auth.name) " +
            "or hasAuthority('DELETE_ANY_COMMENT')")
    public ResponseEntity<?> deleteComment(@PathVariable int adId, @PathVariable int commentId, Authentication auth) {
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("@commentServiceImpl.getEntity(#commentId).author.email.equals(#auth.name) " +
            "or hasAuthority('UPDATE_ANY_COMMENT')")
    public ResponseEntity<CommentDto> updateComment(@PathVariable int commentId, @RequestBody CommentDto newComment,
                                                    Authentication auth, @PathVariable String adId) {
        return ResponseEntity.ok(commentService.update(newComment, commentId, auth.getName()));
    }
}
