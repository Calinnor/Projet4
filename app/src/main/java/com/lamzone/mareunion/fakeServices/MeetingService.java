package com.lamzone.mareunion.fakeServices;

import com.lamzone.mareunion.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingService implements ApiMeeting {

    //private List<Meeting> mMeetings = new ArrayList<>();
    private List<Meeting> mMeetings = FakeMeetingGenerator.generateFakeMeeting();

    @Override
    public List<Meeting> getMeeting() {
        return mMeetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    @Override
    public void addNewMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }
}
