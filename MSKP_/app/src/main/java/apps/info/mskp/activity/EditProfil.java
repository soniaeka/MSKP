package apps.info.mskp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
import apps.info.mskp.model.KotaItem;
import apps.info.mskp.model.ProfileItem;
import apps.info.mskp.model.ReferalItem;

/**
 * Created by ACER on 14/08/2015.
 */
public class EditProfil extends ActionBarActivity {
    private Toolbar mToolbar;
    AQuery aq;
    ProgressDialog pDialog;
    EditText Ednama,Edalamat,EdEmail,EdNoHP,
            EdNamaRekening,Edbank,EdNoRekening,EdCabang,
            EdUpline,EdPosisi,EdSponsor,
            EdAhliWaris,EdHubAhliWaris;
    Spinner spinner_kota, spinner_bank;
    String bank,kota;
    ArrayAdapter<String> adapter;
    String id_member,messages,success;
    @Override
    public void onCreate(
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofil);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_action_previous_item);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();}
        });
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        ///Deklarasi Edittext
        Ednama=(EditText) findViewById(R.id.Ednama);
        Edalamat=(EditText) findViewById(R.id.Edalamat);
        EdEmail=(EditText) findViewById(R.id.EdEmail);
        EdNoHP=(EditText) findViewById(R.id.EdNoHP);
        //EdNamaRekening=(EditText) findViewById(R.id.EdBank);
        Edbank=(EditText) findViewById(R.id.EdNamaRekening);
        EdNoRekening=(EditText) findViewById(R.id.EdNoRekening);
        EdCabang =(EditText) findViewById(R.id.EdCabang);
        EdUpline=(EditText) findViewById(R.id.EdUpline);
        EdPosisi=(EditText) findViewById(R.id.EdPosisi);
        EdSponsor=(EditText) findViewById(R.id.EdSponsor);
        EdAhliWaris=(EditText) findViewById(R.id.EdAhliWaris);
        EdHubAhliWaris=(EditText) findViewById(R.id.EdHubAhliWaris);

        //Pdialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("memuat ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ///AQUERY
        aq = new AQuery(this);
        final ProfileItem profil = (ProfileItem) getIntent().getParcelableExtra("profil");
        bank = profil.getRek_bank();
        kota=profil.getKota();
        id_member=profil.getId_member();

        //Deklaration spinner kota
        spinner_kota=(Spinner) findViewById(R.id.spinner);
        getKota();
        //Deklaration spinner bank
        spinner_bank=(Spinner) findViewById(R.id.spinner_bank);
        getBank();


        Ednama.setText(profil.getNama());
        Edalamat.setText(profil.getAlamat());
        EdEmail.setText(profil.getEmail());
        EdNoHP.setText(profil.getHp());
        //EdNamaRekening.setText(profil.getRek_nama());
        Edbank.setText(profil.getRek_bank());
        EdNoRekening.setText(profil.getRek_no());
        EdCabang.setText(profil.getRek_cab());
        EdUpline.setText(profil.getId_upline());
        EdPosisi.setText(profil.getPosisi());
        EdSponsor.setText(profil.getId_sponsor());
        EdAhliWaris.setText(profil.getWaris_nama());
        EdHubAhliWaris.setText(profil.getWaris_hub());

        Button simpan = (Button) findViewById(R.id.btnEditProfil);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlertDialog("");
                asyncJson();
            }
        });



    }
    public void asyncJson() {
        pDialog.show();
        String url = AppConfig.SERVER+"json/updatepmrofil_json.php";
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("id_member",id_member);

        params.put("nama", Ednama.getText().toString());
        params.put("alamat",Edalamat.getText().toString());
        params.put("email",EdEmail.getText().toString());
        params.put("kota",spinner_kota.getSelectedItemPosition());
        params.put("hp",EdNoHP.getText().toString());
        params.put("rek_nama",Edbank.getText().toString());
        params.put("rek_no",EdNoRekening.getText().toString());
        params.put("rek_bank",spinner_bank.getSelectedItem());
        params.put("rek_cab",EdCabang.getText().toString());
        params.put("waris_nama",EdAhliWaris.getText().toString());
        params.put("waris_hub",EdHubAhliWaris.getText().toString());

        Log.i("nama", Ednama.getText().toString());
        Log.i("alamat",Edalamat.getText().toString());
        Log.i("email",EdEmail.getText().toString());
        Log.i("kota",spinner_kota.getSelectedItem().toString());
        Log.i("hp",EdNoHP.getText().toString());
        Log.i("rek_nama",Edbank.getText().toString());
        Log.i("rek_no",EdNoRekening.getText().toString());
        Log.i("rek_bank",spinner_bank.getSelectedItem().toString());
        Log.i("rek_cab",EdCabang.getText().toString());
        Log.i("waris_nama",EdAhliWaris.getText().toString());
        Log.i("waris_hub",EdHubAhliWaris.getText().toString());

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
                        asyncJson();
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

    public void getKota() {
        pDialog.show();
        String url = AppConfig.SERVER+"json/kota_json.php";
        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {
                    try {
                        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
                        HashMap<String, String> map;
                        JSONArray data = json.getJSONArray("daftar_kota");
                         for(int i = 0; i < data.length(); i++){
                         JSONObject c = data.getJSONObject(i);
                         map = new HashMap<String, String>();
                         map.put("kota", c.getString("kota"));
                         MyArrList.add(map);
                            /* if(kota.equalsIgnoreCase(c.getString("kota"))){
                                 spinner_kota.setSelection(i);
                             }*/

                         }
                        SimpleAdapter sAdap;
                        sAdap = new SimpleAdapter(EditProfil.this, MyArrList, R.layout.spinner_item,
                                new String[] {"kota"}, new int[] {R.id.txt});
                        spinner_kota.setAdapter(sAdap);
                        for(int i=0;i<MyArrList.size();i++){
                            Log.i("nilai array",MyArrList.get(i).toString());
                            if(kota.equals(sAdap.getItem(i).toString())){
                                spinner_kota.setSelection(i);
                            }
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    // ajax error, show error code
                    if (status.getCode()==-101){
                        String connection="Tidak ada koneksi internet";
                        showAlertDialog(connection);
                    } else{
                        Toast.makeText(aq.getContext(),

                                "Error:" + status.getCode(), Toast.LENGTH_LONG)
                                .show();

                    }}
            }
        });

    }

    public void getBank() {
        pDialog.show();
        String url = AppConfig.SERVER+"json/bank_json.php";
        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {
                    try {
                        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
                        HashMap<String, String> map;
                        JSONArray data = json.getJSONArray("daftar_bank");
                        for(int i = 0; i < data.length(); i++){
                            JSONObject c = data.getJSONObject(i);
                            map = new HashMap<String, String>();
                            map.put("bank", c.getString("bank"));
                            MyArrList.add(map);
                            if(bank.equalsIgnoreCase(c.getString("bank"))){
                                spinner_bank.setSelection(i);
                            }

                        }
                        SimpleAdapter sAdap;
                        sAdap = new SimpleAdapter(EditProfil.this, MyArrList, R.layout.spinner_item,
                                new String[] {"bank"}, new int[] {R.id.txt});
                        spinner_bank.setAdapter(sAdap);




                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    // ajax error, show error code
                    if (status.getCode()==-101){
                        String connection="Tidak ada koneksi internet";
                        showAlertDialog(connection);
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
