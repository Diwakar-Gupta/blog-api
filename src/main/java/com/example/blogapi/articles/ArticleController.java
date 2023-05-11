package com.example.blogapi.articles;

import com.example.blogapi.articles.dto.ArticleResponseDTO;
import com.example.blogapi.articles.dto.CreateArticleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public ArticleController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<ArticleResponseDTO>> getArticles(
            @RequestParam(value="author", required = false) Integer author,
            @RequestParam(value="page", defaultValue = "0") String pageString,
            @RequestParam(value="size", defaultValue = "2") String sizeString
            ){
        Integer page = Integer.parseInt(pageString);
        Integer size = Integer.parseInt(sizeString);

        Page<ArticleEntity> result;
        if(author == null){
            result = this.articleService
                    .getArticles(page, size);
        }else{
            result = this.articleService.getArticlesByAuthor(author, page, size);
        }

        var articles = result
                .stream()
                .map((a) -> this.modelMapper.map(a, ArticleResponseDTO.class))
                .toList();
        return ResponseEntity.ok(articles);
    }

    @PostMapping("")
    public ResponseEntity<ArticleResponseDTO> createArticle(@RequestBody CreateArticleDTO articleDTO){
        var article = this.articleService.createArticle(
                articleDTO.getAuthorId(),
                articleDTO.getSlug(),
                articleDTO.getTitle(),
                articleDTO.getSubTitle(),
                articleDTO.getBody()
        );
        var createdArticleDTO = this.modelMapper.map(article, ArticleResponseDTO.class);
        return ResponseEntity.created(URI.create("/articles/" + article.getSlug())).body(createdArticleDTO);
    }

    @PatchMapping("/{slug}")
    public ResponseEntity<ArticleResponseDTO> updateArticle(@AuthenticationPrincipal Integer authId, @PathVariable String slug, @RequestBody CreateArticleDTO articleDTO){
        ArticleEntity article = this.articleService.updateArticle(
                slug,
                articleDTO.getSlug(),
                articleDTO.getTitle(),
                articleDTO.getSubTitle(),
                articleDTO.getBody()
        );
        var updatedArticleDTO = this.modelMapper.map(article, ArticleResponseDTO.class);
        return ResponseEntity.created(URI.create("/articles/" + article.getSlug())).body(updatedArticleDTO);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponseDTO> getArticleBySlug(@PathVariable String slug){
        var article = this.articleService.getArticleBySlug(slug);
        var articleDTO = this.modelMapper.map(article, ArticleResponseDTO.class);
        return ResponseEntity.ok(articleDTO);
    }

    @ExceptionHandler(ArticleService.ArticleNotFoundException.class)
    ResponseEntity<String> handleArticleNotFoundException(ArticleService.ArticleNotFoundException e){
        return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}
