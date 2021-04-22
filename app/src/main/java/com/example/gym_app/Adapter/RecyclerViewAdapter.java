package com.example.gym_app.Adapter;


import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym_app.Interface.ItemClickListener;
import com.example.gym_app.Model.Excercise;
import com.example.gym_app.R;
import com.example.gym_app.ViewExercise;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public GifImageView image;
    public TextView text;
    public TextView opis;

    private ItemClickListener itemClickListener;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        image = (GifImageView) itemView.findViewById(R.id.ex_img);
        text = (TextView) itemView.findViewById(R.id.ex_name);
        opis = (TextView) itemView.findViewById(R.id.text_opis);

        itemView.setOnClickListener(this);
    }
      public void  setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;

    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition());

    }
}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<Excercise> excerciseList;
    private Context context;

    public RecyclerViewAdapter(List<Excercise> excerciseList,Context context) {
        this.excerciseList = excerciseList;
        this.context=context;
    }

    //public RecyclerViewAdapter(List<Excercise> excerciseList, Context baseContext) {
      //  this.excerciseList = excerciseList;
        //this.context=context;

    //}

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.item_excercise,parent,false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.image.setImageResource(excerciseList.get(position).getImage_id());
        holder.text.setText(excerciseList.get(position).getName());
        

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(context, ViewExercise.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("image_id",excerciseList.get(position).getImage_id());
                intent.putExtra("name",excerciseList.get(position).getName());
                intent.putExtra("opiss",excerciseList.get(position).getOpis());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return excerciseList.size();
    }
}
