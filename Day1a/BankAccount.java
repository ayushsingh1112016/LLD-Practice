package Day1a;

public class BankAccount {

  private final Long accountNumber;
  private long amount = 0L;

  public enum AccountStatus {
    ACTIVE(true), SUSPENDED(false), CLOSED(false);

    private final boolean canTransact;

    AccountStatus(boolean canTransact) {
      this.canTransact = canTransact;
    }

    public boolean canTransact() {
      return canTransact;
    }
  }

  private AccountStatus status;

  public BankAccount(Long accountNumber) {
    this.accountNumber = accountNumber;
    this.status = AccountStatus.ACTIVE;
  }

  public Long getAccountNumber() {
    return this.accountNumber;
  }

  public synchronized AccountStatus getStatus() {
    return this.status;
  }

  public synchronized void setStatus(AccountStatus status) {
    this.status = status;
  }

  public synchronized Long checkBalance() {
    return this.amount;
  }

  public synchronized void depositAmount(Long amount) {
    ensureCanTransact();
    if (amount == null || amount <= 0) {
      throw new IllegalArgumentException("amount is invalid");
    }
    this.amount += amount;
  }

  public synchronized void withdrawAmount(Long amount) {
    ensureCanTransact();
    if (amount == null || amount <= 0) {
      throw new IllegalArgumentException("amount is invalid");
    }
    if (amount > this.amount) {
      throw new IllegalArgumentException("insufficient balance");
    }
    this.amount -= amount;
  }

  private void ensureCanTransact() {
    if (!status.canTransact()) {
      throw new IllegalStateException("account is " + status);
    }
  }
}
