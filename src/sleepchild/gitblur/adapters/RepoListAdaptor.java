package sleepchild.gitblur.adapters;

import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.content.Context;
import java.util.List;
import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import sleepviews.*;
import android.graphics.*;
import sleepchild.gitblur.*;

public class RepoListAdaptor extends BaseAdapter
{
    LayoutInflater inf;
    List<RepoItem> repolist = new ArrayList<>();

    public RepoListAdaptor(Context ctx){
        inf = LayoutInflater.from(ctx);
    }

    public void update(List<RepoItem> list){
        repolist = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        // TODO: Implement this method
        return repolist.size();
    }

    @Override
    public Object getItem(int pos)
    {
        return repolist.get(pos);
    }

    @Override
    public long getItemId(int p1)
    {
        // TODO: Implement this method
        return p1;
    }

    //@Override
    public CharSequence[] getAutofillOptions()
    {
        // TODO: Implement this method
        return null;
    }




    @Override
    public View getView(int pos, View v, ViewGroup p3)
    {
        RepoItem ri = repolist.get(pos);

        v = inf.inflate(R.layout.adapteritem_repolistadapter, null, false);

        TextView name = v.findViewById(R.id.repolistitem_reponame);
        name.setText(ri.name);

        TextView remoteurl = v.findViewById(R.id.repolistitem_origin_path);
        remoteurl.setText(ri.remoteurl);

        if(ri.status_err || ri.uncloned){
            RoundyView b = (RoundyView) v.findViewById(R.id.repolistitem_borders);
            b.setBorderColor(Color.parseColor("#ff2222"));
            remoteurl.setTextColor(Color.parseColor("#ff2222"));
            name.setTextColor(Color.parseColor("#ff2222"));
            //((View) v.findViewById(R.id.repolistitem_e12)).setVisibility(View.VISIBLE);
        }

        v.setTag(ri);
        return v;
    }


}
