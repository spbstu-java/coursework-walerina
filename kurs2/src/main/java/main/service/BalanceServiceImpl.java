package main.service;

import main.entity.Balance;
import main.exception.KursNotFoundException;
import main.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BalanceServiceImpl implements BalanceService
{
    private BalanceRepository balanceRepository;
    private OperationsService operationsService;

    public Balance add()
    {
        Balance balance = new Balance();
        balance.setCreateDate(new Timestamp(System.currentTimeMillis()));
        balance.setCredit(BigDecimal.valueOf(0));
        balance.setDebit(BigDecimal.valueOf(0));
        balance.setAmount(BigDecimal.valueOf(0));
        return balanceRepository.save(balance);
    }

    public List<Balance> findAll() { return (List<Balance>) balanceRepository.findAll(); }

    public Balance findById(Long id)
    {
        Optional<Balance> temp = balanceRepository.findById(id);
        if (temp.isPresent())
        {
            return temp.get();
        }
        throw new KursNotFoundException("Balance not found");
    }

    public List<Balance> findByAmount(BigDecimal amount)
    {
        return balanceRepository.findByAmount(amount);
    }

    public List<Balance> orderByAmount(Boolean isAscending)
    {
        if (isAscending)
        {
            return balanceRepository.orderByAmount();
        }
        else
        {
            return balanceRepository.orderByAmountDesc();
        }
    }

    public List<Balance> orderByDate(Boolean isAscending)
    {
        if (isAscending)
        {
            return balanceRepository.orderByDate();
        }
        else
        {
            return balanceRepository.orderByDateDesc();
        }
    }

    public void removeById(Long id) throws KursNotFoundException
    {
        Balance balance = findById(id);
        operationsService.removeByBalanceId(id, false);
        balanceRepository.delete(balance);
    }

    @Autowired
    public void setBalanceRepository(BalanceRepository balanceRepository)
    {
        this.balanceRepository = balanceRepository;
    }

    @Autowired
    public void setOperationsService(OperationsService operationsService)
    {
        this.operationsService = operationsService;
    }
}
