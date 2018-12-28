package com.example.puneet.jashn;

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
import android.widget.EditText;
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

public class GroupchatFragment extends Fragment {

    private DatabaseReference mReference, reference_1;
    FirebaseAuth chatAuth;

    private String mId;
    private EditText mChatInput;
    private String timeNow;
    private String messageText;
    private ChatAdapter mAdapter;
    private static final String TAG = "GroupChatActivity";
    //private Bundle bundle;
    private String eventName;
    private String uName;

    public static GroupchatFragment newInstance() { return new GroupchatFragment(); }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupConnection();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            eventName = bundle.getString("EVENT_NAME");
            Log.d("rohan", eventName);Log.d("rohan", uName);
            uName = bundle.getString("USER_NAME");
        }
        else {
            eventName = "Event";
            uName = "Puneer";
        }
        Log.d(TAG, "onCreate: rohan name" + eventName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_groupchat, container, false);


        mChatInput = (EditText) root.findViewById(R.id.chat_input);

        mChatInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                messageText = mChatInput.getText().toString();
                timeNow = String.valueOf(new Date().getTime());

                ChatData messageEntry = new ChatData(eventName, uName, messageText, timeNow);

                mReference.child("chat").push().setValue(messageEntry);

                closeAndClean();
                return true;
            }
        });

        RecyclerView chat = (RecyclerView) root.findViewById(R.id.chat_message);
        chat.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new ChatAdapter();
        chat.setAdapter(mAdapter);

        return root;
    }

    private void closeAndClean() {
        Utils.closeKeyboard(getContext(), mChatInput);
        mChatInput.setText("");
    }

    private void setupConnection() {

        chatAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = chatAuth.getCurrentUser();
        //final String userID = user.getUid();
        reference_1 = mReference.child("chat");

        reference_1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handleReturn(dataSnapshot);
                Log.d(Constants.LOG_TAG,"SUCCESS!");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(Constants.LOG_TAG,"ERROR: " + databaseError.getMessage());
                Toast.makeText(getContext(), R.string.chat_init_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleReturn(DataSnapshot dataSnapshot) {
        mAdapter.clearData();

        for(DataSnapshot item : dataSnapshot.getChildren()) {
            ChatData data = item.getValue(ChatData.class);
            String eName = data.getEventName();
            if( eName.equals(eventName))
                mAdapter.addData(data);
        }
        mAdapter.notifyDataSetChanged();
    }
}
