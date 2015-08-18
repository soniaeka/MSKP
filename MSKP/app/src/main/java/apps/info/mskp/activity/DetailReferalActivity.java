package apps.info.mskp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;

import apps.info.mskp.R;
import apps.info.mskp.helper.AppConfig;
import apps.info.mskp.model.ProfileItem;

/**
 * Created by ACER on 15/08/2015.
 */
public class DetailReferalActivity extends ActionBarActivity{
    private Toolbar mToolbar;
    AQuery aq;
    ProgressDialog pDialog;
    private WebView webView;
    String id_member,keyword;
    EditText key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailreferal);
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

        Bundle extras = getIntent().getExtras();
        id_member=extras.getString("id_member");


        //Pdialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Menyimpan ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ///AQUERY
        aq = new AQuery(this);

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(AppConfig.SERVER+"member/cobagrafik.php?id_member="+id_member);

         key=(EditText) findViewById(R.id.EdKey);
        Button search = (Button) findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   showAlertDialog(key.getText().toString());
                webView.loadUrl(AppConfig.SERVER+"member/cobagrafik.php?id_member="+key.getText().toString());
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
