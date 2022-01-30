package com.example.wqiu1_rollcount;

import static com.example.wqiu1_rollcount.MainActivity.updateGame;

import java.util.ArrayList;

/**
 * Roll
 * Purpose: represents a roll which has the rollNum (ie. the number on the dice) and the count (ie.
 *      how many times the rollNum occurred).
 * Design rationale: a class was made to represent a roll to make it easier to initialize rolls in
 *      the RollsList, as well as providing common roll functions such as `increaseCount` and `decreaseCount`
 * Outstanding issues: None
 */
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
