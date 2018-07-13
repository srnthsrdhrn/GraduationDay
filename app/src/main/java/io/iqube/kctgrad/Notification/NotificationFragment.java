package io.iqube.kctgrad.Notification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.iqube.kctgrad.ConnectionService.ServiceGenerator;
import io.iqube.kctgrad.R;
import io.iqube.kctgrad.model.NotificationModel;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.iqube.kctgrad.KCTApplication.PREF_ROLL;
import static io.iqube.kctgrad.KCTApplication.SHARED_PREF_NAME;

public class NotificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



    RealmResults<NotificationModel> notifications;
    NotificationAdapter adapter;
    Realm realm;
    View v;
    RealmRecyclerView lv;
    Boolean dataFlag;

    public NotificationFragment() {
        // Required empty public constructor
    }
    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_notifcaiton, container, false);

        lv=(RealmRecyclerView) v.findViewById(R.id.notList);
        retryView=v.findViewById(R.id.retryView);


        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        realm=Realm.getDefaultInstance();
        notifications=realm.where(NotificationModel.class).findAll();

        if (notifications.size()==0)
        {
            loadData(false);
        }
        else
        {
            loadData(true);
        }

        adapter=new NotificationAdapter(getContext(),notifications);

        lv.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
}


    public void loadData(Boolean flag)
    {

        if(!flag)
        {
            showSpinner();
        }


        ServiceGenerator.KCTClient client=ServiceGenerator.createService(ServiceGenerator.KCTClient.class);

        SharedPreferences user = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        client.getNotification("notification/"+user.getString(PREF_ROLL,"")+"/").enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {

                hideLoader();

                if(response.code()==200)
                {
                    realm.beginTransaction();
                    RealmResults<NotificationModel> notifications = realm.where(NotificationModel.class).findAll();
                    notifications.deleteAllFromRealm();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();

                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(NotificationFragment.this.getActivity(), "Error with server please Try again!!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                hideLoader();
                Toast.makeText(NotificationFragment.this.getActivity().getApplicationContext(), "You are offline!!", Toast.LENGTH_LONG).show();
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
