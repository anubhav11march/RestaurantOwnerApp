package com.anubhav11march.foodorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Orders extends AppCompatActivity {
    private RecyclerView foodlist;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        foodlist = (RecyclerView) findViewById(R.id.orderrr);
        foodlist.setHasFixedSize(true);
        foodlist.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter <orde, OrderViewHolder> FBRA = new FirebaseRecyclerAdapter<orde, OrderViewHolder>(
                orde.class,
                R.layout.singleorder,
                OrderViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, orde model, int position) {
                viewHolder.setItemname(model.getItemname());
                viewHolder.setUsername(model.getUsername());
            }
        };
        foodlist.setAdapter(FBRA);
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{
        View OrderView;
        public OrderViewHolder(View itemView){
            super(itemView);
            OrderView = itemView;
        }

        public void setUsername(String username){
            TextView usernameee = (TextView) OrderView.findViewById(R.id.foodDesc);
            usernameee.setText(username);
        }

        public void setItemname(String itemname){
            TextView item = (TextView) OrderView.findViewById(R.id.foodName);
            item.setText(itemname);
        }
    }
}
