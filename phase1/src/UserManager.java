import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserManager {

    public static ArrayList<ArrayList<String>> readfile() throws IOException {
        ArrayList<ArrayList<String>> myList = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader("phase1/src/username.txt"));
            while(in.ready()) {
                String line = in.readLine();
                String[] parts = line.split(", ");
                ArrayList<String> lineList = new ArrayList<>();
                lineList.addAll(Arrays.asList(parts));
                myList.add(lineList);
            }
            return myList;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return myList;
    }

    public static ArrayList<User> splitUser(ArrayList<ArrayList<String>> a) throws IOException {
        ArrayList<User> myList = new ArrayList<>();
        for(ArrayList<String> b: a){
            if(b.get(3).equals("true")){
                String[] c = b.get(6).split("; ");
                ArrayList<String> lineList = new ArrayList<>();
                lineList.addAll(Arrays.asList(c));

                AdministrativeUser d = new AdministrativeUser(b.get(1), b.get(2), true);
                d.setNotification(lineList);

                int i = Integer.parseInt(b.get(0));
                d.setId(i);

                d.setFrozen(b.get(4).equals("true"));

                d.setBorrow(b.get(5).equals("true"));

                String[] f = b.get(7).split("; ");
                ArrayList<String> lineList2 = new ArrayList<>();
                lineList2.addAll(Arrays.asList(f));
                d.setWishLend(lineList2);

                String[] g = b.get(8).split("; ");
                ArrayList<String> lineList3 = new ArrayList<>();
                lineList2.addAll(Arrays.asList(g));
                d.setWishBorrow(lineList3);

                String[] h = b.get(b.size() - 1).split("; ");
                ArrayList<String> lineList4 = new ArrayList<>();
                lineList4.addAll(Arrays.asList(h));
                ArrayList<Integer> lineList5 = new ArrayList<>();

                for(String p: lineList4){
                    lineList5.add(Integer.parseInt(p));
                }

                d.setTradeHistory(lineList5);
                myList.add(d);
            }
            if(b.get(3).equals("false")){
                String[] c = b.get(6).split("; ");
                ArrayList<String> lineList = new ArrayList<>();
                lineList.addAll(Arrays.asList(c));

                ClientUser d = new ClientUser(b.get(1), b.get(2), true);
                d.setNotification(lineList);


                int i = Integer.parseInt(b.get(0));
                d.setId(i);

                d.setFrozen(b.get(4).equals("true"));

                d.setBorrow(b.get(5).equals("true"));

                String[] f = b.get(7).split("; ");
                ArrayList<String> lineList2 = new ArrayList<>();
                lineList2.addAll(Arrays.asList(f));
                d.setWishLend(lineList2);

                String[] g = b.get(8).split("; ");
                ArrayList<String> lineList3 = new ArrayList<>();
                lineList2.addAll(Arrays.asList(g));
                d.setWishBorrow(lineList3);

                String[] h = b.get(b.size() - 1).split("; ");
                ArrayList<String> lineList4 = new ArrayList<>();
                lineList4.addAll(Arrays.asList(h));
                ArrayList<Trade> lineList5 = new ArrayList<>();

                String[] k = b.get(b.size() - 1).split("; ");
                ArrayList<String> lineList6 = new ArrayList<>();
                lineList6.addAll(Arrays.asList(h));
                ArrayList<Integer> lineList7 = new ArrayList<>();

                for(String p: lineList4){
                    lineList7.add(Integer.parseInt(p));
                }

                d.setTradeHistory(lineList7);
                myList.add(d);
            }
        }
        return myList;
    }

    public void addUser(User u) throws IOException {
        try{
            BufferedWriter output = new BufferedWriter(new FileWriter("username", true));
            String name = u.getUsername();
            String s = u.getId()+ ", " + name + ", "  + u.getPassword()+ ", "  + u.getIsAdmin()+ ", "  + u.getIsAdmin()+ ", "  + u.getIsBorrow()+ ", ";
            String m = "";
            for(String i: u.getNotification()){
                m = m + i + "; ";
            }
            s = s + m + ", ";
            String n = " ";
            for(String i: u.getWishLend()){
                n = n + i+ "; ";
            }
            s = s + n + ", ";
            String k = " ";
            for(String i: u.getWishBorrow()){
                k = k + i+ "; ";
            }
            s = s + k + ", ";
            String l = " ";
            for(String i: u.getWishBorrow()){
                l = l + i+ "; ";
            }
            s = s + l;
            s = s + "\n";
            output.append(s);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String name) throws IOException {
        try{
            ArrayList<User> userlist = splitUser(readfile());
            for(User u : userlist){
                if(u.getUsername().equals(name))
                    return u;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(int userId) throws IOException {
        try{
            ArrayList<User> userlist = splitUser(readfile());
            for(User u : userlist){
                if(u.getId().equals(userId))
                    return u;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Trade> findTrade(Integer id){
        try{
            TradeManager a = new TradeManager();
            ArrayList<User> userlist = splitUser(readfile());
            for(User u : userlist){
                if(u.getId().equals(id))
                    return u.getAllTrade();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static boolean verifyUser(String name, String password) throws IOException {
        try{
            ArrayList<User> userlist = splitUser(readfile());
            for(User u : userlist){
                if(u.getUsername().equals(name) && u.getPassword().equals(password))
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}

