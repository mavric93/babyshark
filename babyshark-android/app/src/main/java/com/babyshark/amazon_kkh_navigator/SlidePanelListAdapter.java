package com.babyshark.amazon_kkh_navigator;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class SlidePanelListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private ArrayList<HeaderInfo> deptList;

    public SlidePanelListAdapter(Context context, ArrayList<HeaderInfo> deptList) {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<DetailInfo> productList =
                deptList.get(groupPosition).getDetailList();
        return productList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        Log.e("I happened", "Message me");
        DetailInfo detailInfo = (DetailInfo) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_row, null);
            Log.e("Creating new Child View", "");
        }

        TextView doctor = (TextView) view.findViewById(R.id.doctor);
        doctor.setText(detailInfo.getDoctorName().trim());

        TextView taskPurpose = (TextView) view.findViewById(R.id.taskpurpose);
        taskPurpose.setText(detailInfo.getTaskPurpose().trim());

        TextView duration = (TextView) view.findViewById(R.id.duration);
        duration.setText(detailInfo.getDuration().trim());

        TextView otherdetails = (TextView) view.findViewById(R.id.otherdetails);
        otherdetails.setText(detailInfo.getOtherDetails().trim());

        Log.e("Child View Get", "");

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<DetailInfo> taskList =
                deptList.get(groupPosition).getDetailList();
        return taskList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        HeaderInfo headerInfo = (HeaderInfo) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.group_heading, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(headerInfo.getName().trim());

        TextView distance = (TextView) view.findViewById(R.id.distance);
        distance.setText(headerInfo.getDistFromCurrentLoc().trim());

        TextView taskStatus = (TextView) view.findViewById(R.id.taskstatus);
        taskStatus.setText(headerInfo.getTaskStatus().trim());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
