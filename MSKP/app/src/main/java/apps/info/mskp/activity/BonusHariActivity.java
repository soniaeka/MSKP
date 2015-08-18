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

public class BonusHariActivity extends ActionBarActivity {

    private String sponsor_aktif,pasangan,generasi,
            reward,sponsor_cinta,pasangan_cinta,level_cinta,generasi_cinta,total;
    private TextView textsponsor_aktif;
    private TextView textpasangan;
    private TextView textgenerasi;
    private TextView textreward;
    private TextView textsponsor_cinta;
    private TextView textpasangan_cinta;
    private TextView textlevel_cinta;
    private TextView textgenerasi_cinta;
    private TextView texttotal;
    AQuery aq;
    SessionManager session;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_his);

        textsponsor_aktif=(TextView) findViewById(R.id.bonus_spon);
        textpasangan=(TextView) findViewById(R.id.bonus_pas);
        textgenerasi=(TextView) findViewById(R.id.bonus_gen);
        textreward=(TextView) findViewById(R.id.bonus_rew);
        textsponsor_cinta=(TextView) findViewById(R.id.bonus_sponcinta);
        textpasangan_cinta=(TextView) findViewById(R.id.bonus_pascinta);
        textlevel_cinta=(TextView) findViewById(R.id.bonus_levelcinta);
        textgenerasi_cinta=(TextView) findViewById(R.id.bonus_gencinta);
        texttotal=(TextView) findViewById(R.id.bonus_total);

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
        String url = AppConfig.SERVER+"json/bonushari_json.php";
        Map<String, Object> params = new HashMap<>();
        params.put("id_member", session.getMember());
        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {
                    try {

                        sponsor_aktif=json.getString("sponsor_aktif");
                        pasangan=json.getString("pasangan");
                        generasi=json.getString("generasi");
                        reward=json.getString("reward");
                        sponsor_cinta=json.getString("sponsor_cinta");
                        pasangan_cinta=json.getString("pasangan_cinta");
                        level_cinta=json.getString("level_cinta");
                        generasi_cinta=json.getString("generasi_cinta");
                        total=json.getString("total");

                        textsponsor_aktif.setText(sponsor_aktif);
                        textpasangan.setText(pasangan);
                        textgenerasi.setText(generasi);
                        textreward.setText(reward);
                        textsponsor_cinta.setText(sponsor_cinta);
                        textpasangan_cinta.setText(pasangan_cinta);
                        textlevel_cinta.setText(level_cinta);
                        textgenerasi_cinta.setText(generasi_cinta);
                        texttotal.setText(total);

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
