package io.iqube.kctgrad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

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

        final PhotoViewAttacher mAttacher = new PhotoViewAttacher(map);
        Picasso.with(getContext())
                .load(R.drawable.map)
                .into(map, new Callback() {
                    @Override
                    public void onSuccess() {
                        mAttacher.update();
                    }

                    @Override
                    public void onError() {

                    }
                });


    }


}
