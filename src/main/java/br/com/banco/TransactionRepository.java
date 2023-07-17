package br.com.banco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
    @Query(value = "SELECT * FROM TRANSFERENCIA WHERE CONTA_ID = ?1 AND NOME_OPERADOR_TRANSACAO = %?2%", nativeQuery = true)
    List<Transaction> findByOperatorName(Long accountId, String operatorName);
    @Query(value = "SELECT * FROM TRANSFERENCIA WHERE CONTA_ID = ?1 AND DATA_TRANSFERENCIA <= ?2", nativeQuery = true)
    List<Transaction> findByEndDate(Long accountId, Date endDate);
    @Query(value = "SELECT * FROM TRANSFERENCIA WHERE CONTA_ID = ?1 AND DATA_TRANSFERENCIA >= ?2", nativeQuery = true)
    List<Transaction> findByBeginDate(Long accountId, Date beginDate);
    @Query(value = "SELECT * FROM TRANSFERENCIA WHERE CONTA_ID = ?1 AND DATA_TRANSFERENCIA >= ?2 AND NOME_OPERADOR_TRANSACAO = %?3%", nativeQuery = true)
    List<Transaction> findByBeginDateAndOperatorName(Long accountId, Date beginDate, String operatorName);
    @Query(value = "SELECT * FROM TRANSFERENCIA WHERE CONTA_ID = ?1 AND DATA_TRANSFERENCIA <= ?2 AND NOME_OPERADOR_TRANSACAO = %?3%", nativeQuery = true)
    List<Transaction> findByEndDateAndOperatorName(Long accountId, Date endDate, String operatorName);
    @Query(value = "SELECT * FROM TRANSFERENCIA WHERE CONTA_ID = ?1  AND DATA_TRANSFERENCIA >= ?2 AND DATA_TRANSFERENCIA <= ?3", nativeQuery = true)
    List<Transaction> findByPeriod(Long accountId, Date beginDate, Date endDate);
    @Query(value = """
        SELECT * FROM 
            TRANSFERENCIA 
        WHERE 
            CONTA_ID = ?1  
                AND 
            DATA_TRANSFERENCIA >= ?2 
                AND 
            DATA_TRANSFERENCIA <= ?3 
                AND 
            NOME_OPERADOR_TRANSACAO = %?4%
        """, nativeQuery = true)
    List<Transaction> findByPeriodAndOperatorName(Long accountId, Date beginDate, Date endDate, String operatorName);
}
