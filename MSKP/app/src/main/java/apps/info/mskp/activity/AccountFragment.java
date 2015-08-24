package apps.info.mskp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class AccountFragment extends Fragment {

    private Button btn_transRo;
    private Button btn_pinTRX;
    private Button btn_kartuId;
    private Button btn_transID;

    SessionManager session;

    public AccountFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        session = new SessionManager(getActivity());

        btn_transRo=(Button) rootView.findViewById(R.id.btnTransRo);
        btn_pinTRX=(Button) rootView.findViewById(R.id.btnPinTrx);
        btn_kartuId=(Button) rootView.findViewById(R.id.btnKartuId);
        btn_transID=(Button) rootView.findViewById(R.id.btnTransId);

        btn_transRo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TransaksiROActivity.class);
                //ceklogin(i);
                startActivity(i);
            }
        });

        btn_pinTRX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), HistoryTRXActivity.class);
                //ceklogin(i);
                startActivity(i);
            }
        });

        btn_kartuId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), KartuActivity.class);
               // ceklogin(i);
                startActivity(i);
            }
        });

        btn_transID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TransferKartuActivity.class);
                //ceklogin(i);
                startActivity(i);
            }
        });


        return rootView;
    }

    private void ceklogin(Intent i){
        if(session.isLoggedIn()){
            startActivity(i);
            Log.i("id member",session.KEY_USERNAME);
        }else{
            Toast.makeText(getActivity(),"Anda belum login",Toast.LENGTH_LONG).show();
            session.checkLogin();
        }
    }
}