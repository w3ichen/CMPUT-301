package com.example.rollcount;

import static com.example.rollcount.MainActivity.updateGame;

import java.util.ArrayList;

public class Roll {
    private Integer rollNum;
    private Integer count;

    public Roll(Integer rollNum, Integer count) {
        this.rollNum = rollNum;
        this.count = count;
    }

    public Integer getRollNum() {
        return rollNum;
    }

    public void setRollNum(Integer rollNum) {
        this.rollNum = rollNum;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void increaseCount(int rollIndex, Game game, int gameIndex, ArrayList<Integer> rollCounts) {
        this.count += 1;
        rollCounts.set(rollIndex, this.count);
        game.setRollCounts(rollCounts);
        updateGame(gameIndex, game); // save to persistent storage for game at index
    }

    public void decreaseCount(int rollIndex, Game game, int gameIndex, ArrayList<Integer> rollCounts) {
        if (this.count > 0) {
            this.count -= 1; // only decrement if not 0
            rollCounts.set(rollIndex, this.count);
            game.setRollCounts(rollCounts);
            updateGame(gameIndex, game); // save to persistent storage for game at index
        }
    }

}
