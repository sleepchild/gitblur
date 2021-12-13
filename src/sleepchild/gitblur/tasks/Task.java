package sleepchild.gitblur.tasks;
import sleepchild.gitblur.*;
import org.eclipse.jgit.api.*;
import android.os.*;

public class Task extends AsyncTask<Void, Void, Object>
{
    protected RepoItem repo;
    protected TaskCallback cb;
    
    public Task(RepoItem repo, TaskCallback callback){
        this.repo = repo;
        this.cb = callback;
    }

    @Override
    protected Object doInBackground(Void[] p1){
        return null;
    }
    
    public RepoItem getRepo(){
        return repo;
    }
    
    
}
