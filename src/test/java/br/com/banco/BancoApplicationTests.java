package br.com.banco;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class BancoApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Random random = new Random();
    private Faker faker = new Faker();

    @Test
    public void whenNoAccountId_thenError() throws Exception {
        var response = mvc.perform(get("/")).andReturn();
        var expectedResponse = HttpStatus.BAD_REQUEST.value();
        assertEquals(expectedResponse, response.getResponse().getStatus());
    }

    @Test
    public void whenInvalidAccountId_thenError() throws Exception {
        createRandomAccount();
        var response = mvc.perform(get("/").header("id", 10)).andReturn();
        var expectedResponse = HttpStatus.UNAUTHORIZED.value();
        assertEquals(expectedResponse, response.getResponse().getStatus());
    }

    @Test
    public void whenArgsBeginDate_thenOK() throws Exception {
        Account a1 = createRandomAccount();
        Transaction t1 = createRandomTransactionWithDate(a1.getID(), "20/12/2021");
        Transaction t2 = createRandomTransactionWithDate(a1.getID(), "22/12/2021");
        Transaction t3 = createRandomTransactionWithDate(a1.getID(), "23/12/2021");

        System.out.println("Created 1 account: \n" + a1.toString());
        System.out.println("\nCreated 3 transactions: \n" + t1.toString()  + "\n" + t2.toString() + "\n" + t3.toString());

        var response = mvc.perform(
                        get("/")
                                .header("id", String.valueOf(a1.getID()))
                                .param("beginDate", "22/12/2021"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        List<Transaction> responseList = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Transaction> expectedList = Arrays.asList(t2, t3);

        System.out.println("------------------------");
        System.out.println("RESPONSE: \n\n" + responseList);

        checkListsEqual(expectedList, responseList);
    }

    @Test
    public void whenArgsEndDate_thenOK() throws Exception {
        Account a1 = createRandomAccount();
        Transaction t1 = createRandomTransactionWithDate(a1.getID(), "20/12/2021");
        Transaction t2 = createRandomTransactionWithDate(a1.getID(), "22/12/2021");
        Transaction t3 = createRandomTransactionWithDate(a1.getID(), "23/12/2021");

        System.out.println("Created 1 account: \n" + a1.toString());
        System.out.println("\nCreated 3 transactions: \n" + t1.toString()  + "\n" + t2.toString() + "\n" + t3.toString());

        var response = mvc.perform(
                        get("/")
                                .header("id", String.valueOf(a1.getID()))
                                .param("endDate", "22/12/2021"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        List<Transaction> responseList = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Transaction> expectedList = Arrays.asList(t1, t2);

        System.out.println("------------------------");
        System.out.println("RESPONSE: \n\n" + responseList);

        checkListsEqual(expectedList, responseList);
    }

    @Test
    public void whenArgsEndAndBeginDate_thenOK() throws Exception {
        Account a1 = createRandomAccount();
        Transaction t1 = createRandomTransactionWithDate(a1.getID(), "20/12/2021");
        Transaction t2 = createRandomTransactionWithDate(a1.getID(), "22/12/2021");
        Transaction t3 = createRandomTransactionWithDate(a1.getID(), "23/12/2021");
        Transaction t4 = createRandomTransactionWithDate(a1.getID(), "24/12/2021");
        Transaction t5 = createRandomTransactionWithDate(a1.getID(), "25/12/2021");

        System.out.println("Created 1 account: \n" + a1.toString());
        System.out.println("\nCreated 5 transactions: \n" + t1.toString()  + "\n" + t2.toString() + "\n" + t3.toString()  + "\n" + t4.toString() + "\n" + t5.toString());

        var response = mvc.perform(
                    get("/")
                            .header("id", String.valueOf(a1.getID()))
                            .param("endDate", "24/12/2021")
                            .param("beginDate", "22/12/2021"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        List<Transaction> responseList = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Transaction> expectedList = Arrays.asList(t2, t3, t4);

        System.out.println("------------------------");
        System.out.println("RESPONSE: \n\n" + responseList);

        checkListsEqual(expectedList, responseList);
    }

    @Test
    public void whenArgsOperator_thenOK() throws Exception {
        Account a1 = createRandomAccount();
        Account a2 = createRandomAccount();
        Transaction t1 = createRandomTransfer(a1.getID());
        Transaction t2 = createRandomTransfer(a2.getID());

        System.out.println("Created 2 accounts: \n\n" + a1.toString() + "\n" + a2.toString());
        System.out.println("\nCreated 2 transactions: \n\n" + t1.toString() + "\n" + t2.toString());

        var response = mvc.perform(
                get("/")
                        .header("id", String.valueOf(a1.getID()))
                        .param("operatorName", t1.getOperatorName()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        List<Transaction> responseList = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Transaction> expectedList = Arrays.asList(t1);

        System.out.println("------------------------");
        System.out.println("RESPONSE: \n\n" + responseList);

        checkListsEqual(expectedList, responseList);
    }

    @Test
    public void whenArgsOperatorAndBeginDate_thenOK() throws Exception {
        Account a1 = createRandomAccount();
        Transaction t1 = createRandomTransferWithDate(a1.getID(), "22/12/2021");
        Transaction t2 = createRandomTransferWithDate(a1.getID(), "23/12/2021");
        Transaction t3 = createRandomTransferWithDate(a1.getID(), "24/12/2021");

        System.out.println("Created 1 accounts: \n\n" + a1);
        System.out.println("\n\nCreated 2 transactions: \n\n" + t1 + "\n" + t2 + "\n" + t3);

        var response = mvc.perform(
                        get("/")
                                .header("id", String.valueOf(a1.getID()))
                                .param("operatorName", t2.getOperatorName())
                                .param("beginDate", "23/12/2021"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        List<Transaction> responseList = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Transaction> expectedList = Arrays.asList(t2);

        System.out.println("------------------------");
        System.out.println("RESPONSE: \n\n" + responseList);

        checkListsEqual(expectedList, responseList);
    }

    @Test
    public void whenArgsOperatorAndEndDate_thenOK() throws Exception {
        Account a1 = createRandomAccount();
        Transaction t1 = createRandomTransferWithDate(a1.getID(), "22/12/2021");
        Transaction t2 = createRandomTransferWithDate(a1.getID(), "23/12/2021");
        Transaction t3 = createRandomTransferWithDate(a1.getID(), "24/12/2021");

        System.out.println("Created 1 accounts: \n\n" + a1);
        System.out.println("\n\nCreated 2 transactions: \n\n" + t1 + "\n" + t2 + "\n" + t3);

        var response = mvc.perform(
                        get("/")
                                .header("id", String.valueOf(a1.getID()))
                                .param("operatorName", t2.getOperatorName())
                                .param("endDate", "23/12/2021"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        List<Transaction> responseList = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Transaction> expectedList = Arrays.asList(t2);

        System.out.println("------------------------");
        System.out.println("RESPONSE: \n\n" + responseList);

        checkListsEqual(expectedList, responseList);
    }

    @Test
    public void whenArgsOperatorAndPeriod_thenOK() throws Exception{
        Account a1 = createRandomAccount();
        Transaction t1 = createRandomTransferWithDateAndOperator(a1.getID(), "22/12/2021", "Operator A");
        Transaction t2 = createRandomTransferWithDateAndOperator(a1.getID(), "23/12/2021", "Operator A");
        Transaction t3 = createRandomTransferWithDateAndOperator(a1.getID(), "24/12/2021", "Operator B");
        Transaction t4 = createRandomTransferWithDateAndOperator(a1.getID(), "25/12/2021", "Operator A");
        Transaction t5 = createRandomTransferWithDateAndOperator(a1.getID(), "26/12/2021", "Operator A");

        System.out.println("Created 1 accounts: \n\n" + a1);
        System.out.println("\n\nCreated 5 transactions: \n\n" + t1 + "\n" + t2 + "\n" + t3 + "\n" + t4 + "\n" + t5);

        var response = mvc.perform(
                        get("/")
                                .header("id", String.valueOf(a1.getID()))
                                .param("operatorName", t1.getOperatorName())
                                .param("beginDate", "22/12/2021")
                                .param("endDate", "26/12/2021"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        List<Transaction> responseList = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Transaction> expectedList = Arrays.asList(t1, t2, t4, t5);

        System.out.println("------------------------");
        System.out.println("RESPONSE: \n\n" + responseList);

        checkListsEqual(expectedList, responseList);
    }

    private Account createRandomAccount() {
        String name = faker.name().fullName();
        Account account = new Account(name);
        return accountRepository.save(account);
    }

    private Transaction createRandomTransaction(long accountId) {
        String[] operationTypes = {"Transferencia", "Saque", "Deposito"};
        Date date = faker.date().birthday();
        BigDecimal value = BigDecimal.valueOf(random.nextDouble() * 1000);
        String type = operationTypes[random.nextInt(3)];
        String operatorName = (type == "Transferencia") ? faker.name().fullName() : null;
        Transaction transaction = new Transaction(date, value, type, operatorName, accountId);
        return transactionRepository.save(transaction);
    }

    private Transaction createRandomTransfer(long accountId) {
        Date date = faker.date().birthday();
        BigDecimal value = BigDecimal.valueOf(random.nextDouble() * 1000);
        String type = "Transferencia";
        String operatorName = faker.name().fullName();
        Transaction transaction = new Transaction(date, value, type, operatorName, accountId);
        return transactionRepository.save(transaction);
    }

    private Transaction createRandomTransferWithDate(long accountId, String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        BigDecimal value = BigDecimal.valueOf(random.nextDouble());
        String type = "Transferencia";
        String operatorName = faker.name().fullName();
        Date date = sdf.parse(dateStr);
        Transaction transaction = new Transaction(date, value, type, operatorName, accountId);
        return transactionRepository.save(transaction);
    }

    private Transaction createRandomTransferWithDateAndOperator(long accountId, String dateStr, String operatorName) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        BigDecimal value = BigDecimal.valueOf(random.nextDouble());
        String type = "Transferencia";
        Date date = sdf.parse(dateStr);
        Transaction transaction = new Transaction(date, value, type, operatorName, accountId);
        return transactionRepository.save(transaction);
    }

    private Transaction createRandomTransactionWithDate(long accountId, String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String[] operationTypes = {"Transferencia", "Saque", "Deposito"};
        BigDecimal value = BigDecimal.valueOf(random.nextDouble());
        String type = operationTypes[random.nextInt(3)];
        String operatorName = (type == "Transferencia") ? faker.name().fullName() : null;
        Date date = sdf.parse(dateStr);
        Transaction transaction = new Transaction(date, value, type, operatorName, accountId);
        return transactionRepository.save(transaction);
    }

    private <T> void checkListsEqual(List<T> l1, List<T> l2) {
        assertEquals(l1.size(), l2.size(), "Lists have different sizes");
        assertEquals(l1, l2, "Lists are different");
    }
}
