package main.service;

import main.entity.Balance;

import java.math.BigDecimal;
import java.util.List;

public interface BalanceService
{
    Balance add();
    List<Balance> findAll();
    Balance findById(Long id);
    List<Balance> findByAmount(BigDecimal amount);
    List<Balance> orderByAmount(Boolean isAscending);
    List<Balance> orderByDate(Boolean isAscending);
    void removeById(Long id);
}
