package main.repository;

import main.entity.Articles;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticlesRepository extends CrudRepository<Articles, Long>
{
    List<Articles> findByName(String name);
    @Transactional
    @Modifying
    @Query("UPDATE Articles a SET a.name = :name WHERE a.id = :id")
    void changeName(@Param("id") Long id, @Param("name") String name);
}
