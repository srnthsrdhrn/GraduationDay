package io.iqube.kctgrad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import io.iqube.kctgrad.model.Notification;
import io.realm.Realm;
import io.realm.RealmResults;

public class NotificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    RealmResults<Notification> notifications;

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
        View v= inflater.inflate(R.layout.fragment_notifcaiton, container, false);
        loadData();

        ListView lv=(ListView)v.findViewById(R.id.listView2);
        if(notifications.size()==0)
        {
            TextView tt=new TextView(getContext());
            tt.setText("No Notifications received!! \nAny new notifications will appear here");
            tt.setPadding(10,10,10,10);
            lv.addHeaderView(tt);

        }
        NotificationAdapter adapter=new NotificationAdapter(getContext(),notifications);
        lv.setAdapter(adapter);

        return v;
    }



    public void loadData()
    {
        Realm realm=Realm.getDefaultInstance();
        notifications=realm.where(Notification.class).findAll();

    }

}
