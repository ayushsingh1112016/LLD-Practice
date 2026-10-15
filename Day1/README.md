# Day 1 – Bank Account System

## Problem Statement

Design a minimal banking system where:

- Customers can open an account and get a **unique account number** automatically.
- An account supports **deposit**, **withdraw** and **balance check**.
- Invalid operations (zero or negative amounts, withdrawing more than the balance) must be rejected with a clear error.
- An account has a **status** (`ACTIVE`, `SUSPENDED`, `CLOSED`), new accounts start `ACTIVE`; only `ACTIVE` accounts can transact.
- Account number generation and balance updates must be thread-safe.

## Solution

| File | Responsibility |
|------|----------------|
| [Bank.java](Bank.java) | Creates accounts and hands out unique account numbers (starting at 1000). |
| [BankAccount.java](BankAccount.java) | Holds account number, balance and status; exposes deposit / withdraw / checkBalance, enforces validation and status rules. |
| [Main.java](Main.java) | Demo: creates accounts for Alice and Bob, deposits and prints balance. |

### Flow

1. `Main` calls `Bank.createAccount()`.
2. `Bank` takes the next number from a shared `AtomicLong` sequence (`getAndIncrement`).
3. `Bank` builds and returns `new BankAccount(accountNumber)` with status `ACTIVE` and balance `0`.
4. The caller uses the returned `BankAccount` to deposit, withdraw and check balance.
5. Each deposit/withdraw first checks the status (`canTransact()`), then validates the amount; failures throw.

## Pattern Used

### Static Factory Method (Simple Factory)

`Bank.createAccount()` is a static factory method. Callers never call `new BankAccount(...)` themselves, so they don't need to know how an account number is produced.

```java
public class Bank {
  private static final AtomicLong accountSequence = new AtomicLong(1000L);

  public static BankAccount createAccount() {
    Long accountNumber = accountSequence.getAndIncrement();
    return new BankAccount(accountNumber);
  }
}
```

**Why it fits here**

- **Centralised creation:** account-number generation lives in one place (`Bank`), not scattered across callers.
- **Uniqueness:** the shared `AtomicLong` guarantees unique, thread-safe numbers without explicit locking.
- **Easy to extend:** adding account types (savings, current) or validation later only changes `Bank`, not the callers.

### Enum-based State (lightweight State pattern)

Whether an account may transact depends on its status, so that rule lives on the status itself instead of `if/else` chains scattered in the account.

```java
public enum AccountStatus {
  ACTIVE(true), SUSPENDED(false), CLOSED(false);

  private final boolean canTransact;
  AccountStatus(boolean canTransact) { this.canTransact = canTransact; }
  public boolean canTransact() { return canTransact; }
}
```

`BankAccount.ensureCanTransact()` asks the status and throws `IllegalStateException` if the account is not `ACTIVE`. If statuses later need different behaviour (e.g. a suspended account allows deposits only), this can grow into full State classes.

### Supporting principles

- **Encapsulation:** `accountNumber` is `final` with no setter; `amount` and `status` are private and only changed through `depositAmount` / `withdrawAmount` / `setStatus`.
- **Fail-fast validation:** invalid amounts and overdrafts throw `IllegalArgumentException`; non-active accounts throw `IllegalStateException`. Errors propagate to the caller instead of being swallowed.
- **Thread safety:** `BankAccount` methods that read or change balance/status are `synchronized`; account numbers come from an `AtomicLong`.

## Possible Next Steps

- Add transfer between accounts (needs consistent lock ordering to avoid deadlock).
- Split `AccountStatus` into State classes if behaviour per status diverges.
- Add account types (savings, current) through the factory.
