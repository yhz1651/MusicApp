package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 主界面
 * 底部是navigation菜单
 * 上部是fragment
 */
public class MainActivity extends AppCompatActivity {

    private Button bnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String userid=intent.getStringExtra("userid");
//        Bundle bundle = new Bundle();
//        bundle.putString("aaa",xxx);
//        playFragment.setArgument(bundle);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView); // 获得底部导航视图
        NavController navController = Navigation.findNavController(this,R.id.fragment); // hostfragment
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(navController.getGraph()).build(); // 获取导航图表并装配
//        NavigationUI.setupActionBarWithNavController(this,navController,configuration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}