package io.iqube.kctgrad;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class map extends Fragment {

    ImageView map;

    public static map newInstance() {
        map fragment = new map();

        return fragment;
    }

    public map() {
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
        map = (ImageView)view.findViewById(R.id.image);
        Picasso.with(getContext())
                .load("http://iq.bookflip.in/graduation_day/images/android/mappp.jpg")
                .into(map);

    }


}
