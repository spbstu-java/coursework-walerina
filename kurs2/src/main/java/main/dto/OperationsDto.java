package main.dto;

import java.math.BigDecimal;

public class OperationsDto
{
    private Long articleId;
    private BigDecimal debit;
    private BigDecimal credit;
    private Long balanceId;

    public Long getArticleId()
    {
        return articleId;
    }

    public BigDecimal getDebit()
    {
        return debit;
    }

    public BigDecimal getCredit()
    {
        return credit;
    }

    public Long getBalanceId()
    {
        return balanceId;
    }
}
