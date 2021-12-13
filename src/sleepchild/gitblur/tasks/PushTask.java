package sleepchild.gitblur.tasks;
import sleepchild.gitblur.*;
import android.os.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.transport.*;

public class PushTask extends AsyncTask<Void, Void, Boolean>
{
    RepoItem repo;
    boolean forcepush=false;
    boolean pushall=false;
    String remote;
    TaskCallback cb;
    String successMessage="push complete";
    String errorMessage="error";
    
    public PushTask(RepoItem repo, String remote, boolean pushAll, boolean forcePush, TaskCallback cb){
        this.repo = repo;
        this.remote = remote;
        this.cb=cb;
        this.forcepush = forcePush;
        this.pushall = pushAll;
    }

    @Override
    protected Boolean doInBackground(Void[] p1){
        //
        PushCommand cmd = repo.git.push();
        cmd.setRemote(remote);
        if(pushall){
            cmd.setPushAll();
        }
        if(forcepush){
            cmd.setForce(true);
        }
        if(!repo.username.isEmpty() && !repo.password.isEmpty()){
            cmd.setCredentialsProvider(new UsernamePasswordCredentialsProvider(repo.username, repo.password));
        }
        try
        {
            cmd.call();
            return true;
        }
        catch (GitAPIException e){
            errorMessage= e.getMessage();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        if(result){
            cb.onTaskComplete(this, successMessage);
        }else{
            if(errorMessage!=null || !errorMessage.isEmpty()){
                if(errorMessage.toLowerCase().contains("auth")){
                    cb.onAuthenticationRequiredForTask(this);
                }
            }
        }
        super.onPostExecute(result);
    }

    
    
}
