
import org.example.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public aspect AccountAspect {
    private static final Logger logger = LoggerFactory.getLogger(AccountAspect.class);

    //Method call withdraw on target Account
    pointcut callWithdraw(int amount, Account account):
            call(boolean withdraw(int)) && args(amount) && target(account);

    //Any method execution on target Account
    pointcut executionAny(Account account): execution(* *(..)) && target(account);

    //Any method call except getBalance on target Account
    pointcut callAnyExceptBalance(Account account): call(* *(..)) && !call(* getBalance()) && target(account);

    before(Account account) : executionAny(account){
        logger.debug("Pointcut executionAny  {} to: {}", thisJoinPoint.getKind(), thisJoinPoint.getSignature().getName());
    }

     before(Account account) : callAnyExceptBalance(account){
         logger.debug("Pointcut callAnyExceptBalance {} to: {}", thisJoinPoint.getKind(), thisJoinPoint.getSignature().getName());
     }

     before(int amount, Account account) : callWithdraw(amount, account) {
         logger.debug("Pointcut callWithdraw - Balance before withdrawal: {} Withdraw amount:", account.getBalance(), amount);
     }

    boolean around(int amount, Account account) : callWithdraw(amount, account) {
        if (account.getBalance() < amount) {
            logger.debug("Withdrawal Rejected!");
            return false;
        }
        return proceed(amount, account);
    }

    after(int amount, Account account) : callWithdraw(amount, account) {
        logger.debug("Balance after withdrawal : {}", account.getBalance());
    }

   after(Account account) : executionAny(account){
        logger.debug("After call to: {}",  thisJoinPoint.getSignature().getName());
    }

}