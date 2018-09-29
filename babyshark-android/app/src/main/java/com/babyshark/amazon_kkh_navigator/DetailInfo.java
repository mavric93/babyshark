package com.babyshark.amazon_kkh_navigator;

/// This Class Contains the Details Of A Patients Task

public class DetailInfo {

    private String taskPurpose = "";
    private String doctorName = "";
    private String duration = "";
    private String otherDetails = "";

    public DetailInfo(String taskPurpose, String doctorName, String duration, String otherDetails){
        setTaskPurpose(taskPurpose);
        setDoctorName(doctorName);
        setDuration(duration);
        setOtherDetails(otherDetails);
    }

    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getTaskPurpose() {
        return taskPurpose;
    }
    public void setTaskPurpose(String taskPurpose) {
        this.taskPurpose = taskPurpose;
    }

    public String getDuration(){
        return duration;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public String getOtherDetails(){
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails){
        this.otherDetails = otherDetails;
    }
}
