package myh.simpleaccounting.Util;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    //use it from Activity: Utils.showToast("Message", this);
    //from Fragment: Utils.showToast("Message", getActivity());
    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
