package com.chalo.spacexships.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chalo.spacexships.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalBottomSheet extends BottomSheetDialogFragment {
    public static final String TAG = "ModalBottomSheet";
    private final String port;
    private final String buildYear;
    private final String shipType;
    private final String imageUrl;

    public ModalBottomSheet(String port, String buildYear, String shipType, String imageUrl) {
        this.port = port;
        this.buildYear = buildYear;
        this.shipType = shipType;
        this.imageUrl = imageUrl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false);
        // Initialize views
        TextView homePortTxt = view.findViewById(R.id.portTxt);
        TextView yearBuiltTxt = view.findViewById(R.id.yearTxt);
        TextView typeTxt = view.findViewById(R.id.typeTxt);
        ImageView imageView = view.findViewById(R.id.imageView);

        //set text
        homePortTxt.setText(port);
        if( buildYear.equals("null")){
            yearBuiltTxt.setText(R.string.not_specified);
        }
        else{
            yearBuiltTxt.setText(buildYear);
        }

        typeTxt.setText(shipType);
        // set image to image view using Glide library
        if( imageUrl != null){
            Glide.with(this).load(imageUrl).into(imageView);
        }

        return view;
    }
}
