package com.example.proyectofinal.Helper;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tiniDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tiniDB=new TinyDB(context);
    }

    public void insertFood(PopularDomain item) {
        ArrayList<PopularDomain> listPop = getListCart();
        boolean existAlready = false;

        // Itera sobre los elementos actuales en el carrito para verificar si el producto ya existe
        for (PopularDomain currentItem : listPop) {
            if (currentItem.getTitle().equals(item.getTitle())) {
                // Si el producto ya está en el carrito, incrementa su cantidad en 1
                currentItem.setNumberinCart(currentItem.getNumberinCart() + 1);
                existAlready = true;
                break;
            }
        }

        // Si el producto no está en el carrito, agrégalo con una cantidad de 1
        if (!existAlready) {
            item.setNumberinCart(1);
            listPop.add(item);
        }

        // Guarda la lista actualizada en SharedPreferences
        tiniDB.putListObject("CartList", listPop);

        Toast.makeText(context, "Añadido al carrito", Toast.LENGTH_SHORT).show();
    }


    public ArrayList<PopularDomain> getListCart() {
        return tiniDB.getListObject("CartList");
    }

    public void minusNumberItem(ArrayList<PopularDomain>listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        if(listItem.get(position).getNumberinCart()==1){
            listItem.remove(position);
        }else {
            listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart()-1);
        }
        tiniDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }

    public void plusNumberItem(ArrayList<PopularDomain>listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart()+1);
        tiniDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }
    
    public Double getTotalFee(){
        ArrayList<PopularDomain> listItem=getListCart();
        double fee=0;
        for (int i = 0; i < listItem.size(); i++) {
            fee = fee + (listItem.get(i).getPrice()*listItem.get(i).getNumberinCart());
        }
        return fee;
    }
    public void clearCart() {
        tiniDB.remove("CartList");
        Toast.makeText(context, "Carrito vaciado", Toast.LENGTH_SHORT).show();
    }
}
