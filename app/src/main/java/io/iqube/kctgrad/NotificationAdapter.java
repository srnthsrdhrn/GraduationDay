package io.iqube.kctgrad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.iqube.kctgrad.model.Notification;

public class NotificationAdapter extends ArrayAdapter<Notification>{


    public NotificationAdapter(Context context,List<Notification> nots)
    {
        super(context,R.layout.lv_notification_single,nots);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.lv_notification_single,parent,false);
        }

        TextView message=(TextView) convertView.findViewById(R.id.not_message);
        message.setText(getItem(position).getMessage());

        return convertView;

    }
}
