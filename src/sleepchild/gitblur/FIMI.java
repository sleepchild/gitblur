package sleepchild.gitblur;

import sleepchild.gitblur.gitt.*;
import android.os.*;
import java.io.*;
import java.util.*;
import org.eclipse.jgit.api.*;

public class FIMI
{
    String INTERNAL_DATA_DIR;
//    String unresDir = "/sdcard/gitblur/data/u/";
    public final static String DEFAULT_LOCAL_DIRECTORY = "/sdcard/gitblur/repos/";
    
    public FIMI(){
        INTERNAL_DATA_DIR = Environment.getDataDirectory().getAbsolutePath()+"/";
    }
    
    public List<RepoItem> getRepos(Prefs pref){
        List<RepoItem> l = new ArrayList<>();
        File rd = new File(pref.getLocalDirectory());
        for(File fl : rd.listFiles()){
            if(isRepo(fl)){
                RepoItem ri = getRepoItem(fl);
                ri.fl = fl;
                l.add(ri);
            }else if(isUnresolvedRepo(fl)){
                RepoItem ri = new RepoItem();
                ri.fl = fl;
                String[] s = getUrepoData(fl.getAbsolutePath()+"/urepo");
                ri.name = s[0];
                ri.remoteurl=s[1];
                ri.uncloned=true;
                l.add(ri);
            }
        }
        return l;
    }
    
    public static boolean isRepo(String path){
        return isRepo(new File(path));
    }

    public static boolean isRepo(File file){
        if(!file.isDirectory()){
            return false;
        }
        String i = file.getAbsolutePath()+"/" +".git/index";
        return new File(i).exists();
    }

    public static RepoItem getRepoItem(File fl){
        RepoItem ri = new RepoItem();
        try
        {
            Git g = Git.open(fl);
            ri.remoteurl = g.getRepository().getConfig().getString("remote","origin","url");
            ri.git = g;
            ri.name = fl.getName();
        }
        catch (IOException e)
        {}
        return ri;
    }
    
    boolean isUnresolvedRepo(File fl){
        String urepo = fl.getAbsolutePath()+"/urepo";
        if(new File(urepo).exists()){
            return true;
        }
        return false;
    }
    
    public void writeUrepo(CloneItem ci, String repoBaseDir){
        String udata = ci.dirname+"\n";
        udata+=ci.url+"\n";
        
        touch(repoBaseDir+ci.dirname+"/urepo", udata);
    }
    
    String[] getUrepoData(String path){
        if(!path.endsWith("/urepo")){
            if(new File(path+"/urepo").exists()){
                path = path+"/urepo";
            }else{
                return null;
            }
        }
        String s = readText(path);
        if(s.isEmpty()){
            return null;
        }
        
        return s.split("\n");
    }
    
    public static boolean mkdirs(String path){
        return new File(path).mkdirs();
    }
    
    public void touch(String filepath, String data){
        try
        {
            FileOutputStream o = new FileOutputStream(filepath);
            o.write(data.getBytes());
            o.flush();
            o.close();
        }
        catch (IOException e)
        {}
    }
    
    String readText(String path){
        String data="";
        String line="";
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while((line=br.readLine()) !=null){
                data+=line+"\n";
            }
            br.close();
            return data;
        }
        catch (IOException e)
        {}
        return "";
    }
}
