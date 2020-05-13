package com.lamzone.mareunion.fakeServices;

import com.lamzone.mareunion.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class FakeMeetingService implements FakeApiMeeting {

    private List<Meeting> mTestMeetings = FakeMeetingGenerator.generateFakeMeeting();
    private List<Meeting> mMeetings = new ArrayList<>();

    @Override
    public List<Meeting> getMeeting() {
        return mMeetings;
    }

    @Override
    public List<Meeting> getTestMeeting() {
        return mTestMeetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    @Override
    public void deleteTestMeeting(Meeting meeting) {
        mTestMeetings.remove(meeting);
    }

    @Override
    public void addNewMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public void addNewTestMeeting(Meeting meeting) {
        mTestMeetings.add(meeting);
    }
}
