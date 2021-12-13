package sleepchild.gitblur;
import sleepchild.gitblur.gitt.*;
import java.util.*;

public class G
{
    private static G deft;
    
    private G(){
        //
    }
    
    public static G get(){
        G i = deft;
        if(i==null){
            synchronized(G.class){
                i = G.deft;
                if(i==null){
                    i = G.deft = new G();
                }
            }
        }
        return i;
    }
    
    public RepoItem repoi;
    public CloneItem tempCI;
    
    public GitBService service;
    public MainActivity ma;
    
    public List<ProgressListener> progresses = new ArrayList<>();
    
}
