import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Trade {
    private Meeting meeting;
    private String status;
    private static int numOfTrade;
    private int id;
    private Meeting secondMeeting;
    private final String duration;
    private ArrayList<String> messageList;

    /**
     * Constructor
     * @param status whether the trade is unconfirmed, confirmed, completed
     * @param duration whether the trade is temporary or permanent
     */
    public Trade(String status, String duration){
        this.status = status;
        this.duration = duration;
        messageList = new ArrayList<String>();
        numOfTrade++;
        id = numOfTrade;
    }

    /**
     * return id of the Trade when print Trade
     * @return id of the Trade
     */
    public int getId(){
        return id;
    }

    /**
     * getter for status
     * @return whether the trade is unconfirmed, confirmed and completed
     */
    public String getStatus(){
        return status;
    }

    /**
     * setter for status
     * @param newStatus new status of the trade
     */
    public void setStatus(String newStatus){
        status = newStatus;
    }

    /**
     * getter for duration
     * @return whether the trade is temporary or permanent
     */
    public String getDuration(){
        return duration;
    }

    /**
     *
     * @param dateTime time of the meeting
     * @param place place of the meeting
     * @param traderIds the id of the two traders
     * @return the meeting set
     */
    // can input time and place and create new Meeting object based on Meeting constructor
    public Meeting setMeeting(LocalDateTime dateTime, String place, ArrayList<Integer> traderIds){
        this.meeting = new Meeting(dateTime, place, traderIds);
        return meeting;
    }

    /**
     *
     * @param dateTime time of the meeting
     * @param place place of the meeting
     * @param traderIds the id of the two traders
     * @return the meeting set
     */
    public Meeting setSecondMeeting(LocalDateTime dateTime, String place, ArrayList<Integer> traderIds){
        this.secondMeeting = new Meeting(dateTime, place, traderIds);
        return meeting;
    }

    /**
     * getter for meeting
     * @return the Meeting object inside the trade
     */
    public Meeting getMeeting(){
        return meeting;
    }

    /**
     * change meeting information, use Meeting's methods
     * add Message information in the messageList
     */
    public void changeMeeting(){
    }

    public ArrayList<String> getMessageList(){
        return messageList;
    }

    /**
     * abstract class
     * make trade happen, move items in User's list
     */
    abstract void makeTrade();


    /**
     * abstract class
     * return objects if the trade is temporary, move items back
     */
    abstract void returnObject();




}
