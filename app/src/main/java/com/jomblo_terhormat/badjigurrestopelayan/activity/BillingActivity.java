package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.BillingRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.List;

public class BillingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        Bundle bundle = getIntent().getExtras();
        List<Produk> produks = bundle.getParcelableArrayList("produks");

        BillingRecycleAdapter billingRecycleAdapter =
                new BillingRecycleAdapter(this, produks);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvBilling);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(billingRecycleAdapter);

        TextView sub = (TextView) findViewById(R.id.sub);
        sub.setText(Produk.formatter("" + hitungSub(produks)));

        TextView ppn = (TextView) findViewById(R.id.ppn);
        ppn.setText(Produk.formatter("" + ((int) (hitungSub(produks) * 0.1))));

        TextView grand = (TextView) findViewById(R.id.grand);
        grand.setText(Produk.formatter("" + (((int) (hitungSub(produks) * 0.1)) + hitungSub(produks))));

        Button ask = (Button) findViewById(R.id.ask);
        ask.setOnClickListener(new IntentToast(this, "Billing Anda sedang disiapkan, silakan tunggu", FeedBackActivity.class));


    }

    private int hitungSub(List<Produk> produks) {
        int sub = 0;
        for (Produk produk : produks) {
            sub += produk.getHarga_jual() * produk.getmQty();
        }
        return sub;
    }

    private class IntentToast implements View.OnClickListener {

        private Context mContext;
        private String mToast;
        private Class mClass;


        public IntentToast(Context mContext, String mToast, Class mClass) {
            this.mContext = mContext;
            this.mToast = mToast;
            this.mClass = mClass;
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(mContext, mToast, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, mClass));
        }
    }


}
