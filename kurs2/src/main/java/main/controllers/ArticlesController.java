package main.controllers;

import main.dto.ArticlesDto;
import main.entity.Articles;
import main.exception.KursNotFoundException;
import main.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ArticlesController
{
    private ArticlesService articlesService;

    @PostMapping("/articles/add")
    public ResponseEntity<Articles> addArticle(@RequestBody ArticlesDto articleDto)
    {
        return new ResponseEntity<>(articlesService.add(articleDto), HttpStatus.OK);
    }

    @GetMapping("/articles/show")
    public ResponseEntity<List<Articles>> showArticles()
    {
        return new ResponseEntity<>(articlesService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/articles/show/id/{id}")
    public ResponseEntity<Articles> showArticleById(@PathVariable Long id)
    {
        try
        {
            return new ResponseEntity<>(articlesService.findById(id), HttpStatus.OK);
        }
        catch (KursNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/articles/show/name/{name}")
    public ResponseEntity<List<Articles>> showArticlesByName(@PathVariable String name)
    {
        return new ResponseEntity<>(articlesService.findByName(name), HttpStatus.OK);
    }

    @PostMapping("/articles/change/{id}")
    public ResponseEntity<String> changeArticleName(@PathVariable Long id, @RequestParam String name)
    {
        articlesService.changeName(id, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/articles/remove/{id}")
    public ResponseEntity<String> removeArticleById(@PathVariable Long id)
    {
        try
        {
            articlesService.removeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (KursNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Autowired
    public void setArticlesService(ArticlesService ArticlesService)
    {
        this.articlesService = ArticlesService;
    }
}
