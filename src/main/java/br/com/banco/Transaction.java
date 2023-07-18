package br.com.banco;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "TRANSFERENCIA")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DATA_TRANSFERENCIA")
    private Date transferDate;

    @Column(name = "VALOR")
    private BigDecimal value;

    @Column(name = "TIPO")
    private String type;

    @Column(name = "NOME_OPERADOR_TRANSACAO")
    private String operatorName;

    @Column(name = "CONTA_ID")
    private Long accountId;

    public Transaction(){};

    public Transaction(Date transferDate, BigDecimal value, String type, String operatorName, Long accountId) {
        this.transferDate = transferDate;
        this.value = (type == "Saque")
                ? value.multiply(BigDecimal.valueOf(-1)).setScale(2, RoundingMode.CEILING)
                : value.setScale(2, RoundingMode.CEILING);
        this.type = type;
        this.operatorName = operatorName;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return """
            Transaction {
                id = %d,
                transferDate = %s,
                value = %s,
                type = '%s',
                operatorName = '%s',
                accountId = %d
            }
            """.formatted(id, transferDate, value, type, operatorName, accountId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(transferDate, that.transferDate) &&
                Objects.equals(value, that.value) &&
                Objects.equals(type, that.type) &&
                Objects.equals(operatorName, that.operatorName) &&
                Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transferDate, value, type, operatorName, accountId);
    }
}
