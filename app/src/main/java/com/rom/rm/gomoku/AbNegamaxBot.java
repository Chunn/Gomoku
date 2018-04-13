package com.rom.rm.gomoku;

/**
 * Created by Rơm on 4/8/2018.
 */

public class AbNegamaxBot {

    public AbNegamaxRecord abNegamax(ChessBoard chessBoard,int maxDept,int currentDept, int alpha, int beta){
      if (chessBoard.isGameOver()||currentDept==maxDept){
          return new AbNegamaxRecord(null,chessBoard.evaluate());//move=null=> hết bàn cờ
      }
        Move bestMove=null;//
        int bestScore=-Integer.MAX_VALUE;//giá trị điểm tốt nhất
        int currentScore;
        AbNegamaxRecord abNegamaxRecord ;
        ChessBoard newChessBoard;
        //Duyệt tất cả các nước có thể đi trên bàn cờ
        for (Move move:chessBoard.getMoves()){
            //Tạo mới 1 bàn cờ
            newChessBoard= new ChessBoard(chessBoard.getBitmapWidth(),chessBoard.getBitmapHeight(),
                    chessBoard.getColQty(),chessBoard.getRowQty(),chessBoard.getContext());
            newChessBoard.setBoard(chessBoard.getBoard());
            newChessBoard.setPlayer(chessBoard.getPlayer());
            newChessBoard.makeMove(move);
            //gọi đệ quy
            abNegamaxRecord=abNegamax(newChessBoard,maxDept,currentDept++,-beta,-Math.max(alpha,bestScore));
            currentScore= -abNegamaxRecord.getScore();

            //Cập nhật bestscore
            if (currentScore>bestScore){
                bestScore=currentScore;
                bestMove=move;
                if (bestScore>=beta){
                    return new AbNegamaxRecord(bestMove,bestScore);
                }
            }
        }
        return new AbNegamaxRecord(bestMove,bestScore);
    }

}
