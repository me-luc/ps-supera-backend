package br.com.banco;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TRANSFERENCIA")
public class Transaction {
    @Id @GeneratedValue
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

    public Transaction(Long id, Date transferDate, BigDecimal value, String type, String operatorName, Long accountId) {
        this.id = id;
        this.transferDate = transferDate;
        this.value = value;
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
        return "Transaction{" +
                "id=" + id +
                ", transferDate=" + transferDate +
                ", value=" + value +
                ", type='" + type + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
