package com.akexorcist.myapplication;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.akexorcist.myapplication.adpter.MessageAdapter;
import com.akexorcist.myapplication.common.BaseActivity;
import com.akexorcist.myapplication.model.MessageItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatRoomActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvUserName;
    private EditText etMessage;
    private ImageButton btnSendMessage;
    private RecyclerView rvMessage;
    private MessageAdapter messageAdapter;
    private ArrayList<MessageItem> messageItemList;
    private DatabaseReference messageDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        checkUserAuthentication();
        bindView();
        setupView();
        setupRealtimeDatabase();
    }

    private void bindView() {
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        etMessage = (EditText) findViewById(R.id.et_message);
        btnSendMessage = (ImageButton) findViewById(R.id.btn_send_message);
        rvMessage = (RecyclerView) findViewById(R.id.rv_message);
    }

    private void setupView() {
        btnSendMessage.setOnClickListener(this);
        tvUserName.setText(String.format("%s %s", getString(R.string.sign_in_as), getUsername()));

        messageItemList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageItemList, FirebaseAuth.getInstance().getCurrentUser());
        rvMessage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMessage.setAdapter(messageAdapter);
    }

    private void setupRealtimeDatabase() {
        DatabaseReference rootDatabaseReference = FirebaseDatabase.getInstance().getReference();
        messageDatabaseReference = rootDatabaseReference.child("global_room");
        messageDatabaseReference.limitToFirst(10);
        messageDatabaseReference.addValueEventListener(messageValueEventListener);
    }

    private void checkUserAuthentication() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            showPopupMessage(R.string.please_sign_in);
            finish();
        }
    }

    private String getUsername() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            if (firebaseUser.getDisplayName() == null || firebaseUser.getDisplayName().isEmpty()) {
                return firebaseUser.getEmail();
            } else {
                return firebaseUser.getDisplayName();
            }
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messageDatabaseReference.removeEventListener(messageValueEventListener);
    }

    private ValueEventListener messageValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            MessageItem messageItem = dataSnapshot.getValue(MessageItem.class);
            messageItemList.add(messageItem);
            messageAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            showPopupMessage(R.string.something_error_in_realtime_database);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_sign_out) {
            signOut();
        } else if (id == R.id.menu_change_name) {
            changeName();
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        goToLogin();
        FirebaseAuth.getInstance().signOut();
    }

    private void goToLogin() {
        openActivity(LoginActivity.class);
    }

    private void changeName() {
        // TODO Add change name feature
    }

    @Override
    public void onClick(View view) {
        if (view == btnSendMessage) {
            String message = etMessage.getText().toString();
            sendMessage(message);
        }
    }

    private void sendMessage(String message) {
        if (isMessageValidated(message)) {
            clearMessageBox();
            hideKeyboard();
            sendMessageToRealtimeDatabase(message);
        }
    }

    private void sendMessageToRealtimeDatabase(String message) {
        MessageItem messageItem = new MessageItem(message, getUsername());
        messageDatabaseReference.setValue(messageItem);
    }

    private boolean isMessageValidated(String message) {
        return !(message == null || message.isEmpty());
    }

    private void clearMessageBox() {
        if (etMessage != null) {
            etMessage.setText("");
        }
    }
}
