package Trade.MeetingSystem.Gui;

import Trade.MeetingSystem.MeetingStatus;

import java.util.List;
import java.util.Observable;
import java.util.UUID;

// presenter
public class MainViewPresenter extends Observable implements MPresenter {

    UUID currLogInUser;
    UUID meetingID;
    MeetingStatus meetingStatus;
    List<UUID> users;
    boolean isFirst;

    // Use case
    Model meetingModel;

    // View
    MainView mainView;


    public MainViewPresenter(UUID meetingID, UUID currLogInUser, List<UUID> users, boolean isFirst) {
        this.meetingID = meetingID;
        this.currLogInUser = currLogInUser;
        this.users = users;
        this.isFirst = isFirst;

        // set Model
        meetingModel = new MeetingModel(meetingID, currLogInUser);

        // set View
        mainView = new MainView();

        // get meeting status
        meetingStatus = meetingModel.getMeetingStatus(meetingID);

    }

    @Override
    public void back() {
        // TODO: back to Trade System View!
    }

    @Override
    public Model getModel() {
        return meetingModel;
    }

    @Override
    public UUID getMeetingID() {
        return meetingID;
    }

    @Override
    public void setMeetingID(UUID meetingID) {
        this.meetingID = meetingID;
        getModel().setMeetingID(meetingID);
        System.out.println("Main View Presenter: " + meetingID);

        // notify controller
        setChanged();
        notifyObservers(meetingID);

    }

    @Override
    public UUID getCurrLogInUser() {
        return currLogInUser;
    }

    @Override
    public void run() {
        mainView.setPresenter(this);
        mainView.updateViewFromModel(isFirst);
        mainView.open();
    }

    @Override
    public List<UUID> getUsers() {
        return users;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SetupViewPresenter) {
            setMeetingID((UUID) arg);
            mainView.updateViewFromModel(isFirst);
        }
    }

}
