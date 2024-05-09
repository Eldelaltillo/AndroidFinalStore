package com.example.proyectofinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.proyectofinal.Helper.ManagmentCart;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PopularListAdapter extends RecyclerView.Adapter<PopularListAdapter.Viewholder> {



    /*
    //Codigo base de datos en la nube con API

    private Context mCtx;
    private List<PopularDomain> productList;

    public PopularListAdapter(Context mCtx, List<PopularDomain> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_main, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        PopularDomain product = productList.get(position);

        // Loading the image
        Glide.with(mCtx)
                .load(product.getPicUrl())
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.imageView);

        holder.textViewTitle.setText(product.getTitle());
        holder.textViewRating.setText(String.valueOf(product.getScore()));
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        holder.textViewStorage.setText(product.getStorage());
        holder.textViewRAM.setText(product.getRAM());
        holder.textViewGPU.setText(product.getGPU());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice, textViewStorage, textViewRAM, textViewGPU;
        ImageView imageView;

        public Viewholder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.nombreProducto);
            textViewRating = itemView.findViewById(R.id.descuentoProducto);
            textViewPrice = itemView.findViewById(R.id.precioProducto);
            textViewStorage = itemView.findViewById(R.id.almacenamiento);
            textViewRAM = itemView.findViewById(R.id.ram);
            textViewGPU = itemView.findViewById(R.id.grafica);
            imageView = itemView.findViewById(R.id.imagenProducto);
        }
    }*/

// Aca se separan ambos codigos



    //Codigo sin base de datos

    ArrayList<PopularDomain> items;
    Context context;

    public PopularListAdapter(ArrayList<PopularDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_main,parent,false);
        context=parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        formatter.setMaximumFractionDigits(0); // Establece cero decimales

        String formattedPrice = formatter.format(items.get(position).getPrice());

        double score = items.get(position).getScore();
        DecimalFormat decimalFormat = new DecimalFormat("#");
        String formattedScore = decimalFormat.format(score);



        holder.titleTxt.setText(items.get(position).getTitle());
        holder.feeTxt.setText("$" + formattedPrice);
        holder.ScoreTxt.setText(formattedScore + "%");
        holder.StorageTxt.setText(""+items.get(position).getStorage());
        holder.RamTxt.setText(""+items.get(position).getRAM());
        holder.GpuTxt.setText(""+items.get(position).getGPU());

        /*
        int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getPicUrl(),
                "drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId).transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.pic);*/

        Glide.with(holder.itemView.getContext())
                .load(items.get(position).getPicUrl()) // Obtiene la URL de la imagen mediante getPicUrl()
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(),ProductDescription.class); //cambiar a la vista de los detalles del producto
            intent.putExtra("object",items.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

        // Configurar clic para el layout contenedor
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();

                // Verifica si la posición es válida
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Obtén el PopularDomain seleccionado basado en la posición en el adaptador
                    PopularDomain selectedItem = items.get(position);

                    // Crea una instancia de ManagmentCart utilizando el contexto
                    ManagmentCart managementCart = new ManagmentCart(context);

                    // Inserta el PopularDomain seleccionado en el carrito
                    managementCart.insertFood(selectedItem);

                }


                // Obtén una referencia al BottomNavigationView que contiene tu menú
                BottomNavigationView bottomNavigationView = ((Activity) holder.itemView.getContext()).findViewById(R.id.bottomNavigationView);

                // Simula un clic en el elemento del menú con el ID "carrito"
                bottomNavigationView.setSelectedItemId(R.id.carrito);

                // Configura el tinte del icono del elemento del menú "Carrito"
                MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.carrito);
                if (menuItem != null) {
                    Drawable icon = menuItem.getIcon();
                    if (icon != null) {
                        icon.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.registro_color), PorterDuff.Mode.SRC_IN);
                        menuItem.setIcon(icon);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt,StorageTxt,RamTxt,GpuTxt,feeTxt,ScoreTxt;
        ImageView pic;
        LinearLayout itemLayout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            titleTxt = itemView.findViewById(R.id.nombreProducto);
            StorageTxt = itemView.findViewById(R.id.almacenamiento);
            RamTxt = itemView.findViewById(R.id.ram);
            GpuTxt = itemView.findViewById(R.id.grafica);
            feeTxt = itemView.findViewById(R.id.precioProducto);
            ScoreTxt = itemView.findViewById(R.id.descuentoProducto);
            pic = itemView.findViewById(R.id.imagenProducto);
            itemLayout = itemView.findViewById(R.id.carritoLO);


        }
    }
}
