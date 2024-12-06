package org.example;

import java.sql.Time;
import java.sql.Timestamp;

public class PeriodReport {
    private int itemId;
    private double value;
    private Timestamp startTime;
    private Timestamp endTime;
    private long duration;

    public PeriodReport(int itemId, double value, Timestamp startTime, Timestamp endTime, long duration) {
        this.itemId = itemId;
        this.value = value;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public int getItemId() {
        return itemId;
    }


    public double getValue() {
        return value;
    }


    public Timestamp getStartTime() {
        return startTime;
    }


    public Timestamp getEndTime() {
        return endTime;
    }


    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Item ID: " + itemId +
                "\nValue: " + value +
                "\nStart Time: " + startTime +
                "\nEnd Time: " + endTime +
                "\nDuration (seconds): " + duration;
    }
}
