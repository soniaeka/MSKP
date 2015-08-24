package apps.info.mskp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import apps.info.mskp.model.CategoryItem;
import apps.info.mskp.model.ProfileItem;


public class DataPribadiFragment extends Fragment {
    AQuery aq;
    SessionManager session;
    String id_member;
    private ProgressDialog pDialog;
    Button editProfil,editPassword;
    String success ,messages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_datapribadi, container, false);
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        id_member=user.get(SessionManager.KEY_USERNAME);


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Sedang mengambil data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);




        aq = new AQuery(getActivity());

        getProfilJson();
        getNotif();

        //Edit data
       editProfil = (Button) rootView.findViewById(R.id.btnEditProfil);
       editPassword = (Button) rootView.findViewById(R.id.btnEditPassword);

       return rootView;
    }

    public void getProfilJson() {
        pDialog.show();
        String url = AppConfig.SERVER+"json/getprofil_json.php";
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("id_member", id_member);
        aq.ajax(url, params,JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {
                    try {
                       // JSONArray profilItemArray = json.getJSONArray("profil");
                        final List<ProfileItem> profil_items = new ArrayList<ProfileItem>();
                              //  JSONObject p_object= profilItemArray.getJSONObject(0);
                                final ProfileItem p_item = new ProfileItem();
                                p_item.setId_member(json.getString("id_member"));
                                p_item.setNama(json.getString("nama"));
                                p_item.setAlamat(json.getString("alamat"));
                                p_item.setKota(json.getString("kota"));
                                p_item.setEmail(json.getString("email"));
                                p_item.setHp(json.getString("hp"));
                                p_item.setRek_nama(json.getString("rek_nama"));
                                p_item.setRek_bank(json.getString("rek_bank"));
                                p_item.setRek_no(json.getString("rek_no"));
                                p_item.setRek_cab(json.getString("rek_cab"));
                                p_item.setId_upline(json.getString("id_upline"));
                                p_item.setPosisi(json.getString("posisi"));
                                p_item.setId_sponsor(json.getString("id_sponsor"));
                                p_item.setWaris_nama(json.getString("waris_nama"));
                                p_item.setWaris_hub(json.getString("waris_hub"));
                                p_item.setTanggal_daftar(json.getString("tanggal_daftar"));
                                p_item.setLama(json.getString("lama"));
                                p_item.setPassword(json.getString("password"));
                                //profil_items.add(p_item);
                                //cAdapter.setItem(category_items);

                        aq.id(R.id.no_id).text(p_item.getId_member());
                        aq.id(R.id.nama).text(p_item.getNama());
                        aq.id(R.id.alamat).text(p_item.getAlamat());
                        aq.id(R.id.kota).text(p_item.getKota());
                        aq.id(R.id.email).text(p_item.getEmail());
                        aq.id(R.id.nohp).text(p_item.getHp());
                        aq.id(R.id.nama_rekening).text(p_item.getRek_nama());
                        aq.id(R.id.bank).text(p_item.getRek_bank());
                        aq.id(R.id.nomor_rekening).text(p_item.getRek_no());
                        aq.id(R.id.cabang).text(p_item.getRek_cab());
                        aq.id(R.id.upline).text(p_item.getId_upline());
                        aq.id(R.id.posisi).text(p_item.getPosisi());
                        aq.id(R.id.sponsor).text(p_item.getId_sponsor());
                        aq.id(R.id.ahliwaris).text(p_item.getWaris_nama());
                        aq.id(R.id.hub_ahliwaris).text(p_item.getWaris_hub());
                        aq.id(R.id.tanggaljoin).text(p_item.getTanggal_daftar());
                        aq.id(R.id.lama).text(p_item.getLama());

                        editProfil.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Intent detail_menu = new Intent(getActivity(),EditProfil.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putParcelable("profil",(Parcelable) p_item);
                                detail_menu.putExtras(mBundle);
                                startActivity(detail_menu);

                            }
                        });

                        editPassword.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent detail_menu = new Intent(getActivity(),EditPasswordProfil.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putParcelable("profil",(Parcelable) p_item);
                                detail_menu.putExtras(mBundle);
                                startActivity(detail_menu);

                            }
                        });


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    // ajax error, show error code
                    if (status.getCode()==-101){
                        String connection="Tidak ada koneksi internet";
                        //Toast.makeText(aq.getContext(),connection,Toast.LENGTH_LONG).show();
                        showAlertDialog(connection);
                    } else{
                        Toast.makeText(aq.getContext(),

                                "Error:" + status.getCode(), Toast.LENGTH_LONG)
                                .show();

                    }}
            }
        });

    }
    public void getNotif() {
        pDialog.show();
        String url = AppConfig.SERVER+"json/upgradecinta_cinta.php";
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("id_member", id_member);
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

                    showAlertDialog(messages);


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

        AlertDialog.Builder peringatan=new AlertDialog.Builder(getActivity());

        peringatan.setMessage(message);
        peringatan.setNeutralButton("OK", null);
        peringatan.show();
    }





}
