package sleepchild.gitblur;
import android.app.*;
import android.widget.*;
import android.view.*;
import sleepchild.gitblur.tasks.*;

public class CommitDialog extends BaseDialog implements View.OnClickListener
{
    EditText etMessage, etAuthor, etAuthorEmail;
    Button btnCommit, btnCancel;
    RepoItem repo;
    TaskCallback cb;
    
    CommitDialog(Activity act, RepoItem repo, TaskCallback cb){
        super(act);
        this.repo = repo;
        this.cb = cb;
        setTitle("Commit Changes");
        setContentView(R.layout.dialog_pre_commit);
        i();
    }
    
    private void i(){
        etMessage = (EditText) findViewById(R.id.dialog_pre_commit_message);
        etAuthor = (EditText) findViewById(R.id.dialog_pre_commit_author);
        etAuthorEmail = (EditText) findViewById(R.id.dialog_pre_commit_author_mail);
        
        btnCommit = (Button) findViewById(R.id.dialog_pre_commit_gocommit);
        btnCommit.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.dialog_pre_commit_cancel);
        btnCancel.setOnClickListener(this);
    }
    
    private void validateAndCommit(){
        String message = etMessage.getText().toString();
        String author = etAuthor.getText().toString();
        String email = etAuthorEmail.getText().toString();
        //
        if(message==null || message.isEmpty()){
            //
            return;
        }
        
        if(author==null || author.isEmpty()){
            new CommitTask(repo,message, author, email, cb).execute();
        }else{
            new CommitTask(repo, message, cb).execute();
        }
        
        dismiss();
        
    }
    
    private void cleanAndExit(){
        
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        switch(id){
            case R.id.dialog_pre_commit_gocommit:
                validateAndCommit();
                break;
            case R.id.dialog_pre_commit_cancel:
                dismiss();
                break;
        }
    }
    
}
