package com.lamzone.mareunion.fakeServices;

import com.lamzone.mareunion.model.Meeting;

import java.util.List;

public interface FakeApiMeeting {

    List<Meeting> getMeeting();
    List<Meeting> getTestMeeting();

    /**
     * @param meeting dont forget to put param for methods !(
     */

    void deleteMeeting(Meeting meeting);
    void deleteTestMeeting(Meeting meeting);

    void addNewMeeting(Meeting meeting);
    void addNewTestMeeting(Meeting meeting);

}

