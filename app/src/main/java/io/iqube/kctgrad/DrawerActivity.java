package io.iqube.kctgrad;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class DrawerActivity extends AppCompatActivity implements FeedbackFragment.OnFragmentInteractionListener{
    private static final int PROFILE_SETTING = 100000;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        //Remove line to test RTL support
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)

                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Agenda").withDescription("Happenings").withIdentifier(2),
                        new PrimaryDrawerItem().withName("Notifications").withDescription("Get Notified").withIdentifier(3),
                        new PrimaryDrawerItem().withName("Feedback").withDescription("Give FeedBack").withIdentifier(1)

                         ) // add the items we want to use with our Drawer
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
//                                intent = new Intent(DrawerActivity.this, CompactHeaderDrawerActivity.class);

                                Fragment f = FeedbackFragment.newInstance();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                            }
<<<<<<< HEAD
                            if (drawerItem.getIdentifier() == 3) {
//                                intent = new Intent(DrawerActivity.this, CompactHeaderDrawerActivity.class);

                                Fragment f = NotificationFragment.newInstance();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                            }

=======
                            if (drawerItem.getIdentifier() == 2) {
//                                intent = new Intent(DrawerActivity.this, CompactHeaderDrawerActivity.class);
                                Fragment a = AgendaFragment.newInstance();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, a).commit();
                            }
>>>>>>> origin/master
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
            result.setSelection(1, true);



        }

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

   

}