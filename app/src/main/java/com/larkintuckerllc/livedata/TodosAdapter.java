package com.larkintuckerllc.livedata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



import java.util.List;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.TodoViewHolder> {
    private List<Todo> mTodos;
    Context context;


    public TodosAdapter(Context mcontext, List<Todo> data) {
        this.mTodos=data;
        this.context=mcontext;

    }


    @Override
        public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
            return new TodoViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TodoViewHolder holder, final int position) {
            Todo todo = mTodos.get(position);
            holder.mTvName.setText(todo.getName());
            holder.mTvDate.setText(todo.getcode());
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTodos.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTodos.size();
        }

        class TodoViewHolder extends RecyclerView.ViewHolder {

            private final TextView mTvName;
            private final TextView mTvDate;
             Button del;

            TodoViewHolder(View itemView) {
                super(itemView);
                mTvName = itemView.findViewById(R.id.tvName);
                mTvDate = itemView.findViewById(R.id.tvDate);
                del=itemView.findViewById(R.id.del);
            }

        }
    }


