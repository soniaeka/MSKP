package apps.info.mskp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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
import apps.info.mskp.model.ProfileItem;

/**
 * Created by ACER on 14/08/2015.
 */
public class EditPasswordProfil extends ActionBarActivity {

    private Toolbar mToolbar;
    AQuery aq;
    ProgressDialog pDialog;
    EditText EdOldPass,EdNewPass,EdConfirmPass;
    String OldPass,NewPass,ConfirmPass,id_member,messages,success,password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpassword);
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


        final ProfileItem profil = (ProfileItem) getIntent().getParcelableExtra("profil");
        id_member=profil.getId_member();
        password=profil.getPassword();

        //Deklarasi EditText
        EdOldPass=(EditText) findViewById(R.id.EdOldPass);
        EdNewPass=(EditText) findViewById(R.id.EdNewPass);
        EdConfirmPass=(EditText) findViewById(R.id.EdConfirmPass);

        OldPass=EdOldPass.getText().toString();
        NewPass=EdNewPass.getText().toString().trim();
        ConfirmPass=EdConfirmPass.getText().toString().trim();


        //Pdialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Menyimpan ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ///AQUERY
        aq = new AQuery(this);




        Button EditPassword = (Button) findViewById(R.id.btnEditPassword);
        EditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!OldPass.isEmpty()&&!NewPass.isEmpty()&&!ConfirmPass.isEmpty()){



                }else{
                    //OldPass
                    if(EdOldPass.getText().toString().length() == 0){
                        EdOldPass.setError("Password Lama Kosong");
                    }
                    //NewPass
                    if(EdNewPass.getText().toString().length() == 0){
                        EdNewPass.setError("Password Baru Kosong");
                    }
                    //ConfirmPass
                    if(EdConfirmPass.getText().toString().length() == 0){
                       EdConfirmPass.setError("Konfirm Password Baru Kosong");
                    }

                    if(EdNewPass.getError()==null&&
                            EdOldPass.getError()==null&&
                                EdConfirmPass.getError()==null){

                        if(NewPass.equals(ConfirmPass)){
                            asyncJson();
                        }else{

                            showAlertDialog("Konfirmasi password salah");
                        }

                    }
                }

            }
        });



    }

    public void asyncJson() {
        pDialog.show();
        String url = AppConfig.SERVER+"json/updatepass_json.php";
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("oldpass",EdOldPass.getText().toString());
        params.put("newpass",EdNewPass.getText().toString());
        params.put("id_member",id_member);
        aq.ajax(url,params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {

                    try {
                        success = json.getString("success");
                        messages=json.getString("message");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (success.equals("1")){
                        showAlertDialog(messages);
                        finish();
                    }else {
                     showAlertDialog(messages);
                    }


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
}
