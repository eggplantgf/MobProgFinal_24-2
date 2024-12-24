package com.example.mobprogfinal_v1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobprogfinal_v1.auth.LoginActivity;
import com.example.mobprogfinal_v1.board.BoardActivity;
import com.example.mobprogfinal_v1.providers.AuthManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 유저 인증 상태 확인
        AuthManager authManager = AuthManager.getInstance();
        if (authManager.isAuthenticated()) {
            // 인증된 경우 게시판 화면으로 이동
            startActivity(new Intent(this, BoardActivity.class));
        } else {
            // 인증되지 않은 경우 로그인 화면으로 이동
            startActivity(new Intent(this, LoginActivity.class));
        }
        // 현재 Activity 종료
        finish();
    }
}
