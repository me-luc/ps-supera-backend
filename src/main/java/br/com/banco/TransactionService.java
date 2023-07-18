package br.com.banco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactions(
            Long accountId, String operatorName, String beginDate, String endDate) {
        try {
            TransactionDTO dto = new TransactionDTO(beginDate, endDate, operatorName);

            if (operatorName != null && beginDate != null && endDate != null)
                return transactionRepository.findByPeriodAndOperatorName(accountId, dto.getBeginDate(),
                        dto.getEndDate(), operatorName);

            if (beginDate != null && endDate != null)
                return transactionRepository.findByPeriod(accountId, dto.getBeginDate(), dto.getEndDate());

            if (beginDate != null && operatorName != null)
                return transactionRepository.findByBeginDateAndOperatorName(accountId, dto.getBeginDate(),
                        operatorName);

            if (endDate != null && operatorName != null)
                return transactionRepository.findByEndDateAndOperatorName(accountId, dto.getEndDate(), operatorName);

            if (beginDate != null)
                return transactionRepository.findByBeginDate(accountId, dto.getBeginDate());

            if (endDate != null)
                return transactionRepository.findByEndDate(accountId, dto.getEndDate());

            if (operatorName != null)
                return transactionRepository.findByOperatorName(accountId, dto.getOperatorName());

            return transactionRepository.findByAccountId(accountId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao receber dados de transações", e);
        }
    }
}
