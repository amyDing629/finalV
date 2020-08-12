package User.Adapter;

import User.Entity.ClientUser;
import User.UseCase.AdminActivityManager;

import java.io.FileNotFoundException;

public class AdminController extends ClientUserController {
    private final AdminActivityManager am;


    public AdminController() {
        super();
        this.am = new AdminActivityManager();

    }

    public AdminActivityManager getAm() {
        return am;
    }


    public void checkFrozen(String username) throws FileNotFoundException {
        if(um.readDiff(username)>=um.getDiff(username)){
            System.out.println("You have been freeze due to exceed difference between borrow and lend");
            am.setFreeze(username,true);
        }
        else if(am.incompleteTransaction(um.nameToUUID(username))){
            System.out.println("You have been freeze due to maximum incomplete transaction");
            am.setFreeze(username,true);
        }
        else if(am.tradeLimit(username)){
            System.out.println("You have been freeze due to maximum trade limit");
            am.setFreeze(username,true);
        }
    }

    public void setDiff(String username,int diff) {
        am.setDiff(username, diff);
    }

    public void createAdmin(String name, String password) throws FileNotFoundException {
        am.addNewAdmin(name, password);
    }

    public void setFreeze(String a,boolean s){
        am.setFreeze(a, s);
    }

    public void setIncompleteTransaction(String username,int incompleteTransaction) {
        ClientUser user = um.popUser(um.nameToUUID(username));
        user.setIncompleteTransaction(incompleteTransaction);
        um.addUser(user);
    }

    public void setWeekTransactionLimit(String username, int weekTransaction){
        ClientUser user = um.popUser(um.nameToUUID(username));
        user.setWeekTransactionLimit(weekTransaction);
        um.addUser(user);
    }

    public void setExchangeStandard(int exStandard) {
        am.setExchangeStandard(exStandard);
    }


}
