package sleepchild.gitblur;
import android.content.*;
import android.preference.*;

public class Prefs
{
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    
    enum K{
        PASSWORD,
        USERNAME,
        EMAIL,
        ACCESS_TOKEN,
        LOCAL_DIRECTORY,
        SHOW_HIDDEN
    }
    
    private String k2s(K key){
        return key.toString();
    }
    
    public Prefs(Context ctx){
        pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        edit = pref.edit();
    }
    
    
    //path to local repo directory
    public String getLocalDirectory(){
        return getString(K.LOCAL_DIRECTORY, FIMI.DEFAULT_LOCAL_DIRECTORY);
    }
    
    public void setLocalDirectory(String value){
        putString(K.LOCAL_DIRECTORY, value);
    }
    
    
    
    // username
    public void setUsername(String username){
        putString(K.USERNAME, username);
    }
    
    public String getUserName(){
        return getString(K.USERNAME, "");
    }
    
    // user password
    public String getPassword(){
        return getString(K.PASSWORD,"");
    }
    
    public void setPassword(String password){
        putString(K.PASSWORD, password);
    }
    
    // private access token 
    //
    
    
    // email
    public String getEmail(){
        return getString(K.EMAIL,"");
    }

    public void setEmail(String password){
        putString(K.EMAIL, password);
    }
    
    // show .git and other hidden folders in repo files view
    public boolean getShowHiddenFiles(){
        return getBool(K.SHOW_HIDDEN, false);
    }
    
    public void setShowHiddenFiles(boolean value){
        putBool(K.SHOW_HIDDEN, value);
    }
    
    
    //// private helpers
    // string
    private void putString(K key, String value){
        edit.putString(k2s(key), value).commit();
    }
    
    private String getString(K key, String deft){
        return pref.getString(k2s(key), deft);
    }
    
    // bool
    private void putBool(K key, boolean value){
        edit.putBoolean(k2s(key), value).commit();
    }

    private boolean getBool(K key, boolean deft){
        return pref.getBoolean(k2s(key), deft);
    }
    
    
}
