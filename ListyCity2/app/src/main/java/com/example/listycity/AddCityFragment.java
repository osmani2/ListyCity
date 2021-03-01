package com.example.listycity;

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

public class AddCityFragment extends DialogFragment {
    private EditText cityName;
    private EditText provinceName;
    private OnFragmentInteractionListener listener;

    //Save city and position for editing city
    private City cityObj;
    private int position;

    public interface OnFragmentInteractionListener{
        void onOkPressed(City newCity,Boolean isNew,int position);
    }

    //For new cities
    public AddCityFragment() {
        this.cityObj=null;
        this.position=-1;
    }

    //For existing cities
    public AddCityFragment(City city, int position) {
        this.cityObj = city;
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_city_layout,null);
        cityName = view.findViewById(R.id.city_name_editText);
        provinceName = view.findViewById(R.id.province_name_editText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/Edit City")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String city = cityName.getText().toString();
                        String province = provinceName.getText().toString();

                        //Check if editing a city or not
                        if(cityObj!=null){
                            //City exists, we want to edit it
                            listener.onOkPressed(new City(city, province),false,position);
                        }else {
                            //Making a new city object
                            listener.onOkPressed(new City(city, province), true, position);
                        }
                    }
                }).create();
    }
}
