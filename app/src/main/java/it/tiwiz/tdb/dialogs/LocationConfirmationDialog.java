package it.tiwiz.tdb.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import it.tiwiz.tdb.R;

/**
 * This is a simple Dialog for requesting a confirmation that the
 * retrieved user location is actually correct.
 */
public class LocationConfirmationDialog extends GenericDialog{

    private String mLocation = null;

    private final static String LOCATION_KEY = "location";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LOCATION_KEY, mLocation);
    }

    public LocationConfirmationDialog setLocation (String location) {
        mLocation = location;
        return this;
    }

    private String extractLocationStringFrom (Bundle inputBundle) {
        return getValueFrom(inputBundle, LOCATION_KEY, null);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mLocation = extractLocationStringFrom(savedInstanceState);
        }

        if (mLocation == null) {
            throw new IllegalArgumentException("Location shall not be null!");
        }

        final String message = getString(R.string.location_confirmation_dialog_message, mLocation);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.location_confirmation_dialog_title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.location_confirmation_dialog_positive_button_label, getButtonClickListener())
                .setNegativeButton(R.string.location_confirmation_dialog_negative_button_label, getButtonClickListener());

        return builder.create();
    }
}
