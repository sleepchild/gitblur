package sleepchild.gitblur;
import android.app.*;
import android.os.*;
import android.content.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import sleepchild.gitblur.gitt.CloneItem;
import android.widget.*;
import sleepchild.gitblur.gitt.*;

public class GitBService extends Service {
    
    ExecutorService worker;
    FIMI fm;
    Prefs pref;
    Handler handle = new Handler();
    NotificationManager nMgr;
    
    final static String CMD_CLONE ="SLeep_gb.cmd_clone.comm1";
    final static String CMD_PUSH ="SLeep_gb.CLONE_COMMAND.comm1";

    @Override
    public IBinder onBind(Intent p1) {
        return null;
    }
    
    public static void clone(Context ctx, CloneItem cloneitem){
        Intent st = new Intent(ctx, GitBService.class);
        G.get().tempCI = cloneitem;
        st.setAction(CMD_CLONE);
        ctx.startService(st);
    }
    
    public void getFiles(){
        //
    }
    
    /*
    public static void stop(Context ctx){
        Intent st = new Intent(ctx, GitBService.class);
        st.setAction(CMD_STOP);
        ctx.startService(st);
    }
    //*/

    @Override
    public void onCreate() {
        G.get().service = this;
        super.onCreate();
        if(ma()!=null){
            ma().onConnect(this);
        }
        worker = Executors.newFixedThreadPool(2);
        fm = new FIMI();
        pref = new Prefs(this);
        nMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String cmd = intent.getAction();
        if(cmd!=null){
            switch(cmd){
                case CMD_CLONE:
                    startClone();
                    break;
                case CMD_PUSH:
                    //
                    break;
            }
        }
        return START_NOT_STICKY; // super.onStartCommand(intent, flags, startId);
    }
    
    private void startClone(){
        CloneItem ci = G.get().tempCI;
        G.get().tempCI=null;
        fm.mkdirs(pref.getLocalDirectory()+ ci.dirname);
        fm.writeUrepo(ci, pref.getLocalDirectory());
        toast("submit.");
    }
    
    private MainActivity ma(){
        return G.get().ma;
    }
    
    private void toast(String msg){
        Toast.makeText(this, msg, 500).show();
    }
    
}
