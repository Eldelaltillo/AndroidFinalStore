package com.example.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.proyectofinal.Helper.ChangeNumberItemsListener;
import com.example.proyectofinal.Helper.ManagmentCart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    ArrayList<PopularDomain>listItemSelected;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;


    public CartListAdapter(ArrayList<PopularDomain> listItemSelected,Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemSelected = listItemSelected;
        managmentCart=new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        // Obtener el precio del producto
        double price = listItemSelected.get(position).getPrice();

        // Crear un formateador de números con separador de miles y sin decimales
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        formatter.setMaximumFractionDigits(0); // Establecer cero decimales
        String formattedPrice = formatter.format(price);

        // Obtener el precio y la cantidad del producto
        int quantity = listItemSelected.get(position).getNumberinCart();

        // Calcular el precio total
        double totalPrice = price * quantity;

        // Formatear el precio total con separador de miles y sin decimales
        String formattedTotalPrice = formatter.format(totalPrice);

        holder.title.setText(listItemSelected.get(position).getTitle());
        holder.feeEachItem.setText("$" + formattedPrice);
        holder.totalEachItem.setText("$" + formattedTotalPrice);
        holder.num.setText(String.valueOf(listItemSelected.get(position).getNumberinCart()));

/*
        int drawableResourceId=holder.itemView.getContext().getResources()
                .getIdentifier(listItemSelected.get(position).getPicUrl(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,30,30))
                .into(holder.pic);*/

        Glide.with(holder.itemView.getContext())
                .load(listItemSelected.get(position).getPicUrl()) // Obtiene la URL de la imagen mediante getPicUrl()
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.pic);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(),ProductDescription.class); //cambiar a la vista de los detalles del producto
            intent.putExtra("object",listItemSelected.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

        holder.plusItem.setOnClickListener(v -> {
            managmentCart.plusNumberItem(listItemSelected, position, new ChangeNumberItemsListener() {
                @Override
                public void change() {
                    notifyDataSetChanged();
                    changeNumberItemsListener.change();
                }
            });

        });

        holder.minusItem.setOnClickListener(v -> {
            managmentCart.minusNumberItem(listItemSelected, position, new ChangeNumberItemsListener() {
                @Override
                public void change() {
                    notifyDataSetChanged();
                    changeNumberItemsListener.change();
                }
            });

        });


    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,feeEachItem,plusItem,minusItem;
        ImageView pic;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.titleTxt);
            pic=itemView.findViewById(R.id.pic);
            feeEachItem=itemView.findViewById(R.id.feeEachItem);
            totalEachItem=itemView.findViewById(R.id.totalEachItem);
            plusItem=itemView.findViewById(R.id.plusCardBtn);
            minusItem=itemView.findViewById(R.id.minusCartBtn);
            num=itemView.findViewById(R.id.numberItemTxt);
        }
    }
}
