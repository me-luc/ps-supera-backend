package br.com.banco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping
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

            boolean isValidId = accountService.isValidAccountId(accountId);
            if (!isValidId) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<Transaction> transactions = transactionService.getTransactions(
                    accountId, operatorName, beginDate, endDate);

            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
