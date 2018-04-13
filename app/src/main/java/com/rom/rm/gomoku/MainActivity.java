package com.rom.rm.gomoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_pwp;
    private Button btn_pwb;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_pwp=findViewById(R.id.btn_pwp);
        btn_pwb=findViewById(R.id.btn_pwb);
        btn_exit=findViewById(R.id.btn_exit);

        btn_pwp.setOnClickListener(this);
        btn_pwb.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pwp:
                Intent intent=new Intent(MainActivity.this,TwoClientActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pwb:
                Intent intent1=new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_exit:
                finish();
                break;
            default: break;
        }
    }
}
