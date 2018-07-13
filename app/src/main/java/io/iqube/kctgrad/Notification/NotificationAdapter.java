package io.iqube.kctgrad.Notification;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.iqube.kctgrad.R;
import io.iqube.kctgrad.model.NotificationModel;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class NotificationAdapter extends RealmBasedRecyclerViewAdapter<NotificationModel,NotificationAdapter.ViewHolder>{

    public NotificationAdapter(Context context, RealmResults<NotificationModel> realmResults)
    {
        super(context,realmResults,true,false);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v  = inflater.inflate(R.layout.lv_notification_single,viewGroup,false);
        return new ViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.head.setText(realmResults.get(i).getHead());
        viewHolder.message.setText(realmResults.get(i).getMessage());

    }

    class ViewHolder extends RealmViewHolder{

       TextView head;
       TextView message;

       ViewHolder(LinearLayout container)
       {
           super(container);

           head = (TextView)itemView.findViewById(R.id.not_head);
           message = (TextView)itemView.findViewById(R.id.not_message);

       }


   }

}
