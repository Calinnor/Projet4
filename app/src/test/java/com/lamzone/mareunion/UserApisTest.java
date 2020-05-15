package com.lamzone.mareunion;

import com.lamzone.mareunion.di.DI;
import com.lamzone.mareunion.fakeServices.ApiMeeting;
import com.lamzone.mareunion.fakeServices.ApiPlace;
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

    private ApiMeeting mApiMeeting;
    private ApiPlace mApiPlace;

    @Before
    public void setupPlace() {
        mApiPlace = DI.getNewInstanceFakePlaceApi();
    }

    @Before
    public void setupMeeting() {
        mApiMeeting = DI.getNewInstanceFakeApi();
    }

    /**
     * tests for PlaceItem
     */
    @Test
    public void getPlacesWithSuccess() {
        PlaceItem expectedPlaceItem = mApiPlace.getPlaceItem().get(0);
        assertNotNull(expectedPlaceItem);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getPlaceItemOutOfList() {
        PlaceItem expectedPlaceItem = mApiPlace.getPlaceItem().get(mApiPlace.getPlaceItem().size() + 1);
        assertTrue(mApiPlace.getPlaceItem().contains(expectedPlaceItem));
    }

    @Test
    public void getPlaceItemWithSuccessSamePlaceItem() {
        PlaceItem expectedPlaceItem = mApiPlace.getPlaceItem().get(2);
        assertEquals(mApiPlace.getPlaceItem().get(2), expectedPlaceItem);
    }

    /**
     * tests for Meeting
     */
    @Test
    public void getMeetingWithSucces() {
        Meeting exceptedMeeting = mApiMeeting.getMeeting().get(0);
        assertNotNull(exceptedMeeting);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getMeetingOutOfList() {
        Meeting exceptedMeeting = mApiMeeting.getMeeting().get(mApiMeeting.getMeeting().size() + 1);
        assertTrue(mApiMeeting.getMeeting().contains(exceptedMeeting));
    }

    @Test
    public void getMeetingWithSuccessSamePlaceItem() {
        Meeting expectedMeeting = mApiMeeting.getMeeting().get(0);
        assertEquals(mApiMeeting.getMeeting().get(0), expectedMeeting);
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = mApiMeeting.getMeeting().get(0);
        mApiMeeting.deleteMeeting(meetingToDelete);
        assertEquals(2, mApiMeeting.getMeeting().size());
    }

    @Test
    public void deletedMeetingIsMeetingToDelete() {
        Meeting meetingToDelete = mApiMeeting.getMeeting().get(0);
        mApiMeeting.deleteMeeting(meetingToDelete);
        assertNotEquals(meetingToDelete, mApiMeeting.getMeeting().get(0));
    }

    @Test
    public void deleteNeighbourWithSuccessFromList() {
        Meeting meetingToDelete = mApiMeeting.getMeeting().get(0);
        mApiMeeting.deleteMeeting(meetingToDelete);
        assertFalse(mApiMeeting.getMeeting().contains(meetingToDelete));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void deleteMeetingOutOfListSize() {
        Meeting meetingToDelete = mApiMeeting.getMeeting().get(mApiMeeting.getMeeting().size() + 1);
        mApiMeeting.deleteMeeting(meetingToDelete);
    }

    @Test
    public void addMeetingIsMeetingToAdd() {
        Meeting meetingToAdd = mApiMeeting.getMeeting().get(0);
        mApiMeeting.addNewMeeting(meetingToAdd);
        assertEquals(meetingToAdd, mApiMeeting.getMeeting().get(0));
    }

    @Test
    public void addMeetingWithSuccessFromList() {
        int sizeToFind = mApiMeeting.getMeeting().size() + 1;
        Meeting meetingToAdd = mApiMeeting.getMeeting().get(0);
        mApiMeeting.addNewMeeting(meetingToAdd);
        assertEquals(sizeToFind, mApiMeeting.getMeeting().size());
    }


    /**
     * tests for FakePlaceName
     */
    @Test
    public void getFakePlaceNameWithSucces() {
        String exceptedPlaceName = mApiPlace.getFakePlaceNames().get(0);
        assertNotNull(exceptedPlaceName);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getFakePlaceNameOutOfList() {
        String exceptedPlaceName = mApiPlace.getFakePlaceNames().get(mApiPlace.getFakePlaceNames().size() + 1);
        assertTrue(mApiPlace.getFakePlaceNames().contains(exceptedPlaceName));
    }

    @Test
    public void getFakePlaceNameWithSuccessSamePlaceItem() {
        String exceptedPlaceName = mApiPlace.getFakePlaceNames().get(3);
        assertEquals(mApiPlace.getFakePlaceNames().get(3), exceptedPlaceName);
    }


}