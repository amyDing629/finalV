package Trade;

import Inventory.*;
import Inventory.Item;
import User.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class TwowayTrade extends Trade {
    private final UUID trader1Id;
    private final UUID trader2Id;
    private final Item item1to2;
    private final Item item2to1;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private UserManager um = new UserManager();
    private Inventory iv = new Inventory();
    /**
     *
     * @param trader1Id Trader who participates in the trade
     * @param trader2Id Trader who participates in the trade
     * @param item1 the item that trader1 give to trader2
     * @param item2 the item that trader2 give to trader1
     * @param duration whether the trade is temporary or permanent
     */
    public TwowayTrade(UUID trader1Id, UUID trader2Id, Item item1, Item item2, int duration, LocalDateTime time) throws IOException {
        super(duration, time);
        this.trader1Id = trader1Id;
        this.trader2Id = trader2Id;
        item1to2 = item1;
        item2to1 = item2;
    }

    /**
     * getter for a list of users involved in the trade
     * @return a list of users
     */
    public ArrayList<UUID> getUsers(){
        ArrayList<UUID> users = new ArrayList<>();
        users.add(trader1Id);
        users.add(trader2Id);
        return users;
    }

    /**
     * to string
     * @return trade information in a string format
     */
    public String toString(){
        return "trade.Trade" + getId() + ": trader1 " + trader1Id +" makes two way trade with trader" + trader2Id +
                " for item " + item1to2 + "and" + item2to1 +  " at " + getCreateTime().format(formatter);
    }

    /**
     * get a list of items involved in the trade
     * @return a list of items
     */
    @Override
    public ArrayList<Item> getItemList() {
        ArrayList<Item> rst = new ArrayList<Item>();
        rst.add(item1to2);
        rst.add(item2to1);
        return rst;
    }

    /**
     * remove items from users' wishLists after the trade is completed.
     * @throws IOException the trade hasn't been made.
     */
    @Override
    public void makeTrade() throws IOException {
        User u1 = um.getUser(trader1Id);
        User u2 = um.getUser(trader2Id);
        u1.getWishBorrow().remove(item2to1.getName());
        u1.getWishLend().remove(item1to2.getName());
        u2.getWishBorrow().remove(item1to2.getName());
        u2.getWishLend().remove(item2to1.getName());
        iv.getLendingList().remove(item1to2);
        iv.getLendingList().remove(item2to1);
    }

    @Override
    public String getType() {
        return "twoway";
    }


}