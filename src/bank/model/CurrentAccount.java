package bank.model;

import bank.enums.AccountStatus;

import java.math.BigDecimal;

public class CurrentAccount extends Account{
    private BigDecimal overdraftLimit;

    public CurrentAccount(String accountNumber, Customer customer, BigDecimal balance, AccountStatus status,BigDecimal overdraftLimit){
        super(accountNumber,customer,balance,status);
        this.overdraftLimit=overdraftLimit;
    }


    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            System.out.println("Invalid withdrawal amount!");
            return false;
        }

        BigDecimal remainingBalance=getBalance().subtract(amount);

        if(remainingBalance.compareTo(overdraftLimit.negate())>=0){
            updateBalance(remainingBalance);
            System.out.println("Withdrawal successful");
            System.out.println("Remaining Balance: "+getBalance());
            return true;
        }
        else{
            System.out.println("Insufficient balance! Overdraft limit exceeded.");
            return false;
        }
    }
}
