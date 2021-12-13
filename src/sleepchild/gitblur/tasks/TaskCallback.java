package sleepchild.gitblur.tasks;

public interface TaskCallback
{
    public void onTaskComplete(Object task, String message);
    public void onTaskFailed(Object task, String message);
    public void onAuthenticationRequiredForTask(Object task);
}
