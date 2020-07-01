import java.time.LocalDateTime;
import java.util.*;
public class ClientUser extends User {
    private String password;
    private String username;
    private Integer id;

    private boolean isFrozen;
    private List<String> notification;
    private int weekTransactionLimit;
    private int incompleteTransactionLimit;

    //wish list
    private List<String> wishLend;
    private List<String> wishBorrow;

    private List<String> lend;
    private List<String> borrowed;

    private boolean isBorrow;

    //Inventory

    //tradeHistory
    private List<Trade> tradeHistory;
    private boolean isAdmin;

    public ClientUser(String username, String password ,List<String> notification,Boolean isAdmin){
        super(username, password, notification, isAdmin);
        isBorrow=true;
    }

    public void setBorrow(boolean borrow) {
        isBorrow = borrow;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIncompleteTransactionLimit() {
        return incompleteTransactionLimit;
    }

    public int getWeekTransactiionLimit() {
        return weekTransactionLimit;
    }

    public void setIncompleteTransaction(int incompleteTransaction) {
        this.incompleteTransactionLimit = incompleteTransaction;
    }

    public void setWeekTransactiionLimit(int weekTransactiion) {
        this.weekTransactionLimit = weekTransactiion;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public List<String> getLend() {
        return lend;
    }

    public List<String> getBorrowed() {
        return borrowed;
    }

    @Override
    public List<String> getNotification() {
        return notification;
    }

    @Override
    public void addNotification(String no) {
        notification.add(no);
    }

    @Override
    public void changePassword(String password) {
        this.password=password;
    }

    @Override
    public void changeUsername(String username) {
        this.username=username;
    }

    @Override
    public void setWishLend(ArrayList<String> lineList2) {

    }

    @Override
    public void setWishBorrow(ArrayList<String> lineList3) {

    }

    @Override
    public void setTradeHistory(ArrayList<Trade> lineList5) {

    }

    @Override
    public void setFrozen(boolean aTrue) {

    }

    public boolean getIsFrozen(){
        return isFrozen;
    }
    public void setIsFrozen(boolean a){isFrozen = a;}

    public List<String> getWishLend() {
        return wishLend;
    }
    public List<String> getWishBorrow() {
        return wishBorrow;
    }

    public List<Trade> getTradeHistory() {
        List<Trade> trade=new ArrayList<>();
        for (int i=0;i<3;i++){
            trade.add(tradeHistory.get(tradeHistory.size()-1-i));
        }
        return trade;
    }


    public int getIncompleteTransaction(){
        int number=0;
        for (Trade trade : tradeHistory) {
            if (trade.status.equals("incomplete")) {
                number++;
            }
        }
        return number;
    }

    public int getTradeNumber(){
        Trade s = tradeHistory.get(tradeHistory.size() - 1);
        LocalDateTime x  = s.getCreateTime();
        LocalDateTime y = x.minusDays(7);
        int number = 0;
        for (Trade trade : tradeHistory){
            if(trade.getMeeting().getDateTime().isAfter(y) && trade.getMeeting().getDateTime().isBefore(x)){
                number ++;
            }
        }
        return number;
    }

    public boolean getIsAdmin(){
        return isAdmin;
    }

    public void addWishLend(String wish) {
        wishLend.add(wish);
    }
    public void addWishBorrow(String wish) {
        wishBorrow.add(wish);
    }
    public void addTradeHistory(String history) {
        wishBorrow.add(history);
    }
}
