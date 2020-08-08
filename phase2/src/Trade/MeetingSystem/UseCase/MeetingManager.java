package Trade.MeetingSystem.UseCase;

import Trade.MeetingSystem.Entity.Meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

interface MeetingManager {
    boolean isMeetingIdExist(UUID meetingID);

    Meeting getMeetingWithId(UUID meetingID);

    UUID setUpMeeting(ArrayList<UUID> userIds, LocalDateTime dateTime, String place, UUID currLogInUser);

    UUID setUpSecondMeeting(UUID firstMeetingID);

    void editMeeting(Meeting meeting, UUID currLogInUser, LocalDateTime dateTime, String place);

    void agreeMeeting(Meeting meeting, UUID currLogInUser);

    void confirmMeeting(Meeting meeting, UUID currLogInUser);
}
