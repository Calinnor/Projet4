package com.lamzone.mareunion.controler.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lamzone.mareunion.R;
import com.lamzone.mareunion.controler.fragment.DatePickerFragment;
import com.lamzone.mareunion.di.DI;
import com.lamzone.mareunion.fakeServices.ApiMeeting;
import com.lamzone.mareunion.fakeServices.ApiPlace;
import com.lamzone.mareunion.model.Meeting;
import com.lamzone.mareunion.view.event.DeleteMeetingEvent;
import com.lamzone.mareunion.view.recycler.MyMeetingAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ApiMeeting mApiMeeting;
    ApiPlace mApiPlace;
    private List<Meeting> mMeeting = new ArrayList<>();
    @BindView(R.id.list_meetings_for_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_meeting)
    FloatingActionButton addNewMeetingButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String mDateToFilter;
    @BindView(R.id.lbl_no_task)
    TextView textViewNothingToShow;

    private MyMeetingAdapter myMeetingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_meeting);

        ButterKnife.bind(this);
        mApiMeeting = DI.getFakeMeetingApi(); //return a list
        mApiPlace = DI.getApiFakePlace();
        this.configureToolbar();
        clickOnAddNewMeetingButton();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyMeetingAdapter(mMeeting));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initEmptyList();
    }

    private void initEmptyList() {
        mApiMeeting.getMeeting().clear();
        myMeetingAdapter.updateMeetings(mApiMeeting.getMeeting());
        selectVisibility();
    }

    private void selectVisibility() {
        if (mApiMeeting.getMeeting().size() == 0) {
            textViewNothingToShow.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            textViewNothingToShow.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void initList() {
        myMeetingAdapter = new MyMeetingAdapter(mApiMeeting.getMeeting());
        mRecyclerView.setAdapter(myMeetingAdapter);
        selectVisibility();
    }



    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_meeting_list_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_all_meeting:
                initList();
                break;
            case R.id.filter_meeting_by_date:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePickerMain");
                break;
            case R.id.filter_meeting_by_place:
                dialogBoxForPlaceNameFiltering();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteMeetingEvent event) {
        mApiMeeting.deleteMeeting(event.mMeeting);
        initList();
    }

    public void clickOnAddNewMeetingButton() {
        addNewMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMeetingActivity.this, AddNewMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initListPlaceName(String placeName) {
        mMeeting = mApiMeeting.getMeeting();
        List<Meeting> mMeetingPlaceFiltered = new ArrayList<>();
        for (Meeting meeting : mMeeting) {
            if (meeting.getMeetingPlaceName().equals(placeName)) mMeetingPlaceFiltered.add(meeting);
        }
        mRecyclerView.setAdapter(new MyMeetingAdapter(mMeetingPlaceFiltered));
    }

    private void dialogBoxForPlaceNameFiltering() {
        List<String> fakePlaceNames = new ArrayList<>(mApiPlace.getFakePlaceNames());
        String[] placeNamesToFiltered = new String[fakePlaceNames.size()];
        fakePlaceNames.toArray(placeNamesToFiltered);
        final String[] places = new String[1];
        final AlertDialog.Builder builderRoom = new AlertDialog.Builder(this);
        builderRoom.setTitle("Choisissez une Salle");
        builderRoom.setSingleChoiceItems(placeNamesToFiltered, -1, (dialog, placeName) -> places[0] = placeNamesToFiltered[placeName]);
        builderRoom.setPositiveButton("OK", (dialogInterface, i) -> initListPlaceName(places[0]));
        builderRoom.setNegativeButton("Annuler", (dialog, resetButton) -> initEmptyList());
        AlertDialog dialogRoom = builderRoom.create();
        dialogRoom.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (month < 10 && dayOfMonth < 10) {
            mDateToFilter = "0" + dayOfMonth + "/0" + month + "/" + year;
        } else if (month < 10) {
            mDateToFilter = dayOfMonth + "/0" + month + "/" + year;
        } else if (dayOfMonth < 10) {
            mDateToFilter = "0" + dayOfMonth + "/" + month + "/" + year;
        } else {
            mDateToFilter = dayOfMonth + "/" + month + "/" + year;
        }
        mMeeting = mApiMeeting.getMeeting();
        List<Meeting> mMeetingDateFiltered = new ArrayList<>();
        for (Meeting meeting : mMeeting) {
            if (meeting.getMeetingDate().equals(mDateToFilter))
                mMeetingDateFiltered.add(meeting);
        }
        mRecyclerView.setAdapter(new MyMeetingAdapter(mMeetingDateFiltered));
    }

}
