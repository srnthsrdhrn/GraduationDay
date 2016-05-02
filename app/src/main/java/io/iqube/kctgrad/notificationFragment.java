package io.iqube.kctgrad;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.iqube.kctgrad.model.Question;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link notificationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link notificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView lvAgenda;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notificationFragment.
     */
    // TODO: Rename and change types and number of parameters
   // public static notificationFragment newInstance(String param1, String param2) {
     //   notificationFragment fragment = new notificationFragment();
       // Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
     //   return fragment;
   // }

    public notificationFragment() {
        // Required empty public constructor
    }

    public static FeedbackFragment newInstance() {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lvAgenda = (ListView)getView().findViewById(R.id.lvagenda);
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchEvents();


    }

    public void fetchEvents() {
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



        // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
