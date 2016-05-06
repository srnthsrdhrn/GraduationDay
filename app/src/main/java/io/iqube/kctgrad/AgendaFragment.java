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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.iqube.kctgrad.model.program;


public class AgendaFragment extends Fragment {


    ListView lvAgenda;
    ProgressDialog ring;

    View v;


    ArrayList<program> programs;

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
     //   fetchEvents();
        fillData();

        LinearLayout HeaderView= (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.lv_agenda_header,null);
        lvAgenda.addHeaderView(HeaderView);
        AgendaAdapter adapter = new AgendaAdapter(getActivity().getApplicationContext(), R.layout.row, programs);
        lvAgenda.setAdapter(adapter);

        Button b=(Button) view.findViewById(R.id.venue);
       final  View v=view.findViewById(R.id.venueimg);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                v.setVisibility(View.VISIBLE);
            }
        });

        Button b1=(Button)v.findViewById(R.id.exit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v11) {
                v.setVisibility(View.INVISIBLE);
            }
        });

        ImageView img=(ImageView)view.findViewById(R.id.an);



        Picasso.with(getContext()).load(R.drawable.an).into(img, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
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
                                AgendaAdapter adapter = new AgendaAdapter(getActivity().getApplicationContext(), R.layout.row, programs);
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


    public void fillData()
    {

        programs=new ArrayList<>();
        program p=new program("Registration and assembling","08:30 AM","12:00 PM","08:30 AM");
        programs.add(p);
        p=new program("Rehearsal","09:30 AM","02:00 PM","09:30 AM");
        programs.add(p);
        p=new program("Arrival of Principal, HoDs to the venue","09:50 AM","02:20 PM","09:50 AM");
        programs.add(p);
        p=new program("Arrival of the Dignitaries","09.55 AM","2:25 PM","09.55 AM");
        programs.add(p);
        p=new program("Robing","10:00 AM","2:30 PM","10:00 AM");
        programs.add(p);
        p=new program("Academic Procession starts","10:05 AM","2:35 PM","10:05 AM");
        programs.add(p);
        p=new program("Occupying the Dias","10:10 AM","2:40 PM","10:10 AM");
        programs.add(p);
        p=new program("Invocation - Tamil Thai Vazhthu","10.12 AM","2:42 PM","10.12 AM");
        programs.add(p);
        p=new program("Welcome Address ","10.15 AM","2:45 PM","10.15 AM");
        programs.add(p);
        p=new program("College Report(2015-16) by the Principal","10:20 AM","2:50 PM","10:20 AM");
        programs.add(p);
        p=new program("Declaring the graduation ceremony open","10:35 AM","3:05 PM","10:35 AM");
        programs.add(p);
        p=new program("Introduction of Chief Guest","10.36 AM","3:06 PM","10.36 AM");
        programs.add(p);
        p=new program("Graduation Day Address by the Chief Guest","10.40 AM","3:10 PM","10.40 AM");
        programs.add(p);
        p=new program("Presentation of candidates for degrees","10.55 AM","3:25 PM","10.55 AM");
        programs.add(p);
        p=new program("Administering the pledge","12.15 PM","4:40 PM","12.15 PM");
        programs.add(p);
        p=new program("Conferment of the degrees","12.18 PM","4:43 PM","12.18 PM");
        programs.add(p);
        p=new program("Signing the register of graduates","12.21 PM","4:46 PM","12.21 PM");
        programs.add(p);
        p=new program("Dissolution of the graduation ceremony ","12:23 PM","4:48 PM","12:23 PM");
        programs.add(p);
        p=new program("National Anthem ","12:24 PM","4:49 PM","12:24 PM");
        programs.add(p);
        p=new program("Academic Procession proceeds back to the robing chamber","12:26 PM","4:51 PM","12:26 PM");
        programs.add(p);






    }

    public class AgendaAdapter extends ArrayAdapter {
        private List<program> agenda;
        private int resource;
        private LayoutInflater inflater;
        public AgendaAdapter(Context context, int resource, List<program> objects) {
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
                holder.name = (TextView)convertView.findViewById(R.id.name);
                holder.s1 = (TextView)convertView.findViewById(R.id.s1);
                holder.s2 = (TextView)convertView.findViewById(R.id.s2);
                holder.s3 = (TextView)convertView.findViewById(R.id.s3);
                convertView.setTag(holder);
            }
            else
            {
                holder = (viewHolder) convertView.getTag();
            }
            holder.name.setText(programs.get(position).getName());
            holder.s1.setText(programs.get(position).getS1());
            holder.s2.setText(programs.get(position).getS2());
            holder.s3.setText(programs.get(position).getS3());

            // holder.time.setText(agenda.get(position).getStart()+"-"+agenda.get(position).getEnd());
            return convertView;
        }

        class viewHolder {

            private TextView name;
            private TextView s1;
            private TextView s2;
            private TextView s3;
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