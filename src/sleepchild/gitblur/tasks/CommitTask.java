package sleepchild.gitblur.tasks;
import sleepchild.gitblur.*;
import android.os.*;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;

public class CommitTask extends AsyncTask<Void, Void, Boolean>
{
    RepoItem repo;
    TaskCallback cb;
    String commitMessage, authorName, authorEmail;
    String completeMessage="changes commited";
    String errorMessage="";
    
    public CommitTask(RepoItem repo, String message, TaskCallback cb){
        this.repo = repo;
        this.cb = cb;
        this.commitMessage = message;
    }
    
    public CommitTask(RepoItem repo, String message, String author, String email, TaskCallback cb){
        this.repo = repo;
        this.cb = cb;
        this.commitMessage = message;
        this.authorName = author;
        this.authorEmail = email;
    }

    @Override
    protected Boolean doInBackground(Void[] p1){
        CommitCommand cmd = repo.git.commit();
        cmd.setAll(true);
        cmd.setMessage(commitMessage);
        if(authorName!=null && authorEmail!=null){
            if(!authorName.isEmpty()){
                cmd.setAuthor(authorName, authorEmail);
            }
        }
        try{
            cmd.call();
            return true;
        }catch (GitAPIException e){
            errorMessage = e.getMessage();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result){
        if(result && cb!=null){
            cb.onTaskComplete(this, completeMessage);  
        }else{
            if(cb!=null){
                cb.onTaskFailed(this, errorMessage);
            }
        }
        super.onPostExecute(result);
    }
    
}
