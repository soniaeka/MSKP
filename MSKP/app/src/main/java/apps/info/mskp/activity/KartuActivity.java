package apps.info.mskp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import apps.info.mskp.R;
import apps.info.mskp.helper.AppConfig;
import apps.info.mskp.helper.SessionManager;

public class KartuActivity extends ActionBarActivity {

    private ProgressDialog pDialog;
    private TextView jmlh_cinta;
    private TextView jmlh_kartuid;
    private TextView jmlh_pinpro;
    private TextView jmlh_pinup;
    public String jumaktif,jumaktif2,jumaktif3,jumaktif4;
    AQuery aq;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu);

        jmlh_cinta=(TextView) findViewById(R.id.jmlh_cinta);
        jmlh_kartuid=(TextView) findViewById(R.id.jmlh_kartuid);
        jmlh_pinpro=(TextView) findViewById(R.id.jmlh_pinpro);
        jmlh_pinup=(TextView) findViewById(R.id.jmlh_pinup);

        session = new SessionManager(this);

        //Pdialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        // view klik button register
        aq = new AQuery(this);

        if(!session.isLoggedIn()){
            show("Anda belum login.");
        }else {
            asyncJson();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void asyncJson() {
        pDialog.show();
        String url = AppConfig.SERVER+"json/kartuid_json.php";
        Map<String, Object> params = new HashMap<>();
        params.put("id_member", session.getMember());
        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {
                    try {
                        jumaktif = json.getString("jumaktif");
                        jumaktif2 = json.getString("jumaktif2");
                        jumaktif3 = json.getString("jumaktif3");
                        jumaktif4 = json.getString("jumaktif4");

                        jmlh_cinta.setText(jumaktif);
                        jmlh_kartuid.setText(jumaktif2);
                        jmlh_pinpro.setText(jumaktif3);
                        jmlh_pinup.setText(jumaktif4);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {
                    // ajax error, show error code
                    if (status.getCode() == -101) {
                        showAlertDialog("Tidak ada koneksi internet");
                    } else {
                        Toast.makeText(aq.getContext(),
                                "Error:" + status.getCode(), Toast.LENGTH_LONG)
                                .show();

                    }
                }
            }
        });

    }

    public void showAlertDialog(String message){

        AlertDialog.Builder peringatan=new AlertDialog.Builder(this);

        peringatan.setMessage(message);
        peringatan.setNeutralButton("OK", null);
        peringatan.show();
    }

    public void show(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
