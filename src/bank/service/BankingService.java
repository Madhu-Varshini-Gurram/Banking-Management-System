package bank.service;
import bank.enums.AccountStatus;
import bank.enums.TransactionType;
import bank.exceptions.*;
import bank.model.*;
import bank.service.Bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BankingService {
    private final Bank bank;

    public BankingService(Bank bank){
        this.bank=bank;
    }

    public void deposit(String accountNumber, BigDecimal amount)
            throws AccountNotFoundException,
            AccountInactiveException,
            InvalidAmountException
    {
        Account acc=bank.getAccount(accountNumber);

        if(acc == null){
            throw new AccountNotFoundException("Account not found!");
        }

        if (acc.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountInactiveException("Account is not active!");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }

        acc.deposit(amount);

        Transaction transaction=new Transaction(
                UUID.randomUUID().toString(),
                accountNumber,
                TransactionType.DEPOSIT,
                amount,
                LocalDateTime.now(),
                "Cash Deposit"
        );

        bank.addTransaction(transaction);

        System.out.println("Deposit completed successfully!");
    }


    public void withdraw(String accountNumber, BigDecimal amount) throws AccountNotFoundException,
            AccountInactiveException,
            InvalidAmountException,
            InsufficientBalanceException {
        Account acc=bank.getAccount(accountNumber);
        if(acc==null){
            throw new AccountNotFoundException("Account not found");
        }

        if (acc.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountInactiveException("Account is not active. Withdrawal not allowed.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException( "Withdrawal amount must be greater than zero!");
        }


        boolean success=acc.withdraw(amount);
        if(!success){
            throw new InsufficientBalanceException("Insufficient balance or minimum balance requirement not met!");
        }


        Transaction transaction=new Transaction(
                UUID.randomUUID().toString(),
                accountNumber,
                TransactionType.WITHDRAWAL,
                amount,
                LocalDateTime.now(),
                "Cash Withdrawal"
        );

        bank.addTransaction(transaction);

        System.out.println("Withdrawal completed successfully!");
    }


    public void checkBalance(String accountNumber) throws AccountNotFoundException{
        Account acc=bank.getAccount(accountNumber);
        if(acc==null) {
            throw new AccountNotFoundException("Account not found!");
        }

        System.out.println("\n----- ACCOUNT DETAILS -----");
        System.out.println("Account Number: " + acc.getAccountNumber());
        System.out.println("Customer Name: " + acc.getCustomer().getName());
        System.out.println("Current Balance: " + acc.getBalance());
        System.out.println("Account Status: " + acc.getStatus());
    }


    public void transfer(String fromAccount, String toAccount, BigDecimal amount) throws AccountNotFoundException,
            AccountInactiveException,InvalidAmountException,
            InsufficientBalanceException,InvalidAccountException {
        Account acc1 = bank.getAccount(fromAccount);
        Account acc2 = bank.getAccount(toAccount);

        if (acc1 == null || acc2 == null) {
            throw new AccountNotFoundException("Sender or receiver account not found!");

        }


        if (acc1.getStatus() != AccountStatus.ACTIVE || acc2.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountInactiveException(
                    "Both accounts must be active for transfer!"
            );
        }



        if(fromAccount.equals(toAccount)){
            throw new InvalidAccountException("Cannot transfer to the same account!");

        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(
                    "Transfer amount must be greater than zero!"
            );
        }


        boolean success=acc1.withdraw(amount);
        if (!success) {
            throw new InsufficientBalanceException(
                    "Insufficient balance for transfer!"
            );
        }
        acc2.deposit(amount);

        Transaction senderTransaction =new Transaction(
                UUID.randomUUID().toString(),
                fromAccount,
                TransactionType.TRANSFER,
                amount,
                LocalDateTime.now(),
                "Money Transferred"
        );

        Transaction receivetransaction=new Transaction(
                UUID.randomUUID().toString(),
                toAccount,
                TransactionType.TRANSFER,
                amount,
                LocalDateTime.now(),
                "Money Received"
        );

        bank.addTransaction(senderTransaction);
        bank.addTransaction(receivetransaction);

        System.out.println("Transfer completed successfully!");


    }


    public void registerCustomer(String customerId, String name,
                                 String phone, String email) throws DuplicateCustomerException,
            InvalidAccountException{

        if (customerId == null || customerId.trim().isEmpty()) {
            System.out.println("Customer ID cannot be empty!");
            return;
        }

        if (!Customer.isValidCustomerId(customerId)) {
            throw new InvalidAccountException("Invalid customer ID format!");

        }

        if (bank.getCustomer(customerId) != null) {
            throw new DuplicateCustomerException("Customer ID already exists!");

        }

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAccountException("Customer name cannot be empty!");
        }

        if (!Customer.isValidPhone(phone)) {
            throw new InvalidAccountException("Invalid phone number!");
        }

        if (bank.isPhoneExists(phone)) {
            throw new DuplicateCustomerException("Phone number already registered!");
        }

        if (!Customer.isValidEmail(email)) {
            throw new InvalidAccountException("Invalid email format!");
        }

        if (bank.isEmailExists(email)) {
            throw new DuplicateCustomerException("Email already registered!");
        }

        Customer customer = new Customer(customerId, name, phone, email);
        bank.addCustomer(customer);

        System.out.println("Customer registered successfully!");
    }

    public void openSavingsAccount(String customerId,String accountNumber,BigDecimal initialBalance) throws CustomerNotFoundException,
            DuplicateAccountException, InvalidAccountException{
        Customer existingcustomer=bank.getCustomer(customerId);

        if(existingcustomer==null){
            throw new CustomerNotFoundException("Customer not found. Register first.");
        }

        if (!Account.isValidAccountNumber(accountNumber)) {
            throw new InvalidAccountException("Invalid account number!");
        }

        Account existingAcc=bank.getAccount(accountNumber);
        if(existingAcc!=null){
            throw new DuplicateAccountException("Account already exists!");
        }
        BigDecimal minimumBalance=new BigDecimal("500");

        if (initialBalance == null || initialBalance.compareTo(minimumBalance)<0) {
            throw new InvalidAccountException("Savings account requires a minimum balance of ₹500!");
        }

        BigDecimal interestRate = new BigDecimal("4");

        SavingsAccount savingsAccount=new SavingsAccount(accountNumber,existingcustomer,initialBalance, AccountStatus.ACTIVE,minimumBalance,interestRate);
        bank.addAccount(savingsAccount);
        System.out.println("Savings account opened successfully!");

    }

    public void openCurrentAccount(String customerId, String accountNumber, BigDecimal initialBalance) throws CustomerNotFoundException,
            DuplicateAccountException, InvalidAccountException {

        Customer customer = bank.getCustomer(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found!");
        }

        if (!Account.isValidAccountNumber(accountNumber)) {
            throw new InvalidAccountException("Invalid account number!");
        }

        if (bank.getAccount(accountNumber) != null) {
            throw new DuplicateAccountException("Account already exists!");
        }

        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAccountException("Initial balance cannot be negative!");
        }

        BigDecimal overdraftLimit = new BigDecimal("2000");

        CurrentAccount account = new CurrentAccount(accountNumber, customer, initialBalance, AccountStatus.ACTIVE, overdraftLimit);

        bank.addAccount(account);

        System.out.println("Current account opened successfully!");
    }

    public void viewTransactionHistory(String accountNumber) throws AccountNotFoundException {
        Account acc=bank.getAccount(accountNumber);
        if(acc==null){
            throw new AccountNotFoundException("Account not found");
        }

        bank.displayTransactionHistory(accountNumber);
    }


    public void calculateSavingsInterest(String accountNumber) throws AccountNotFoundException, InvalidAccountException {

        Account acc = bank.getAccount(accountNumber);
        if (acc == null) {
            throw new AccountNotFoundException("Account not found!");
        }


        if (!(acc instanceof SavingsAccount)) {
            throw new InvalidAccountException("Interest calculation is available only for Savings Accounts.");
        }

        SavingsAccount savingsAccount = (SavingsAccount) acc;

        BigDecimal interest = savingsAccount.calculateInterest();

        System.out.println("\n----- SAVINGS INTEREST DETAILS -----");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Current Balance: ₹" + acc.getBalance());
        System.out.println("Interest Rate  : " + savingsAccount.getInterestRate() + "%");
        System.out.println("Interest       : ₹" + interest);

    }
    public void freezeAccount(String accountNumber)  throws AccountNotFoundException, AccountInactiveException{
        Account acc = bank.getAccount(accountNumber);

        if (acc == null) {
            throw new AccountNotFoundException("Account not found!");

        }

        if (acc.getStatus() == AccountStatus.CLOSED) {
            throw new AccountInactiveException("Cannot freeze a closed account!");
        }

        if (acc.getStatus() == AccountStatus.FROZEN) {
            throw new AccountInactiveException("Account is already frozen!");
        }

        acc.setStatus(AccountStatus.FROZEN);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                accountNumber,
                TransactionType.FREEZE,
                BigDecimal.ZERO,
                LocalDateTime.now(),
                "Account Frozen"
        );

        bank.addTransaction(transaction);

        System.out.println("Account frozen successfully!");
    }

    public void activateAccount(String accountNumber) throws AccountNotFoundException, AccountInactiveException{
        Account acc = bank.getAccount(accountNumber);

        if (acc == null) {
            throw new AccountNotFoundException("Account not found!");
        }

        if (acc.getStatus() == AccountStatus.CLOSED) {
            throw new AccountInactiveException("Cannot activate a closed account!");
        }

        if (acc.getStatus() == AccountStatus.ACTIVE) {
            throw new AccountInactiveException("Account is already active!");
        }

        acc.setStatus(AccountStatus.ACTIVE);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                accountNumber,
                TransactionType.ACTIVATION,
                BigDecimal.ZERO,
                LocalDateTime.now(),
                "Account Activated"
        );

        bank.addTransaction(transaction);

        System.out.println("Account activated successfully!");
    }


    public void closeAccount(String accountNumber) throws AccountNotFoundException,
            AccountInactiveException,InvalidAccountException{
        Account acc = bank.getAccount(accountNumber);

        if (acc == null) {
            throw new AccountNotFoundException("Account not found!");
        }

        if (acc.getStatus() == AccountStatus.CLOSED) {
            throw new AccountInactiveException("Account is already closed!");
        }

        if (acc.getBalance().compareTo(BigDecimal.ZERO) > 0) {

            if (acc instanceof SavingsAccount) {

                SavingsAccount savings = (SavingsAccount) acc;

                if (savings.withdrawForClosure()) {

                    // Record the final withdrawal
                    Transaction withdrawalTransaction = new Transaction(
                            UUID.randomUUID().toString(),
                            accountNumber,
                            TransactionType.WITHDRAWAL,
                            savings.getMinimumBalance(),
                            LocalDateTime.now(),
                            "Final minimum balance withdrawn during account closure"
                    );

                    bank.addTransaction(withdrawalTransaction);

                    System.out.println("Final balance ₹"
                            + savings.getMinimumBalance()
                            + " withdrawn successfully.");
                } else {
                    throw new InvalidAccountException(
                            "Withdraw the excess balance first. Only the minimum balance can be withdrawn during closure."
                    );
                }

            } else {
                throw new InvalidAccountException(
                        "Account balance must be zero before closing!"
                );
            }
        }

        acc.setStatus(AccountStatus.CLOSED);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                accountNumber,
                TransactionType.CLOSURE,
                BigDecimal.ZERO,
                LocalDateTime.now(),
                "Account Closed"
        );

        bank.addTransaction(transaction);

        System.out.println("Account closed successfully!");
    }


    public void viewAllAccounts(){
        bank.displayAllAccounts();
    }

    public void displayBankSummary() {

        System.out.println("\n========== BANK SUMMARY ==========");

        System.out.println("Total Customers: " + bank.getTotalCustomers());

        System.out.println("Total Accounts: " + bank.getTotalAccounts());

        System.out.println("Savings Accounts: " + bank.getTotalSavingsAccounts());

        System.out.println("Current Accounts: " + bank.getTotalCurrentAccounts());

        System.out.println("Total Bank Balance: ₹" + bank.getTotalBankBalance());

        System.out.println("==================================");
    }



}
