package User.PointSystem;

import Trade.Entity.Trade;
import Trade.UseCase.TradeManager;
import Trade.TradeStatus;
import User.Entity.ClientUser;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import Trade.Adaptor.BorderGUI;
import User.UseCase.UserManager;

/**
 * [Controller]
 * Responsible for awarding the users with bonus trades.
 */
public class AwardActivities {

    TradeManager tm = new TradeManager();
    PointManager pm = new PointManager();
    Trade currTrade;
    List<Trade> tradeList;
    JFrame fr;
    PointPresenter pp;
    String currUser;
    UserManager um;

    /**
     * Constructs the AwardActivities for user
     */
    public AwardActivities(String currUser, JFrame fr, BorderGUI tg){
        this.currUser = currUser;
        this.fr = fr;
        pp = new PointPresenter(tg);
        um = new UserManager();

    }

    Trade getCurrTrade(String num){
        int tradeNum = Integer.parseInt(num.trim());
        currTrade = tradeList.get(tradeNum);
        return currTrade;

    }

    boolean checkInput(String num){
        if (!isNum(num)){
            return true;
        }else return Integer.parseInt(num) < 0 | Integer.parseInt(num) >= tradeList.size();
    }

    private boolean isNum(String str){
        Pattern pattern = Pattern.compile("^[0-9]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * Provide a list of trades for user to select as bonus trades to avoid counting towards being frozen.
     * The trades in list are incomplete trades within the most recent 7 days.
     * Once the trade is selected as bonus, a fixed amount of bonus points will be deducted.
     * @param user the current user who makes actions
     */
    public List<Trade> getTradesForExchange(ClientUser user){
        List<Trade> result = new ArrayList<Trade>();
        tradeList = tm.getWeekTradeList(user.getUsername()); // get all trades within the most recent seven days
        for (Trade t: tradeList) {
            if (t.getStatus().equals(TradeStatus.incomplete) && !user.getSelectedBonusTrades().contains(t.getId())) {
                result.add(t); // get incomplete trade from the recent trades
            }
        }return result;
    }

    /**
     * Set the selected trade to bonus and update the points for user. (update pointList as well)
     * Notify the user when bonus points are not enough to exchange for a trade.
     * @param userId the ID of current user who is making actions
     * @param selected the trade user selected to be bonus
     */
    public void getBonus(UUID userId, Trade selected){
        ClientUser user = this.um.getUser(userId);
        if (selected == null){
            pp.notTradeSelected();
        }else if (pm.getUserPoints(user) < pm.getExStandard()){
           pp.pointNotEnough();
        }
        else{
            ClientUser u1 = this.um.popUser(userId);
            u1.addBonusTrade(selected.getId());
            this.um.addUser(u1);
            this.pm.setUserPoints(user.getId());
            pp.changeSuccess();
        }

    }

    void submitBut(String tradeNum){
        pp.resetInputArea();
        if (checkInput(tradeNum)){
            pp.wrongInput();
        }else{
            currTrade = getCurrTrade(tradeNum);
            pp.presentTradeInfo(currTrade);
            pp.updateSuccess();
        }
    }

    void backBut(){
        fr.setVisible(true);
        pp.closeFrame();
    }

    public void updateBut(){
        pp.updateFrame(getTradesForExchange(um.getUser(currUser)));
        pp.updatePoint(um.getUser(currUser).getBonusPoints());
        noTradeSelected();
        pp.resetCurr();
    }

    public void updatePoint(){
        ClientUser user = um.getUser(currUser);
        pm.setUserPoints(user.getId());
        pp.updatePoint(pm.getUserPoints(user));
    }

    public void updateStandard(){
        pp.updateStandard(pm.getExStandard());
    }

    public void noTradeSelected(){
        pp.noTradeCurr();
    }

    public void ebBut(){
        getBonus(um.getUser(currUser).getId(), currTrade);
        updateBut();
        //pp.changeSuccess();
    }

    public void updateList(){
        pp.updateFrame(getTradesForExchange(um.getUser(currUser)));
    }

}
