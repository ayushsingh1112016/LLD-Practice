package Day1;

import java.util.concurrent.atomic.AtomicLong;

public class Bank {
  private static final AtomicLong accountSequence = new AtomicLong(1000L);

  public static BankAccount createAccount() {
    Long accountNumber = accountSequence.getAndIncrement();
    return new BankAccount(accountNumber);
  }

}
