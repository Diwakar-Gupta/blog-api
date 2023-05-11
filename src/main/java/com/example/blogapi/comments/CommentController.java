package com.example.blogapi.comments;

import com.example.blogapi.articles.ArticleService;
import com.example.blogapi.comments.dto.CommentResponseDTO;
import com.example.blogapi.comments.dto.CommentResponseDTOWithAuthor;
import com.example.blogapi.comments.dto.CreateCommentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("")
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/articles/{articleSlug}/comments")
    ResponseEntity<List<CommentResponseDTOWithAuthor>> getComments(@PathVariable String articleSlug){
        List<CommentEntity> comments = this.commentService.getCommentsByArticleSlug(articleSlug);
        var commentsDTO = comments
                .stream()
                .map((c) -> this.modelMapper.map(c, CommentResponseDTOWithAuthor.class))
                .toList();
        return ResponseEntity.ok(commentsDTO);
    }

    @PostMapping("/articles/{articleSlug}/comments")
    ResponseEntity<CommentResponseDTOWithAuthor> createComment(@AuthenticationPrincipal Integer authId, @PathVariable String articleSlug, @RequestBody CreateCommentDTO commentDTO){
        CommentEntity comment = this.commentService.createCommentForArticleSlug(articleSlug, commentDTO.getAuthorId(), commentDTO.getTitle(), commentDTO.getBody());
        var createdCommentDTO = this.modelMapper.map(comment, CommentResponseDTOWithAuthor.class);
        return ResponseEntity.created(URI.create("/articles/"+articleSlug+"/comments/"+comment.getId())).body(createdCommentDTO);
    }

    @GetMapping("/comments/{commentId}")
    ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable Integer commentId){
        var comment = this.commentService.getCommentById(commentId);
        var commentDTO = this.modelMapper.map(comment, CommentResponseDTO.class);
        return ResponseEntity.ok(commentDTO);
    }

    @DeleteMapping("/comments/{commendId}")
    ResponseEntity<String> deleteCommentById(@AuthenticationPrincipal Integer userId, @PathVariable Integer commentId){
        this.commentService.deleteCommentById(commentId);
        return ResponseEntity.accepted().body("Comment deleted successfully");
    }

    @ExceptionHandler({
            ArticleService.ArticleNotFoundException.class,
            CommentService.CommentNotFoundException.class
    })
    ResponseEntity<String> handleArticleOrCommentNotFoundException(Exception e){
        return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}
