package sleepchild.gitblur.tasks;
import sleepchild.gitblur.*;
import org.eclipse.jgit.api.errors.*;

public class AddTask extends Task
{
    //protected RepoItem repo;
    //protected TaskCallback cb;
    
    private String completeMessage="task complete";
    private String errorMessage="unspecified error";
    
    public AddTask(RepoItem repo, TaskCallback cb){
        super(repo, cb);
    }

    @Override
    protected Boolean doInBackground(Void[] p1){
        try
        {
            repo.git.add().addFilepattern(".").call();
            return true;
        }
        catch (GitAPIException e){
            errorMessage = e.getMessage();
        }
        return false;//super.doInBackground(p1);
    }

    @Override
    protected void onPostExecute(Object result){
        boolean res = (Boolean) result;
        if(res){
            cb.onTaskComplete(this, completeMessage);
        }else{
            cb.onTaskFailed(this, errorMessage);
        }
        super.onPostExecute(result);
    }
    
}
