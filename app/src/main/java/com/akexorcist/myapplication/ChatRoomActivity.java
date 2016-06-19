package com.akexorcist.myapplication;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;

import com.akexorcist.myapplication.common.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChatRoomActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        checkUserAuthentication();
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

    }
}
