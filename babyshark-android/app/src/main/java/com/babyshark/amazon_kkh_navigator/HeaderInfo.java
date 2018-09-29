package com.babyshark.amazon_kkh_navigator;

import java.util.ArrayList;

public class HeaderInfo {

    private String taskName;
    private String distFromCurrentLoc;
    private String taskStatus;
    private ArrayList<DetailInfo> detailsList = new ArrayList<DetailInfo>();

    public HeaderInfo(String name, String taskStatus, String distFromCurrentLoc, ArrayList<DetailInfo> taskList){
        setName(name);
        setTaskStatus(taskStatus);
        setDistFromCurrentLoc(distFromCurrentLoc);
        setDetailList(taskList);
    }

    public String getDistFromCurrentLoc(){
        return distFromCurrentLoc;
    }

    public void setDistFromCurrentLoc(String distFromCurrentLoc){
        this.distFromCurrentLoc = distFromCurrentLoc;
    }

    public String getTaskStatus(){
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus){
        this.taskStatus = taskStatus;
    }

    public String getName() {
        return taskName;
    }
    public void setName(String name) {
        this.taskName = name;
    }
    public ArrayList<DetailInfo> getDetailList() {
        return detailsList;
    }
    public void setDetailList(ArrayList<DetailInfo> detailList) { // This is used for initializing the taskList
        this.detailsList = detailsList;
    }

    public void addDetailsToDetailList(DetailInfo details, int addtoindex){
        if(this.detailsList==null){
            this.detailsList = new ArrayList<DetailInfo>();
        }

        if(addtoindex < 0 || addtoindex > this.detailsList.size()-1){
            this.detailsList.add(details);
        }
        else{
            this.detailsList.add(addtoindex, details);
        }
    }
}
