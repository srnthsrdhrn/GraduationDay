package io.iqube.kctgrad.Guest;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import io.iqube.kctgrad.ConnectionService.ServiceGenerator;
import io.iqube.kctgrad.R;
import io.iqube.kctgrad.model.Guest;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by Navin on 26-02-2017.
 */

public class GuestAdapter extends RealmBasedRecyclerViewAdapter<Guest,GuestAdapter.ViewHolder> {

    public GuestAdapter(Context context,RealmResults<Guest> realmResults)
    {
        super(context,realmResults,true,false);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v  = inflater.inflate(R.layout.lv_guest_single,viewGroup,false);
        return new GuestAdapter.ViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.name.setText(realmResults.get(i).getName());
        viewHolder.profession.setText(realmResults.get(i).getProfession());
        viewHolder.description.setText(realmResults.get(i).getDesc());
        viewHolder.guestImage.setImageURI(realmResults.get(i).getImage());

    }

    class ViewHolder extends RealmViewHolder
    {

        TextView name;
        TextView profession;
        TextView description;
        SimpleDraweeView guestImage;

        ViewHolder(LinearLayout v)
        {
            super(v);

            name = (TextView)itemView.findViewById(R.id.guestName);
            profession = (TextView)itemView.findViewById(R.id.profession);
            description = (TextView)itemView.findViewById(R.id.description);
            guestImage = (SimpleDraweeView) itemView.findViewById(R.id.guestImage);

        }



    }
}
