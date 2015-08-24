package apps.info.mskp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import apps.info.mskp.adapter.CategoryAdapter;
import apps.info.mskp.adapter.ReferalAdapter;
import apps.info.mskp.helper.AppConfig;
import apps.info.mskp.helper.SessionManager;
import apps.info.mskp.model.CategoryItem;
import apps.info.mskp.model.ReferalItem;

/**
 * Created by ACER on 15/08/2015.
 */
public class ReferalFragment extends Fragment {
    AQuery aq;
    SessionManager session;
    String id_member;
    private ProgressDialog pDialog;
    String total;

    ReferalAdapter rAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_referal, container, false);
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        id_member=user.get(SessionManager.KEY_USERNAME);


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Sedang mengambil data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        aq = new AQuery(getActivity());

        rAdapter= new ReferalAdapter(getActivity());
        ListView lv = (ListView) rootView.findViewById(R.id.listReferal);
        lv.setAdapter(rAdapter);

          lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               Intent menuInten = new Intent(getActivity(),DetailReferalActivity.class);
                menuInten.putExtra("id_member",rAdapter.getItem(position).getId_member());
               startActivity(menuInten);

            }
        });



        getReferal();
        return rootView;
    }

    public void getReferal() {
        pDialog.show();
      String url = AppConfig.SERVER+"json/referal_json.php";

        Map<String, Object> params = new HashMap<String,Object>();
        params.put("id_member", id_member);
        aq.ajax(url, params,JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                pDialog.dismiss();
                if (json != null) {
                    try {

                       JSONArray referalItemArray = json.getJSONArray("daftar_data");
                      //  total="Jumlah PenSponsoran : "+json.getString("total_data")+" anggota";
                        List<ReferalItem> referal_items = new ArrayList<ReferalItem>();
                        for (int i = 0; i < referalItemArray.length(); i++) {

                            try {

                                ReferalItem referal_item = new ReferalItem();
                                referal_item .setId(referalItemArray.getJSONObject(i).getInt("id"));
                                referal_item .setId_member(referalItemArray.getJSONObject(i).getString("id_member"));
                                referal_item .setAlamat(referalItemArray.getJSONObject(i).getString("alamat"));
                                referal_item .setNama(referalItemArray.getJSONObject(i).getString("nama"));
                                referal_items.add(referal_item );

                            } catch (JSONException e) {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT);

                                e.printStackTrace();
                            }

                        }
                        rAdapter.setItem(referal_items);
                     //   aq.id(R.id.total).text(total);


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

        AlertDialog.Builder peringatan=new AlertDialog.Builder(getActivity());

        peringatan.setMessage(message);
        peringatan.setNeutralButton("OK", null);
        peringatan.show();
    }


}
