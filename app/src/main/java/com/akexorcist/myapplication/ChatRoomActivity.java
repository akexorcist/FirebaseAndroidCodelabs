package com.akexorcist.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.akexorcist.myapplication.common.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ChatRoomActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
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
