package apps.info.mskp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import apps.info.mskp.model.HistoryTRX;

public class HistoryTRXActivity extends ActionBarActivity {

    private AQuery aq;
    private ProgressDialog pDialog;

    String success ,messages;
    List<HistoryTRX> list=new ArrayList<>();
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_trx);

        session = new SessionManager(this);

        ListView lv = (ListView) findViewById(R.id.list_history);

        ArrayAdapter<HistoryTRX> adapter=new ArrayAdapter<HistoryTRX>(this,
                android.R.layout.simple_list_item_1,list);

        lv.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Sedang mengambil data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);


        aq = new AQuery(this);

        asyncJson();

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

    public void show(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void asyncJson() {
        pDialog.show();
        String url = AppConfig.SERVER+"json/trxhistory_json.php";
        Map<String, Object> params = new HashMap<>();
        params.put("id_member", session.getMember());
        aq.ajax(url, params,JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {
                    try {

                        success = json.getString("success");
                        messages = json.getString("message");

                        if (success.equals("0")){
                            String msg="Kode\t:\t"+success
                                    +"\nPesan\t:\t"+messages;
                            show(msg);
                        }else if(success.equals("1")){

                            String msg="Kode\t:\t"+success
                                    +"\nPesan\t:\t"+messages;
                            show(msg);

                            JSONArray categoryItemArray = json.getJSONArray("jumaktif");

                            for (int i = 0; i < categoryItemArray.length(); i++) {

                                try {
                                    HistoryTRX trx = new HistoryTRX();
                                    trx.setPin(categoryItemArray.getJSONObject(i).getString("pintrx"));
                                    trx.setJenis(categoryItemArray.getJSONObject(i).getString("jenis"));
                                    trx.setTanggal(categoryItemArray.getJSONObject(i).getString("aktifnya"));
                                    trx.setStatus(categoryItemArray.getJSONObject(i).getString("status"));
                                    list.add(trx);
                                } catch (JSONException e) {
                                    show(e.toString());
                                    e.printStackTrace();
                                }

                            }

                            JSONArray categoryItemArray2 = json.getJSONArray("jumaktif2");

                            for (int i = 0; i < categoryItemArray.length(); i++) {

                                try {

                                    HistoryTRX trx = new HistoryTRX();
                                    trx.setPin(categoryItemArray2.getJSONObject(i).getString("pintrx"));
                                    trx.setJenis(categoryItemArray2.getJSONObject(i).getString("jenis"));
                                    trx.setTanggal(categoryItemArray2.getJSONObject(i).getString("aktifnya"));
                                    trx.setStatus(categoryItemArray2.getJSONObject(i).getString("status"));
                                    list.add(trx);

                                } catch (JSONException e) {
                                    show(e.toString());

                                    e.printStackTrace();
                                }

                            }

                        }else{
                            String msg = "Kode\t:\t" + success
                                    + "\nPesan\t:\t" + messages;

                            show(msg);
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Log.e("error", e.toString());
                        Log.e("error", "nilai sukses=" + success);
                    }
                } else {
                    // ajax error, show error code
                    if (status.getCode()==-101){
                        showAlertDialog("tidak ada koneksi internet");
                    } else{
                        Toast.makeText(aq.getContext(),"Error:" + status.getCode(), Toast.LENGTH_LONG)
                                .show();

                    }}
            }
        });

    }

    public void showAlertDialog(String message){

        AlertDialog.Builder peringatan=new AlertDialog.Builder(this);

        peringatan.setMessage(message);
        peringatan.setNeutralButton("OK", null);
        peringatan.show();
    }
}
