package io.iqube.kctgrad;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AgendaFragment extends Fragment {


    ListView lvAgenda;
    ProgressDialog ring;



    public static AgendaFragment newInstance() {
        AgendaFragment fragment = new AgendaFragment();

        return fragment;
    }

    public AgendaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvAgenda = (ListView)getView().findViewById(R.id.lvagenda);
        fetchEvents();
    }


    public void fetchEvents() {
        showSpinner();
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
                                AgendaAdapter adapter = new AgendaAdapter(getActivity().getApplicationContext(), R.layout.row, agenda);
                                lvAgenda.setAdapter(adapter);

                            } catch (Exception jsonExcep) {

                            }

                            hideLoader();

                        }
                        else
                        {
                            Toast.makeText(getContext(),"Some Error Happened",Toast.LENGTH_SHORT).show();
                        }


                    }
                }
        );
    }
    public class AgendaAdapter extends ArrayAdapter {
        private List<models> agenda;
        private int resource;
        private LayoutInflater inflater;
        public AgendaAdapter(Context context, int resource, List<models> objects) {
            super(context, resource, objects);
            agenda = objects;
            this.resource = resource;
            inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
           // holder.time.setText(agenda.get(position).getStart()+"-"+agenda.get(position).getEnd());
            return convertView;
        }

        class viewHolder {

            private TextView event;
            private TextView description;
            private TextView time;
            private TextView venue;
        }

    }

    protected void showSpinner()
    {

        ring = ProgressDialog.show(getActivity(), "Please wait ...", "Loading ...", true);

        ring.setCancelable(false);
    }

    protected void hideLoader()
    {
        if(ring!=null)
            ring.cancel();
    }
}