package com.babyshark.amazon_kkh_navigator;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;

    private PopupWindow currentViewedTask;
    SlidingUpPanelLayout slidePanelLayout;

    private LinkedHashMap<String, HeaderInfo> patientTasks = new LinkedHashMap<>(); //Maps Task Name to respective Task Details
    private ArrayList<HeaderInfo> _taskList = new ArrayList<>(); // Used to handle populating the expandable Task List

    private SlidePanelListAdapter myListAdapter;
    private ExpandableListView expandableListView;


    public void ShowPopupMenu(View v){
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.taskpopupwindow,null);

        currentViewedTask = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );


        // Set an elevation value for popup window, This Call requires API level 21
        if(Build.VERSION.SDK_INT>=21){
            currentViewedTask.setElevation(5.0f);
        }

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // Dismiss the popup window
                currentViewedTask.dismiss();
            }
        });

        currentViewedTask.showAtLocation(slidePanelLayout, Gravity.CENTER,0,0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        HardCodedTaskDataInit();
        GenerateSlidePanelContentButtons();
        showCurrentTask();
    }

    private void HardCodedTaskDataInit(){ //We Hardcode the Task HeaderInfo and DetailInfo within this method

        //This Method Should Be Run Before GenerateSlidePanelContentButtons() to properly init the values used by GenerateSlidePanelContentButtons() Method

        addTask(Task.values[0][0],Task.values[0][1],Task.values[0][2],Task.values[0][3],Task.values[0][4],Task.values[0][5],Task.values[0][6], -1);
        addTask(Task.values[1][0],Task.values[1][1],Task.values[1][2],Task.values[1][3],Task.values[1][4],Task.values[1][5],Task.values[1][6], -1);
        addTask(Task.values[2][0],Task.values[2][1],Task.values[2][2],Task.values[2][3],Task.values[2][4],Task.values[2][5],Task.values[2][6], -1);
    }

    private void GenerateSlidePanelContentButtons(){

        // Get reference to the ExpandableListView
        expandableListView = (ExpandableListView) findViewById(R.id.list); //References the ListView in activity_main.xml

        //create the adapter by passing your ArrayList data
        myListAdapter = new SlidePanelListAdapter(MainActivity.this, _taskList);

        //attach the adapter to the list
        expandableListView.setAdapter(myListAdapter);

        //listener for child row click
        expandableListView.setOnChildClickListener(myListItemClicked);
    }

    private boolean showCurrentTask(){
        if(expandableListView.getCount() > 0){
            expandableListView.expandGroup(0);
            return true;
        }
        return false;
    }

    //our child listener
    private ExpandableListView.OnChildClickListener myListItemClicked =  new ExpandableListView.OnChildClickListener() {

        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {

            collapseAll();
            showCurrentTask();

            //get the group header
            HeaderInfo headerInfo = _taskList.get(groupPosition);
            //get the child info
            DetailInfo detailInfo =  headerInfo.getDetailList().get(childPosition);
            return false;
        }
    };

    private int addTask(String taskName, String taskStatus, String distFromCurrentLoc, String taskPurpose, String doctorName, String duration, String otherDetails, int index){

        //distFromCurrentLoc is a value of the distance from the current task location to the next.
        //This Method is also responsible for adding DetailInfo to a Task

        int groupPosition = 0;

        //Check the hash map if the task already exists
        HeaderInfo headerInfo = patientTasks.get(taskName);

        //Add if the task doesn't exist
        if(headerInfo == null){

            ArrayList<DetailInfo> details = new ArrayList<DetailInfo>();
            details.add(new DetailInfo(taskPurpose, doctorName, duration, otherDetails));

            headerInfo = new HeaderInfo(taskName,  taskStatus,  distFromCurrentLoc, details);
            patientTasks.put(taskName, headerInfo);

            if(index <0 || index > _taskList.size()-1){
                _taskList.add(headerInfo);
                groupPosition = _taskList.size()-1;
            }
            else{
                _taskList.add(index,headerInfo);
                groupPosition = index;
            }

        }
        else { // If a Task already exists we just add DetailInfo

            if(taskStatus != headerInfo.getTaskStatus()){ //Update Task Status if value is different from input
                headerInfo.setTaskStatus(taskStatus);
            }

            ArrayList<DetailInfo> detailList = headerInfo.getDetailList();

            int listSize = detailList.size(); //Size of the children list

            DetailInfo detailInfo = new DetailInfo(taskPurpose, doctorName, duration, otherDetails); //Create a new child Detail and add that to the group

            detailList.add(detailInfo);
            headerInfo.setDetailList(detailList);

            //find the group position inside the list
            groupPosition = _taskList.indexOf(headerInfo);
        }

        return groupPosition;
    }

    //Method to expand all groups(AKA Our Tasks)
    private void expandAll() {
        int count = myListAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableListView.expandGroup(i);
        }
    }

    //Method to collapse all groups(AKA Our Tasks)
    private void collapseAll() {
        int count = myListAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableListView.collapseGroup(i);
        }
    }
}
