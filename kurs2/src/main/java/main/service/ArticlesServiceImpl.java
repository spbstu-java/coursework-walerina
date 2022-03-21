package main.service;

import main.dto.ArticlesDto;
import main.entity.Articles;
import main.exception.KursNotFoundException;
import main.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ArticlesServiceImpl implements ArticlesService
{
    private ArticlesRepository articlesRepository;
    private OperationsService operationsService;

    public Articles add(ArticlesDto articleDto)
    {
        Articles article = new Articles();
        article.setName(articleDto.getName());
        return articlesRepository.save(article);
    }

    public List<Articles> findAll()
    {
        return (List<Articles>) articlesRepository.findAll();
    }

    public Articles findById(Long id)
    {
        Optional<Articles> temp = articlesRepository.findById(id);
        if (temp.isPresent())
        {
            return temp.get();
        }
        throw new KursNotFoundException("Article not found");
    }

    public List<Articles> findByName(String name)
    {
        return articlesRepository.findByName(name);
    }

    public void changeName(Long id, String name)
    {
        articlesRepository.changeName(id, name);
    }

    public void removeById(Long id) throws KursNotFoundException
    {
        Articles article = findById(id);
        operationsService.removeByArticleId(id, true);
        articlesRepository.delete(article);
    }

    @Autowired
    public void setArticlesRepository(ArticlesRepository articlesRepository)
    {
        this.articlesRepository = articlesRepository;
    }

    @Autowired
    public void setOperationsService(OperationsService operationsService)
    {
        this.operationsService = operationsService;
    }
}
