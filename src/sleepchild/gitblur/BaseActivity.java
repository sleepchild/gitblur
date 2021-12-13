package sleepchild.gitblur;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.*;

public class BaseActivity extends Activity
{
    public Context ctx;
    public Prefs prefs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ctx = this;
        prefs = new Prefs(ctx);
        //
        onInit();
    }
    
    protected void onInit(){}
    
    public void startActivity(Class<?> clazz){
        startActivity(new Intent(this, clazz));
    }
    
    public void toast(String msg){
        Toast.makeText(ctx, msg, 500).show();
    }

    //@Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        // TODO: Implement this method
    }
    
    protected void showView(View v){
        v.setVisibility(View.VISIBLE);
    }
    
    protected void hideView(View v){
        v.setVisibility(View.GONE);
    }
}
