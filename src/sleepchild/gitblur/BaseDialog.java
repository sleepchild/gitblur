package sleepchild.gitblur;
import android.app.*;
import android.view.*;
import java.util.*;

public class BaseDialog extends Dialog
{
    BaseDialog(Activity act){
        super(act);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        // TODO: Implement this method
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId)
    {
        // TODO: Implement this method
    }
}
