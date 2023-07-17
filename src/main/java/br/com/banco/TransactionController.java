package br.com.banco;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;
    @GetMapping("/")
    public ResponseEntity<List<Transaction>> getTransactions(
            @RequestParam(value = "operatorName", required = false) String operatorName,
            @RequestParam(value = "beginDate", required = false) String beginDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            HttpServletRequest request
    ) {
        String accountIdHeader = request.getHeader("id");
        if (accountIdHeader == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Long accountId = Long.parseLong(accountIdHeader);

        try {
            TransactionDTO dto = new TransactionDTO(beginDate, endDate, operatorName);

            if(operatorName != null && beginDate != null && endDate != null)
                return new ResponseEntity<>(
                        transactionRepository.findByPeriodAndOperatorName(accountId, dto.getBeginDate(), dto.getEndDate(), operatorName),
                        HttpStatus.OK
                );

            if(beginDate != null && endDate != null)
                return new ResponseEntity<>(
                        transactionRepository.findByPeriod(accountId, dto.getBeginDate(), dto.getEndDate()),
                        HttpStatus.OK
                );

            if(beginDate != null && operatorName != null)
                return new ResponseEntity<>(
                        transactionRepository.findByBeginDateAndOperatorName(accountId, dto.getBeginDate(), operatorName),
                        HttpStatus.OK
                );

            if(endDate != null && operatorName != null)
                return new ResponseEntity<>(
                        transactionRepository.findByEndDateAndOperatorName(accountId, dto.getEndDate(), operatorName),
                        HttpStatus.OK
                );

            if(beginDate != null)
                return new ResponseEntity<>(
                        transactionRepository.findByBeginDate(accountId, dto.getBeginDate()),
                        HttpStatus.OK
                );

            if(endDate != null)
                return new ResponseEntity<>(
                        transactionRepository.findByBeginDate(accountId, dto.getEndDate()),
                        HttpStatus.OK
                );

            return new ResponseEntity<>(transactionRepository.findByAccountId(accountId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
