package main.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Operations {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "article_id")
    private Long articleId;
    @Basic
    @Column(name = "debit")
    private BigDecimal debit;
    @Basic
    @Column(name = "credit")
    private BigDecimal credit;
    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;
    @Basic
    @Column(name = "balance_id")
    private Long balanceId;
    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Articles ArticlesByArticleId;
    @ManyToOne
    @JoinColumn(name = "balance_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Balance balanceByBalanceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Articles getArticlesByArticleId() {
        return ArticlesByArticleId;
    }

    public void setArticlesByArticleId(Articles ArticlesByArticleId) {
        this.ArticlesByArticleId = ArticlesByArticleId;
    }

    public Balance getBalanceByBalanceId() {
        return balanceByBalanceId;
    }

    public void setBalanceByBalanceId(Balance balanceByBalanceId) {
        this.balanceByBalanceId = balanceByBalanceId;
    }
}
