package sleepchild.gitblur;
import android.app.*;
import android.content.*;

public class Notify
{
    NotificationManager mgr;
    
    public Notify(){
        //
    }
    
    public static Notification get(Context ctx, String title, String text){
        Notification.Builder b = new Notification.Builder(ctx);
        b.setSmallIcon(R.drawable.ic_launcher);
        b.setContentTitle(title);
        b.setContentText(text);
        return b.build();
    }
}
