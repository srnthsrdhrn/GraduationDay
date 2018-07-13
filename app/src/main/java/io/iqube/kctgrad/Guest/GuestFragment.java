package io.iqube.kctgrad.Guest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.iqube.kctgrad.ConnectionService.ServiceGenerator;
import io.iqube.kctgrad.Notification.NotificationAdapter;
import io.iqube.kctgrad.Notification.NotificationFragment;
import io.iqube.kctgrad.R;
import io.iqube.kctgrad.model.Guest;
import io.iqube.kctgrad.model.NotificationModel;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GuestFragment extends Fragment {

    Realm realm;
    RealmResults<Guest> guests;
    GuestAdapter adapter;
    RealmRecyclerView lv;

      public static GuestFragment newInstance() {
        GuestFragment fragment = new GuestFragment();

        return fragment;
    }

    public GuestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_about_guest, container, false);

        lv=(RealmRecyclerView) v.findViewById(R.id.guest_list);
        retryView=v.findViewById(R.id.retryView);


        return v;
    }


    public void loadData(boolean flag)
    {
        if(!flag)
        {
            showSpinner();
        }

        ServiceGenerator.KCTClient client=ServiceGenerator.createService(ServiceGenerator.KCTClient.class);

        client.getGuests().enqueue(new Callback<List<Guest>>() {
            @Override
            public void onResponse(Call<List<Guest>> call, Response<List<Guest>> response) {
                hideLoader();

                if(response.code()==200)
                {
                    realm.beginTransaction();
                    RealmResults<Guest> guests = realm.where(Guest.class).findAll();
                    guests.deleteAllFromRealm();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();

                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(GuestFragment.this.getActivity(), "Error with server please Try again!!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Guest>> call, Throwable t) {
                hideLoader();
                Toast.makeText(GuestFragment.this.getActivity().getApplicationContext(), "You are offline !!", Toast.LENGTH_LONG).show();
            }


        });

    }

    @Override
    public void onStart() {
        super.onStart();
        realm=Realm.getDefaultInstance();
        guests=realm.where(Guest.class).findAll();

        if(guests.size()==0)
        {
            loadData(false);
        }
        else
        {
            loadData(true);
        }

        adapter=new GuestAdapter(getContext(),guests);

        lv.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
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

