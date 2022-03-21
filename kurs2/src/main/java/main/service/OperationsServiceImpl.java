package main.service;

import main.dto.OperationsDto;
import main.entity.Operations;
import main.exception.KursNotFoundException;
import main.repository.BalanceRepository;
import main.repository.OperationsRepository;
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
public class OperationsServiceImpl implements OperationsService
{
    private BalanceRepository balanceRepository;
    private OperationsRepository operationsRepository;
    private BalanceService balanceService;
    private ArticlesService articlesService;

    public Operations add(OperationsDto operationDto) throws KursNotFoundException
    {
        Operations operation = new Operations();
        operation.setArticlesByArticleId(articlesService.findById(operationDto.getArticleId()));
        operation.setBalanceByBalanceId(balanceService.findById(operationDto.getBalanceId()));
        operation.setArticleId(operationDto.getArticleId());
        operation.setBalanceId(operationDto.getBalanceId());
        operation.setDebit(operationDto.getDebit());
        operation.setCredit(operationDto.getCredit());
        operation.setCreateDate(new Timestamp(System.currentTimeMillis()));
        balanceRepository.addOperation(operation.getBalanceId(), operation.getDebit(), operation.getCredit());
        return operationsRepository.save(operation);
    }

    public List<Operations> findAll() { return (List<Operations>) operationsRepository.findAll(); }

    public Operations findById(Long id)
    {
        Optional<Operations> temp = operationsRepository.findById(id);
        if (temp.isPresent())
        {
            return temp.get();
        }
        throw new KursNotFoundException("Operation not found");
    }

    public List<Operations> findByBalanceId(Long id)
    {
        return operationsRepository.findByBalanceId(id);
    }

    public List<Operations> findByArticleId(Long id)
    {
        return operationsRepository.findByArticleId(id);
    }

    public List<Operations> findByBalanceAmount(BigDecimal amount)
    {
        return operationsRepository.findByBalanceAmount(amount);
    }

    public List<Operations> orderByDate(Boolean isAscending)
    {
        if (isAscending)
        {
            return operationsRepository.orderByDate();
        }
        else
        {
            return operationsRepository.orderByDateDesc();
        }
    }

    public List<Operations> orderByBalanceAmount(Boolean isAscending)
    {
        if (isAscending)
        {
            return operationsRepository.orderByBalanceAmount();
        }
        else
        {
            return operationsRepository.orderByBalanceAmountDesc();
        }
    }

    public List<Operations> orderByBalanceDate(Boolean isAscending)
    {
        if (isAscending)
        {
            return operationsRepository.orderByBalanceDate();
        }
        else
        {
            return operationsRepository.orderByBalanceDateDesc();
        }
    }

    public void changeDebit(Long id, BigDecimal debit) throws KursNotFoundException
    {
        Operations operation = findById(id);
        balanceRepository.removeOperation(operation.getBalanceId(), operation.getDebit(), BigDecimal.valueOf(0));
        operationsRepository.changeDebit(operation.getId(), debit);
        balanceRepository.addOperation(operation.getBalanceId(), debit, BigDecimal.valueOf(0));
    }

    public void changeCredit(Long id, BigDecimal credit) throws KursNotFoundException
    {
        Operations operation = findById(id);
        balanceRepository.removeOperation(operation.getBalanceId(), BigDecimal.valueOf(0), operation.getCredit());
        operationsRepository.changeCredit(operation.getId(), credit);
        balanceRepository.addOperation(operation.getBalanceId(), BigDecimal.valueOf(0), credit);
    }

    public void removeById(Long id, Boolean withBalance) throws KursNotFoundException
    {
        Operations operation = findById(id);
        if (withBalance)
        {
            balanceRepository.removeOperation(operation.getBalanceId(), operation.getDebit(), operation.getCredit());
        }
        operationsRepository.delete(operation);
    }

   public void removeByArticleId(Long articleId, Boolean withBalance)
    {
        List<Operations> operations = findByArticleId(articleId);
        for (Operations operation: operations)
        {
            if (withBalance)
            {
                balanceRepository.removeOperation(operation.getBalanceId(), operation.getDebit(), operation.getCredit());
            }
            operationsRepository.delete(operation);
        }
    }

    public void removeByBalanceId(Long balanceId, Boolean withBalance)
    {
        List<Operations> operations = findByBalanceId(balanceId);
        for (Operations operation: operations)
        {
            if (withBalance)
            {
                balanceRepository.removeOperation(operation.getBalanceId(), operation.getDebit(), operation.getCredit());
            }
            operationsRepository.delete(operation);
        }
    }

    @Autowired
    public void setBalanceRepository(BalanceRepository balanceRepository)
    {
        this.balanceRepository = balanceRepository;
    }

    @Autowired
    public void setOperationsRepository(OperationsRepository operationsRepository)
    {
        this.operationsRepository = operationsRepository;
    }

    @Autowired
    public void setBalanceService(BalanceService balanceService)
    {
        this.balanceService = balanceService;
    }

    @Autowired
    public void setArticlesService(ArticlesService articlesService)
    {
        this.articlesService = articlesService;
    }
}
