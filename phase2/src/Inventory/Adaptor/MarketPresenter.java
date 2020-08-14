package Inventory.Adaptor;

import Trade.Adaptor.BorderGUI;
import Trade.Adaptor.InputAndPresent;

public class MarketPresenter implements iMarketPresenter {
    private final InputAndPresent bta;

    public MarketPresenter(BorderGUI bta){
        this.bta = bta;
    }

    public void resetInputArea(){
        bta.setInput("input","item name");
    }

    public void updateListM(String availableList){
        bta.setListText(availableList);
    }

    public void closeFrame(){
        bta.getFrame().setVisible(false);
    }

    public void resetCurr(){
        bta.setCurrText("not item selected");
    }

    public void wrongInput(){
        bta.setMsgText("wrong input");
    }

    public void updateCurr(String itemInfo){

        bta.setCurrText(itemInfo);
    }
}