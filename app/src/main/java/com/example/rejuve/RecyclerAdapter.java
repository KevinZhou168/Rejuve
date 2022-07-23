package com.example.rejuve;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Paladin> paladinsList;

    public recyclerAdapter(ArrayList<Paladin> paladinsList) {
        this.paladinsList = paladinsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView rankTV, nameTV, pointsTV;

        public MyViewHolder(final View v) {
            super(v);
            rankTV = v.findViewById(R.id.leaderboardRankTV);
            nameTV = v.findViewById(R.id.leaderboardNameTV);
            pointsTV = v.findViewById(R.id.leaderboardPointsTV);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        int rankNum = position + 1;
        String rank = rankNum + ".";
        String name = paladinsList.get(position).getName();
        int pointsNum = paladinsList.get(position).getPoints();
        String points = pointsNum + "pts";
        holder.rankTV.setText(rank);
        holder.nameTV.setText(name);
        holder.pointsTV.setText(points);
    }

    @Override
    public int getItemCount() {
        return paladinsList.size();
    }
}

