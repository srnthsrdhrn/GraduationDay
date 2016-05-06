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

import com.squareup.picasso.Picasso;

public class home extends Fragment {

    ImageView home;

  public static home newInstance() {
        home fragment = new home();
        return fragment;
    }

    public home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.image_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        home = (ImageView)view.findViewById(R.id.image);
        Picasso.with(getContext())
                .load("http://iq.bookflip.in/graduation_day/images/android/home.jpg")
                .into(home);

    }



}
