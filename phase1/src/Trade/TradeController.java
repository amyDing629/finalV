package Trade;

import Inventory.Inventory;
import Inventory.Item;
import User.User;
import User.*;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * [controller]
 * the action of trade depend on the input of user
 */
public class TradeController {
    private final User currUser;
    private User tarUser;
    private final TradeManager tm;
    private Trade currTrade;
    private UserManager um;
    private Inventory iv;

    /**
     * [constructor]
     * @param currUser the user that is using the system
     */
    TradeController(User currUser, TradeManager tm, UserManager um, Inventory iv){
        this.currUser = currUser;
        this.tm = tm;
        this.um = um;
        this.iv = iv;
    }

    TradeController(User currUser, Trade currTrade, TradeManager tm, UserManager um){
        this.currUser = currUser;
        this.currTrade = currTrade;
        this.tm = tm;
        this.um = um;
    }




    /**
     * get owner of the item
     * @param item current item selected by currUser
     * @throws IOException can not find the target user
     */
    void getTarUser(Item item) throws IOException {
        tarUser = um.getUser(item.getOwnerName());
    }


    /**
     * check the frozen status of two users.
     * @throws IOException one of the users's account is frozen
     */
    void checkInput() throws IOException {
        if (currUser.getIsFrozen()) {
            throw new IOException("your account is frozen!");
        }
        if (tarUser == null){
            System.out.println("tarUser not found");
        }
        if (tarUser.getIsFrozen()) {
            throw new IOException("the account of the item owner is frozen!");
        }
        if (tarUser == currUser){
            throw new IOException("you cannot make trade with yourself");
        }
    }

    /**
     * create trade according to the user's input
     * @param line the user's input
     * @param item the item of the trade
     * @return whether or not the trade is a oneway trade
     * @throws IOException the trade is not created
     */
    boolean createTrade(String line, Item item) throws IOException {
        LocalDateTime time = LocalDateTime.now();
        item.setIsInTrade(true);
        switch (line) {
            case "1":
                Trade trade1 = tm.createOnewayTrade(currUser.getId(), tarUser.getId(), item, 30, time);
                trade1.setCreator(currUser.getId());
                return true;
            case "2":
                Trade trade2 = tm.createOnewayTrade(currUser.getId(), tarUser.getId(), item, -1, time);
                trade2.setCreator(currUser.getId());
                return true;
            default: {
                return false;
            }
        }
    }

    /**
     * create trade if the trade is a two way trade
     * @param line the input of users
     * @param item1 the first item
     * @param item2 the second item
     * @throws IOException if the trade is not created
     */
    void createTrade(String line, Item item1, Item item2) throws IOException {
        item1.setIsInTrade(true);
        item2.setIsInTrade(true);
        LocalDateTime time = LocalDateTime.now();
        if (line.equals("3")){
            Trade trade3 = tm.createTwowayTrade(currUser.getId(), tarUser.getId(), item1, item2, 30, time);
            trade3.setCreator(currUser.getId());
        }else{
            Trade trade4 = tm.createTwowayTrade(currUser.getId(), tarUser.getId(), item1, item2, -1, time);
            trade4.setCreator(currUser.getId());

        }
    }

    /**
     * check the status of the current trade
     * @return the status
     */
    String checkTradeMeeting() {
        return tm.checkTradeMeeting(currTrade);
    }
    /**
     * confirm trade(agree with the trade)
     */
    void confirmTrade() {
        tm.confirmTrade(currTrade);
    }

    /**
     * set the status of trade to complete and make trade
     * @throws IOException if the item is not deleted from user's wishlist and inventory
     */
    void completeTrade() throws IOException {
        tm.completeTrade(currTrade);
    }

    /**
     * set the status of trade to cancelled
     */
    void cancelTrade(){
        tm.cancelTrade(currTrade);
    }



}

