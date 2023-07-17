package br.com.banco;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionDTO {
    private Date beginDate;
    private Date endDate;
    private String operatorName;

    public TransactionDTO(String beginDate, String endDate, String operatorName) throws ParseException {
        this.beginDate = setBeginDate(beginDate);
        this.endDate = setEndDate(endDate);
        this.operatorName = operatorName;
    }

    public Date setBeginDate(String beginDate) throws ParseException {
        if (beginDate == null || beginDate.isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(beginDate);
    }

    public Date setEndDate(String endDate) throws ParseException {
        if (endDate == null || endDate.isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(endDate);
    }

    public String getOperatorName() {
        return operatorName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
