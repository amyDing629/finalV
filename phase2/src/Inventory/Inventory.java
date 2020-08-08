package Inventory;

import User.DataAccess;

import java.util.ArrayList;

/**
 * [use case class]
 * the object that edits Item list in gateway
 */
public class Inventory {

    DataAccess dataAccess = new InvDataAccess();

    /**
     * get a list of items that is not in the trade
     * @return available item list
     */
    public ArrayList<Item> getAvailableList() {
        ArrayList<Item> result = new ArrayList<>();
        for (Object item : dataAccess.getList()) {
            Item i = (Item) item;
            if (!i.getIsInTrade()) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * add the item into the inventory
     * @param item the item added
     */
    public void addItem(Item item){
        dataAccess.addObject(item);
    }

    /**
     * Removes the item from .ser file and return true, if it exists; or return false.
     *
     * @param item the deleted item
     */
    public boolean deleteItem(Item item) {
        if (dataAccess.hasObject(item)) {
            dataAccess.removeObject(item);
            return true;
        } else {
            return false;
        }
    }

    /**
     * get item through its name
     * @param name the item name you want to get
     * @return item
     */
    public Item getItem(String name){
        return (Item) dataAccess.getObject(name);
    }


    public Item createItem(String name, String owner){
        return new Item(name, owner);
    }

    public void setDescription(String des, Item item){
        item.setDescription(des);
    }

    public boolean getIsInTrade(Item it){
        return it.getIsInTrade();

    }

    public void setIsInTrade(Item it, boolean inTrade){
        it.setIsInTrade(inTrade);
    }

    public String getName(Item it){
        return it.getName();
    }

    public String getDescription(Item it){
        return it.getDescription();
    }

    public void setDescription(Item it, String des){
        it.setDescription(des);
    }

    public String getOwnerName(Item it){
        return it.getOwnerName();
    }

}