package main.repository;


import main.entity.Operations;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

public interface OperationsRepository extends CrudRepository<Operations, Long>
{
    List<Operations> findByBalanceId(Long balanceId);
    List<Operations> findByArticleId(Long articleId);

    @Query("SELECT o FROM Operations o INNER JOIN Balance b ON o.balanceId = b.id WHERE b.amount = :amount")
    List<Operations> findByBalanceAmount(@Param("amount") BigDecimal amount);

    @Query("SELECT o FROM Operations o ORDER BY o.createDate")
    List<Operations> orderByDate();

    @Query("SELECT o FROM Operations o ORDER BY o.createDate DESC")
    List<Operations> orderByDateDesc();

    @Query("SELECT o FROM Operations o INNER JOIN Balance b ON o.balanceId = b.id ORDER BY b.amount")
    List<Operations> orderByBalanceAmount();

    @Query("SELECT o FROM Operations o INNER JOIN Balance b ON o.balanceId = b.id ORDER BY b.amount DESC")
    List<Operations> orderByBalanceAmountDesc();

    @Query("SELECT o FROM Operations o INNER JOIN Balance b ON o.balanceId = b.id ORDER BY b.createDate")
    List<Operations> orderByBalanceDate();

    @Query("SELECT o FROM Operations o INNER JOIN Balance b ON o.balanceId = b.id ORDER BY b.createDate DESC")
    List<Operations> orderByBalanceDateDesc();

    @Transactional
    @Modifying
    @Query("UPDATE Operations o SET o.debit = :debit WHERE o.id = :id")
    void changeDebit(@Param("id") Long id, @Param("debit") BigDecimal debit);

    @Transactional
    @Modifying
    @Query("UPDATE Operations o SET o.credit = :credit WHERE o.id = :id")
    void changeCredit(@Param("id") Long id, @Param("credit") BigDecimal credit);
}
