package com.babyshark.amazon_kkh_navigator;

public final class Task {

    private Task() {
    }

    public static final String[][] values = new String[][]{
            // Format is TaskName, CurrentDist, TaskStatus, Doctor, Purpose, Duration, OtherDetails Respectively
            {"Blood Test", "80m", "Doctor Is Waiting", "Dr Bryan Aw", "Verification For Diagnosis", "20 Minutes", " --- "},
            {"Head to Room 160 for X-Ray", " 100m ", "Upcoming", "Dr Prakesh", "Verification For Diagnosis", "30 Minutes", " --- "},
            {"Head to Room 141 for Temperature Taking", " 70m ", " Done ", "Dr Kim Jung On", " --- ", "Completed", " --- "},
            {"Head to Room 168 for Consultation", "50m", "Upcoming", "Dr Trump", "General Consultation", "30Min", " --- "}
    };
}
