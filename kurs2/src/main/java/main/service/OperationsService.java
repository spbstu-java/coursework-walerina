package main.service;

import main.dto.OperationsDto;
import main.entity.Operations;

import java.math.BigDecimal;
import java.util.List;

public interface OperationsService
{
    Operations add(OperationsDto operationDto);
    List<Operations> findAll();
    Operations findById(Long id);
    List<Operations> findByBalanceId(Long id);
    List<Operations> findByArticleId(Long id);
    List<Operations> findByBalanceAmount(BigDecimal amount);
    List<Operations> orderByDate(Boolean isAscending);
    List<Operations> orderByBalanceAmount(Boolean isAscending);
    List<Operations> orderByBalanceDate(Boolean isAscending);
    void changeDebit(Long id, BigDecimal debit);
    void changeCredit(Long id, BigDecimal credit);
    void removeById(Long id, Boolean withBalance);
    void removeByArticleId(Long id, Boolean withBalance);
    void removeByBalanceId(Long id, Boolean withBalance);
}
