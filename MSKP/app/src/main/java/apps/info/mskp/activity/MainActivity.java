package apps.info.mskp.activity;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import java.util.Calendar;
import java.util.HashMap;

import apps.info.mskp.R;
import apps.info.mskp.helper.AppConfig;
import apps.info.mskp.helper.SimpleDividerItemDecoration;
import apps.info.mskp.helper.SessionManager;



public class MainActivity extends ActionBarActivity implements FragmentDrawerIsLoggedIn.FragmentDrawerListener{

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawerIsLoggedIn drawerIsLoggedIn;


    SessionManager session;
    String username,email,photo;
    String user_id;
    TextView tagusername;
    final Context context = this;

    Bitmap bitmap;
    String URL;
    AQuery aq;
    // Refresh menu item
    private MenuItem refreshMenuItem;
    public static int alarmInterval = 10;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";
    ImageView userphoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionManager(getApplicationContext());
      //  session.checkLogin();
        if(!session.isLoggedIn()){
            Intent i = new Intent(this, LoginActivity.class);
            finish();
            startActivity(i);
        }
        //CEK LOGIN
        HashMap<String, String> user = session.getUserDetails();

      username = user.get(SessionManager.KEY_USERNAME);

        aq = new AQuery(this);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_isloggedin);
            mRecyclerView = (RecyclerView) findViewById(R.id.drawerList);
            mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                    getApplicationContext()
            ));

                tagusername=(TextView) findViewById(R.id.logUsername);
                tagusername.setText(username);
                ImageView a =(ImageView) findViewById(R.id.userphoto);

                a.setImageResource(R.drawable.guest);

             //   aq.id(R.id.userphoto).image(photo);

               // showAlertDialog(photo);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);







            drawerIsLoggedIn = (FragmentDrawerIsLoggedIn)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerIsLoggedIn .setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

            drawerIsLoggedIn .setDrawerListener(this);


        // display the first navigation drawer view on app launch
        displayView(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(session.isLoggedIn()){
            getMenuInflater().inflate(R.menu.menu_main, menu);

            //RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.action_notification).getActionView();

           // TextView tv = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
            //tv.setText("20");
        }else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if(id==R.id.home){
            Toast.makeText(getApplicationContext(), "Home action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }



        if(id==R.id.action_about){
            showAbout();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
/*
    private void search(){
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.search, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText Edtsearch = (EditText) promptsView.findViewById(R.id.Edtsearch);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                                Bundle b = new Bundle();
                                b.putString("key", Edtsearch.getText().toString());
                                i.putExtras(b);
                                startActivity(i);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
*/

    private void Logout(){
        session.logoutUser();
        finish();
    }



    private void showAbout(){
        AlertDialog.Builder peringatan=new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        peringatan.setView(inflater.inflate(R.layout.activity_about, null));
        AlertDialog ad = peringatan.create();
        peringatan.setCancelable(false);
        peringatan.setNeutralButton("Tutup",null);
        peringatan.show();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
            displayView(position);
    }

    public void post(){
        displayView(4);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        int icon;


            switch (position) {

                case 0:
                    fragment = new DataPribadiFragment();
                    title = getString(R.string.title_DataPribadi);
                    break;
                case 1:
                    fragment = new ReferalFragment();
                    title = getString(R.string.title_DataPenSponsoran);
                    break;
                case 2:
                    fragment = new ReferalFragment();
                    title = getString(R.string.title_provider);
                    break;
                case 3:
                    fragment = new DataPribadiFragment();
                    title = getString(R.string.title_post);
                    break;
                case 4:
                    Logout();
                    break;
                default:
                    break;
            }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


    public void onBackPressed(){
        AlertDialog.Builder peringatan=new AlertDialog.Builder(this);
        // peringatan.setTitle("AlertDialog");
        //peringatan.setMessage(R.drawable.ic_car);
        peringatan.setMessage("Tutup Aplikasi?");
        peringatan.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        peringatan.setNegativeButton("Tidak",null);
        peringatan.show();

    }

    public void showAlertDialog(String message){

        AlertDialog.Builder peringatan=new AlertDialog.Builder(this);
        peringatan.setTitle("AlertDialog");
        peringatan.setMessage(message);
        peringatan.setNeutralButton("OK", null);
        peringatan.show();
    }

    /**
     * Async task to load the data from server
     * **/
    private class SyncData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // set the progress bar view
            refreshMenuItem.setActionView(R.layout.action_progressbar);

            refreshMenuItem.expandActionView();
        }

        @Override
        protected String doInBackground(String... params) {
            // not making real request in this demo
            // for now we use a timer to wait for sometime
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            refreshMenuItem.collapseActionView();
            // remove the progress bar view
            refreshMenuItem.setActionView(null);
        }
    }



}
