package User.UseCase;

import Trade.Entity.Trade;
import Trade.GateWay.TradeDataAccess;
import Trade.UseCase.TradeManager;
import User.Entity.ClientUser;
import User.Gateway.DataAccess;
import User.Gateway.UserDataAccess;
import Trade.TradeStatus;

import java.time.LocalDateTime;
import java.util.*;

/**
 * [UseCase class]
 * The renew and modification of users
 */
public class UserManager {


    DataAccess tradeAccess = new TradeDataAccess();
    DataAccess dataAccess = new UserDataAccess();

    /**
     * Return the list of ClientUser
     *
     * @return list of ClientUser
     */
    public List<ClientUser> getUserList() {
        List<ClientUser> result = new ArrayList<>();
        for (Object o : dataAccess.getList()) {
            result.add((ClientUser) o);
        }
        return result;
    }


    /**
     * Return the ClientUser with such name, or return null if there no such user.
     *
     * @param name the name of the user that the manager wants to get
     *             find the user by the user name
     */
    public ClientUser getUser(String name) {
        return (ClientUser) dataAccess.getObject(name);
    }

    /**
     * @param userId the ID of the user that the manager wants to get
     *               find the user by the user ID
     */
    public ClientUser getUser(UUID userId) {
        return (ClientUser) dataAccess.getObject(userId);
    }


    public ClientUser popUser(UUID id){
        if (dataAccess.hasObject(id)) {
            ClientUser result =  (ClientUser) dataAccess.getObject(id);
            dataAccess.removeObject(id);
            System.out.println(getUserList());
            return result;
        } else {
            return null;
        }
    }

    /**
     * Add a ClientUser
     *
     * @param user the clientUser we want to add
     */
    public void addUser(ClientUser user){
        dataAccess.addObject(user);
    }


    /**
     * @param name     the name of the user that the manager check
     * @param password the password of the user that the manager check
     *                 Check if the name matches with the password
     */
    public boolean verifyUser(String name, String password) {
        dataAccess.deSerialize();
        ClientUser user = getUser(name);
        if (user == null) {
            return false;
        } else {
            return user.getPassword().equals(password);
        }
    }


    /**
     * Create a ClientUser with name, password, isAdmin; and save it to .ser file
     *
     * @param name     the name of the clientUser
     * @param password the password of the clientUser
     * @param isAdmin  if the clientUser is admin
     */
    public boolean createClientUser(String name, String password, boolean isAdmin) {
        if((getUser(name)==null)) {
            dataAccess.addObject(new ClientUser(name, password, isAdmin));
            return true;
        }
        else{
            return false;
        }
    }


    public int readDiff(String username) {
        ClientUser user = getUser(username);
        return user.getLendCounter() - user.getBorrowCounter();
    }

    /**
     * return the list of most frequent three traders that the user trades with
     * if the user trades with less than three traders, return all the traders the user trades with
     */
    public List<String> getFrequentUser(UUID userID) {
        TradeManager tm = new TradeManager();
        ClientUser user = getUser(userID);

        List<Trade> a = tm.getAllTrade(userID);
        HashMap<UUID, Integer> b = new HashMap<>();
        for (Trade c : a) {
            for (UUID d : c.getUsers()) {
                if (!(d.equals(user.getId()))) {
                    if (b.containsKey(d)) {
                        b.replace(d, b.get(d) + 1);
                    } else {
                        b.put(d, 1);
                    }
                }
            }
        }
        if(b.size() == 0){
            List<String> EmptyList = new ArrayList<>(Collections.emptyList());
            EmptyList.add("no user");
            return EmptyList;}
        int e = 0;
        ArrayList<String> g = new ArrayList<>();
        int maxValueInMap = (Collections.max(b.values()));
        for (Map.Entry<UUID, Integer> entry : b.entrySet()) {
            if (entry.getValue() == maxValueInMap && e != 3) {
                g.add(getUser(entry.getKey()).getUsername());
                e++;
                b.remove(entry.getKey());
            }
        }
        return g;
    }

    //FINISHED
    public boolean getIsAdmin(String a){return getUser(a).getIsAdmin();}

    //FINISHED
    public List<String> getWishLend(UUID a) {
        return getUser(a).getWishLend();
    }
    //FINISHED
    public List<String> getWishBorrow(UUID a) {
        return getUser(a).getWishBorrow();
    }
    //finished
    public int getWeekTransactionLimit(String a) {
        dataAccess.deSerialize();
        dataAccess.updateSer();
        return getUser(a).getWeekTransactionLimit();
    }
    //finished
    public int getIncompleteTransactionLimit(String a) {
        dataAccess.deSerialize();
        dataAccess.updateSer();
        return getUser(a).getIncompleteTransactionLimit();
    }

    //finished
    public int getDiff(String a) {
        dataAccess.deSerialize();
        dataAccess.updateSer();
        return getUser(a).getDiff();
    }
    //finished
    public String getPassword(ClientUser a) {
        dataAccess.deSerialize();
        dataAccess.updateSer();
        return a.getPassword();
    }

    //finished
    public String getUsername(UUID a){
        return getUser(a).getUsername();}

    //finished
    public UUID getId(ClientUser a) {
        return a.getId();
    }

    public void setPassword(String name, String password) {
        ClientUser a = popUser(nameToUUID(name));
        a.setPassword(password);
        addUser(a);
    }

    //finished
    public boolean getIsFrozen(UUID userID){
        return getUser(userID).getIsFrozen();
    }

    public ArrayList<ArrayList<String>> getActions(ClientUser a) {
        return a.getActions();
    }

    public String UUIDToName(UUID userID){
        return getUser(userID).getUsername();
    }

    public UUID nameToUUID(String name){
        return getUser(name).getId();
    }

    public List<UUID> getSelectedBonusTrades(UUID userID){
        return getUser(userID).getSelectedBonusTrades();
    }

    public int getTradeNumber(String username) {
        ClientUser user = (ClientUser) dataAccess.getObject(username);

        if(user.getTradeHistory().size() == 0){return 0;}
        Trade s = getTrade(user.getTradeHistory().get(user.getTradeHistory().size() - 1));
        LocalDateTime x  = s.getCreateTime();
        LocalDateTime y = x.minusDays(7);
        int number = 1;
        for (UUID i : user.getTradeHistory()){
            if(getTrade(i).getCreateTime().isAfter(y) && getTrade(i).getCreateTime().isBefore(x)){
                number ++;
            }
        }
        return number;
    }

    public void deleteBItem(String username,String it){
        ClientUser user = popUser(getUser(username).getId());
        user.getWishBorrow().remove(it);
        addUser(user);
    }
    public void deleteLItem(String username,String it){
        ClientUser user = popUser(getUser(username).getId());
        user.getWishLend().remove(it);
        addUser(user);
    }

    public Trade getTrade(UUID id) {
        return (Trade) tradeAccess.getObject(id);
    }

    public int getIncompleteTransaction(UUID userId) {
        ClientUser user = (ClientUser) dataAccess.getObject(userId);

        int number = 0;
        for (UUID i : user.getTradeHistory()) {

            if (getTrade(i).getStatus().equals(TradeStatus.incomplete)) {
                number++;
            }
        }
        return number;
    }

    public void addAction(String username,String type,String preValue){
        ClientUser a = popUser(nameToUUID(username));
        a.addAction(type,preValue);
        addUser(a);
    }

    public void removeAcition(String username,String type,String preValue){
        ClientUser a = popUser(nameToUUID(username));
        a.removeAction(type,preValue);
        addUser(a);
    }
}

