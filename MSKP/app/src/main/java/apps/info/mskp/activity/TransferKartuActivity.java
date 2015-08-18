package apps.info.mskp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class TransferKartuActivity extends ActionBarActivity {

    private ProgressDialog pDialog;
    private EditText edtTujuan,edtjmlhpin;
    private Button btntansfer;
    private RadioGroup radioGroup;
    String success ,messages;
    AQuery aq;
    SessionManager session;
    int pilih;
    int hasil=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_kartu);

        btntansfer=(Button)findViewById(R.id.btnTransPin);
        edtTujuan=(EditText)findViewById(R.id.tujuanid);
        edtjmlhpin=(EditText) findViewById(R.id.jmlh_pin);

        radioGroup=(RadioGroup) findViewById(R.id.radio_grouptrans);

        pilih=radioGroup.getCheckedRadioButtonId();

        session = new SessionManager(getApplicationContext());

        //Pdialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        // view klik button register
        aq = new AQuery(this);

        //view klik button login
        btntansfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("KLIK","Hasil pilihan\t:\t"+pilih+"");

                 if (edtTujuan.getText().toString().trim().length() > 0
                        && edtjmlhpin.getText().toString().trim().length() > 0 && hasil > -1) {
                    asyncJson();
                } else if (edtTujuan.getText().toString().trim().length() == 0
                        && edtjmlhpin.getText().toString().trim().length() == 0) {
                    edtTujuan.setError("PIN tujuan kosong");
                    edtjmlhpin.setError("Jumlah PIN kosong");
                } else if (edtjmlhpin.getText().toString().trim().length() == 0) {
                    edtjmlhpin.setError("Jumlah PIN kosong");
                } else if(hasil == -1){
                     showAlertDialog("Jenis transfer harus dipilih");
                 }else{
                    // Toast.makeText(getApplicationContext(), "Username/password masih kosong gan.!!", Toast.LENGTH_LONG).show();
                    edtTujuan.setError("PIN tujuan kosong");
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_join:
                if (checked)
                    hasil=0;
                    break;
            case R.id.radio_cinta:
                if (checked)
                    hasil=1;
                    break;
            case R.id.radio_ro:
                if (checked)
                    hasil=2;
                    break;
            case R.id.radio_upgrade:
                if (checked)
                    hasil=3;
                    break;
        }
    }

    public void asyncJson() {
        pDialog.show();
        String url = AppConfig.SERVER+"json/transferid_json.php";
        Map<String, Object> params = new HashMap<>();
        params.put("id_member", session.getMember() );
        params.put("idmem",edtTujuan.getText().toString() );
        params.put("jum",edtjmlhpin.getText().toString() );
        params.put("jenis",hasil);
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
                        Log.e("error", e.toString());
                    }
                    if (success.equals("0")) {

                        String msg = "Kode\t:\t" + success
                                + "\nPesan\t:\t" + messages;

                        show(msg);
                    } else {
                        showAlertDialog(messages);
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
