package com.rom.rm.gomoku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Rơm on 4/8/2018.
 */

public class GameActivity extends AppCompatActivity {
    private ImageView imageView;
    private ChessBoard chessBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot);
        chessBoard=new ChessBoard(600,600,8,8,this);
        chessBoard.init();
        imageView=findViewById(R.id.img_gomoku);
        imageView.setImageBitmap( chessBoard.drawChessBoard());
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

               return chessBoard.onTouch(v,event);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.endgame,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        if(item.getItemId()==R.id.item_end){
            Intent intent=new Intent(GameActivity.this,MainActivity.class);
            startActivity(intent);
          finish();
        }
        return true;
    }

}
