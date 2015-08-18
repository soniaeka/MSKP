package apps.info.mskp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class TransaksiROActivity extends Activity {

    private Button btnValidasi;
    private ProgressDialog pDialog;

    EditText pin_trx;
    AQuery aq;
    String success ,messages;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_ro);

        btnValidasi=(Button)findViewById(R.id.btnValidasi);
        pin_trx=(EditText) findViewById(R.id.pin_trx);

        session = new SessionManager(this);

        //Pdialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        // view klik button register
        aq = new AQuery(this);

        //view klik button login
        btnValidasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!session.isLoggedIn()){
                    show("Anda belum login.");
                }else if ( pin_trx.getText().toString().trim().length() > 0){
                    asyncJson();
                }else if( pin_trx.getText().toString().trim().length() == 0){
                    pin_trx.setError("pin masih kosong");
                }
            }
        });
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
        String url = AppConfig.SERVER+"json/trx_json.php";
        Map<String, Object> params = new HashMap<>();
        params.put("id_member", session.getMember());
        params.put("pintrx", pin_trx.getText().toString().toUpperCase());
        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {
                    try {

                        success = json.getString("success");
                        messages = json.getString("message");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Log.e("error", "nilai sukses=" + success);
                    }

                    if (success.equals("0")){
                        String msg = "Kode\t:\t" + success
                                + "\nPesan\t:\t" + messages;

                        show(msg);
                        pin_trx.setText("");
                    }else{
                        String msg = "Kode\t:\t" + success
                                + "\nPesan\t:\t" + messages;

                        show(msg);
                    }



                } else {
                    // ajax error, show error code
                    if (status.getCode() == -101) {
                        showAlertDialog("tidak ada koneksi internet");
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
