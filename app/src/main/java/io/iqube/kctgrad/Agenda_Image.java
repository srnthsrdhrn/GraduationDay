package io.iqube.kctgrad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;


public class Agenda_Image extends Fragment implements View.OnClickListener {
    ViewFlipper image_slide;
    TextView next;
    TextView previous;
    ImageView dept;
    ImageView agenda;
    ImageView timing;

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
        return inflater.inflate(R.layout.agenda_image, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image_slide = (ViewFlipper)view.findViewById(R.id.image_slider);
        next=(TextView) view.findViewById(R.id.next);
        previous=(TextView) view.findViewById(R.id.previous);
        timing=(ImageView)view.findViewById(R.id.timing);
        agenda=(ImageView)view.findViewById(R.id.agenda);
        dept=(ImageView)view.findViewById(R.id.dept);
        Picasso.with(getContext()).load(R.drawable.deptmt).into(dept);
        Picasso.with(getContext()).load(R.drawable.timing).into(timing);
        Picasso.with(getContext()).load(R.drawable.agenda).into(agenda);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        if(v==next)
        {
            image_slide.showNext();
        }
        else if(v==previous)
        {
            image_slide.showPrevious();
        }

    }




}
