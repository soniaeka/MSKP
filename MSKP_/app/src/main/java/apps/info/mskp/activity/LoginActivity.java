package apps.info.mskp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import apps.info.mskp.R;
import apps.info.mskp.helper.AppConfig;
import apps.info.mskp.helper.SessionManager;

/**
 * Created by ACER on 15/05/2015.
 */
public class LoginActivity extends Activity {
    private Button btnLogin;
    private Button btnLinkToRegister;
    private ProgressDialog pDialog;

    EditText edtIdmember,edtPassword;
    AQuery aq;
    String success ,messages;
    SessionManager session;
    String idmember;
    String email;
    String userphoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnLogin=(Button)findViewById(R.id.btnLogin);
        edtIdmember=(EditText)findViewById(R.id.idmember);
        edtPassword=(EditText)findViewById(R.id.password);


        session = new SessionManager(getApplicationContext());


        //Pdialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Login ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        // view klik button register
        aq = new AQuery(this);



        //view klik button login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();*/

          if ( edtIdmember.getText().toString().trim().length() > 0
                        && edtPassword.getText().toString().trim().length() > 0)
                {
                    asyncJson();

                }else if( edtIdmember.getText().toString().trim().length() == 0
                        && edtPassword.getText().toString().trim().length() == 0 ){
              edtIdmember.setError("username kosong");
                    edtPassword.setError("password kosong");
                }else if(edtPassword.getText().toString().trim().length() == 0){
                    edtPassword.setError("password kosong");
                }
                else
                {
                    // Toast.makeText(getApplicationContext(), "Username/password masih kosong gan.!!", Toast.LENGTH_LONG).show();
                    edtIdmember.setError("username kosong");
                }
            }
        });

    }

    public void asyncJson() {
        pDialog.show();
        String url = AppConfig.SERVER+"json/login_json.php";
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("id_member", edtIdmember.getText().toString() );
        params.put("password",edtPassword.getText().toString() );
        aq.ajax(url,params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {
                    try {

                        success = json.getString("success");
                        messages = json.getString("message");
                        Log.e("error", "nilai sukses=" + success);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (success.equals("1")){

                        session.createLoginSession(edtIdmember.getText().toString());
                        HashMap<String, String> user = session.getUserDetails();

                       Intent i = new Intent(getApplicationContext(), MainActivity.class);
                       startActivity(i);
                       finish();

                    }else {
                        showAlertDialog(messages);}


                } else {
                    // ajax error, show error code
                    if (status.getCode()==-101){
                        showAlertDialog("tidak ada koneksi internet");
                    } else{
                        Toast.makeText(aq.getContext(),
                                "Error:" + status.getCode(), Toast.LENGTH_LONG)
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

    public void onBackPressed(){
        AlertDialog.Builder peringatan=new AlertDialog.Builder(this);
        //peringatan.setTitle("AlertDialog");
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

}
