package br.com.banco;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TransactionDTO {
    private Date beginDate;
    private Date endDate;
    private String operatorName;
    private  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public TransactionDTO(String beginDate, String endDate, String operatorName) throws ParseException {
        setBeginDate(beginDate);
        setEndDate(endDate);
        this.operatorName = operatorName;
    }

    public void setBeginDate(String beginDate) throws ParseException {
        if (beginDate == null || beginDate.isEmpty()) {
            this.beginDate = null;
            return;
        }
        this.beginDate = sdf.parse(beginDate);
        System.out.println(beginDate);
    }

    public void setEndDate(String endDate) throws ParseException {
        if (endDate == null || endDate.isEmpty()) {
            this.endDate = null;
            return;
        }

        this.endDate = sdf.parse(endDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.endDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        this.endDate = calendar.getTime();
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

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", operatorName='" + operatorName + '\'' +
                '}';
    }
}
