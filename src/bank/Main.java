package bank;

import bank.exceptions.*;
import bank.service.Bank;
import bank.service.BankingService;

import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();
        BankingService service = new BankingService(bank);
        Scanner sc = new Scanner(System.in);

        boolean exit = false;

        do {
            System.out.println("\n================================");
            System.out.println("   WELCOME TO JAVA BANKING SYSTEM");
            System.out.println("================================");

            System.out.println("\n========== BANKING SYSTEM ==========");
            System.out.println("1. Register Customer");
            System.out.println("2. Open Savings Account");
            System.out.println("3. Open Current Account");
            System.out.println("4. Deposit Money");
            System.out.println("5. Withdraw Money");
            System.out.println("6. Transfer Money");
            System.out.println("7. Check Balance");
            System.out.println("8. View Transaction History");
            System.out.println("9. Calculate Savings Interest");
            System.out.println("10. Freeze Account");
            System.out.println("11. Activate Account");
            System.out.println("12. Close Account");
            System.out.println("13. View All Accounts");
            System.out.println("14. Display Bank Summary");
            System.out.println("15. Exit");
            System.out.println("====================================");

            int choice = 0;

            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {

                case 1: {
                    try {
                        System.out.print("Enter Customer ID: ");
                        String customerId = sc.next();

                        sc.nextLine();

                        System.out.print("Enter Customer Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Phone: ");
                        String phone = sc.next();

                        System.out.print("Enter Email: ");
                        String email = sc.next();

                        service.registerCustomer(customerId, name, phone, email);

                    } catch (DuplicateCustomerException |
                             InvalidAccountException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }

                case 2: {
                    try {
                        System.out.print("Enter Customer ID: ");
                        String customerId = sc.next();

                        System.out.print("Enter Account Number: ");
                        String accountNumber = sc.next();

                        System.out.print("Enter Initial Balance: ");
                        BigDecimal initialBalance = sc.nextBigDecimal();

                        service.openSavingsAccount(
                                customerId,accountNumber, initialBalance
                        );

                    } catch (CustomerNotFoundException |
                             DuplicateAccountException |
                             InvalidAccountException e) {
                        System.out.println("Error: " + e.getMessage());

                    } catch (InputMismatchException e) {
                        System.out.println("Invalid balance! Enter a numeric amount.");
                        sc.nextLine();
                    }
                    break;
                }

                case 3: {
                    try {
                        System.out.print("Enter Customer ID: ");
                        String customerId = sc.next();

                        System.out.print("Enter Account Number: ");
                        String accountNumber = sc.next();

                        System.out.print("Enter Initial Balance: ");
                        BigDecimal initialBalance = sc.nextBigDecimal();

                        service.openCurrentAccount(
                                 customerId,accountNumber, initialBalance
                        );

                    } catch (CustomerNotFoundException |
                             DuplicateAccountException |
                             InvalidAccountException e) {
                        System.out.println("Error: " + e.getMessage());

                    } catch (InputMismatchException e) {
                        System.out.println("Invalid balance! Enter a numeric amount.");
                        sc.nextLine();
                    }
                    break;
                }

                case 4: {
                    try {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = sc.next();

                        System.out.print("Enter Deposit Amount: ");
                        BigDecimal amount = sc.nextBigDecimal();

                        service.deposit(accountNumber, amount);

                    } catch (AccountNotFoundException |
                             AccountInactiveException |
                             InvalidAmountException e) {
                        System.out.println("Error: " + e.getMessage());

                    } catch (InputMismatchException e) {
                        System.out.println("Invalid amount! Enter a numeric value.");
                        sc.nextLine();
                    }
                    break;
                }

                case 5: {
                    try {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = sc.next();

                        System.out.print("Enter Withdrawal Amount: ");
                        BigDecimal amount = sc.nextBigDecimal();

                        service.withdraw(accountNumber, amount);

                    } catch (AccountNotFoundException |
                             AccountInactiveException |
                             InvalidAmountException |
                             InsufficientBalanceException e) {
                        System.out.println("Error: " + e.getMessage());

                    } catch (InputMismatchException e) {
                        System.out.println("Invalid amount! Enter a numeric value.");
                        sc.nextLine();
                    }
                    break;
                }

                case 6: {
                    try {
                        System.out.print("Enter Sender Account Number: ");
                        String senderAccount = sc.next();

                        System.out.print("Enter Receiver Account Number: ");
                        String receiverAccount = sc.next();

                        System.out.print("Enter Transfer Amount: ");
                        BigDecimal amount = sc.nextBigDecimal();

                        service.transfer(
                                senderAccount, receiverAccount, amount
                        );

                    } catch (AccountNotFoundException |
                             AccountInactiveException |
                             InvalidAmountException |
                             InsufficientBalanceException |
                             InvalidAccountException e) {
                        System.out.println("Error: " + e.getMessage());

                    } catch (InputMismatchException e) {
                        System.out.println("Invalid amount! Enter a numeric value.");
                        sc.nextLine();
                    }
                    break;
                }

                case 7: {
                    try {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = sc.next();

                        service.checkBalance(accountNumber);

                    } catch (AccountNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }

                case 8: {
                    try {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = sc.next();

                        service.viewTransactionHistory(accountNumber);

                    } catch (AccountNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }

                case 9: {
                    try {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = sc.next();

                        service.calculateSavingsInterest(accountNumber);

                    } catch (AccountNotFoundException |
                             InvalidAccountException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }

                case 10: {
                    try {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = sc.next();

                        service.freezeAccount(accountNumber);

                    } catch (AccountNotFoundException |
                             AccountInactiveException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }

                case 11: {
                    try {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = sc.next();

                        service.activateAccount(accountNumber);

                    } catch (AccountNotFoundException |
                             AccountInactiveException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }

                case 12: {
                    try {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = sc.next();

                        service.closeAccount(accountNumber);

                    } catch (AccountNotFoundException |
                             AccountInactiveException |
                             InvalidAccountException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }

                case 13:
                    service.viewAllAccounts();
                    break;

                case 14:
                    service.displayBankSummary();
                    break;

                case 15:
                    exit = true;
                    System.out.println("Thank you for using Java Banking System!");
                    break;

                default:
                    System.out.println("Invalid choice! Please select 1-15.");
                    break;
            }

        } while (!exit);

        sc.close();
    }
}
