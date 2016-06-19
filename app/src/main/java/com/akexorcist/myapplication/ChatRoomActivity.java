package com.akexorcist.myapplication;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.akexorcist.myapplication.common.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatRoomActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvUserName;
    private EditText etMessage;
    private ImageButton btnSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        checkUserAuthentication();
        bindView();
        setupView();
    }

    private void bindView() {
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        etMessage = (EditText) findViewById(R.id.et_message);
        btnSendMessage = (ImageButton) findViewById(R.id.btn_send_message);
    }

    private void setupView() {
        btnSendMessage.setOnClickListener(this);
        tvUserName.setText(String.format("%s %s", getString(R.string.sign_in_as), getUsername()));
    }

    private void checkUserAuthentication() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            showPopupMessage(R.string.please_sign_in);
            finish();
        } else {
            showBottomMessage(String.format("%s %s", getString(R.string.user_greeting), getUsername()), Snackbar.LENGTH_LONG);
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
            // TODO Send message to realtime database
        }
    }

    private void sendMessageToDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
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
