package Inventory;

import User.Gateway.DataAccess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * [gateway class]
 * This class reads and writes all the item information from ItemList.ser into lendingList
 */
public class InvDataAccess implements DataAccess {

    private final String serFilePath = "phase2/src/itemList.ser";
    private List<Item> lendingList;

    /**
     * [constructor]
     */
    // https://stackoverflow.com/questions/1205995/what-is-the-list-of-valid-suppresswarnings-warning-names-in-java
    @SuppressWarnings("all")
    public InvDataAccess() {
        lendingList = new ArrayList<>();

        try {
            File serFile = new File(serFilePath);
            if (serFile.exists()) {
                deSerialize();
            } else {
                serFile.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialize(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(serFilePath);
            OutputStream buffer = new BufferedOutputStream(fileOut);
            ObjectOutputStream out = new ObjectOutputStream(buffer);

            out.writeObject(lendingList);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public List<Object> getList() {
        deSerialize();
        return new ArrayList<>(lendingList);
    }

    @Override
    public Object getObject(String name) {
        deSerialize();
        for (Item item : lendingList) {
            if (item.getName().equals(name))
                return item;
        }
        return null;
    }

    @Override
    public Object getObject(UUID uuid) {
        return null;
    }

    @Override
    public void addObject(Object o) {
        deSerialize();
        lendingList.add((Item) o);
        updateSer();
    }

    @Override
    public void updateSer() {
        File writer = new File(serFilePath);
        writer.deleteOnExit();
        serialize();
    }

    // source: https://stackoverflow.com/questions/31540556/casting-object-to-list-results-in-unchecked-cast-warning
    @SuppressWarnings("unchecked")
    public void deSerialize() {
        try {
            FileInputStream fileIn = new FileInputStream(serFilePath);
            InputStream buffer = new BufferedInputStream(fileIn);
            ObjectInputStream in = new ObjectInputStream(buffer);

            lendingList = (List<Item>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void setList(List<Object> userList) {

    }

    @Override
    public boolean hasObject(Object o) {
        deSerialize();
        Item item = (Item) o;
        return lendingList.contains(item);
    }

    @Override
    public void removeObject(Object o) {
        deSerialize();
        Item item = (Item) o;
        lendingList.remove(item);
        updateSer();
    }

//    /**
//     * read all the items from ItemList.txt
//     */
//    public void readFile(){
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("phase2/src/ItemList.txt"));
//            String line = reader.readLine();
//            while (line != null) {
//                String[] lst = line.split(",");
//                Item newItem = new Item(lst[0], lst[2]);
//                newItem.setDescription(lst[1]);
//                newItem.setIsInTrade(Boolean.parseBoolean(lst[3]));
//                iv.addItem(newItem);
//                line = reader.readLine();
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//    /**
//     *
//     * @param item the item wanted to add to file
//     * @throws IOException when the item cannot be added to file
//     */
//    private void addItemToFile(Item item) throws IOException {
//        try {
//            FileOutputStream fos = new FileOutputStream("phase2/src/ItemList.txt", true);
//            fos.write((item.getName()+","+item.getDescription()+","+item.getOwnerName()+","+item.getIsInTrade()+"\n").getBytes());
//        }catch(IOException e){
//            throw new IOException("cannot add item to file");
//
//        }
//    }
//
//
//    /**
//     * update trade.txt with information in trade list of gateway.
//     */
//    public void updateFile(){
//        File file = new File("phase2/src/itemList.txt");
//        try {
//            if(!file.exists()) {
//                boolean result = file.createNewFile();
//                if (!result){
//                    System.out.println("the trade file is not updated successfully");
//                }
//            }
//            FileWriter fileWriter =new FileWriter(file);
//            fileWriter.write("");
//            fileWriter.flush();
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        serialize();
//    }


}
