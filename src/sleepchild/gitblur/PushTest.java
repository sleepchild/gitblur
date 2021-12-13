package sleepchild.gitblur;
import org.eclipse.jgit.api.*;
import java.io.*;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.lib.*;
import android.os.*;
import org.eclipse.jgit.api.errors.*;

public class PushTest {
    public PushTest(ProgressMonitor pm, CB cb){
        try
        {
           
            Git g = Git.open(new File("/sdcard/gitly/flashiex4"));
            //*
            AddCommand add = g.add();
           // add.setUpdate(true);
            add.addFilepattern(".");
            
            //*/
            CommitCommand commit = g.commit();
            commit.setMessage("update");
            
            PushCommand push =  g.push();
            UsernamePasswordCredentialsProvider creds = new UsernamePasswordCredentialsProvider(
               "sleepchild",
                "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");
            push.setCredentialsProvider(creds);
            push.setProgressMonitor(pm);
            new PushTask(push,add, commit, cb).execute();
            //
        }
        catch (IOException e)
        {}
    }
    //
    class PushTask extends AsyncTask<Void, Void, Boolean>
    {
        PushCommand cmd;
        AddCommand add;
        CommitCommand comm;
        Iterable<PushResult> res;
        CB callback;
        
        public PushTask(PushCommand pc, AddCommand ad, CommitCommand com, CB cb){
            cmd = pc;
            callback = cb;
            add = ad;
            comm = com;
        }

        @Override
        protected Boolean doInBackground(Void[] p1)
        {
            try
            {
                add.call();
                comm.call();
                res = cmd.call();
                return true;
            }
            catch (GitAPIException e)
            {}
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if(result){
                callback.onResult("okay",res);
            }else{
                callback.onResult("failed",null);
            }
            super.onPostExecute(result);
        }
        
    }
    
    public interface CB{
        public void onResult(String resp, Iterable<PushResult> result);
    }
}
