import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.UUID;
public class User {
    protected UUID id;
    protected String password;
    protected String username;
    private List<String> notification;
    private boolean isAdmin;
    private List<String> wishLend;
    private List<String> wishBorrow;
    private boolean isBorrow;
    private boolean isFrozen;
    protected List<UUID> tradeHistory;
    private int weekTransactionLimit;
    private int incompleteTransactionLimit;
    private List<String> lend;
    private List<String> borrowed;

    public User(String username, String password, boolean isAdmin){
        this.username = username;
        this.password = password;
        //this.id = id;
        this.notification=new ArrayList<>();
        this.wishLend= new ArrayList<>();
        this.wishBorrow= new ArrayList<>();
        this.tradeHistory=new ArrayList<>();
        this.isAdmin = isAdmin;
        id = UUID.randomUUID();
    }


    public String getPassword() {
        return password;
    }
    public String getUsername(){return username;}
    public UUID getId() {
        return id;
    }

    public boolean getIsFrozen(){
        return isFrozen;
    }

    public List<String> getNotification(){return notification;}

    public void setIncompleteTransaction(int incompleteTransaction) {
        this.incompleteTransactionLimit = incompleteTransaction;
    }

    public void setId(UUID a){this.id = a;}

    public void setWeekTransactionLimit(int weekTransaction){
        this.weekTransactionLimit = weekTransaction;
    }

    public void addWishes(String hi){
        this.wishLend.add(hi);
    }

    public void removeBWishes(String hi){
        this.wishBorrow.remove(hi);
    }

    public void removeLWishes(String hi){
        this.wishLend.remove(hi);
    }

    public void setWishLend(List<String> wishLend){
        this.wishLend = wishLend;
    }

    public void setNotification(List<String> notification){
        this.notification = notification;
    }

    public void setTradeHistory(List<UUID> tradeHistory) {
        this.tradeHistory = tradeHistory;
    }

    public List<UUID> getTradeHistory(){
        return tradeHistory;
    }

    public boolean getIsAdmin(){return isAdmin;}

    public List<String> getWishLend() {
        return wishLend;
    }
    public List<String> getWishBorrow() {
        return wishBorrow;
    }
    public void setFrozen(boolean aTrue){
        isFrozen = aTrue;
    }

    public void setBorrow(boolean borrow){

        isBorrow = borrow;
    }

    public List<Trade> getAllTrade(){
        TradeManager a = new TradeManager();
        ArrayList<Trade> b = new ArrayList<>();
        for(UUID i: tradeHistory){
            b.add(a.getTrade(i));
        }
        return b;
    }

    public List<Trade> getUnconfirmed(){
        List<Trade> trade=new ArrayList<>();
        for(Trade t: getAllTrade()){
            if(t.status.equals("unconfirmed")){
                trade.add(t);
            }
        }
        return trade;
    }

    public List<Trade> getIncomplete(){
        List<Trade> trade=new ArrayList<>();
        for(Trade t: getAllTrade()){
            if(t.status.equals("incomplete")){
                trade.add(t);
            }
        }
        return trade;
    }

    public List<Trade> getTradeHistoryTop() {
        List<Trade> trade=new ArrayList<>();
        TradeManager a = new TradeManager();
        int y = 0;
        while(y <  3) {
            for (int i = getAllTrade().size(); i>0;i-- ) {
                if ((!(getAllTrade().get(i).status.equals("unconfirmed"))) || (!(getAllTrade().get(i).status.equals("cancelled")))) {
                    trade.add(getAllTrade().get(i));
                    y++;
                }
                i = i - 1;
            }
        }
        return trade;
    }

    public void decideTrade(boolean a, Trade b){
        if(a){b.setStatus("incomplete");}
        else {b.setStatus("cancelled");}
    }

    public void addNotification(String no){
        notification.add(no);
    }

    public int getWeekTransactionLimit() {
        return weekTransactionLimit;
    }

    public int getIncompleteTransactionLimit() {
        return incompleteTransactionLimit;
    }

    public void setWishBorrow(ArrayList<String> lineList3) {
        wishBorrow = lineList3;
    }

    public void setUsername(String input1) {
        username=input1;
    }

    public void setPassword(String input2) {
        password=input2;
    }

    public int getIncompleteTransaction(){
        TradeManager a = new TradeManager();
        int number=0;
        for (UUID i : tradeHistory) {
            if (a.getTrade(i).status.equals("incomplete")) {
                number++;
            }
        }
        return number;
    }

    public int getTradeNumber(){
        TradeManager a = new TradeManager();
        if(tradeHistory.size() == 0){return 0;}
        Trade s = a.getTrade(tradeHistory.get(tradeHistory.size() - 1));
        LocalDateTime x  = s.getCreateTime();
        LocalDateTime y = x.minusDays(7);
        int number = 0;
        for (UUID i : tradeHistory){
            if(a.getTrade(i).getCreateTime().isAfter(y) && a.getTrade(i).getCreateTime().isBefore(x)){
                number ++;
            }
        }
        return number;
    }

    public boolean getIsBorrow() {
        return isBorrow;
    }

    public List<String> getLend() {
        return lend;
    }

    public List<String> getBorrowed() {
        return borrowed;
    }


}