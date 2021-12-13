package sleepchild.gitblur;
import org.eclipse.jgit.api.*;
import java.io.*;

public class RepoItem
{
    // todo: implem getter/ setters
    public String remoteurl;
    public String localpath;
    public String name;
    public File fl;
    public Git git;
    public String username;
    public String password;
    //String riff;
    public boolean status_err=false;
    public boolean uncloned=false;
    
    public RepoItem(){}
}
