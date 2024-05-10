package com.example.proyectofinal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.proyectofinal.Helper.ManagmentCart;
import com.example.proyectofinal.Helper.PopularDomain;
import com.example.proyectofinal.R;

import java.text.NumberFormat;

public class ProductDescription extends AppCompatActivity {

    private Button addToCartBtn,buyBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, reviewTxt,StorageTxt,RamTxt,GpuTxt, groupTxt;
    private ImageView picItem,backBtn;
    private PopularDomain object;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_description);

        managmentCart = new ManagmentCart(this);
        
        initView();
        getBundle();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getBundle() {
        object = (PopularDomain) getIntent().getSerializableExtra("object");

        // Dentro de tu método getBundle() o donde sea apropiado
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(0); // Establece cero decimales
        String formattedPrice = format.format(object.getPrice());

        titleTxt.setText(object.getTitle());
        feeTxt.setText("$" + formattedPrice);
        descriptionTxt.setText(object.getDescription());
        StorageTxt.setText(object.getStorage());
        RamTxt.setText(object.getRAM());
        GpuTxt.setText(object.getGPU());
        groupTxt.setText(object.getCategoria());
        reviewTxt.setText("("+object.getReview()+" reseñas)");

        // Dentro de tu método onCreate o en cualquier otro lugar apropiado
        Glide.with(this)
                .load(object.getPicUrl()) // Reemplaza "object" con el objeto correcto que contiene la URL de la imagen
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(picItem); // Reemplaza "picItem" con tu ImageView correspondiente

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberinCart(numberOrder);
                managmentCart.insertFood(object);
            }
        });

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDescription.this, LocationMapsActivity.class);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void initView() {
        addToCartBtn=findViewById(R.id.addToCartBtn);
        buyBtn=findViewById(R.id.buyBtn);
        feeTxt=findViewById(R.id.priceTxtDes);
        titleTxt=findViewById(R.id.itemTxtDes);
        StorageTxt = findViewById(R.id.almacenamientoDes);
        RamTxt = findViewById(R.id.ramDes);
        GpuTxt = findViewById(R.id.graficaDes);
        descriptionTxt=findViewById(R.id.descriptionTxtDes);
        picItem=findViewById(R.id.picDescription);
        reviewTxt=findViewById(R.id.ratesTxtDes);
        backBtn = findViewById(R.id.backBtnDes);
        groupTxt = findViewById(R.id.groupTxtDes);

    }
}