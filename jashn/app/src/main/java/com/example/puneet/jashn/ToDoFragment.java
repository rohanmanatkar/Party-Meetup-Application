package com.example.puneet.jashn;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puneet.jashn.Utils.Constants;
import com.example.puneet.jashn.Utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import static java.lang.Boolean.TRUE;

public class ToDoFragment extends Fragment {

    private DatabaseReference tReference, reference_2;
    FirebaseAuth todoAuth;

    private String tId;
    private EditText tToDoInput;
    private String todoItemText;
    private TodoAdapter tAdapter;
    private String eventName;
    private String userName;
    private CheckBox checkBox;
    private Boolean checkbox_checked;
    private Bundle bundle;
    private static final String TAG = "ToDOActivity";

    public static ToDoFragment newInstance() { return new ToDoFragment(); }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupConnection();

        bundle = this.getArguments();
        if (bundle != null) {
            eventName = bundle.getString("EVENT_NAME");
            userName = bundle.getString("USER_NAME");
        }
        else {
            eventName = "DefaultEvent";
            userName = "DefaultUser";
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);

        tToDoInput = (EditText) view.findViewById(R.id.todo_input);

        tToDoInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                todoItemText = tToDoInput.getText().toString();
                checkbox_checked = false;

                //tId = tReference.push().getKey();
                TodoData todoEntry = new TodoData(eventName, userName, todoItemText, checkbox_checked);

                tReference.child("todo").push().setValue(todoEntry);

                closeAndClean();
                return true;
            }
        });

        /*checkBox = (CheckBox) view.findViewById(R.id.CheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (checkBox.isChecked()) {
                    checkbox_checked = true;
                }
                else checkbox_checked = false;
            }
        });*/

        RecyclerView todoItems = (RecyclerView) view.findViewById(R.id.todoListItems);
        todoItems.setLayoutManager(new LinearLayoutManager(getContext()));

        tAdapter = new TodoAdapter();
        todoItems.setAdapter(tAdapter);

        /*checkBox = (CheckBox) view.findViewById(R.id.CheckBox);
        if (checkBox.isChecked()) { checkbox_checked = true; }
        else { checkbox_checked = false; }*/

        return view;
    }

    private void closeAndClean() {
        Utils.closeKeyboard(getContext(), tToDoInput);
        tToDoInput.setText("");
    }

    private void setupConnection() {
        todoAuth = FirebaseAuth.getInstance();
        tReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = todoAuth.getCurrentUser();
        //final String userID = user.getUid();
        reference_2 = tReference.child("todo");

        reference_2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tododataSnapshot) {
                Log.d(Constants.LOG_TAG,"SUCCESS!");
                handleReturn(tododataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(Constants.LOG_TAG,"ERROR: " + databaseError.getMessage());
                Toast.makeText(getContext(), R.string.chat_init_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleReturn(DataSnapshot tododataSnapshot) {
        tAdapter.clearData();

        for(DataSnapshot todoitem : tododataSnapshot.getChildren()) {
            TodoData tododata = todoitem.getValue(TodoData.class);
            String eName = tododata.getEventName();
            if( eName.equals(eventName))
                tAdapter.addData(tododata);
        }
        tAdapter.notifyDataSetChanged();
    }
}