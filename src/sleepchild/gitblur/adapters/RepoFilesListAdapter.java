package sleepchild.gitblur.adapters;
import android.content.*;
import android.widget.*;
import android.view.*;
import java.util.*;
import java.io.*;
import sleepchild.gitblur.*;

public class RepoFilesListAdapter extends BaseAdapter
{
    LayoutInflater inf;
    List<File> fileslist = new ArrayList<>();
    
    public RepoFilesListAdapter(Context ctx){
        inf = LayoutInflater.from(ctx);
    }
    
    public void update(List<File> list){
        fileslist= list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return fileslist.size();
    }

    @Override
    public File getItem(int pos){
        return fileslist.get(pos);
    }

    @Override
    public long getItemId(int p1){
        return p1;
    }

    @Override
    public View getView(int pos, View v, ViewGroup p3){
        File fl = getItem(pos);
        
        v = inf.inflate(R.layout.adapteritem_repo_fileslistadapter, null, false);
        TextView name = (TextView) v.findViewById(R.id.adapteritem_repo_file_name);
        name.setText(fl.getName());
        if(fl.isFile()){
            ImageView ic = (ImageView) v.findViewById(R.id.adapteritem_repo_file_icon);
            ic.setBackgroundResource(R.drawable.ic_file_d);
        }
        v.setTag(fl);
        return v;
    }

    //@Override
    public CharSequence[] getAutofillOptions()
    {
        // TODO: Implement this method
        return null;
    }
    
    
}
