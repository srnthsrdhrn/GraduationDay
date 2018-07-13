package io.iqube.kctgrad.FeedBack;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.iqube.kctgrad.ConnectionService.ServiceGenerator;
import io.iqube.kctgrad.R;
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

    ListView Questions;
    ArrayList<Question> questionsList;
    int user_id = 1;


    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;


    QuestionArrayAdapter adapter;


    public FeedbackFragment() {
        // Required empty public constructor
    }

    public static FeedbackFragment newInstance() {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        LinearLayout FooterView= (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.lv_questions_footer,null);
        Questions.addFooterView(FooterView);

        questionsList=new ArrayList<Question>();
        adapter=new QuestionArrayAdapter(this.getActivity(),questionsList);
        Questions.setAdapter(adapter);
        retryView=view.findViewById(R.id.retryView);


        loadData();


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
                loadData();
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

    public void loadData()
    {
        showSpinner();

        ServiceGenerator.KCTClient client=ServiceGenerator.createService(ServiceGenerator.KCTClient.class);

        client.getQuestions().enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if(isLoaderVisible()) {
                    hideLoader();
                }
                if(response.code()==200)
                {

                    questionsList=new ArrayList<Question>(response.body());
                    for(Question question:questionsList)
                    {
                        adapter.add(question);
                    }

                    adapter.notifyDataSetChanged();
//                    Toast.makeText(FeedbackFragment.this.getActivity(), "Finished", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(FeedbackFragment.this.getActivity().getApplicationContext(), "You are offline !!", Toast.LENGTH_LONG).show();
                showRetryView();
                hideLoader();
            }


        });
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

        JsonArray arr=adapter.getPostData();

        for (int i=0;i<arr.size();i++)
        {
            JsonObject obj=(JsonObject) arr.get(i);
            if(obj.get("options").isJsonNull()) {
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

        jsonObject.addProperty("user",user_id);
        jsonObject.add("answerSet",adapter.getPostData());

        Log.d("FeedBack",jsonObject.toString());

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
