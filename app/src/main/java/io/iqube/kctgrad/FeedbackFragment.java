package io.iqube.kctgrad;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.iqube.kctgrad.ConnectionService.ServiceGenerator;
import io.iqube.kctgrad.model.Degree;
import io.iqube.kctgrad.model.Question;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedbackFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Spinner degrees;
    ListView Questions;
    ArrayList<Question> questionsList;
    ArrayList<Degree> degreeArrayList;
    SpinnerAdapter spinnerAdapter;
    EditText name;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    QuestionArrayAdapter adapter;


    public FeedbackFragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Questions=(ListView) view.findViewById(R.id.listView);

        LinearLayout HeaderView=(LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.lv_questions_header,null);
//        HeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout FooterView= (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.lv_questions_footer,null);
        Questions.addHeaderView(HeaderView,null,false);
        Questions.addFooterView(FooterView);

        name=(EditText)view.findViewById(R.id.name) ;
        name.requestFocus();
        questionsList=new ArrayList<Question>();
        degreeArrayList=new ArrayList<Degree>();
        adapter=new QuestionArrayAdapter(this.getActivity(),questionsList);
        Questions.setAdapter(adapter);
        degrees=(Spinner)view.findViewById(R.id.spinner);
        spinnerAdapter=new SpinnerAdapter(getContext(),degreeArrayList);
        retryView=view.findViewById(R.id.retryView);


        loadDate();

//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degrees.setAdapter(spinnerAdapter);

        Button submit=(Button) view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });


        Button retry=(Button)view.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideRetryView();
                loadDate();
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void loadDate()
    {
        showSpinner();

        ServiceGenerator.KCTClient client=ServiceGenerator.createService(ServiceGenerator.KCTClient.class);

        client.getQuestions().enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if(response.code()==200)
                {

                    questionsList=new ArrayList<Question>(response.body());
                    for(Question question:questionsList)
                    {
                        adapter.add(question);
                    }

                    adapter.notifyDataSetChanged();
                    Toast.makeText(FeedbackFragment.this.getActivity(), "Finished", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(FeedbackFragment.this.getActivity(), "Error with server please Try again!!", Toast.LENGTH_LONG).show();
                }

                if(done1)
                {
                    hideLoader();
                }
                else
                    done1=true;
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Toast.makeText(FeedbackFragment.this.getActivity().getApplicationContext(), "Network Error happened!!", Toast.LENGTH_LONG).show();
                showRetryView();
                hideLoader();
            }


        });



    client.getDegrees().enqueue(new Callback<List<Degree>>() {
        @Override
        public void onResponse(Call<List<Degree>> call, Response<List<Degree>> response) {

            if(response.code()==200)
            {
                spinnerAdapter.addAll(response.body());
            }

            if(done1)
            {
                hideLoader();
            }
            else
                done1=true;

        }

        @Override
        public void onFailure(Call<List<Degree>> call, Throwable t) {
            Toast.makeText(FeedbackFragment.this.getActivity().getApplicationContext(), "Network Error happened!!", Toast.LENGTH_LONG).show();
            showRetryView();
            hideLoader();
        }
    });

    }


    protected class SpinnerAdapter extends ArrayAdapter<Degree>
    {
        public SpinnerAdapter(Context context,List<Degree> objects) {
            super(context, android.R.layout.simple_spinner_item, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                TextView label = new TextView(getContext());
                label.setTextColor(Color.BLACK);
                Degree degree = getItem(position);
                label.setText(degree.getName());
                label.setId(degree.getId());
                return label;
            }

            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView label = new TextView(getContext());
            label.setTextColor(Color.BLACK);
            label.setText(getItem(position).getName());
            label.setPadding(10,10,10,10);
            return label;
        }
    }

    ProgressDialog ring;

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

    Boolean done1=false;
    public Boolean isLoaderVisible()
    {
        if(ring!=null)
            return ring.isShowing();
        else
            return false;
    }


    public Boolean isInvalidInput()
    {
        if(name.getText().toString().isEmpty())
        {
            Toast.makeText(FeedbackFragment.this.getActivity(), "Please Fill the name", Toast.LENGTH_LONG).show();
            return true;
        }

        JsonArray arr=adapter.getPostData();

        for (int i=0;i<arr.size();i++)
        {
            JsonObject obj=(JsonObject) arr.get(i);
            if(obj.get("option").isJsonNull()) {
                Toast.makeText(FeedbackFragment.this.getActivity(), "Please fill all Questions", Toast.LENGTH_SHORT).show();
                return true;
            }
        }


        return false;


    }



    protected void submitData()
    {

        if(isInvalidInput())
        {
            return;
        }


        ServiceGenerator.KCTClient client=ServiceGenerator.createService(ServiceGenerator.KCTClient.class);

        JsonObject jsonObject=new JsonObject();




        jsonObject.addProperty("name",name.getText().toString());
        jsonObject.addProperty("degree",new Integer(degrees.getSelectedView().getId()));
        jsonObject.add("answersTh",adapter.getPostData());
        showSpinner();
        client.finishFeedBack(jsonObject).enqueue(
                new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        hideLoader();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        hideLoader();

                    }
                }
        );
    }


    View retryView;

    public void showRetryView()
    {
        retryView.setVisibility(View.VISIBLE);
    }

    public void hideRetryView()
    {
        retryView.setVisibility(View.INVISIBLE);
    }

}
