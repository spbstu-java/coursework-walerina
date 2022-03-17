package main.service;

import main.dto.ArticlesDto;
import main.entity.Articles;

import java.util.List;

public interface ArticlesService
{
    Articles add(ArticlesDto articleDto);
    List<Articles> findAll();
    Articles findById(Long id);
    List<Articles> findByName(String name);
    void changeName(Long id, String name);
    void removeById(Long id);
}
