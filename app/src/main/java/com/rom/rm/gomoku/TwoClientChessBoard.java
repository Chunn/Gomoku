package com.rom.rm.gomoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TwoClientChessBoard {

        private int bitmapWidth;
        private int bitmapHeight;
        private int colQty;
        private int rowQty;
        private Canvas canvas;
        private Paint paint;
        private Bitmap bitmap;
        private ImageView imageView;

        private int[][] board; //Lưu mước đi
        private int player; //Lượt chơi
        private List<Line> lines;
        private Context context;//Vị trí hiện dialog

        private Bitmap bmtTick;
        private Bitmap bmtCross;

        private Move move;

        public TwoClientChessBoard(int bitmapWidth, int bitmapHeight, int colQty, int rowQty, Context context) {
            this.bitmapWidth = bitmapWidth;
            this.bitmapHeight = bitmapHeight;
            this.colQty = colQty;
            this.rowQty = rowQty;
            this.context = context;
        }

        public int getBitmapWidth() {
            return bitmapWidth;
        }

        public void setBitmapWidth(int bitmapWidth) {
            this.bitmapWidth = bitmapWidth;
        }

        public int getBitmapHeight() {
            return bitmapHeight;
        }

        public void setBitmapHeight(int bitmapHeight) {
            this.bitmapHeight = bitmapHeight;
        }

        public int getColQty() {
            return colQty;
        }

        public void setColQty(int colQty) {
            this.colQty = colQty;
        }

        public int getRowQty() {
            return rowQty;
        }

        public void setRowQty(int rowQty) {
            this.rowQty = rowQty;
        }

        public int[][] getBoard() {
            return board;
        }

        public void setBoard(int[][] board) {
            this.board = board;
        }

        public int getPlayer() {
            return player;
        }

        public void setPlayer(int player) {
            this.player = player;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public void init() {
            lines = new ArrayList<>();
            bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            paint = new Paint();
            board = new int[rowQty][colQty];
            for (int i = 0; i < rowQty; i++) {
                for (int j = 0; j < colQty; j++) {
                    board[i][j] = -1; //chưa đi
                }
            }
            paint.setStrokeWidth(2);
            int celWidth = bitmapWidth / colQty;
            int celHeight = bitmapHeight / rowQty;
            player = 0;
            for (int i = 0; i <= colQty; i++) {
                lines.add(new Line(celWidth * i, 0, celWidth * i, bitmapHeight));
            }
            for (int i = 0; i <= rowQty; i++) {
                lines.add(new Line(0, i * celHeight, bitmapWidth, i * celHeight));
            }

        }

        public Bitmap drawChessBoard() {
            for (int i = 0; i < lines.size(); i++) {
                canvas.drawLine(lines.get(i).getStartX(), lines.get(i).getStartY(), lines.get(i).getEndX(),
                        lines.get(i).getEndY(), paint);
            }
            bmtCross = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross);
            bmtTick = BitmapFactory.decodeResource(context.getResources(), R.drawable.tick);
            return bitmap;
        }
        public boolean onTouchTwoPlayer(final View view, MotionEvent motionEvent) {

            Log.d("Test", motionEvent.getX() + "-" + motionEvent.getY());
            int celWidth = view.getWidth() / colQty;
            int celHeight = view.getHeight() / rowQty;

            int colIndex = (int) (motionEvent.getX() / celWidth);
            int rowIndex = (int) (motionEvent.getY() / celHeight);
            Log.d("Kq", colIndex + "-" + rowIndex);

            if (board[rowIndex][colIndex] != -1)
                return true; // đã có người đi rồi
            board[rowIndex][colIndex] = player;
            player = (player + 1) % 2; // đổi lượt
            onDrawChessBoard(colIndex, rowIndex, view);//ng chơi
            makeMove(new Move(rowIndex,colIndex));
            view.invalidate(); //kêu view vẽ
            if (isGameOver()){
                int check=evaluate();
                if (check==1){
                    Toast.makeText((TwoClientActivity)context,"You win",Toast.LENGTH_SHORT).show();
                } else if (check==-1){
                    Toast.makeText((TwoClientActivity)context,"Game over",Toast.LENGTH_SHORT).show();
                } else if (check==0){
                    Toast.makeText((TwoClientActivity)context,"A drawn game",Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            return true;
        }
        public void onDrawChessBoard(int colIndex, int rowIndex, View view) {
            int celWidth = view.getWidth() / colQty;
            int celHeight = view.getHeight() / rowQty;

            board[rowIndex][colIndex] = player;
            int padding = 20;
            if (player == 0) {
                canvas.drawBitmap(bmtTick,
                        new Rect(0, 0, bmtTick.getWidth(), bmtTick.getHeight()),
                        new Rect(colIndex * celWidth + padding, rowIndex * celHeight + padding,
                                (colIndex + 1) * celWidth - padding, (rowIndex + 1) * celHeight - padding),
                        paint);
            } else if (player == 1) {
                canvas.drawBitmap(bmtCross, new Rect(0, 0, bmtCross.getWidth(), bmtCross.getHeight()),
                        new Rect(colIndex * celWidth + padding, rowIndex * celHeight + padding, (colIndex + 1) * celWidth - padding,
                                (rowIndex + 1) * celHeight - padding), paint);
            }

        }

        //ktra coi có hết game chưa
        public boolean isGameOver() {
            if (checkWin(0) || checkWin(1)) return true;

            int count = 0;
            for (int i = 0; i < rowQty; i++) {
                for (int j = 0; j < colQty; j++) {
                    if (board[i][j] == -1) count++;
                }
            }
            if (count == 0) {
                return true;//trò chơi kết thúc
            }
            //chưa thắng hoặc còn vị trí để đi=>game chưa kết thúc
            return false;
        }

        private boolean checkWin(int player) {

            boolean vertical = true, horizontal = true;
            boolean diagonalOne = true, diagonalTwo = true;
            int countH = 0;
            int countV = 0;
            // Check theo hàng
            for (int i = 0; i < rowQty; i++) {
                for (int j = 0; j < colQty; j++) {
                    if (board[i][j] != player)
                        horizontal = false;
                    if (board[j][i] != player)
                        vertical = false;
                    if (horizontal) countH++;
                    if (vertical) countV++;
                    if (countH == 5 || countV == 5 || !(horizontal || vertical)) break;
                }
                if (horizontal || vertical) {
                    return true;
                }
            }
            //check theo đường chéo
            int colIndex;
            int countDO = 0;
            int countDT = 0;
            for (int n = 0; n < rowQty; n++) {
                colIndex = rowQty - 1 - n;
                if (board[n][n] != player)
                    diagonalOne = false;
                if (board[colIndex][n] != player)
                    diagonalTwo = false;
                if (diagonalOne) countDO++;
                if (diagonalTwo) countDT++;
                if (countDO == 5 || countDT == 5 || !(diagonalOne || diagonalTwo))
                    break;
            }
            if (diagonalOne || diagonalTwo)
                return true;
            else
                return false; //chưa thắng

        }

        //get toàn bộ nước đi còn đi được vào danh sacgs
        public List<Move> getMoves() {
            //tạo mới 1 danh sách gán các vị trí còn đi được vào danh sách
            List<Move> moves = new ArrayList<>();
            for (int i = 0; i < rowQty; i++) {
                for (int j = 0; j < colQty; j++) {
                    if (board[i][j] == -1) moves.add(new Move(i, j));//có thể đi dc
                }
            }
            return moves;
        }

        public void makeMove(Move move) {
            board[move.getRowIndex()][move.getColIndex()] = player; //lưu nước đi
            player = (player + 1) % 2;//hoan đổi người chơi

        }

        //đánh giá bàn cờ
        public int evaluate() {
            if (checkWin(player))
                return 1; //thắng
            if (checkWin((player + 1) % 2))
                return -1;//thua
            return 0;//hòa
        }

        public int[][] getNewBoard() {
            int[][] newBoard = new int[rowQty][colQty];
            for (int i = 0; i < rowQty; i++) {
                for (int j = 0; j < colQty; j++) {
                    newBoard[i][j] = board[i][j];
                }
            }
            return newBoard;
        }

        public int getCurrentDept() {
            int count = 0;
            for (int i = 0; i < rowQty; i++) {
                for (int j = 0; j < colQty; j++) {
                    if (board[i][j] == -1) count++;
                }
            }
            return count;
        }

}
