package sleepchild.gitblur;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.content.*;
import sleepchild.*;
import android.view.*;
import android.os.Handler;
import android.os.*;
import java.util.*;
import sleepchild.gitblur.adapters.*;

public class MainActivity extends BaseActivity {

    GitBService git;
    ListView list24;
    RepoListAdaptor repoAdaptor;
    Handler handle;
    boolean active;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        G.get().ma = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        i();
    }
    
    private void i(){
        list24 = (ListView) findViewById(R.id.main_list24);
        list24.setDivider(null);
        repoAdaptor = new RepoListAdaptor(this);
        list24.setAdapter(repoAdaptor);
        list24.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> p1, View v, int pos, long p4) {
                RepoItem ri = (RepoItem) v.getTag();
                G.get().repoi = ri;
                startActivity(RepositoryActivity.class);
            }
        });
    }
    
    public void onButton(View v){
        int id = v.getId();
        switch(id){
            case R.id.activity_main_btn_clone:
                startActivity(CloneActivity.class);
                break;
        }
    }
    
    public void toast(String msg){
        Toast.makeText(ctx,msg,500).show();
    }

    //@Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        //
    }
    
    public void onConnect(GitBService se){
        git = se;
        //toast("connect!");
    }
    
    public void onUpdate(final String msg){
        runOnUiThread(new Runnable(){
            public void run(){
                toast(msg);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add("settings");
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    protected void onPause()
    {
        G.get().ma = null;
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        G.get().ma = this;
        super.onResume();
        if(repoAdaptor!=null){
            new GetReposTask(prefs).execute();
        }
    }
    
    class GetReposTask extends AsyncTask<Void, Void, List<RepoItem>>
    {
        private Prefs pref;
        public GetReposTask(Prefs pref){
            this.pref = pref;
        }

        @Override
        protected List<RepoItem> doInBackground(Void[] p1)
        {
            List<RepoItem> repos =  new FIMI().getRepos(pref);
            return repos;
        }

        @Override
        protected void onPostExecute(List<RepoItem> result)
        {
            repoAdaptor.update(result);
            super.onPostExecute(result);
        }
        
    }

    
    
}
