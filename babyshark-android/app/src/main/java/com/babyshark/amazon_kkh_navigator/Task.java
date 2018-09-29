package com.babyshark.amazon_kkh_navigator;

public final class Task {

    private Task(){}

    public static final String[][] values = new String[][]{
    // Format is TaskName, CurrentDist, TaskStatus, Doctor, Purpose, Duration, OtherDetails Respectively
            {"Head to Room 157 for Blood Test", "80m", "Doctor Is Ready", "Dr Bryan Aw", "Verification For Diagnosis", "1HR Estimated", " --- "},
            {"Head to Room 160 for X-Ray", " -- ", "Pending", "Dr Prakesh", "Verification For Diagnosis", "30Min Estimated", " --- "},
            {"Head to Room 141 for Temperature Taking", " --- ", " --- ", "Dr Kim Jung On", " --- ", "Completed", " --- "}
    };
}
