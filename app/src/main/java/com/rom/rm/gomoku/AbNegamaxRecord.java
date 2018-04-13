package com.rom.rm.gomoku;

/**
 * Created by Rơm on 4/8/2018.
 */

public class AbNegamaxRecord {
    Move move;
    int score;

    public AbNegamaxRecord(Move move, int score) {
        this.move = move;
        this.score = score;
    }

    public AbNegamaxRecord() {
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
