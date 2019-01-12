package com.example.lluqn.mychemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lluqn.mychemo.R;

import java.util.List;

public class AdapterPerfilOp extends RecyclerView.Adapter<AdapterPerfilOp.ViewHolder> {

    private List<Integer> mImage;
    private List<String> mTitulos;
    private LayoutInflater mInflater;
    private Context context;
    private static ClickListener clickListener;

    public AdapterPerfilOp(Context context, List<Integer> mImage, List<String> mTitulos) {
        this.mInflater = LayoutInflater.from(context);
        this.mImage = mImage;
        this.mTitulos = mTitulos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.recycler_opcoes, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int image = mImage.get(i);
        String titulo = mTitulos.get(i);

        viewHolder.myImage.setImageResource(image);
        viewHolder.myText.setText(titulo);
    }

    @Override
    public int getItemCount() {
        return mTitulos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView myImage;
        TextView myText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            myImage = itemView.findViewById(R.id.imagem_item);
            myText = itemView.findViewById(R.id.text_item);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        AdapterPerfilOp.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

}
