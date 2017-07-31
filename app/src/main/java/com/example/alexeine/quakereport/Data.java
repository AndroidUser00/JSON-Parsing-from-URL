package com.example.alexeine.quakereport;

/**
 * Created by Alexeine on 28-07-2017.
 */

public class Data{
    private double magnitude;
    private String place;
    private long date;
    private long time;
    private String url;

    public Data(double magnitude, String place, Long date, Long time, String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.date = date;
        this.time=time;
        this.url=url;
    }
    public  double getMagnitude()
    {
        return magnitude;
    }
    public  String getPlace()
    {
        return place;
    }
    public Long getDate()
    {
        return date;
    }
    public long getTime()
    {
        return time;
    }
    public String getUrl()
    {
        return url;
    }

}