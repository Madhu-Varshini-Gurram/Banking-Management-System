package bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import bank.enums.TransactionType;

public class Transaction {
    private String transactionId;
    private String accountNumber;
    private TransactionType  transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String description;

    public Transaction(String transactionId,String accountNumber,TransactionType transactionType,BigDecimal amount,LocalDateTime transactionDate,String description){
        this.transactionId=transactionId;
        this.accountNumber=accountNumber;
        this.transactionType=transactionType;
        this.amount=amount;
        this.transactionDate=transactionDate;
        this.description=description;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return "\nTransaction ID : " + transactionId +
                "\nAccount Number : " + accountNumber +
                "\nType           : " + transactionType +
                "\nAmount         : ₹" + amount +
                "\nDate & Time    : " + transactionDate.format(formatter) +
                "\nDescription    : " + description +
                "\n-----------------------------------------";
    }
}



