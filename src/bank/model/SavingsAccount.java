package bank.model;

import bank.enums.AccountStatus;
import bank.interfaces.InterestBearing;

import java.math.BigDecimal;

public class SavingsAccount extends Account implements InterestBearing {
    private final BigDecimal minimumBalance;

    private final BigDecimal interestRate;

    public SavingsAccount(String accountNumber, Customer customer, BigDecimal balance, AccountStatus status,BigDecimal minimumBalance,BigDecimal interestRate){
        super(accountNumber,customer,balance,status);
        this.minimumBalance=minimumBalance;
        this.interestRate=interestRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }


    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            System.out.println("Invalid withdrawal amount!");
            return false;
        }

        BigDecimal remainingBalance=getBalance().subtract(amount);

        if(remainingBalance.compareTo(minimumBalance)>=0){
            updateBalance(remainingBalance);
            System.out.println("Withdrawal successful");
            System.out.println("Remaining Balance: "+getBalance());
            return true;
        }
        else{
            System.out.println("Insufficient balance! Mininum balance must be maintained");
            return false;
        }
    }

    @Override
    public BigDecimal calculateInterest(){
        return (getBalance().multiply(interestRate)) .divide(BigDecimal.valueOf(100));
    }


    public boolean withdrawForClosure() {
        if (getBalance().compareTo(minimumBalance) == 0) {
            updateBalance(BigDecimal.ZERO);
            return true;
        }

        return false;
    }





}
