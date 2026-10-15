package Day1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
      BankAccount aliceAccount =  Bank.createAccount();
      BankAccount bobAccount =  Bank.createAccount();

      System.out.println("AliceAccount :" + aliceAccount.getAccountNumber() );
      aliceAccount.depositAmount(2383L);
      System.out.println("AliceBalance :" + aliceAccount.checkBalance() );
    }
}