package com.babyshark.amazon_kkh_navigator;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;

    private PopupWindow currentViewedTask;

    private LinkedHashMap<String, HeaderInfo> patientTasks = new LinkedHashMap<>(); //Maps Task Name to respective Task Details for tracking purposes
    private ArrayList<HeaderInfo> _queuedTaskList = new ArrayList<HeaderInfo>(); // Used to handle populating the ExpandableTaskList

    private SlidePanelListAdapter myListAdapter;
    private ExpandableListView expandableListView;

    private String currentActiveTaskName; // This is to keep track of the current active task so that we can update the respective views

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings3: //Show Payment Options Button

                ShowPopupMenu(R.layout.payment_hardcodedview);
                return true;
        }

        return false;
    }

    public void ShowPopupMenu(int layouttoshow) { //Not in use at the moment can be used to pop-up a new view
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        //https://stackoverflow.com/questions/24997930/trouble-working-with-showatlocationview-int-int-int-for-popupwindow
        // Inflate the custom layout/view
        View customView = inflater.inflate(layouttoshow, null);

        currentViewedTask = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );


        // Set an elevation value for popup window, This Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            currentViewedTask.setElevation(5.0f);
        }

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // Dismiss the popup window
                currentViewedTask.dismiss();
            }
        });

        currentViewedTask.showAtLocation(findViewById(R.id.sliding_layout), Gravity.BOTTOM, 0, 0);
    }

    WifiManager wifiManager;
    List<ScanResult> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflator.inflate(R.layout.actionbar_custom, null);
            Resources res = (Resources) getResources();
            TextView textview = view.findViewById(R.id.actionbar_title);
            textview.setText(res.getString(R.string.main_activity));

            actionBar.setCustomView(view);

            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            //https://stackoverflow.com/questions/27612424/v7-21-actionbaractivity-not-showing-the-app-main-icon-on-the-left
            //https://stackoverflow.com/questions/16026007/remove-padding-around-action-bar-left-icon-on-android-4-0
            actionBar.setIcon(R.drawable.kkh_logo);
        }

        setContentView(R.layout.activity_main);

        initNavigation();
        HardCodedTaskDataInit();

        GenerateSlidePanelContentButtons(); // This only Generates Content for the ExpandableListView
        //The UI for the currenttasks has to be generated seperately using the updateCurrentActiveTaskView() Method
        updateCurrentActiveTaskView(patientTasks.get(currentActiveTaskName));
    }

    private void HardCodedTaskDataInit() { //We Hardcode the Task HeaderInfo and DetailInfo within this method

        //This Method Should Be Run Before GenerateSlidePanelContentButtons() to properly init the values used by GenerateSlidePanelContentButtons() Method

        addTask(Task.values[0][0], Task.values[0][1], Task.values[0][2], Task.values[0][3], Task.values[0][4], Task.values[0][5], Task.values[0][6], -1, false);
        addTask(Task.values[1][0], Task.values[1][1], Task.values[1][2], Task.values[1][3], Task.values[1][4], Task.values[1][5], Task.values[1][6], -1, true);
        addTask(Task.values[2][0], Task.values[2][1], Task.values[2][2], Task.values[2][3], Task.values[2][4], Task.values[2][5], Task.values[2][6], -1, true);
        addTask(Task.values[3][0], Task.values[3][1], Task.values[3][2], Task.values[3][3], Task.values[3][4], Task.values[3][5], Task.values[3][6], -1, true);
    }

    private void GenerateSlidePanelContentButtons() {

        // Get reference to the ExpandableListView
        expandableListView = (ExpandableListView) findViewById(R.id.list); //References the ListView in activity_main.xml

        //create the adapter by passing your ArrayList data
        myListAdapter = new SlidePanelListAdapter(mContext, (ArrayList<HeaderInfo>) (ArrayList<HeaderInfo>) _queuedTaskList);

        expandableListView.setGroupIndicator(null);

        //attach the adapter to the list
        expandableListView.setAdapter(myListAdapter);

        expandableListView.setOnChildClickListener(myListItemClicked); // Listener for child row click
        expandableListView.setOnGroupClickListener(myListGroupClicked); // Listener for group heading click
    }

    //our child listener
    private ExpandableListView.OnChildClickListener myListItemClicked = new ExpandableListView.OnChildClickListener() {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {

            //Listens for when child elements of a Task is clicked
            parent.collapseGroup(groupPosition);
            //get the group header
            HeaderInfo headerInfo = _queuedTaskList.get(groupPosition);
            //get the child info
            DetailInfo detailInfo = headerInfo.getDetailList().get(childPosition);
            return false;
        }
    };

    //our group listener
    private ExpandableListView.OnGroupClickListener myListGroupClicked = new ExpandableListView.OnGroupClickListener() {

        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            // Detects when a task Button is clicked
            //get the group header
            HeaderInfo headerInfo = _queuedTaskList.get(groupPosition);

            if (!parent.isGroupExpanded(groupPosition)) {
                return false;
            } else {
                parent.collapseGroup(groupPosition);
                return true;
            }
        }
    };

    private boolean removeTask(String taskName) {

        if(taskName==currentActiveTaskName){
            getAndSetNextTask();
        }

        //Check the hash map if the task exists
        HeaderInfo headerInfo = patientTasks.get(taskName);

        if (headerInfo != null) {

            for (int i = 0; i < _queuedTaskList.size(); i++) {
                if (_queuedTaskList.get(i).getName() == headerInfo.getName()) {
                    _queuedTaskList.remove(i);
                    patientTasks.remove(taskName);
                    myListAdapter.notifyDataSetChanged();
                    return true;
                }
            }
        }

        return false;
    }

    private void addTask(String taskName, String taskStatus, String distFromCurrentLoc, String taskPurpose, String doctorName, String duration, String otherDetails, int index, boolean queueTask) {

        //distFromCurrentLoc is a value of the distance from the current task location to the next.
        //This Method is also responsible for adding DetailInfo to a Task
        //After The OnCreate Method, any calls to the addTask Method must be accompanied by a call to the myListAdapter.notifyDataSetChanged() Method

        //Check the hash map if the task already exists
        HeaderInfo headerInfo = patientTasks.get(taskName);

        //Add if the task doesn't exist
        if (headerInfo == null) {

            ArrayList<DetailInfo> details = new ArrayList<DetailInfo>();

            headerInfo = new HeaderInfo(taskName, taskStatus, distFromCurrentLoc, details);
            patientTasks.put(taskName, headerInfo);

            if (index < 0 || index > _queuedTaskList.size() - 1) {
                if(queueTask){
                    _queuedTaskList.add(headerInfo);
                }
            } else {
                if(queueTask){
                    _queuedTaskList.add(index, headerInfo);
                }
            }
        }

        if (taskStatus != headerInfo.getTaskStatus()) { //Update Task Status if value is different from input
            headerInfo.setTaskStatus(taskStatus);

            if (patientTasks.containsKey(taskName)) {
                patientTasks.put(taskName, headerInfo);
            }
        }

        ArrayList<DetailInfo> detailList = headerInfo.getDetailList();

        DetailInfo detailInfo = new DetailInfo(taskPurpose, doctorName, duration, otherDetails); //Create a new child Detail and add that to the group

        detailList.add(detailInfo);
        headerInfo.setDetailList(detailList);

        if (patientTasks.containsKey(taskName)) {
            patientTasks.put(taskName, headerInfo);
        }

        if(!queueTask){
            currentActiveTaskName = taskName;
        }
    }

    //Method to expand all groups(AKA Our Tasks)
    private void expandAll() {
        int count = myListAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandableListView.expandGroup(i);
        }
    }

    //Method to collapse all groups(AKA Our Tasks)
    private void collapseAll() {
        int count = myListAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandableListView.collapseGroup(i);
        }

    }
    public void initNavigation(){
        Navigation navigation = new Navigation(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void clearAllTasks(){
        //Clear all tasks and update the ExpandableListView accordingly
        patientTasks.clear();
        _queuedTaskList.clear();

        clearCurrentTaskInView();
        currentActiveTaskName = null;

        if(myListAdapter!=null){
            myListAdapter.notifyDataSetChanged();
        }
    }

    private boolean updateTask(String taskName, String taskStatus, String distFromCurrentLoc){
        //Updates a tasks HeaderInfo
        //Check the hash map if the task already exists
        HeaderInfo headerInfo = patientTasks.get(taskName);

        if (headerInfo != null) {

            if(headerInfo.getTaskStatus()!=taskStatus){
                headerInfo.setTaskStatus(taskStatus);
            }

            if(headerInfo.getDistFromCurrentLoc()!=distFromCurrentLoc){
                headerInfo.setDistFromCurrentLoc(distFromCurrentLoc);
            }

            patientTasks.put(taskName,headerInfo);

            if(currentActiveTaskName==taskName){
                updateCurrentActiveTaskView(headerInfo);
            }

            return true;
        }

        return false;
    }


    private void getAndSetNextTask(){

        try{
            HeaderInfo info = _queuedTaskList.get(0);
            currentActiveTaskName = info.getName();

            patientTasks.remove(currentActiveTaskName);
            _queuedTaskList.remove(0);

            updateCurrentActiveTaskView(info);
            myListAdapter.notifyDataSetChanged();
        }
        catch(IndexOutOfBoundsException o){
            Log.i(o.toString(), "No more queued tasks");
        }
    }

    private void updateCurrentActiveTaskView(HeaderInfo info){

        //Set the Header
        TextView heading = (TextView) findViewById(R.id.heading);
        heading.setText(info.getName().trim());
        TextView distance = (TextView) findViewById(R.id.distance);
        distance.setText(info.getDistFromCurrentLoc().trim());
        TextView taskStatus = (TextView) findViewById(R.id.taskstatus);
        taskStatus.setText(info.getTaskStatus().trim());

        //For now we assume tasks will only have one detail object so we get and set the values at index 0

        try{

            DetailInfo details = info.getDetailList().get(0);

            //Set the Details
            TextView doctor = (TextView) findViewById(R.id.doctor);
            doctor.setText(getResources().getString(R.string.doctor) + details.getDoctorName().trim());

            TextView taskPurpose = (TextView) findViewById(R.id.taskpurpose);
            taskPurpose.setText(getResources().getString(R.string.purpose) + details.getTaskPurpose().trim());

            TextView duration = (TextView) findViewById(R.id.duration);
            duration.setText(getResources().getString(R.string.duration) + details.getDuration().trim());

            TextView otherdetails = (TextView) findViewById(R.id.otherdetails);
            otherdetails.setText(getResources().getString(R.string.details) + details.getOtherDetails().trim());
        }
        catch(IndexOutOfBoundsException o){
            Log.e(o.toString(), "HeaderInfo doesnt contain DetailInfo at index 0 of its detailsList");
        }
    }

    private void clearCurrentTaskInView(){//Only handles clearing the view of text, Clears the CurrentActiveTask View
        TextView heading = (TextView) findViewById(R.id.heading);
        heading.setText("");
        TextView distance = (TextView) findViewById(R.id.distance);
        distance.setText("");
        TextView taskStatus = (TextView) findViewById(R.id.taskstatus);
        taskStatus.setText("");

        TextView doctor = (TextView) findViewById(R.id.doctor);
        doctor.setText("");

        TextView taskPurpose = (TextView) findViewById(R.id.taskpurpose);
        taskPurpose.setText("");

        TextView duration = (TextView) findViewById(R.id.duration);
        duration.setText("");

        TextView otherdetails = (TextView) findViewById(R.id.otherdetails);
        otherdetails.setText("");
    }
}

