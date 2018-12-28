package com.example.puneet.jashn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    public List<Event> list2;

    private List<Category> dataModelList;
    private Context mContext;
    Category category;

    public CategoryAdapter(List<Category> list, Context context) {
        dataModelList = list;
        mContext = context;
    }


    // View holder class whose objects represent each list item

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView nameTextView;
        public TextView detailTextView;
        public TextView feesTextView;
        public CardView cardView;
        public FrameLayout frameLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.event_photo);
            nameTextView = itemView.findViewById(R.id.event_name);
            detailTextView = itemView.findViewById(R.id.event_detail);
            feesTextView = itemView.findViewById(R.id.event_fees);
            cardView = itemView.findViewById(R.id.card_view);
            frameLayout = itemView.findViewById(R.id.Layout_frame_home);
        }

//        public void bindData(Category category, Context context) {
//            dateTextView.setText(category.getDate());
//            nameTextView.setText(category.getEventName());
//            detailTextView.setText(category.getEventDetail());
//
//        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate out card list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        // Return a new view holder

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        // Bind data for the item at position
//        holder.bindData(dataModelList.get(position), mContext);
        holder.dateTextView.setText(dataModelList.get(position).getDate());
        holder.detailTextView.setText(dataModelList.get(position).getEventDetail());
        holder.nameTextView.setText(dataModelList.get(position).getEventName());
        holder.feesTextView.setText(dataModelList.get(position).getFees());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list2 = new ArrayList<>();
                Event details =new Event();
                details.setEventID(dataModelList.get(position).getEventID());
                details.setEventName(dataModelList.get(position).getEventName());
                details.setDate(dataModelList.get(position).getDate());
                details.setTime(dataModelList.get(position).getTime());
                details.setLocation(dataModelList.get(position).getVenue());
                details.setEntryFee(dataModelList.get(position).getFees());
                details.setDescription(dataModelList.get(position).getEventDetail());
                details.setMinAge(dataModelList.get(position).getMinAge());
                details.setGuests(dataModelList.get(position).getGuests());




                list2.add(details);
                Toast.makeText(mContext,dataModelList.get(position).getEventName() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext() ,InvitationActivity.class);
//                intent.putExtra("event_name",dataModelList.get(position).getEventName());
////                intent.putExtra("event_details",dataModelList.get(position).getEventDetail());
////                intent.putExtra("event_date",dataModelList.get(position).getDate());


                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("categoryList", (ArrayList<? extends Parcelable>) list2);
                intent.putExtras(bundle);
                holder.cardView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        // Return the total number of items
        return dataModelList.size();
    }
}