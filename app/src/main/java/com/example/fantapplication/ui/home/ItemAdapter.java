package com.example.fantapplication.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fantapplication.PurchaseBottomDialogFragment;
import com.example.fantapplication.R;
import com.example.fantapplication.Retrofit.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.AppViewHolder>{
    private Context mContext;
    private ArrayList<Item> items = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnItemListener mOnItemListener;


    ItemAdapter(ArrayList data, OnItemListener onItemListener){
        items = data;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_layout, parent, false);
        return new AppViewHolder(view, mOnItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        String title = items.get(position).getTitle();
        holder.title.setText(title);

        String description = items.get(position).getDesciption();
        holder.description.setText(description);

        String price = "Price: "+items.get(position).getPrice() + "Kr";
        holder.price.setText(price);

        String id = items.get(position).getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                PurchaseBottomDialogFragment fragment = new PurchaseBottomDialogFragment();
                fragment.setParameters(title, description, price, id);
                fragment.show(activity.getSupportFragmentManager(), "PurchaseBottomDialogFragment");
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.itemsRecyclerView, fragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        CardView cardView;
        TextView title, description, price;

        OnItemListener onItemListener;

        public AppViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            image = itemView.findViewById(R.id.popUpItemImage);
            title = itemView.findViewById(R.id.popUpItemName);
            price = itemView.findViewById(R.id.popUpItemPrice);
            description = itemView.findViewById(R.id.popUpItemDescription);
            cardView = itemView.findViewById(R.id.itemListCard);
            this.onItemListener = onItemListener;

        }

    }

    public interface OnItemListener{
        void onItemClick(int position);
    }

}
