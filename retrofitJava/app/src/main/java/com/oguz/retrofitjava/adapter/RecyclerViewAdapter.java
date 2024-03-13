package com.oguz.retrofitjava.adapter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oguz.retrofitjava.R;
import com.oguz.retrofitjava.model.CryptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<CryptoModel> cryptoList;

    private String[] colors = {"#a3ff00","#ff00aa","#b4a7d6","#a4c2f4","#8ee5ee","#cd950c","#f5f5f5","#f47932"};

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoList) {
        this.cryptoList = cryptoList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(cryptoList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public static class RowHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textPrice;

        public RowHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void bind(CryptoModel cryptoModel,String[] colors, Integer position) {
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]));
            textName = itemView.findViewById(R.id.text_name);
            textPrice = itemView.findViewById(R.id.text_price);
            textName.setText(cryptoModel.currency);
            textPrice.setText(cryptoModel.price);

        }


    }
}
