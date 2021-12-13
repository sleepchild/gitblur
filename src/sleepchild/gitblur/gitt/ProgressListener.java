package sleepchild.gitblur.gitt;

public interface ProgressListener
{
    public static final int REASON_COMPLETE = 1;
    public static final int REASON_ERROR = 2;
    
    public void onStart();
    public void onStop(int reason);
    public void onProgress(int proggress);
}
