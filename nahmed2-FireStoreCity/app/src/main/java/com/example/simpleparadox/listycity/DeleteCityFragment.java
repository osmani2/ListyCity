package com.example.simpleparadox.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

//Long click on item in listview calls this fragment
//Users can either delete or not

public class DeleteCityFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;

    private int position;

    public interface OnFragmentInteractionListener{
        void onDeletePressed(final int position);
    }

    /* Keeps track of index of experiment object to be deleted/edited */
    public DeleteCityFragment(int position) {
        this.position=position;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    +" must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.delete_city_layout,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Delete City")

                .setNegativeButton("Cancel",null)
                //exit fragment

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDeletePressed(position);
                    }
                })
                .create();
    }
}
