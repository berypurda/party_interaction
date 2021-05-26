package com.example.partyinteraction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> implements Filterable {

    private ArrayList<Customer> mCustomerData;
    private ArrayList<Customer> mCustormerDataAll;
    private Context mContext;
    private int lastPosition = -1;

    CustomerAdapter(Context context, ArrayList<Customer> Data){
        this.mCustomerData = Data;
        this.mCustormerDataAll = Data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomerAdapter.ViewHolder holder, int position) {
        Customer currentItem = mCustomerData.get(position);

        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mCustomerData.size();
    }

    @Override
    public Filter getFilter() {
        return CustomerFilter;
    }

    private Filter CustomerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Customer> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();
            if(constraint == null || constraint.length() == 0){
                results.count = mCustormerDataAll.size();
                results.values = mCustormerDataAll;
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Customer item : mCustormerDataAll){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            results.count = filteredList.size();
            results.values = filteredList;


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mCustomerData = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private Button name;
        private TextView info;
        private TextView gender;
        private ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            info = itemView.findViewById(R.id.description);
            gender = itemView.findViewById(R.id.gender);
            image = itemView.findViewById(R.id.picture);

        }

        public void bindTo(@NonNull Customer currentItem) {
            name.setText(currentItem.getName());
            info.setText(currentItem.getInfo());
            gender.setText(currentItem.getGender());

            Glide.with(mContext).load(currentItem.getImageresource()).into(image);
        }
    }
}

