package bank.service;

import java.math.BigDecimal;
import java.util.HashMap;

import bank.model.*;

import java.util.ArrayList;


public class Bank {
    private HashMap<String, Account> accounts;
    private HashMap<String, Customer> customers;

    private ArrayList<Transaction> transactions;

    public Bank(){
        this.accounts=new HashMap<>();
        this.customers=new HashMap<>();
        this.transactions=new ArrayList<>();
    }

    public void addCustomer(Customer customer){
        this.customers.put(customer.getCustomerId(),customer);
    }

    public void addAccount(Account account){
        this.accounts.put(account.getAccountNumber(),account);
    }

    public Customer getCustomer(String customerId){
        return customers.get(customerId);
    }

    public Account getAccount(String accountNumber){
        return accounts.get(accountNumber);
    }


    public void displayAllAccounts(){
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }
        for(Account acc:accounts.values()){
            System.out.println(acc);
        }
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public void displayTransactionHistory(String accountNumber){
        boolean found=false;
        for(Transaction t:transactions){
            if(t.getAccountNumber().equals(accountNumber)){
                System.out.println(t);
                found=true;
            }
        }
        if(!found) System.out.println("No transaction history found.");

    }

    public int getTotalCustomers(){
        return customers.size();
    }
    public int getTotalAccounts(){
        return accounts.size();
    }


    public int getTotalSavingsAccounts() {
        int count = 0;

        for (Account acc : accounts.values()) {
            if (acc instanceof SavingsAccount) {
                count++;
            }
        }

        return count;
    }


    public int getTotalCurrentAccounts() {
        int count = 0;

        for (Account acc : accounts.values()) {
            if (acc instanceof CurrentAccount) {
                count++;
            }
        }

        return count;
    }

    public BigDecimal getTotalBankBalance() {
        BigDecimal total = BigDecimal.ZERO;

        for (Account acc : accounts.values()) {
            total = total.add(acc.getBalance());
        }

        return total;
    }

    public boolean isPhoneExists(String phone) {
        for (Customer customer : customers.values()) {
            if (customer.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmailExists(String email) {
        for (Customer customer : customers.values()) {
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }


}
