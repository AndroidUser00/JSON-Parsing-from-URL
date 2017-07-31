package com.example.alexeine.quakereport;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ListView listView;
     String usgs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = c.getTime();
        String date = sdf.format(d);
        usgs="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2017-07-01&endtime="+date+"&minfelt=50&minmagnitude=5";
        Toast.makeText(this, "Date : "+date, Toast.LENGTH_SHORT).show();
Eq eq = new Eq();
        eq.execute();

    }

    public class Eq extends AsyncTask<String, Object, ArrayList<Data>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);

            builder1.setMessage("This Application is still in development phase press yes to continue No to exit");

            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Toast.makeText(MainActivity.this, "Data of all earthquake from all over the globe of this month Click items for more" +
                                            "Information ",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            Toast.makeText(MainActivity.this, "This is my Toast message!",
                                    Toast.LENGTH_LONG).getDuration();

                            MainActivity.this.finish();

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        @Override
        protected ArrayList<Data> doInBackground(String... url) {
            ArrayList<Data> eq = QueryUtils.Fetch_Earthquake_Data(usgs);

            return eq;
        }

        @Override
        protected void onPostExecute(final ArrayList<Data> res) {
            super.onPostExecute(res);
            if (res!=null && res.size()>0){
            Toast.makeText(MainActivity.this, ""+res.size(), Toast.LENGTH_SHORT).show();
            CustomAdapter cs = new CustomAdapter(MainActivity.this, 1, res);
            listView.setAdapter(cs);
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Data d = res.get(position);
        String uri = d.getUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        startActivity(intent);

    }
});
        }else{
                Toast.makeText(MainActivity.this, "Could not retrieve the List of earthquakes", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

