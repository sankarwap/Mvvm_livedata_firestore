package com.larkintuckerllc.livedata;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class dataActivity extends AppCompatActivity {
    TodosAdapter mTodosAdapter;
    private TodosViewModel mTodosViewModel;
    private List<Todo> mTodos;
    private ProgressDialog dialog;
    public FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        db = FirebaseFirestore.getInstance();


        dialog = new ProgressDialog(dataActivity.this);
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
        final RecyclerView todosRecyclerView = findViewById(R.id.rvTodos);
        todosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTodosViewModel = ViewModelProviders.of(this).get(TodosViewModel.class);
        final Observer<List<Todo>> todosObserver = new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable final List<Todo> todos) {
                dialog.dismiss();
                if (todos == null) {
                    Toast.makeText(dataActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                } else if (mTodos == null) {
                    mTodos = todos;
                    mTodosAdapter = new TodosAdapter(getApplicationContext(), mTodos);
                    todosRecyclerView.setAdapter(mTodosAdapter);
                }
            }
        };
        mTodosViewModel.getTodos().observe(this, todosObserver);
    }


}
