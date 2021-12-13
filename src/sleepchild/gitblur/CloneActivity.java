package sleepchild.gitblur;
import android.view.*;
import android.widget.*;
import android.content.*;
import sleepchild.gitblur.gitt.*;
import java.util.*;
import sleepchild.gitblur.tasks.*;
import org.eclipse.jgit.lib.*;
import android.app.*;

public class CloneActivity extends BaseActivity implements ProgressMonitor
{
    EditText etUrl, etDir, etUsername, etPassword;
    String cloneUrl=""; // git url from which to clone;
    String cloneDirectory="";// name of dire we clone into; just the name. the full path will be generated elsewhere.
    ClipboardManager clipmgr;
    TextView mess;
    ProgressDialog pd;
    
    @Override
    protected void onInit()
    {
        super.onInit();
        setContentView(R.layout.activity_clone);
        i();
         
    }
    
    private void i(){
        clipmgr = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        etUrl = (EditText) findViewById(R.id.activity_clone_eturl);
        etDir = (EditText) findViewById(R.id.activity_clone_etdirectory);
        etUsername = (EditText) findViewById(R.id.activity_clone_etusername);
        etPassword = (EditText) findViewById(R.id.activity_clone_etpassword);
        mess = (TextView) findViewById(R.id.activity_clone_messagetext);
        
        //
        etUsername.setText(prefs.getUserName());
        etPassword.setText(prefs.getPassword());
        //
        //etUsername.addTextChangedListener();
        pd = new ProgressDialog(this);
        //pd.setIndeterminate(false);
        pd.setMax(100);
        //pd.setCanceledOnTouchOutside(false);
    }
    
    public void onButton(View v){
        int id = v.getId();
        switch(id){
            case R.id.activity_clone_btnClone:
                cloneRepo();
                break;
            case R.id.activity_clone_pasteurl:
                pasteUrl();
                break;
        }
    }
    
    private void pasteUrl(){
        String s = clipmgr.getPrimaryClip().getItemAt(0).getText().toString();
        if(!s.startsWith("https") || !s.startsWith("http")){
            //warn("not a url");
            return;
        }
        cloneUrl=s;
        etUrl.setText(cloneUrl);
        autoSetFolder();
    }
    
    void autoSetFolder(){
        String s = cloneUrl;
        if(s.endsWith("/")){
            s = s.substring(s.length()-2);
        }
        s = s.substring(cloneUrl.lastIndexOf("/")+1);
        
        if(s.endsWith(".git")){
            s = s.substring(0,(s.length()- 4));
        }
        etDir.setText(s);
    }
    
    private void cloneRepo(){
        //
        cloneUrl = etUrl.getText().toString();
        cloneDirectory = etDir.getText().toString();
        
        if(cloneUrl==null || cloneUrl.isEmpty()){
            warn("url is required!");
            return;
        }
        
        String t = cloneUrl.toLowerCase();
        if(!t.startsWith("https") || !t.startsWith("http")){
            warn("please enter an valid url!");
            return;
        }
        
        String uname = etUsername.getText().toString();
        String pass = etPassword.getText().toString();
        
        warn("");
        
        if(cloneDirectory==null || cloneDirectory.isEmpty()){
            String s = cloneUrl.substring(cloneUrl.lastIndexOf("/")+1);
            cloneDirectory = s.replace(".","_");
        }
        
        CloneItem c = new CloneItem(new Date().getTime());
        c.url = cloneUrl;
        c.dirname = prefs.getLocalDirectory()+ cloneDirectory;
        c.uname = uname;
        c.pass = pass;
        
        //
        //GitBService.clone(this, c);
        pd.show();
        new CloneTask(c, this).execute();
        
        //finish();
    }
    
    void warn(String msg){
        mess.setText(msg);
        mess.setTextColor(getResources().getColor(R.color.amber));
    }

    @Override
    public void start(final int p1)
    {
        runOnUiThread(new Runnable(){
            public void run(){
                //pd.show();
            }
        });
       // toast("start");
    }

    @Override
    public void beginTask(String p1, int p2)
    {
        runOnUiThread(new Runnable(){
            public void run(){
                //toast("begin");
            }
        });
    }

    @Override
    public void update(final int p1)
    {
        runOnUiThread(new Runnable(){
            public void run(){
                //toast("update");
                pd.setProgress(p1);
            }
        });
    }

    @Override
    public boolean isCancelled()
    {
        runOnUiThread(new Runnable(){
            public void run(){
                //toast("canceled");
            }
        });
        return false;
    }

    @Override
    public void endTask()
    {
        runOnUiThread(new Runnable(){
            public void run(){
                toast("end");
                pd.dismiss();
            }
        });
    }
    
}
