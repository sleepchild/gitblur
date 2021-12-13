package sleepchild.gitblur.tasks;
import android.os.*;
import sleepchild.gitblur.*;
import sleepchild.gitblur.gitt.*;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.*;
import java.io.*;

public class CloneTask extends AsyncTask<Void, Void, Boolean>
{
    CloneItem clone;
    ProgressMonitor pm;
    
    public CloneTask(CloneItem clone, ProgressMonitor pm){
        this.clone = clone;
        this.pm = pm;
    }

    @Override
    protected Boolean doInBackground(Void[] p1)
    {
        CloneCommand cmd = Git.cloneRepository();
        cmd.setURI(clone.url);
        cmd.setDirectory(new File(clone.dirname));
        cmd.setTimeout(3000);
        if(pm!=null){
            cmd.setProgressMonitor(pm);
        }
        try
        {
            cmd.call();
            return true;
        }
        catch (GitAPIException e){
            try
            {
                FileOutputStream o = new FileOutputStream("/sdcard/gblog.txt");
                o.write(e.getMessage().getBytes());
                o.flush();
                o.close();
            }
            catch (IOException ee)
            {}
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        //
        super.onPostExecute(result);
    }

    
}
