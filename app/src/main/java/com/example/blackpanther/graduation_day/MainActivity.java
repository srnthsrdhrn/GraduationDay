package com.example.blackpanther.graduation_day;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvAgenda;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvAgenda = (ListView)findViewById(R.id.lvagenda);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().endsWith(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    String token = intent.getStringExtra("token");
                    String topic = intent.getStringExtra("topic");
                    Toast.makeText(getApplicationContext(), "GCM token:" + token + "\n" + "GCM topic: " + topic, Toast.LENGTH_LONG).show();
                }else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){

                    Toast.makeText(getApplicationContext(),"GCM Registration Error !!",Toast.LENGTH_LONG).show();
                }else {

                }
            }
        };

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }


    public void fetchEvents()
    {
        Ion.with(this).load("http://iq.bookflip.in/graduation_day/returnevents.php").asString().withResponse().setCallback(
                new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e == null) {
                            String jsonString = result.getResult();

                            try {


                                JSONArray parentArray = new JSONArray(jsonString);

                                List<models> agenda = new ArrayList<>();

                                for (int i = 0; i < parentArray.length(); i++) {
                                    JSONObject finalObject = parentArray.getJSONObject(i);
                                    models agendaModels = new models();
                                    agendaModels.setEvent(finalObject.getString("event"));
                                    agendaModels.setDescription(finalObject.getString("description"));
                                    agendaModels.setStart(finalObject.getString("start_time"));
                                    agendaModels.setEnd(finalObject.getString("end_time"));
                                    agendaModels.setVenue(finalObject.getString("venue"));


                                    agenda.add(agendaModels);
                                }
                                AgendaAdapter adapter = new AgendaAdapter(getApplicationContext(), R.layout.row, agenda);
                                lvAgenda.setAdapter(adapter);

                            } catch (Exception jsonExcep) {

                            }

                        }

                    }
                }
        );
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(ConnectionResult.SUCCESS != resultCode)
        {
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                Toast.makeText(getApplicationContext(),"Google Play Service is not Enabled in this device!",Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplication());
            }
            else
            {
                Toast.makeText(getApplicationContext(),"This device does not support for Google Play Service!",Toast.LENGTH_LONG).show();
            }
        }else {
            Intent intent = new Intent(this,GCMRegistrationIntentService.class);
            startService(intent);
        }
    }


    public class AgendaAdapter extends ArrayAdapter {
        private List<models> agenda;
        private int resource;
        private LayoutInflater inflater;
        public AgendaAdapter(Context context, int resource, List<models> objects) {
            super(context, resource, objects);
            agenda = objects;
            this.resource = resource;
            inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            viewHolder holder = null;

            if(convertView == null)
            {
                holder = new viewHolder();
                convertView = inflater.inflate(resource,null);
                holder.event = (TextView)convertView.findViewById(R.id.eventName);
                holder.description = (TextView)convertView.findViewById(R.id.description);
                holder.time = (TextView)convertView.findViewById(R.id.time);
                holder.venue = (TextView)convertView.findViewById(R.id.venue);
                convertView.setTag(holder);
            }
            else
            {
                holder = (viewHolder) convertView.getTag();
            }
            holder.event.setText(agenda.get(position).getEvent());
            holder.description.setText(agenda.get(position).getDescription());
            holder.venue.setText(agenda.get(position).getVenue());
            holder.time.setText(agenda.get(position).getStart()+"-"+agenda.get(position).getEnd());
            return convertView;
        }

        class viewHolder {

            private TextView event;
            private TextView description;
            private TextView time;
            private TextView venue;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {


            fetchEvents();
        }

        return super.onOptionsItemSelected(item);
    }

}
