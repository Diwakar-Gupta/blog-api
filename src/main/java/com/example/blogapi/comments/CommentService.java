package com.example.blogapi.comments;

import com.example.blogapi.articles.ArticleEntity;
import com.example.blogapi.articles.ArticleService;
import com.example.blogapi.users.UserEntity;
import com.example.blogapi.users.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, ArticleService articleService, UserService userService) {
        this.commentRepository = commentRepository;
        this.articleService = articleService;
        this.userService = userService;
    }

    public List<CommentEntity> getCommentsByArticleSlug(String articleSlug) {
        ArticleEntity article = this.articleService.getArticleBySlug(articleSlug);
        return this.commentRepository.getByArticle(article);
    }

    public CommentEntity createCommentForArticleSlug(String articleSlug, Integer authorId, String title, String body) {
        ArticleEntity article = this.articleService.getArticleBySlug(articleSlug);
        UserEntity author = this.userService.getUserById(authorId);

        CommentEntity comment = new CommentEntity();
        comment.setArticle(article);
        comment.setAuthor(author);
        comment.setTitle(title);
        comment.setBody(body);

        return this.commentRepository.save(comment);
    }

    public CommentEntity getCommentById(Integer id){
        return this.commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }

    public void deleteCommentById(Integer commentId) {
        // TODO comment authod should be authenticated user
        var comment = getCommentById(commentId);
        this.commentRepository.delete(comment);
    }

    public static class CommentNotFoundException extends IllegalArgumentException {
        public CommentNotFoundException(Integer id) {
            super("Comment with id: "+id+" not found");
        }
    }
}
