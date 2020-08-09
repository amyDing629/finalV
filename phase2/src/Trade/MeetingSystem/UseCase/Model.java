package Trade.MeetingSystem.UseCase;

import Trade.MeetingSystem.Entity.Meeting;
import Trade.MeetingSystem.MeetingStatus;

import java.util.UUID;

/**
 *
 */
public interface Model {
    String getMeetingInfo(UUID meetingID);

    Meeting getMeeting(UUID meetingID);

    boolean isTimePlaceChanged(UUID meetingID, String inputTime, String inputPlace); // return if input had difference to the record

    boolean isEditable(UUID meetingID, UUID currLogInUser);

    MeetingStatus getMeetingStatus(UUID meetingID);

    boolean otherUserAgreed(UUID meetingID);

    boolean otherUserConfirmed(UUID meetingID);

    boolean isLastUserCurrUser();

    String getCurrUser();

    void setMeetingID(UUID meetingID);
}