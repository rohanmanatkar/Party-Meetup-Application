package com.example.puneet.jashn;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.MyViewHolder> {
    private List<MyEvent> dataModelList;
    private Context mContext;
    MyEvent myEvent;
    private String TAG = "MyEventAdapter";

    public MyEventAdapter(List<MyEvent> list, Context context) {
        Log.d(TAG, "MyEventAdapter: ");
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
            cardView =  itemView.findViewById(R.id.card_view);
            frameLayout = itemView.findViewById(R.id.Layout_frame_home);
        }

//        public void bindData(MyEvent myEvent, Context context) {
//            dateTextView.setText(myEvent.getDate());
//            nameTextView.setText(myEvent.getTitle());
//            detailTextView.setText(myEvent.getDescription());
//
//        }
    }

    @NonNull
    @Override
    public MyEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate out card list item
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        // Return a new view holder
        return new MyEventAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.dateTextView.setText(dataModelList.get(position).getDate());
        holder.detailTextView.setText(dataModelList.get(position).getDescription());
        holder.nameTextView.setText(dataModelList.get(position).getTitle());
        holder.feesTextView.setText(dataModelList.get(position).getFees());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,dataModelList.get(position).getTitle() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext() ,EventLandingActivity.class);
//                Bundle extras =  new Bundle();
//                extras.putString("event_id",dataModelList.get(position).getEventID());
//                extras.putString("event_name",dataModelList.get(position).getEventName());
//                extras.putString("event_details",dataModelList.get(position).getEventDetail());
//                extras.putString("event_date",dataModelList.get(position).getDate());
                Log.d(TAG, "onClick: eventID " +dataModelList.get(position).getEventID());
//                Log.d(TAG, "onClick: eventName " +dataModelList.get(position).getEventName());
                intent.putExtra("event_id",dataModelList.get(position).getEventID());
//                intent.putExtras(extras);
//                intent.putExtra("event_name",dataModelList.get(position).getEventName());
//                intent.putExtra("event_details",dataModelList.get(position).getEventDetail());
//                intent.putExtra("event_date",dataModelList.get(position).getDate());
                holder.cardView.getContext().startActivity(intent);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,dataModelList.get(position).getTitle() , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext() ,EventLandingActivity.class);
//                Bundle extras =  new Bundle();
//                extras.putString("event_id",dataModelList.get(position).getEventID());
//                extras.putString("event_name",dataModelList.get(position).getEventName());
//                extras.putString("event_details",dataModelList.get(position).getEventDetail());
//                extras.putString("event_date",dataModelList.get(position).getDate());
                        Log.d(TAG, "onClick: eventID " +dataModelList.get(position).getEventID());
//                Log.d(TAG, "onClick: eventName " +dataModelList.get(position).getEventName());
                        intent.putExtra("event_id",dataModelList.get(position).getEventID());
//                intent.putExtras(extras);
//                intent.putExtra("event_name",dataModelList.get(position).getEventName());
//                intent.putExtra("event_details",dataModelList.get(position).getEventDetail());
//                intent.putExtra("event_date",dataModelList.get(position).getDate());
                        holder.cardView.getContext().startActivity(intent);

                    }
                });

            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

}
