package main.repository;

import main.entity.Balance;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

public interface BalanceRepository extends CrudRepository<Balance, Long>
{
    List<Balance> findByAmount(BigDecimal amount);

    @Query("SELECT b FROM Balance b ORDER BY b.amount")
    List<Balance> orderByAmount();

    @Query("SELECT b FROM Balance b ORDER BY b.amount DESC")
    List<Balance> orderByAmountDesc();

    @Query("SELECT b FROM Balance b ORDER BY b.createDate")
    List<Balance> orderByDate();

    @Query("SELECT b FROM Balance b ORDER BY b.createDate DESC")
    List<Balance> orderByDateDesc();

    @Transactional
    @Modifying
    @Query("UPDATE Balance b SET b.debit = b.debit + :debit, b.credit = b.credit + :credit, b.amount = b.debit - b.credit WHERE b.id = :balanceId")
    void addOperation(@Param("balanceId") Long balanceId, @Param("debit") BigDecimal debit, @Param("credit") BigDecimal credit);

    @Transactional
    @Modifying
    @Query("UPDATE Balance b SET b.debit = b.debit - :debit, b.credit = b.credit - :credit, b.amount = b.debit - b.credit WHERE b.id = :balanceId")
    void removeOperation(@Param("balanceId") Long balanceId, @Param("debit") BigDecimal debit, @Param("credit") BigDecimal credit);
}
