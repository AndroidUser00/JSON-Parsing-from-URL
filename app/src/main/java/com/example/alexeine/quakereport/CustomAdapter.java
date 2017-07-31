package com.example.alexeine.quakereport;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexeine on 28-07-2017.
 */

public class CustomAdapter extends ArrayAdapter<Data> {
    ArrayList<Data> dataList;
    Context context;
    int  resource;
private static final String SEPERATOR = " of ";
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView  = inflater.inflate(R.layout.customlistitem,null,true);

        }
        Data datas  = getItem(position);

        TextView magnitude = (TextView)convertView.findViewById(R.id.magText);
       // magnitude.setText(datas.getMag());
        TextView place=(TextView)convertView.findViewById(R.id.primaryLocation);
        TextView offset=(TextView)convertView.findViewById(R.id.offset);
String fomattedMagnitude  = formatMagnitude(datas.getMagnitude());
       // place.setText(datas.getPlace());

magnitude.setText(fomattedMagnitude);
        GradientDrawable magnitudeCirlce = (GradientDrawable)magnitude.getBackground();
        int magnitudeColor=getMagnitudeColor(datas.getMagnitude());
        magnitudeCirlce.setColor(magnitudeColor);

TextView time =(TextView)convertView.findViewById(R.id.timeText);
        Date dateObject = new Date(datas.getTime());
        TextView date =(TextView)convertView.findViewById(R.id.dateText);
String formdate = formatDate(dateObject);
        date.setText(formdate);

        String formtime = formatTime(dateObject);
        time.setText(formtime);


        String originalLocation = datas.getPlace();
String primaryLocation ;
        String locationOffset;
if (originalLocation.contains(SEPERATOR)){
    String[] parts  = originalLocation.split(SEPERATOR);
    locationOffset = parts[0] + SEPERATOR;
    primaryLocation=parts[1];
}else{
    locationOffset = getContext().getString(R.string.near_the);
    primaryLocation = originalLocation;
}
        place.setText(primaryLocation);
        offset.setText(locationOffset);

        return convertView;
    }

    private int getMagnitudeColor(double mag) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(mag);
        if(mag>=0 && mag<=1){
            magnitudeColorResourceId = R.color.magnitude1;

        }
      else  if(mag>=0 && mag<=1.5){
            magnitudeColorResourceId = R.color.magnitude2;

        }
        else  if(mag>=1.6 && mag<=2.5){
            magnitudeColorResourceId = R.color.magnitude3;

        }
        else  if(mag>=2.6 && mag<=3.5){
            magnitudeColorResourceId = R.color.magnitude4;

        }
        else  if(mag>=3.6 && mag<=4.5){
            magnitudeColorResourceId = R.color.magnitude5;

        }
        else  if(mag>=4.6 && mag<=5.5){
            magnitudeColorResourceId = R.color.magnitude6;

        }
        else  if(mag>=5.6 && mag<=6.5){
            magnitudeColorResourceId = R.color.magnitude7;

        }
        else  if(mag>=6.6 && mag<=7.5){
            magnitudeColorResourceId = R.color.magnitude8;

        }
        else  if(mag>=7.5 && mag<=8.5){
            magnitudeColorResourceId = R.color.magnitude9;

        }
        else  if(mag>=8/6 && mag<=9.5){
            magnitudeColorResourceId = R.color.magnitude10plus;

        }else{
            magnitudeColorResourceId = R.color.magnitude10plus;

        }
        /*switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }*/
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    private String formatMagnitude(double mag) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(mag);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    public CustomAdapter(Context context, int resource, ArrayList<Data> datas) {
        super(context, resource,datas);
        this.dataList = datas;
        this.context = context;
        this.resource = resource;



    }
}
