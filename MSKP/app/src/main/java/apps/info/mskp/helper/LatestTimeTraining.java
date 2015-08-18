package apps.info.mskp.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by ACER on 06/07/2015.
 */

@SuppressLint("CommitPrefEdits")
public class LatestTimeTraining {

    // Shared Preferences
    SharedPreferences preff;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // nama sharepreference
    private static final String PREF_NAME = "Sesii";

    // All Shared Preferences Keys
   // private static final String IS_LOGIN = "IsLoggedIn";
  //  public static final String KEY_USERNAME= "logUsername";
  //  public static final String KEY_EMAIL="LogEmail";
    public static final String key_photo="LogPhoto";
    public static final String Latest_Time="";

    // Constructor
    public LatestTimeTraining(Context context){
        this._context = context;
        preff = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preff.edit();
    }
    public void createDateTime(String datetime){
        // Storing login value as TRUE

        editor.putString(Latest_Time, datetime);
        editor.commit();
    }

    public HashMap<String, String> getLatestTime(){
        HashMap<String, String> time = new HashMap<String, String>();
        time.put(Latest_Time, preff.getString(Latest_Time, null));
        return time;
    }
    public void clearTime(){
        // Clearing all data from Shared Preferences
        editor.clear();
    }



}
