package sleepchild.gitblur;
import android.content.*;
import android.app.*;
import sleepchild.gitblur.tasks.*;
import android.widget.*;
import android.view.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.*;
import java.util.*;
import android.os.*;

public class PushDialog extends BaseDialog implements View.OnClickListener
{
    RepositoryActivity act;
    RepoItem repo;
    TaskCallback cb;
    CheckBox cbPushAll, cbForcePush;
    Button btnCancel;
    ListView list2;
    boolean pushAll=false;
    boolean forcePush=false;
    RefAdaptor adapter;
    EditText etUsername, etPassword;
    String username;
    String password;
    
    public PushDialog(RepositoryActivity ctx, RepoItem repo, TaskCallback cb){
        super(ctx);
        this.act = ctx;
        this.repo = repo;
        this.cb = cb;
        setTitle("Push to..");
        setContentView(R.layout.dialog_pre_push);
        i();
        getRemotes();
    }
    
    private void i(){
        list2 = (ListView) findViewById(R.id.dialog_pre_push_list2);
        adapter = new RefAdaptor(getContext());
        list2.setAdapter(adapter);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> p1, View v, int p3, long p4)
            {
                push(v.getTag().toString());
            }
        });
        btnCancel = (Button) findViewById(R.id.dialog_pre_push_cancel);
        btnCancel.setOnClickListener(this);
        cbPushAll = (CheckBox) findViewById(R.id.dialog_pre_push_cbpushall);
        cbForcePush = (CheckBox) findViewById(R.id.dialog_pre_push_cbforcepush);
        cbForcePush.setOnClickListener(this);
        cbPushAll.setOnClickListener(this);
        etUsername = (EditText) findViewById(R.id.dialog_pre_push_username);
        etPassword = (EditText) findViewById(R.id.dialog_pre_push_password);
    }
    
    private void push(String remote){
        password = ""+etPassword.getText().toString();
        username = ""+etUsername.getText().toString();
        
        if(username.isEmpty() || password.isEmpty()){
            //notify
            return;
        }
        repo.username = username;
        repo.password = password;
        new PushTask(repo, remote, pushAll, forcePush, cb).execute();
        dismiss();
    }
    
    private void getRemotes(){
        List<String> rlist = new ArrayList<>();
        for(String r : repo.git.getRepository().getConfig().getSubsections("remote")){
            rlist.add(r);
        }
        adapter.update(rlist);
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch(id){
            case R.id.dialog_pre_push_cbpushall:
                pushAll = cbPushAll.isChecked();
                break;
            case R.id.dialog_pre_push_cbforcepush:
                forcePush = cbForcePush.isChecked();
                break;
            case R.id.dialog_pre_push_cancel:
                dismiss();
                break;
        }
    }
    
    class RefAdaptor extends BaseAdapter
    {
        List<String> remotes = new ArrayList<>();
        LayoutInflater inf;
        Context ctx;
        
        public RefAdaptor(Context ctx){
            this.ctx = ctx;
            this.inf = LayoutInflater.from(ctx);
        }
        
        public void update(List<String> list){
            remotes = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount(){
            return remotes.size();
        }

        @Override
        public Object getItem(int p1) {
            return remotes.get(p1);
        }

        @Override
        public long getItemId(int p1){
            return p1;
        }

        @Override
        public View getView(int pos, View v, ViewGroup p3)
        {
            String r = remotes.get(pos);
            v = inf.inflate(R.layout.adapteritem_repo_remotes_list, null, false);
            TextView name = v.findViewById(R.id.adapteritem_repo_remotes_list_name);
            name.setText(r);
            v.setTag(r);
            return v;
        }

        @Override
        public CharSequence[] getAutofillOptions()
        {
            // TODO: Implement this method
            return null;
        }
        
        
    }
    
}
