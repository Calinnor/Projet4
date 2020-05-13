package com.lamzone.mareunion;

import com.lamzone.mareunion.di.DI;
import com.lamzone.mareunion.fakeServices.FakeApiMeeting;
import com.lamzone.mareunion.fakeServices.FakeApiPlace;
import com.lamzone.mareunion.model.Meeting;
import com.lamzone.mareunion.model.PlaceItem;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserApisTest {

    private FakeApiMeeting mFakeApiMeeting;
    private FakeApiPlace mFakeApiPlace;

    @Before
    public void setupPlace() {
        mFakeApiPlace = DI.getNewInstanceFakePlaceApi();
    }

    @Before
    public void setupMeeting() {
        mFakeApiMeeting = DI.getNewInstanceFakeApi();
    }

    /**
     * tests for PlaceItem
     */
    @Test
    public void getPlacesWithSuccess() {
        PlaceItem execeptedPlaceItem = mFakeApiPlace.getPlaceItem().get(0);
        assertNotNull(execeptedPlaceItem);
    }

    @Test (expected = IndexOutOfBoundsException.class )
    public void getPlaceItemOutOfList() {
        PlaceItem expectedPlaceItem = mFakeApiPlace.getPlaceItem().get(mFakeApiPlace.getPlaceItem().size()+1);
        assertTrue(mFakeApiPlace.getPlaceItem().contains(expectedPlaceItem));
    }

    @Test
    public void getPlaceItemWithSuccessSamePlaceItem() {
        PlaceItem expectedPlaceItem = mFakeApiPlace.getPlaceItem().get(2);
        assertEquals(mFakeApiPlace.getPlaceItem().get(2), expectedPlaceItem);
    }

    /**
     * tests for Meeting
     */
    @Test
    public void getMeetingWithSucces(){
        Meeting exceptedMeeting = mFakeApiMeeting.getTestMeeting().get(0);
        assertNotNull(exceptedMeeting);
    }

    @Test (expected = IndexOutOfBoundsException.class )
    public void getMeetingOutOfList() {
        Meeting exceptedMeeting = mFakeApiMeeting.getTestMeeting().get(mFakeApiMeeting.getTestMeeting().size()+1);
        assertTrue(mFakeApiMeeting.getTestMeeting().contains(exceptedMeeting));
    }

    @Test
    public void getMeetingWithSuccessSamePlaceItem() {
        Meeting expectedMeeting= mFakeApiMeeting.getTestMeeting().get(0);
        assertEquals(mFakeApiMeeting.getTestMeeting().get(0), expectedMeeting);
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = mFakeApiMeeting.getTestMeeting().get(0);
        mFakeApiMeeting.deleteTestMeeting(meetingToDelete);
        assertEquals(2, mFakeApiMeeting.getTestMeeting().size());
    }

    @Test
    public void deletedMeetingIsMeetingToDelete() {
        Meeting meetingToDelete = mFakeApiMeeting.getTestMeeting().get(0);
        mFakeApiMeeting.deleteTestMeeting(meetingToDelete);
        assertNotEquals(meetingToDelete, mFakeApiMeeting.getTestMeeting().get(0));
    }

    @Test
    public void deleteNeighbourWithSuccessFromList() {
        Meeting meetingToDelete = mFakeApiMeeting.getTestMeeting().get(0);
        mFakeApiMeeting.deleteTestMeeting(meetingToDelete);
        assertFalse(mFakeApiMeeting.getTestMeeting().contains(meetingToDelete));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void deleteMeetingOutOfListSize() {
        Meeting meetingToDelete = mFakeApiMeeting.getTestMeeting().get(mFakeApiMeeting.getTestMeeting().size()+1);
        mFakeApiMeeting.deleteTestMeeting(meetingToDelete);
    }

    @Test
    public void addMeetingIsMeetingToAdd() {
        Meeting meetingToAdd = mFakeApiMeeting.getTestMeeting().get(0);
        mFakeApiMeeting.addNewTestMeeting(meetingToAdd);
        assertEquals(meetingToAdd, mFakeApiMeeting.getTestMeeting().get(0));
    }

    @Test
    public void addMeetingWithSuccessFromList() {
        Meeting meetingToAdd = mFakeApiMeeting.getTestMeeting().get(0);
        mFakeApiMeeting.addNewTestMeeting(meetingToAdd);
        assertEquals(4, mFakeApiMeeting.getTestMeeting().size());
    }


    /**
     * tests for FakePlaceName
     */
    @Test
    public void getFakePlaceNameWithSucces() {
        String exceptedPlaceName = mFakeApiPlace.getFakePlaceNames().get(0);
        assertNotNull(exceptedPlaceName);
    }

    @Test (expected = IndexOutOfBoundsException.class )
    public void getFakePlaceNameOutOfList() {
        String exceptedPlaceName = mFakeApiPlace.getFakePlaceNames().get(mFakeApiPlace.getFakePlaceNames().size()+1);
        assertTrue(mFakeApiPlace.getFakePlaceNames().contains(exceptedPlaceName));
    }

    @Test
    public void getFakePlaceNameWithSuccessSamePlaceItem() {
        String exceptedPlaceName= mFakeApiPlace.getFakePlaceNames().get(3);
        assertEquals(mFakeApiPlace.getFakePlaceNames().get(3), exceptedPlaceName);
    }





}