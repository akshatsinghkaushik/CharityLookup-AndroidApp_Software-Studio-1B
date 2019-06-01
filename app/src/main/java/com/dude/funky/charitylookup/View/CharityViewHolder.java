package com.dude.funky.charitylookup.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dude.funky.charitylookup.R;

public class CharityViewHolder extends RecyclerView.ViewHolder {

    private View view;

    TextView textViewName;
    TextView textViewVersion;
    ImageView imageViewIcon;

    CharityViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
    }

    void setName(String Name) {
        textViewName.setText(Name);
    }

}
