package Trade.GateWay;

import Trade.Entity.Trade;
import User.Gateway.DataAccess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TradeDataAccess implements DataAccess {

    private final String serFilePath = "phase2/src/trade.ser";
    private List<Trade> tradeList;


    // https://stackoverflow.com/questions/1205995/what-is-the-list-of-valid-suppresswarnings-warning-names-in-java
    @SuppressWarnings("all")
    public TradeDataAccess() {
        tradeList = new ArrayList<>();

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

    @Override
    public List<Object> getList() {
        deSerialize();
        return new ArrayList<>(tradeList);
    }

    @Override
    public Object getObject(String name) {
        // not used!
        return null;
    }

    @Override
    public Object getObject(UUID uuid) {
        deSerialize();
        for (Trade trade : tradeList) {
            if (trade.getId().equals(uuid))
                return trade;
        }
        return null;
    }


    @Override
    public void addObject(Object o) {
        deSerialize();
        tradeList.add((Trade) o);
        updateSer();
    }

    @Override @SuppressWarnings("ALL")
    public void updateSer() {
        File file = new File(serFilePath);
        file.delete();
        try {
            if(!file.exists()) {
                boolean result = file.createNewFile();
                if (!result){
                    System.out.println("the new file is not created");
                }
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serialize();
    }

    public void serialize() {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(serFilePath);
            OutputStream buffer = new BufferedOutputStream(fileOut);
            ObjectOutputStream out = new ObjectOutputStream(buffer);

            out.writeObject(tradeList);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    // source: https://stackoverflow.com/questions/31540556/casting-object-to-list-results-in-unchecked-cast-warning
    @SuppressWarnings("unchecked")
    @Override
    public void deSerialize() {
        try {
            File file = new File(serFilePath);
            if (!(file.length() == 0)){
                FileInputStream fileIn = new FileInputStream(serFilePath);
                InputStream buffer = new BufferedInputStream(fileIn);
                ObjectInputStream in = new ObjectInputStream(buffer);

                tradeList = (List<Trade>) in.readObject();
                in.close();
                fileIn.close();
                //System.out.println("deSerialize: "+lendingList);
            }

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
        for (Trade i: tradeList){
            if (i.getId().equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeObject(String o) {

    }

    @Override
    public void removeObject(UUID o) {
        deSerialize();
        tradeList.removeIf(i -> i.getId().equals(o));
        updateSer();

    }


//    /**
//     * read info from trade.txt to trade list in gateway
//     */
    /*
    public void readFile(Inventory iv) {
        Trade trade;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("phase2/src/trade.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] lst = line.split(",");
                UUID tradeId = UUID.fromString(lst[0]);
                UUID user1Id = UUID.fromString(lst[3]);
                UUID user2Id = UUID.fromString(lst[4]);
                int duration = Integer.parseInt(lst[2]);
                ArrayList<UUID> users = new ArrayList<>();
                users.add(user1Id);
                users.add(user2Id);
                LocalDateTime tradeTime = LocalDateTime.parse(lst[10],formatter);
                Item item1 = iv.getItem(lst[5]);
                String fstMeeting = lst[7];
                String scdMeeting = lst[8];
                HashMap<UUID, Boolean> idToC = new HashMap<>();
                HashMap<UUID, MeetingEditor> idToE = new HashMap<>();
                HashMap<UUID, Boolean> idToA = new HashMap<>();

                if (lst[1].equals("oneway")){
                    trade = new OnewayTrade(user1Id,user2Id,item1,duration,tradeTime);


                }else{
                    Item item2 = iv.getItem(lst[6]);
                    trade = new TwowayTrade(user1Id,user2Id,item1,item2,duration,tradeTime);
                }
                if (!fstMeeting.equals("null")) {
                    String[] fm = fstMeeting.split("/");
                    LocalDateTime fmTime = LocalDateTime.parse(fm[0], formatter);
                    trade.setMeeting(fmTime, fm[1], users);
                    //set confirmed status
                    String[] confirmMap = fm[4].split(";");
                    idToC.put(user1Id,Boolean.parseBoolean(confirmMap[0]));
                    idToC.put(user2Id,Boolean.parseBoolean(confirmMap[1]));
                    trade.getMeeting().setConfirmedStatusFull(idToC);
                    //set edition time
                    String[] editTime = fm[3].split(";");
                    MeetingEditor me1 = new MeetingEditor(user1Id);
                    me1.setTimeOfEdition(Integer.parseInt(editTime[0]));
                    MeetingEditor me2 = new MeetingEditor(user2Id);
                    me2.setTimeOfEdition(Integer.parseInt(editTime[1]));
                    idToE.put(user1Id,new MeetingEditor(user1Id));
                    idToE.put(user2Id,new MeetingEditor(user2Id));
                    //set agree status
                    String[] agreeMap = fm[5].split(";");
                    idToA.put(user1Id,Boolean.parseBoolean(agreeMap[0]));
                    idToA.put(user2Id,Boolean.parseBoolean(agreeMap[1]));
                    trade.getMeeting().setAgreedStatusFull(idToA);
                    trade.getMeeting().setIdToEditor(idToE);
                    trade.getMeeting().setStatus(MeetingStatus.valueOf(fm[2]));
                    trade.getMeeting().changeLastEditUser(UUID.fromString(fm[6]));
                }
                if (!scdMeeting.equals("null")){
                    String[] sm = scdMeeting.split("/");
                    LocalDateTime smTime = LocalDateTime.parse(sm[0],formatter);
                    trade.setSecondMeeting(smTime, sm[1], users);
                    trade.getSecondMeeting().setStatus(MeetingStatus.valueOf(sm[2]));
                }

                trade.setStatus(TradeStatus.valueOf(lst[9]));
                trade.setId(tradeId);
                trade.setCreator(UUID.fromString(lst[11]));
                tm.getTradeList().add(trade);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

     */

//    /**
//     * add trade in trade list of gateway to trade.txt
//     * @param trade the trade wants to be added
//     */
//    private void addTradeToFile(Trade trade) {
//        try {
//            FileOutputStream fos = new FileOutputStream("phase2/src/trade.txt", true);
//            UUID id = trade.getId();
//            String type;
//            type = trade.getType();
//            Integer duration = trade.getDuration();
//            UUID user1 = trade.getUsers().get(0);
//            UUID user2 = trade.getUsers().get(1);
//            ArrayList<Item> items = trade.getItemList();
//            String item1; String item2;
//            if (items.size() == 1){
//                item1 = items.get(0).getName();
//                item2 = null;
//            }else{
//                item1 = items.get(0).getName();
//                item2 = items.get(1).getName();
//            }
//            Meeting fm = trade.getMeeting();
//            String fmStr = null;
//            if (fm != null){
//                HashMap<UUID, MeetingEditor> idToE = fm.getIdToEditor();
//                String idToEdStr = idToE.get(user1).getTimeOfEdition() + ";"+
//                        idToE.get(user2).getTimeOfEdition();
//                HashMap<UUID, Boolean> conStatus = fm.getConfirmedStatusFull();
//                String idToCoStr = conStatus.get(user1) + ";" + conStatus.get(user2);
//                HashMap<UUID, Boolean> agreeStatus = fm.getAgreedStatusFull();
//                String idToAgreeStr = agreeStatus.get(user1) + ";" + agreeStatus.get(user2);
//                String lastEditor = fm.getLastEditUser().toString();
//                //2020-06-30 11:49/home/incomplete/0;0/false;false
//                fmStr = fm.getDateTime().format(formatter)+"/"+fm.getPlace()+"/"+fm.getStatus()
//                        +"/"+idToEdStr+"/"+ idToCoStr+"/"+idToAgreeStr+"/"+lastEditor;
//
//            }
//            Meeting sm = trade.getSecondMeeting();
//            String smStr;
//            if (sm == null){
//                smStr = null;
//            }else{
//                HashMap<UUID, Boolean> conStatus = sm.getConfirmedStatusFull();
//                String idToCoStr = conStatus.get(user1) + ";" + conStatus.get(user2);
//                smStr = sm.getDateTime().format(formatter)+"/"+sm.getPlace()+"/"+sm.getStatus()+"/"+idToCoStr;
//            }
//            String status = trade.getStatus().toString();
//            String time = trade.getCreateTime().format(formatter);
//            String creator = trade.getCreator().toString();
//
//            fos.write((id+","+type+","+duration+","+user1+","+user2+","+item1+","+item2+","+fmStr+","
//                    +smStr+","+status+","+time+","+creator+"\n").getBytes());
//            fos.close();
//        }catch(IOException e){
//            System.out.println("cannot edit file");
//        }
//    }
//
//    /**
//     * update trade.txt with information in trade list of gateway.
//     */
//    public void updateFile(){
//        File file = new File("phase2/src/trade.txt");
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