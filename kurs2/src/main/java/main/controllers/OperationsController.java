package main.controllers;

import main.dto.OperationsDto;
import main.entity.Operations;
import main.exception.KursNotFoundException;
import main.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class OperationsController
{
    private OperationsService operationsService;

    @PostMapping("/operations/add")
    public Operations addOperation(@RequestBody OperationsDto operationDto)
    {
        try
        {
            return operationsService.add(operationDto);
        }
        catch (KursNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/operations/show")
    public ResponseEntity<List<Operations>> showOperations()
    {
        return new ResponseEntity<>(operationsService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/operations/show/id/{id}")
    public ResponseEntity<Operations> showOperationById(@PathVariable Long id)
    {
        try
        {
            return new ResponseEntity<>(operationsService.findById(id), HttpStatus.OK);
        }
        catch (KursNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/operations/show/balance-id/{balanceId}")
    public ResponseEntity<List<Operations>> showOperationsByBalanceId(@PathVariable Long balanceId)
    {
        return new ResponseEntity<>(operationsService.findByBalanceId(balanceId), HttpStatus.OK);
    }

    @GetMapping("/operations/show/article-id/{articleId}")
    public ResponseEntity<List<Operations>> showOperationsByArticleId(@PathVariable Long articleId)
    {
        return new ResponseEntity<>(operationsService.findByArticleId(articleId), HttpStatus.OK);
    }

    @GetMapping("/operations/show/balance/amount/{balanceAmount}")
    public ResponseEntity<List<Operations>> showOperationsByBalanceAmount(@PathVariable BigDecimal balanceAmount)
    {
        return new ResponseEntity<>(operationsService.findByBalanceAmount(balanceAmount), HttpStatus.OK);
    }

    @GetMapping("/operations/show/date/ascending")
    public ResponseEntity<List<Operations>> showOperationsByDateAscending()
    {
        return new ResponseEntity<>(operationsService.orderByDate(true), HttpStatus.OK);
    }

    @GetMapping("/operations/show/date/descending")
    public ResponseEntity<List<Operations>> showOperationsByDateDescending()
    {
        return new ResponseEntity<>(operationsService.orderByDate(false), HttpStatus.OK);
    }

    @GetMapping("/operations/show/balance/amount/ascending")
    public ResponseEntity<List<Operations>> showOperationsByBalanceAmountAscending()
    {
        return new ResponseEntity<>(operationsService.orderByBalanceAmount(true), HttpStatus.OK);
    }

    @GetMapping("/operations/show/balance/amount/descending")
    public ResponseEntity<List<Operations>> showOperationsByBalanceAmountDescending()
    {
        return new ResponseEntity<>(operationsService.orderByBalanceAmount(false), HttpStatus.OK);
    }

    @GetMapping("/operations/show/balance/date/ascending")
    public ResponseEntity<List<Operations>> showOperationsByBalanceDateAscending()
    {
        return new ResponseEntity<>(operationsService.orderByBalanceDate(true), HttpStatus.OK);
    }

    @GetMapping("/operations/show/balance/date/descending")
    public ResponseEntity<List<Operations>> showOperationsByBalanceDateDescending()
    {
        return new ResponseEntity<>(operationsService.orderByBalanceDate(false), HttpStatus.OK);
    }

    @PostMapping("/operations/change/debit/{id}")
    public ResponseEntity<String> changeOperationDebit(@PathVariable Long id, @RequestParam BigDecimal debit)
    {
        try
        {
            operationsService.changeDebit(id, debit);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (KursNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/operations/change/credit/{id}")
    public ResponseEntity<String> changeOperationCredit(@PathVariable Long id, @RequestParam BigDecimal credit)
    {
        try
        {
            operationsService.changeCredit(id, credit);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (KursNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/operations/remove/id/{id}")
    public ResponseEntity<String> removeOperationById(@PathVariable Long id)
    {
        try
        {
            operationsService.removeById(id, true);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (KursNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/operations/remove/article-id/{id}")
    public ResponseEntity<String> removeOperationByArticleId(@PathVariable Long id)
    {
        operationsService.removeByArticleId(id, true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/operations/remove/balance-id/{id}")
    public ResponseEntity<String> removeOperationByBalanceId(@PathVariable Long id)
    {
        operationsService.removeByBalanceId(id, true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public void setOperationsService(OperationsService operationsService)
    {
        this.operationsService = operationsService;
    }
}
