package io.iqube.kctgrad.Home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import io.iqube.kctgrad.AboutFragment;
import io.iqube.kctgrad.Agenda.AgendaFragment;
import io.iqube.kctgrad.FeedBack.FeedbackFragment;
import io.iqube.kctgrad.Guest.GuestFragment;
import io.iqube.kctgrad.LoginActivity;
import io.iqube.kctgrad.Notification.NotificationFragment;
import io.iqube.kctgrad.R;
import io.iqube.kctgrad.model.NotificationModel;
import io.realm.Realm;
import io.realm.RealmResults;

import static io.iqube.kctgrad.KCTApplication.PREF_NAME;
import static io.iqube.kctgrad.KCTApplication.SHARED_PREF_NAME;

public class DrawerActivity extends AppCompatActivity implements FeedbackFragment.OnFragmentInteractionListener{


    private static final int PROFILE_SETTING = 100000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin();
        setContentView(R.layout.activity_drawer);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        goTo();

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.home_head)
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIdentifier(1),
                        new PrimaryDrawerItem().withName("Agenda").withIdentifier(2),
                        new PrimaryDrawerItem().withName("Guests").withIdentifier(3),
                        new PrimaryDrawerItem().withName("Notifications").withIdentifier(4),
                        new PrimaryDrawerItem().withName("Feedback").withIdentifier(5)
                         ) // add the items we want to use with our Drawer
                .addStickyDrawerItems(
                        new PrimaryDrawerItem().withName("About").withIdentifier(6),
                        new PrimaryDrawerItem().withName("Logout").withIdentifier(7)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {

                                Fragment f = Home.newInstance();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                                toolbar.setTitle("Home");
                                toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
                            }

                            if (drawerItem.getIdentifier() == 2) {
                                Fragment a = AgendaFragment.newInstance();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, a).commit();
                                toolbar.setTitle("Agenda");
                                toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
                            }

                            if (drawerItem.getIdentifier() == 3) {
                                Fragment a = GuestFragment.newInstance();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, a).commit();
                                toolbar.setTitle("Guests");
                                toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
                            }

                            if (drawerItem.getIdentifier() == 4) {
                                Fragment f = NotificationFragment.newInstance();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                                toolbar.setTitle("Notifications");
                                toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
                            }

                            if (drawerItem.getIdentifier() == 5) {
                                Fragment f = FeedbackFragment.newInstance();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                                toolbar.setTitle("Feedback");
                                toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
                            }
                            if (drawerItem.getIdentifier() == 6) {
                                Fragment a = AboutFragment.newInstance();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, a).commit();
                                toolbar.setTitle("About");
                                toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
                            }
                            if (drawerItem.getIdentifier() == 7) {

                                SharedPreferences user = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = user.edit();
                                edit.clear();
                                edit.apply();

                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                RealmResults<NotificationModel> notification = realm.where(NotificationModel.class).findAll();
                                notification.deleteAllFromRealm();
                                realm.close();
                                startActivity(new Intent(DrawerActivity.this,LoginActivity.class));

                            }

                            if (intent != null) {
                                DrawerActivity.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 11
            result.setSelection(7, true);


            result.openDrawer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void fun()
    {

    }

public void checkLogin() {

    SharedPreferences user = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

    if(user.getString(PREF_NAME,"").length()==0)
    {
        finish();
        startActivity(new Intent(DrawerActivity.this, LoginActivity.class));
    }
}

public void goTo()
{
    Intent intent = getIntent();
    try {
        if(intent.getStringExtra("type").equals("notification"))
        {
            Fragment f = NotificationFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
        }
        else
        {
            Fragment f = Home.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
        }
    }
    catch (NullPointerException e)
    {
        Fragment f = Home.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
    }

}

}