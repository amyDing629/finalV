package Trade.MeetingSystem;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class MeetingSessionPresenter {

    abstract void printIntro();

    void printSuccessInfo(LocalDateTime dateTime, String place) {
        System.out.println("  " + "- the current proposed time is:" + dateTime.toString());
        System.out.println("  " + "- the current proposed place is:" + place);
    }

    void printSuccessInfo(UUID currLogInUser, Meeting meeting) {
        System.out.println("Successful Action!");
    }

    void printMeetingStatusInfo(Meeting meeting) {
        System.out.println("Meeting current status: " + meeting.getStatus());
    }

    abstract void printExit();


}