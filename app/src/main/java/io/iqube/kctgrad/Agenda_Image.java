package io.iqube.kctgrad;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;


public class Agenda_Image extends Fragment {
    ImageView agenda;
    public static Agenda_Image newInstance() {
        Agenda_Image fragment = new Agenda_Image();
                return fragment;
    }

    public Agenda_Image() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agenda__image, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        agenda = (ImageView)view.findViewById(R.id.agenda);
        Picasso.with(getContext())
                .load(R.drawable.online_scheduleee)
                .into(agenda);

    }


}
