package sleepchild.gitblur.gitt;

public class CloneItem
{
    private String id;
    public String url;
    public String dirname;
    public boolean complete;
    public String uname;
    public String pass;
    
    public CloneItem(long time){
        id = ""+time;
    }
    
    public String getId(){
        return id;
    }

    @Override
    public String toString() {
        String str = id+"\n";
        str+= url+"\n";
        str+= dirname+"\n";
        str+= complete+"\n";
        str+= uname+"\n";
        str+= pass+"\n";
        return str;
    }
    
}
