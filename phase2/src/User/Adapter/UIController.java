package User.Adapter;
import Inventory.Inventory;
import Trade.TradeManager;
import User.Entity.ClientUser;
import User.Gateway.UserDataAccess;
import User.UseCase.AdminActivityManager;
import User.UseCase.ApprovalManager;
import User.UseCase.UserManager;

import java.io.*;
import java.util.Scanner;

public class UIController {
    public UserManager um;
    public AdminActivityManager am;
    public TradeManager tm;
    public ApprovalManager iam;
    Inventory iv;

    public UIController(UserManager um, AdminActivityManager am,TradeManager tm,ApprovalManager iam,Inventory iv){
        this.um=um;
        this.am=am;
        this.tm=tm;
        this.iam=iam;
        this.iv=iv;
    }
    public void checkFrozen(String user) throws FileNotFoundException {
        if(um.readDiff(user)>=um.getDiff(user)){
            System.out.println("You have been freeze due to exceed difference between borrow and lend");
            am.setFreeze(user,true);
        }
        else if(am.incompleteTransaction(um.nameToUUID(user))){
               System.out.println("You have been freeze due to maximum incomplete transaction");
            am.setFreeze(user,true);
        }
        else if(am.tradeLimit(user)){
            System.out.println("You have been freeze due to maximum trade limit");
            am.setFreeze(user,true);
        }
    }

    public void checkFileEmpty(File file) throws FileNotFoundException {
        if (file.length() == 0) {
            ClientUser b = new ClientUser("admin", "123", true);
            um.addUser(b);
            new UserDataAccess().serialize();
        }
    }

    public void printAllUserInfo(){
        System.out.println("Users:");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "phase1/src/username.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumber(String words){
        Scanner sc=new Scanner(System.in);
        System.out.println(words);
        System.out.print(">");
        int a=sc.nextInt();
        return a;
    }
    public String getString(String words){
        Scanner sc=new Scanner(System.in);
        System.out.println(words);
        System.out.print(">");
        return sc.nextLine();
    }

//    public boolean checkItemExist(String item){
//        for (Item n : iv.getLendingList()) {
//            if (n.getName().equals(item)) {
//                return true;
//            }
//        }
//        return false;
//    }
    public void UserDisplayStatus(String user) throws FileNotFoundException {
        System.out.println("Hello," + user);
        System.out.println("Admin:"+um.getIsAdmin(user));
        checkFrozen(user);
        System.out.println("Freeze Status: " + um.getIsFrozen(um.nameToUUID(user)));
        System.out.println("Trade limit: " + tm.getTradeNumber(user) + "/"
                + um.getWeekTransactionLimit(user));
        System.out.println("Incomplete trade limit: " + (tm.getIncomplete(um.nameToUUID(user)).size()
                + "/" + um.getIncompleteTransactionLimit(user)));
        System.out.println("Difference between borrow and lend:"+um.readDiff(user)+"/"+um.getDiff(user));
        System.out.println("**************************************************************");
    }
}
