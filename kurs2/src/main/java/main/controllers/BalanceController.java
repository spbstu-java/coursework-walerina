package main.controllers;

import main.entity.Balance;
import main.exception.KursNotFoundException;
import main.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class BalanceController
{
    private BalanceService balanceService;

    @PostMapping("/balance/add")
    public Balance addBalance()
    {
        return balanceService.add();
    }

    @GetMapping("/balance/show")
    public ResponseEntity<List<Balance>> showBalance()
    {
        return new ResponseEntity<>(balanceService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/balance/show/id/{id}")
    public ResponseEntity<Balance> showBalanceById(@PathVariable Long id)
    {
        try
        {
            return new ResponseEntity<>(balanceService.findById(id), HttpStatus.OK);
        }
        catch (KursNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/balance/show/amount/{amount}")
    public ResponseEntity<List<Balance>> showBalanceByAmount(@PathVariable BigDecimal amount)
    {
        return new ResponseEntity<>(balanceService.findByAmount(amount), HttpStatus.OK);
    }

    @GetMapping("/balance/show/amount/ascending")
    public ResponseEntity<List<Balance>> showBalanceByAmountAscending()
    {
        return new ResponseEntity<>(balanceService.orderByAmount(true), HttpStatus.OK);
    }

    @GetMapping("/balance/show/amount/descending")
    public ResponseEntity<List<Balance>> showBalanceByAmountDescending()
    {
        return new ResponseEntity<>(balanceService.orderByAmount(false), HttpStatus.OK);
    }

    @GetMapping("/balance/show/date/ascending")
    public ResponseEntity<List<Balance>> showBalanceByDateAscending()
    {
        return new ResponseEntity<>(balanceService.orderByDate(true), HttpStatus.OK);
    }

    @GetMapping("/balance/show/date/descending")
    public ResponseEntity<List<Balance>> showBalanceByDateDescending()
    {
        return new ResponseEntity<>(balanceService.orderByDate(false), HttpStatus.OK);
    }

    @PostMapping("/balance/remove/{id}")
    public ResponseEntity<String> removeBalanceById(@PathVariable Long id)
    {
        try
        {
            balanceService.removeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (KursNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Autowired
    public void setBalanceService(BalanceService balanceService)
    {
        this.balanceService = balanceService;
    }
}
