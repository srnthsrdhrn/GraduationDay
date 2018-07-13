package io.iqube.kctgrad.Agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.iqube.kctgrad.ConnectionService.ServiceGenerator;
import io.iqube.kctgrad.MapActivity;
import io.iqube.kctgrad.Notification.NotificationAdapter;
import io.iqube.kctgrad.Notification.NotificationFragment;
import io.iqube.kctgrad.R;
import io.iqube.kctgrad.model.Agenda;
import io.iqube.kctgrad.model.NotificationModel;
import io.iqube.kctgrad.model.Program;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

import static io.iqube.kctgrad.KCTApplication.PREF_ROLL;
import static io.iqube.kctgrad.KCTApplication.SHARED_PREF_NAME;


public class AgendaFragment extends Fragment {


    RealmRecyclerView lvAgenda;
    ProgressDialog ring;
    AgendaAdapter adapter;
    Realm realm;
    RealmResults<Agenda> agendas;

    View v;

    public static AgendaFragment newInstance() {
        AgendaFragment fragment = new AgendaFragment();

        return fragment;
    }

    public AgendaFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.content_main, container, false);

        lvAgenda=(RealmRecyclerView) v.findViewById(R.id.lvagenda);
        retryView=v.findViewById(R.id.retryView);


        return v;
    }


   public void getAgenda(Boolean flag)
    {

        if(!flag)
        {
            showSpinner();
        }

        ServiceGenerator.KCTClient client=ServiceGenerator.createService(ServiceGenerator.KCTClient.class);

        SharedPreferences user = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        client.getAgenda("agenda/"+user.getString(PREF_ROLL,"")+"/").enqueue(new retrofit2.Callback<List<Agenda>>() {
            @Override
            public void onResponse(Call<List<Agenda>> call, Response<List<Agenda>> response) {
                hideLoader();

                if(response.code()==200)
                {
                    realm.beginTransaction();
                    RealmResults<Agenda> notifications = realm.where(Agenda.class).findAll();
                    notifications.deleteAllFromRealm();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();

                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(AgendaFragment.this.getActivity(), "Error with server please Try again!!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Agenda>> call, Throwable t) {
                hideLoader();
                Toast.makeText(AgendaFragment.this.getActivity().getApplicationContext(), "You are offline !!", Toast.LENGTH_LONG).show();
            }


        });

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

    @Override
    public void onStart() {
        super.onStart();
        realm=Realm.getDefaultInstance();

        agendas=realm.where(Agenda.class).findAll();

        if(agendas.size()==0)
        {
            getAgenda(false);
        }
        else {
            getAgenda(true);
        }

        adapter=new AgendaAdapter(getContext(),agendas,getActivity());

        lvAgenda.setAdapter(adapter);

    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
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