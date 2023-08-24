package com.abhinav.dictionaryapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhinav.dictionaryapp.Models.Definitions;
import com.abhinav.dictionaryapp.R;
import com.abhinav.dictionaryapp.ViewHolders.AntonymViewHolder;

import java.util.List;

public class AntonymAdapter extends RecyclerView.Adapter<AntonymViewHolder> {

    private final Context context;
    private final List<String> antonymsList;

    public AntonymAdapter(Context context, List<String> antonymsList) {
        this.context = context;
        this.antonymsList = antonymsList;
    }

    @NonNull
    @Override
    public AntonymViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AntonymViewHolder(LayoutInflater.from(context).inflate(R.layout.antonyms_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AntonymViewHolder holder, int position) {
            holder.tvAntonyms.setText(antonymsList.get(position));
    }

    @Override
    public int getItemCount() {
        return antonymsList.size();
    }
}
