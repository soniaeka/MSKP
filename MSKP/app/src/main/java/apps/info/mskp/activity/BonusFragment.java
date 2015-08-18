package apps.info.mskp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import apps.info.mskp.R;
import apps.info.mskp.helper.SessionManager;

/**
        * Created by Bustomi Raharjo on 12/08/2015.
        */
public class BonusFragment extends android.support.v4.app.Fragment {

    private Button btn_bonusHari;
    private Button btn_bonusTotal;
    private Button btn_bonusHis;

    SessionManager session;

    public BonusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_bonus, container, false);

        session = new SessionManager(getActivity());

        btn_bonusHari=(Button) rootView.findViewById(R.id.btnBonusHari);
        btn_bonusTotal=(Button) rootView.findViewById(R.id.btnBonusTotal);
        btn_bonusHis=(Button) rootView.findViewById(R.id.btnBonusHis);

        btn_bonusHari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BonusHariActivity.class);
                ceklogin(i);
            }
        });

        btn_bonusTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BonusTotalActivity.class);
                ceklogin(i);
            }
        });

        btn_bonusHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BonusHisActivity.class);
                ceklogin(i);
            }
        });

        return rootView;
    }

    private void ceklogin(Intent i){
        if(session.isLoggedIn()){
            startActivity(i);
            Log.i("id member", session.KEY_USERNAME);
        }else{
            Toast.makeText(getActivity(), "Anda belum login", Toast.LENGTH_LONG).show();
            session.checkLogin();
        }
    }
}

