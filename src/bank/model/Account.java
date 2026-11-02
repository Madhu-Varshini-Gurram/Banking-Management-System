package bank.model;

import bank.enums.AccountStatus;

import java.math.BigDecimal;

public abstract class Account {
    private String accountNumber;
    private Customer customer;
    private BigDecimal balance;
    private AccountStatus status;

    public Account(String accountNumber,Customer customer,BigDecimal balance,AccountStatus status){
        this.accountNumber=accountNumber;
        this.customer=customer;
        this.balance=balance;
        this.status=status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    protected void updateBalance(BigDecimal newBalance) {
        this.balance=newBalance;
    }

    public AccountStatus getStatus() {
        return status;
    }



    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public abstract boolean withdraw(BigDecimal amount);

    public void deposit(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO)>0){
            BigDecimal newBalance = getBalance().add(amount);
            updateBalance(newBalance);
        }
        else{
            System.out.println("Invalid deposit amount.");
        }
    }


    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.matches("^A[0-9]{3,6}$");
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber +
                "\nCustomer Name: " + customer.getName() +
                "\nBalance: " + balance +
                "\nStatus: " + status;
    }
}
