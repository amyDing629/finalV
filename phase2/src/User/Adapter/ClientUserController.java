package User.Adapter;

import Inventory.Inventory;
import Inventory.Item;
import Trade.TradeManager;
import User.Entity.ClientUser;
import User.Gateway.UserDataAccess;
import User.UseCase.ApprovalManager;
import User.UseCase.UserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientUserController {
    UserManager um = new UserManager();
    ApprovalManager iam = new ApprovalManager();
    TradeManager tm = new TradeManager();


    public boolean getIsAdmin(String username) {
        return um.getUser(username).getIsAdmin();
    }

    public boolean checkItemExist(String item, Inventory iv) {
        for (Item n : iv.getLendingList()) {
            if (iv.getName(n).equals(item)) {
                return true;
            }
        }
        return false;
    }

//    public ItemApprovalManager getIam() {
//        return iam;
//    }
//
//    public UserManager getUm() {
//        return um;
//    }
//
//    public String getUsername(ClientUser user){
//        return user.getUsername();
//    }

    /**
     * @param name     the name of the user that the manager check
     * @param password the password of the user that the manager check
     *                 Check if the name matches with the password
     */
    public boolean verifyUser(String name, String password) {
        return um.verifyUser(name, password);
    }

    /**
     * @param userId the ID of the user that the manager wants to get
     * find the user by the user ID
     */
    public boolean checkUser(UUID userId) {
        return um.getUser(userId) == null;
    }

    /**
     * @param name the name of the user that the manager wants to get
     *             find the user by the user name
     */
    public boolean checkUser(String name) {
        return um.getUser(name) == null;
    }

    public void createClientUser(String name, String password) throws FileNotFoundException {
        um.createClientUser(name, password, false);
    }

    public boolean getIsFrozen(UUID userID){
        return um.getIsFrozen(userID);
    }

    public UUID nameToUUID(String name){
        return um.nameToUUID(name);
    }


    public UUID getIDbyName(String name) {
        return um.getUser(name).getId();
    }

    public String getNameByID(UUID uuid) {
        return um.getUser(uuid).getUsername();
    }

    public ClientUser getUser(String username){
        return um.getUser(username);
    }

    public void checkFileEmpty(File file){
        if (file.length() == 0) {
            ClientUser b = new ClientUser("admin", "123", true);
            um.addUser(b);
            new UserDataAccess().serialize();
        }
    }

    public String getPassword(String username){
        return um.getPassword(um.getUser(username));
    }
    public void setPassword(String name, String password){
        um.setPassword(um.getUser(name), password);
    }
    public List<ClientUser> getUserList() {return um.getUserList();}
    public ArrayList<ArrayList<String>> getActions(String username) {
        return um.getActions(um.getUser(username));
    }
}
