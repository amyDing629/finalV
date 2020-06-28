public class TradeProcessPresentor {
    TradeManager tradeManager;

    public TradeProcessPresentor(){
        TradeManager tradeManager = new TradeManager();
    }

    public void run(int tradeId){
        Trade trade = tradeManager.getTrade(tradeId);
        MeetingSystem mt = new MeetingSystem(trade.getUsers());
        mt.run();
    }

}