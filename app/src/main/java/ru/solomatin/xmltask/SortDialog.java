package ru.solomatin.xmltask;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Диалог для выбора типа сортировки
 */
public class SortDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(style, theme);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.sort_title)
                .setItems(R.array.sort_types, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity) getActivity()).sortProductList(which);
                        dialog.dismiss();
                    }
                })
                .setCancelable(true);
        return builder.create();
    }

}
