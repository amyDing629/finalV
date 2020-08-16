package User.UseCase;

import Trade.UseCase.TradeManager;
import User.Entity.ClientUser;
import User.Gateway.DataAccess;
import User.Gateway.UserDataAccess;
import User.PointSystem.PointManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * This is the use case class of administrative user
 * Administrative user are allowed to
 */

public class AdminActivityManager {
    UserManager um = new UserManager();
    TradeManager tm = new TradeManager();
    DataAccess userAccess = new UserDataAccess();


    /**
     * @param a the user that the administrative user wants to set frozen
     * set the user.ClientUser a account frozen
     */
//    public void setFreeze(ClientUser a,boolean s) {
//        a.setFrozen(s);
//    }

    public void setFreeze(String a,boolean s){
        ClientUser ca = (ClientUser)userAccess.getObject(a);
        if(ca != null){ca.setFrozen(s);}
        userAccess.updateSer();
    }

    /**
     * @param username the name of the administrative user that wants to add
     * @param password the password of the administrative user that wants to add
     * the initial administrative user can add the new administrative user
     */
//    public void addNewAdmin(ClientUser user,String username, String password) {
//        if(user.getUsername().equals("admin")) {
//            ClientUser admin = new ClientUser(username, password, true);
//            userAccess.addObject(admin);
//        }
//    }

    public void addNewAdmin(String username, String password) {
        if(userAccess.getObject("admin")!= null) {
            um.addUser(new ClientUser(username, password, true));
        }
    }


//    /**
//     * Change thresholds for a particular user upon selection
//     * @param username the username of this particular user
//     * @param newLimit the new threshold that the admin wants to adjust to
//     * @param whichToChange indicates the threshold to adjust
//     */
//    public void AdjustThreshold(String username, int newLimit, int whichToChange) {
//        List<Object> b = userAccess.getList();
//        for (Object u: b) {
//            ClientUser a = (ClientUser)u;
//            if (a.getUsername().equals(username)) {
//                if (whichToChange == 1) {
//                    a.setDiff(newLimit);
//                }if (whichToChange == 2) {
//                    a.setWeekTransactionLimit(newLimit);
//                }if (whichToChange == 3) {
//                    a.setIncompleteTransaction(newLimit);
//                }
//
//            }
//        }
//        userAccess.setList(b);
//    }

//    /**
//     * Adjust the limit of lent-borrow difference threshold of a particular user
//     * @param username the username of the user
//     * @param newDiff the new threshold that the admin wants to adjust to
//     */
//    public void AdjustDiffForUser(String username, int newDiff) {
//        List<Object> b = userAccess.getList();
//        for (Object u: b) {
//            ClientUser a = (ClientUser)u;
//            if (a.getUsername().equals(username)) {
//                a.setDiff(newDiff);
//            }
//        }
//        userAccess.setList(b);
//    }

//    /**
//     * Adjust the limit of lent-borrow difference threshold of a particular user
//     * @param newDiff the new threshold that the admin wants to adjust to
//     */
//    public void AdjustDiffForAll(int newDiff) {
//        List<Object> b = userAccess.getList();
//        for (Object u: b) {
//            ClientUser a = (ClientUser)u;
//            a.setDiff(newDiff);
//        }
//        userAccess.setList(b);
//    }
//
//    /**
//     * Change the weekly transaction limit for a particular user
//     * @param newLimit the new threshold that the admin wants to adjust to
//     */
//    public void AdjustWeeklyLimit(String username, int newLimit) {
//        List<Object> b = userAccess.getList();
//        for (Object u: b) {
//            ClientUser a = (ClientUser)u;
//            if (a.getUsername().equals(username)) {
//                a.setWeekTransactionLimit(newLimit);
//            }
//        }
//        userAccess.setList(b);
//    }
//
//    /**
//     * Change the incomplete transaction limit for a particular user
//     * @param newLimit the new threshold that the admin wants to adjust to
//     */
//    public void AdjustIncompleteLimit(String username, int newLimit) {
//        List<Object> b = userAccess.getList();
//        for (Object u: b) {
//            ClientUser a = (ClientUser)u;
//            if (a.getUsername().equals(username)) {
//                a.setIncompleteTransaction(newLimit);
//            }
//        }
//        userAccess.setList(b);
//    }

    /**
     * @param username the user that the administrative user wants to check the incomplete transaction limit
     * set the account of user a frozen if a has exceeded the incomplete transaction limit
     */
    public boolean incompleteTransaction(UUID username){
        int tl = tm.getIncompleteTransaction(username);
        ClientUser a = um.popUser(username);
        boolean setFrozen;
        if(!a.getIsFrozen()) {
            setFrozen = (tl > a.getIncompleteTransactionLimit());
            if (setFrozen) {
                a.setFrozen(true);
            }
        }else{
            setFrozen = false;
        }
        um.addUser(a);
        return setFrozen;
    }

//    public boolean checkLeft(String username){
//        ClientUser a = (ClientUser) userAccess.getObject(username);
//        LocalDateTime now = LocalDateTime.now();
//        if(!a.getIsLeft()){
//            a.setLeft(now.isBefore(a.getEnd()));
//            return true;
//        }
//        return false;
//    }

    /**
     * @param username the user name that the administrative user wants to check the transaction limit
     * set the user.ClientUser a account frozen a has exceeded the week transaction limit
     */
    public boolean tradeLimit(UUID username){
        int tl = tm.getTradeNumber(username);
        ClientUser a = um.popUser(username);
        boolean setFrozen;
        if(!a.getIsFrozen()) {
            setFrozen = (tl > a.getWeekTransactionLimit());
            if (setFrozen) {
                a.setFrozen(true);
            }
        }else{
            setFrozen = false;
        }
        um.addUser(a);
        return setFrozen;
    }

    public void setDiff(String username, int diff) {
        um.getUser(username).setDiff(diff);
    }

    public void setWeekTransactionLimit(String username,int weekTransaction){
        userAccess.deSerialize();
        um.getUser(username).setWeekTransactionLimit(weekTransaction);
        userAccess.updateSer();
    }
    public void setIncompleteTransaction(String username,int incompleteTransaction) {
        userAccess.deSerialize();
        um.getUser(username).setIncompleteTransaction(incompleteTransaction);
        userAccess.updateSer();
    }
    public void setExchangeStandard(int exStandard) {
        userAccess.deSerialize();
        //pm.setExStandard(exStandard);
        userAccess.updateSer();
    }

//    /**
//     * @param userId The ID of the administrative user.
//     * get the administrative user by the user ID
//     */
//    public ClientUser getAdmin(UUID userId){
//        List<Object> b = userAccess.getList();
//        for(Object u: b) {
//            ClientUser a = (ClientUser)u;
//            if(a.getId().equals(userId) && a.getIsAdmin()) {
//                return a;
//            }
//        }
//        return null;
//    }



}
