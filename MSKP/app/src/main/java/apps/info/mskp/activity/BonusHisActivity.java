package apps.info.mskp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apps.info.mskp.R;
import apps.info.mskp.helper.AppConfig;
import apps.info.mskp.helper.SessionManager;
import apps.info.mskp.model.HistoryBonus;

public class BonusHisActivity extends ActionBarActivity {


    List<HistoryBonus> list=new ArrayList<>();

    private String nama,
            rek_nama,
            rek_bank,
            rek_no;

    private TextView textnama;
    private TextView textid;
    private TextView textrek_nama;
    private TextView textrek_bank;
    private TextView textrek_no;

    AQuery aq;
    SessionManager session;
    private ProgressDialog pDialog;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_his2);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_action_previous_item);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ListView lv = (ListView) findViewById(R.id.hisbonus_list);

        ArrayAdapter<HistoryBonus> adapter=new ArrayAdapter<HistoryBonus>(this,
                android.R.layout.simple_list_item_1,list);

        lv.setAdapter(adapter);

        textid=(TextView) findViewById(R.id.hisbonus_idmember);
        textnama=(TextView) findViewById(R.id.hisbonus_namamember);
        textrek_nama=(TextView) findViewById(R.id.hisbonus_namarek);
        textrek_bank=(TextView) findViewById(R.id.hisbonus_namabank);
        textrek_no=(TextView) findViewById(R.id.hisbonus_norek);

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
        String url = AppConfig.SERVER+"json/bonushistory_json.php";
        Map<String, Object> params = new HashMap<>();
        params.put("id_member", session.getMember());
        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {
                    try {

                        nama=json.getString("nama");
                        rek_nama=json.getString("rek_nama");
                        rek_bank=json.getString("rek_bank");
                        rek_no=json.getString("rek_no");

                        JSONArray categoryItemArray = json.getJSONArray("total_kom");

                        for (int i = 0; i < categoryItemArray.length(); i++) {

                            try {
                                HistoryBonus trx = new HistoryBonus();
                                trx.setId(categoryItemArray.getJSONObject(i).getString("id"));
                                trx.setJumlah(categoryItemArray.getJSONObject(i).getString("jumlah"));
                                trx.setTanggal(categoryItemArray.getJSONObject(i).getString("tanggal"));
                                trx.setStatus(categoryItemArray.getJSONObject(i).getString("status"));
                                list.add(trx);
                            } catch (JSONException e) {
                                show(e.toString());
                                e.printStackTrace();
                            }

                        }

                        textnama.setText(nama);
                        textrek_nama.setText(rek_nama);
                        textrek_bank.setText(rek_bank);
                        textrek_no.setText(rek_no);

                        textid.setText(session.getMember());

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
