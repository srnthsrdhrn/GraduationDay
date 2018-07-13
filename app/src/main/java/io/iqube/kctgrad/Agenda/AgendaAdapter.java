package io.iqube.kctgrad.Agenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.iqube.kctgrad.Guest.GuestAdapter;
import io.iqube.kctgrad.Home.DrawerActivity;
import io.iqube.kctgrad.MapActivity;
import io.iqube.kctgrad.R;
import io.iqube.kctgrad.model.Agenda;
import io.iqube.kctgrad.model.Guest;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by Navin on 27-02-2017.
 */

public class AgendaAdapter extends RealmBasedRecyclerViewAdapter<Agenda,AgendaAdapter.ViewHolder> {

    private OnItemClickListener listener;
    Context context;
    Activity activity;


    public  interface OnItemClickListener {
    }


    public AgendaAdapter(Context context, RealmResults<Agenda> realmResults, Activity activity)
    {
        super(context,realmResults,true,false);
        this.listener = listener;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v  = inflater.inflate(R.layout.row,viewGroup,false);
        return new AgendaAdapter.ViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {

        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.US);

        Date fromTime = new Date();
        Date toTime = new Date();
        Date date = new Date();
        try {
            fromTime = formats.parse(realmResults.get(i).getFromTime().replace("T", " ").replace("Z", "").trim());
            toTime = formats.parse(realmResults.get(i).getToTime().replace("T", " ").replace("Z", "").trim());
            date = formats.parse(realmResults.get(i).getFromTime().replace("T", " ").replace("Z", "").trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);



        viewHolder.name.setText(realmResults.get(i).getName());
        viewHolder.session.setText("SESSION : "+realmResults.get(i).getSession());
        viewHolder.from.setText(timeFormat.format(fromTime));
        viewHolder.to.setText(timeFormat.format(toTime));
        viewHolder.venue.setText(realmResults.get(i).getVenue().getVenue());
        viewHolder.date.setText(dateFormat.format(date));

        final String venue = realmResults.get(i).getVenue().getVenue();
        final String lat = realmResults.get(i).getVenue().getLat().toString();
        final String  lng = realmResults.get(i).getVenue().getLng().toString();

        viewHolder.venue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doMap(lat,lng,venue);

            }
        });

    }

    class ViewHolder extends RealmViewHolder implements View.OnClickListener
    {
        TextView name,session,from,to,date,venue;

        ViewHolder(LinearLayout v)
        {
            super(v);

            name = (TextView)itemView.findViewById(R.id.name);
            session = (TextView)itemView.findViewById(R.id.session);
            from = (TextView)itemView.findViewById(R.id.fromTime);
            to = (TextView)itemView.findViewById(R.id.toTime);
            date = (TextView)itemView.findViewById(R.id.date);
            venue = (TextView)itemView.findViewById(R.id.venue);

        }

        @Override
        public void onClick(View v) {
        }
    }

    public void doMap(String lat,String lng,String venue)
    {
        Intent intent  = new Intent(activity,MapActivity.class);

        intent.putExtra("Lat",lat);
        intent.putExtra("Lng",lng);
        intent.putExtra("venue",venue);

        getContext().startActivity(intent);
    }
}
