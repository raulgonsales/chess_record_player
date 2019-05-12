package main.java.parser;

import java.util.Objects;

public class Move {
    private int from_row;
    private int from_col;

    private int to_row;
    private int to_col;

    private String stone;
    private boolean defend;
    private boolean check;
    private boolean check_mat;
    private String swap_stone;


    public String getStone() {
        return stone;
    }

    public boolean isDefend() {
        return defend;
    }

    public boolean isCheck() {
        return check;
    }

    public boolean isCheck_mat() {
        return check_mat;
    }

    public String getSwap_stone() {
        return swap_stone;
    }

    public Move(Move other) {
        this.from_row = other.from_row;
        this.from_col = other.from_col;
        this.to_row = other.to_row;
        this.to_col = other.to_col;
        this.stone = other.stone;
        this.defend = other.defend;
        this.check = other.check;
        this.check_mat = other.check_mat;
        this.swap_stone = other.swap_stone;
    }


    public Move(int from_row, int from_col, int to_row, int to_col, String stone,
                boolean defend, boolean check, String swap_stone) {
        this.from_row = from_row;
        this.from_col = from_col;
        this.to_row = to_row;
        this.to_col = to_col;
        this.stone = stone;
        this.defend = defend;
        this.check = check;
        this.swap_stone = swap_stone;
    }

    public Move(int from_row, int from_col, int to_row, int to_col, String stone,
                boolean defend, boolean check, boolean check_mat, String swap_stone) {
        this.from_row = from_row;
        this.from_col = from_col;
        this.to_row = to_row;
        this.to_col = to_col;
        this.stone = stone;
        this.defend = defend;
        this.check = check;
        this.check_mat = check_mat;
        this.swap_stone = swap_stone;
    }

    Move() {
        this.from_col = 0;
        this.from_row = 0;

        this.to_col = 0;
        this.to_row = 0;
    }

    public int getFrom_row() {
        return from_row;
    }

    public int getFrom_col() {
        return from_col;
    }

    public int getTo_row() {
        return to_row;
    }

    public int getTo_col() {
        return to_col;
    }

    public void setFrom_row(int from_row) {
        this.from_row = from_row;
    }

    public void setFrom_col(int from_col) {
        this.from_col = from_col;
    }

    public void setTo_row(int to_row) {
        this.to_row = to_row;
    }

    public void setTo_col(int to_col) {
        this.to_col = to_col;
    }

    public void setStone(String stone) {
        this.stone = stone;
    }

    public void setDefend(boolean defend) {
        this.defend = defend;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void setCheck_mat(boolean check_mat) {
        this.check_mat = check_mat;
    }

    public void setSwap_stone(String swap_stone) {
        this.swap_stone = swap_stone;
    }

    Move(int from_col, int from_row, int to_col, int to_row) {

        this.from_col = from_col;
        this.from_row = from_row;

        this.to_col = to_col;
        this.to_row = to_row;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return getFrom_row() == move.getFrom_row() &&
                getFrom_col() == move.getFrom_col() &&
                getTo_row() == move.getTo_row() &&
                getTo_col() == move.getTo_col() &&
                defend == move.defend &&
                check == move.check &&
                check_mat == move.check_mat &&
                Objects.equals(stone, move.stone) &&
                Objects.equals(swap_stone, move.swap_stone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom_row(), getFrom_col(), getTo_row(), getTo_col(), stone, defend, check, check_mat, swap_stone);
    }

    private String int_to_str(int i) {
        if (i == 1) return "a";
        if (i == 2) return "b";
        if (i == 3) return "c";
        if (i == 4) return "d";
        if (i == 5) return "e";
        if (i == 6) return "f";
        if (i == 7) return "g";
        if (i == 8) return "h";
        else return "X";

    }

    @Override
    public String toString() {
        String ret = "";
        if (this.stone != null) ret += this.stone;
        if (this.from_col != 0) ret += int_to_str(this.from_col);
        if (this.from_row != 0) ret += this.from_row;
        if (this.defend) ret += "x";
        if (this.to_col != 0) ret += int_to_str(this.to_col);
        if (this.to_row != 0) ret += this.to_row;
        if (this.swap_stone != null) ret += this.swap_stone;
        if (this.check) ret += "+";
        if (this.check_mat) ret += "#";
        return ret;
    }
}
