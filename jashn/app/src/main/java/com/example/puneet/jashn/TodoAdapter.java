package com.example.puneet.jashn;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    static final class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView todoItem;

        TodoViewHolder(View todoview) {
            super(todoview);

            todoItem = (TextView) todoview.findViewById(R.id.todo_item_message);
        }
    }

    private List<TodoData> todoContent = new ArrayList<>();

    public void clearData() {
        todoContent .clear();
    }

    public void addData(TodoData tododata) {
        todoContent.add(tododata);
    }

    @Override
    public int getItemCount() {
        return todoContent.size();
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_todo_item, parent, false);
        return new TodoViewHolder(root);
    }

    @Override
    public void onBindViewHolder(TodoAdapter.TodoViewHolder holder, int position) {
        TodoData tododata = todoContent.get(position);
        holder.todoItem.setText(tododata.getTodoItem());
    }
}
