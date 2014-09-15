package it.tiwiz.tdb.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * This class is the basic skeleton for all the Dialogs,
 * providing {@link it.tiwiz.tdb.dialogs.GenericDialog.Callbacks} interfaces for {@link android.app.Activity}
 * to implement and various utility methods and internal objects.
 *
 * @see #getValueFrom(android.os.Bundle, String, Object)
 * @see #getButtonClickListener()
 */
public class GenericDialog extends DialogFragment{

    public interface Callbacks {
        public void onPositiveButtonCLick(DialogInterface dialog, String dialogTag);
        public void onNegativeButtonClick(DialogInterface dialog, String dialogTag);
    }

    public GenericDialog() {
        //empty constructor needed for initialization
    }

    /**
     * This generic method will extract any value from the input {@link android.os.Bundle}
     * providing the caller with a {@code default value} whenever the requested object for the given key
     * cannot be retrieved.
     */
    protected <T> T getValueFrom (Bundle inputBundle, String key, T defaultValue) {

        if(inputBundle != null && inputBundle.containsKey(key)) {
            return (T) inputBundle.get(key);
        } else {
            return defaultValue;
        }
    }

    private void onPositiveButtonClick() {
        if (isValidContext()) {
            ((Callbacks)getActivity()).onPositiveButtonCLick(getDialog(), getTag());
        }
    }

    private void onNegativeButtonClick() {
        if (isValidContext()) {
            ((Callbacks)getActivity()).onNegativeButtonClick(getDialog(), getTag());
        }
    }

    private boolean isValidContext() {
        return (getActivity() != null && getActivity() instanceof Callbacks);
    }

    private final DialogInterface.OnClickListener mButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    onPositiveButtonClick();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    onNegativeButtonClick();
                    break;
            }
        }
    };

    protected DialogInterface.OnClickListener getButtonClickListener() {
        return mButtonClickListener;
    }
}
