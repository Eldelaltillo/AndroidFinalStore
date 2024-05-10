package com.example.proyectofinal.Adapters;

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
import com.example.proyectofinal.Helper.PopularDomain;
import com.example.proyectofinal.Activities.ProductDescription;
import com.example.proyectofinal.R;

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
        PopularDomain item = listItemSelected.get(position);
        double price = item.getPrice();
        int quantity = item.getNumberinCart();

        String formattedPrice = formatPrice(price);
        String formattedTotalPrice = formatTotalPrice(price, quantity);

        bindItemData(holder, item, formattedPrice, formattedTotalPrice);
        bindListeners(holder, position);
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public String formatPrice(double price) {
        long roundedPrice = Math.round(price);
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return "$" + formatter.format(roundedPrice);
    }

    public String formatTotalPrice(double price, int quantity) {
        // Calcular el precio total
        double totalPrice = price * quantity;
        // Redondear el precio total al entero más cercano
        long roundedTotalPrice = Math.round(totalPrice);
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return "$" + formatter.format(roundedTotalPrice);
    }

    private void bindItemData(ViewHolder holder, PopularDomain item, String formattedPrice, String formattedTotalPrice) {
        holder.title.setText(item.getTitle());
        holder.StorageTxt.setText(item.getStorage());
        holder.RamTxt.setText(item.getRAM());
        holder.GpuTxt.setText(item.getGPU());
        holder.feeEachItem.setText(formattedPrice);
        holder.totalEachItem.setText(formattedTotalPrice);
        holder.num.setText(String.valueOf(item.getNumberinCart()));
        loadImage(holder, item.getPicUrl());
    }

    private void bindListeners(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> openProductDescription(holder.itemView.getContext(), listItemSelected.get(position)));
        holder.plusItem.setOnClickListener(v -> incrementItem(position));
        holder.minusItem.setOnClickListener(v -> decrementItem(position));
    }

    private void loadImage(ViewHolder holder, String picUrl) {
        Glide.with(holder.itemView.getContext())
                .load(picUrl)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.pic);
    }

    private void openProductDescription(Context context, PopularDomain item) {
        Intent intent = new Intent(context, ProductDescription.class);
        intent.putExtra("object", item);
        context.startActivity(intent);
    }

    private void incrementItem(int position) {
        managmentCart.plusNumberItem(listItemSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        });
    }

    private void decrementItem(int position) {
        managmentCart.minusNumberItem(listItemSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,feeEachItem,plusItem,minusItem,StorageTxt,RamTxt,GpuTxt;
        ImageView pic;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.titleTxt);
            StorageTxt = itemView.findViewById(R.id.almacenamientoC);
            RamTxt = itemView.findViewById(R.id.ramC);
            GpuTxt = itemView.findViewById(R.id.graficaC);
            pic=itemView.findViewById(R.id.pic);
            feeEachItem=itemView.findViewById(R.id.feeEachItem);
            totalEachItem=itemView.findViewById(R.id.totalEachItem);
            plusItem=itemView.findViewById(R.id.plusCardBtn);
            minusItem=itemView.findViewById(R.id.minusCartBtn);
            num=itemView.findViewById(R.id.numberItemTxt);
        }
    }
}
