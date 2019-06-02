package com.dude.funky.charitylookup.View.ViewBooking;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dude.funky.charitylookup.R;

public class BookingViewHolder extends RecyclerView.ViewHolder {

    private View view;

    TextView textViewName;
    TextView textViewVersion;


    BookingViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);

    }

    void setName(String Name) {
        textViewName.setText(Name);
    }

}
