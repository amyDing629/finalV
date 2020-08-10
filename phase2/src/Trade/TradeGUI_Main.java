package Trade;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class TradeGUI_Main {
    String currUser;
    JFrame cf;

    /**
     * constructor
     * @param currUser the user that is using the system
     */
    public TradeGUI_Main(String currUser, JFrame cf){
        this.currUser = currUser;
        this.cf = cf;
    }

    public void run(){
        JFrame frame = new JFrame("Select Trade Session");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 250);

        JPanel menu = new JPanel();
        JLabel lb = new JLabel("    Hello, "+ currUser);
        JButton rt = new JButton("Request Trade");
        JButton ct = new JButton("Complete Trade");
        JButton at = new JButton("Agree/Refuse Requested Trade");
        JButton th = new JButton("View Trade History (and Frequent Traders)");
        JButton re = new JButton("Back");

        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(lb);
        menu.add(rt);
        menu.add(at);
        menu.add(ct);
        menu.add(th);
        menu.add(re);

        frame.getContentPane().add(menu);
        frame.setVisible(true);

        rt.addActionListener(e -> {
            frame.setVisible(false);
            BorderGUIBuilder builder = new SelectItemToTradeBuilder(currUser, frame);
            TradeGUIEngineer engineer = new TradeGUIEngineer(builder);
            engineer.constructGUI();
            TradeGUIPlan tg = engineer.getGUI();
            tg.run();
        });

        at.addActionListener(e -> {
            frame.setVisible(false);
            BorderGUIBuilder builder = new AcceptTradeGUIBuilder(currUser, frame);
            TradeGUIEngineer engineer = new TradeGUIEngineer(builder);
            engineer.constructGUI();
            TradeGUIPlan tg = engineer.getGUI();
            tg.run();

        });

        ct.addActionListener(e -> {
            frame.setVisible(false);
            BorderGUIBuilder builder = new CompleteTradeGUIBuilder(currUser, frame);
            TradeGUIEngineer engineer = new TradeGUIEngineer(builder);
            engineer.constructGUI();
            TradeGUIPlan tg = engineer.getGUI();
            tg.run();
        });

        th.addActionListener(e -> {
            frame.setVisible(false);
            BorderGUIBuilder builder = new TradeHistoryGUIBuilder(currUser, frame);
            TradeGUIEngineer engineer = new TradeGUIEngineer(builder);
            engineer.constructGUI();
            TradeGUIPlan tg = engineer.getGUI();
            tg.run();
        });

        re.addActionListener(e -> {
            frame.setVisible(false);
            cf.setVisible(true);
        });

    }

}
