package sleepchild.gitblur;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.os.*;
import sleepchild.gitblur.adapters.*;
import java.util.*;
import java.io.*;
import sleepchild.gitblur.tasks.*;

public class RepositoryActivity extends BaseActivity implements TaskCallback
{
    ListView list88;
    RelativeLayout drawerRight;
    RepoFilesListAdapter adapter;
    RepoItem repoitem;
    List<String> backstack = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle si){
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(si);
        setContentView(R.layout.activity_repository);
        repoitem = G.get().repoi;
        G.get().repoi =null;
        //
        setTitle(repoitem.name);
        //
        i();
    }
    
    private void i(){
        drawerRight = (RelativeLayout) findViewById(R.id.repository_activity_drawer_right);
        list88 = (ListView) findViewById(R.id.repository_activity_list88);
        adapter = new RepoFilesListAdapter(ctx);
        list88.setAdapter(adapter);
        list88.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> p1, View v, int p3, long p4)
            {
               
                File fl = (File) v.getTag();
                if(fl.isDirectory()){
                    backstack.add(fl.getParent());
                    showFiles(fl);
                }
            }
        });
        //todo: is the use of @AsyncTask perfomant in this case
       // new GetFilesTask(repoitem.fl).execute();
       showFiles(repoitem.fl);
    }
    
    private void showActionsDrawer(){
        showView(drawerRight);
    }
    
    private void closeActionsDrawer(){
        hideView(drawerRight);
    }

    @Override
    public synchronized void onTaskComplete(Object obj, String message){
        /*
        if(obj instanceof AddTask){
            toast("files added");
        }else if(obj instanceof CommitTask){
            //
        }
        //*/
        toast(message);
    }

    @Override
    public synchronized void onAuthenticationRequiredForTask(Object obj){
        toast("authentication required!");
    }

    @Override
    public synchronized void onTaskFailed(Object obj, String reason){
        toast(reason);
    }
    
    void showFiles(File fl){
        
        List<File> l = new ArrayList<>();
        List<File> fs = new ArrayList<>();
        List<File> ds = new ArrayList<>();
        for(File ml : fl.listFiles()){
            if(ml.getName().equals(".git") && ml.isDirectory()){
                if(prefs.getShowHiddenFiles()==false){
                    continue;
                }
            }
            if(ml.isDirectory()){
                ds.add(ml);
            }else{
                fs.add(ml);
            }
        }
        Collections.sort(fs);
        Collections.sort(ds);
        l.addAll(ds);
        l.addAll(fs);
        adapter.update(l);
    }
    
    
    public void onActionDrawerButton(View v){
        String tag = v.getTag().toString();
        switch(tag){
            case COM.add:
                new AddTask(repoitem, this).execute();
                break;
            case COM.push:
                new PushDialog(this, repoitem, this).show();
                break;
            case COM.commit:
                new CommitDialog(this, repoitem, this).show();
                break;
        }
        closeActionsDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu
          .add("drawer")
          .setIcon(R.drawable.ic_drawer)
          .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        String title = item.getTitle().toString();
        switch(title){
            case "drawer":
                showActionsDrawer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if(backstack.isEmpty()){
            super.onBackPressed();
        }else{
            //
            String p = backstack.remove(backstack.size()-1);
            showFiles(new File(p));
        }
    }
    
    
    
    public void closeDrawer(View c){
        closeActionsDrawer();
    }
    
    public void none(View v){}
    
    /*
    class GetFilesTask extends AsyncTask<Void , Void, List<File>>{
        File fl;
        public GetFilesTask(File dir){
            fl=dir;
        }

        @Override
        protected List<File> doInBackground(Void[] p1){
            //
            List<File> res = Arrays.asList(fl.listFiles());
            return res;
        }

        @Override
        protected void onPostExecute(List<File> result){
            adapter.update(result);
            super.onPostExecute(result);
        }
        
    }
    //*/
    
    class COM {
        static final String push ="push";
        static final String pull ="pull";
        static final String add ="add";
        static final String commit = "commit";
        static final String clone = "clone";
        
    }
    
}
