import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void convert1() {
        ArrayList<Round> list;
        Parser parser = new Parser("tests/inputs/not_existing_file.txt");
        list = parser.convert();
        assertNull(list);
    }

    @Test
    public void convert2() {
        ArrayList<Round> list;
        Parser parser;
        parser = new Parser("tests/inputs/OK_1.txt");
        list = parser.convert();
        assertNotNull(list);
    }

    @Test
    public void convert3() {
        ArrayList<Round> list;
        Parser parser;
        parser = new Parser("tests/inputs/OK_1.txt");
        list = parser.convert();
        //Df6
        Move move = new Move(0, 0, 6, 6, "D", false, false, false, null);
        assertEquals(list.get(1).getBlack(), move);
        Move move_2 = new Move(1, 0, 6, 6, "D", false, false, false, null);
        assertNotEquals(list.get(1).getBlack(), move_2);
    }
}