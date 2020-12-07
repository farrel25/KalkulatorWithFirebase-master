package com.example.utskalkulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class customadapter extends RecyclerView.Adapter<customadapter.MyviewHolder>{

    private Context context;
    private ArrayList id,nomer1,nomer2,sum,simbol;

    customadapter(Context context,ArrayList iddb,ArrayList number1,ArrayList symbol,ArrayList number2,ArrayList hasil)
    {

        this.context=context;
        this.id=iddb;
        this.nomer1=number1;
        this.simbol=symbol;
        this.nomer2=number2;
        this.sum=hasil;


    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.historylayout,parent,false);
        return  new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customadapter.MyviewHolder holder, int position) {

        holder.idtext.setText(String.valueOf(id.get(position)));
        holder.no1.setText(String.valueOf(nomer1.get(position)));
        holder.simbul.setText(String.valueOf(simbol.get(position)));
        holder.no2.setText(String.valueOf(nomer2.get(position)));
        holder.count.setText(String.valueOf(sum.get(position)));


    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyviewHolder extends  RecyclerView.ViewHolder {

        TextView idtext,no1,count,no2,simbul;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            idtext =itemView.findViewById(R.id.idnya);
            no1 = itemView.findViewById(R.id.angka1);
            simbul = itemView.findViewById(R.id.perhitungan);
            no2 = itemView.findViewById(R.id.angka2);
            count = itemView.findViewById(R.id.total);

        }
    }
}
