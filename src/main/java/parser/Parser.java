package main.java.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Parser err_code:
 * 0-OK
 * 1-file not exist on you dont permissions to read file
 * 2-you dont have permission to write file
 * 3-Syntax error of file
 */
public class Parser {

    private String path;
    private ArrayList<String> data;
    private int err_code;

    public Parser(String path) {
        this.path = path;
        data = new ArrayList<>();
        err_code = 0;
    }

    /**
     * method which load data to ArrayList of Strings by lines
     *
     * @return true if data was load successfully otherwise false
     */
    private boolean load_data() {
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                this.data.add(line);
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            this.err_code = 1;
            return false;
        }

        return true;
    }

    /**
     *
     * @return return code of parsing data
     */
    public int getErr_code() {
        return err_code;
    }

    /**
     * method which convert file to ArrayList of rounds
     * @return ArrayList of rounds from file
     */
    public ArrayList<Round> convert() {
        if (!load_data()) {
            return null;
        }
        try {
            ArrayList<Round> list = new ArrayList<>();
            String patern = "(\\d+)\\.\\s+(\\S+)\\s*(\\S+)*$";
            String line, s_wh, s_bl;
            Move tmp_w, tmp_b;
            for (int i = 0; i < this.data.size(); i++) {
                line = this.data.get(i);
                int t = Integer.parseInt(line.replaceAll(patern, "$1"));
                if (Integer.parseInt(line.replaceAll(patern, "$1")) != (i + 1)) {
                    this.err_code = 3;
                    return null;
                }
                s_wh = line.replaceAll(patern, "$2");
                s_bl = line.replaceAll(patern, "$3");

                if (!s_wh.equals("")) {
                    tmp_w = fill(s_wh);
                } else {
                    tmp_w = null;
                }

                if (!s_bl.equals("")) {
                    tmp_b = fill(s_bl);
                } else {
                    tmp_b = null;
                }


                list.add(new Round(tmp_w, tmp_b));
            }
            return list;
        } catch (Exception e) {
            this.err_code = 3;
            return null;
        }

    }

    /**
     *
     * @param s move of one figure in String
     * @return move which was fill with data from string
     */
    private Move fill(String s) {
        String patern = "^(K|D|V|S|J|p){0,1}([a-h]){0,1}([1-8]){0,1}(x){0,1}([a-h]){0,1}([1-8]){0,1}(K|D|V|S|J|p){0,1}(\\+){0,1}(#){0,1}$";
        Move move = new Move();
        String tmp;
        tmp = s.replaceAll(patern, "$1");
        if (tmp.equals("")) {
            move.setStone(null);
        } else {
            move.setStone(tmp);
        }
        tmp = s.replaceAll(patern, "$2");
        if (tmp.equals("")) {
            move.setFrom_col(0);
        } else {
            move.setFrom_col(leatther_to_int(tmp));
        }
        tmp = s.replaceAll(patern, "$3");
        if (tmp.equals("")) {
            move.setFrom_row(0);
        } else {
            move.setFrom_row(Integer.parseInt(tmp));
        }
        tmp = s.replaceAll(patern, "$4");
        if (tmp.equals("")) {
            move.setKill(false);
        } else {
            move.setKill(true);
        }
        tmp = s.replaceAll(patern, "$5");
        if (tmp.equals("")) {
            move.setTo_col(0);
        } else {
            move.setTo_col(leatther_to_int(tmp));
        }
        tmp = s.replaceAll(patern, "$6");
        if (tmp.equals("")) {
            move.setTo_row(0);
        } else {
            move.setTo_row(Integer.parseInt(tmp));
        }
        tmp = s.replaceAll(patern, "$7");
        if (tmp.equals("")) {
            move.setSwap_stone(null);
        } else {
            move.setSwap_stone(tmp);
        }
        tmp = s.replaceAll(patern, "$8");
        if (tmp.equals("")) {
            move.setCheck(false);
        } else {
            move.setCheck(true);
        }
        tmp = s.replaceAll(patern, "$9");
        if (tmp.equals("")) {
            move.setCheck_mat(false);
        } else {
            move.setCheck_mat(true);
        }

        if (move.getTo_col() == 0 && move.getTo_row() == 0) {
            move.setTo_col(move.getFrom_col());
            move.setTo_row(move.getFrom_row());
            move.setFrom_col(0);
            move.setFrom_row(0);
        }

        return move;
    }

    /**
     *
     * @param s leather to convert
     * @return int value of leather on board
     */
    int leatther_to_int(String s) {

        char ch = s.charAt(0);

        //ASCI
        return ((int) ch) - ((int) 'a') + 1;
    }
}