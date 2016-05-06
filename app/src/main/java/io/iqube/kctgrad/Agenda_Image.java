package io.iqube.kctgrad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        return inflater.inflate(R.layout.image_fragment, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        agenda = (ImageView)view.findViewById(R.id.image);
        Picasso.with(getContext())
                .load("http://iq.bookflip.in/graduation_day/images/android/online_scheduleee.jpg")
                .into(agenda);

    }


}
