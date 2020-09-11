package edu.coursera.concurrent;

public class BankTransactionUsingLockOrdering extends ThreadSafeBankTransaction {

  private static final Object tieLock = new Object();

  @Override
  public void issueTransfer(int amount, Account src, Account dst) {
    int fromHash = System.identityHashCode(src);
    int toHash = System.identityHashCode(dst);
    if(fromHash < toHash){
      synchronized (src){
        synchronized (dst){
          src.performTransfer(amount,dst);
        }
      }
    }
    else if(fromHash > toHash){
      synchronized (dst){
        synchronized (src){
          src.performTransfer(amount,dst);
        }
      }
    }
    else{
      synchronized (tieLock){
        synchronized (src){
          synchronized (dst){
            src.performTransfer(amount,dst);
          }
        }
      }
    }
  }
}
