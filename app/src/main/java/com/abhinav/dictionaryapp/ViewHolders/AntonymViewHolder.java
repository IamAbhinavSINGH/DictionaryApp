package com.abhinav.dictionaryapp.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhinav.dictionaryapp.R;

public class AntonymViewHolder extends RecyclerView.ViewHolder {

    public TextView tvAntonyms;
    public AntonymViewHolder(@NonNull View itemView) {
        super(itemView);

        tvAntonyms = itemView.findViewById(R.id.tvAntonyms);
    }
}
