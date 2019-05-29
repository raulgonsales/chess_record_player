package main.java.models.interfaces;

/**
 * Field interface.
 *
 * @author Bohdan Inhliziian (xinhli00)
 * @author Konetzný Jakub (xinhli00)
 */
public interface Field {
    void positiveHighlightField();

    enum Direction implements java.io.Serializable, java.lang.Comparable<Field.Direction>{
        LD(0), // Left-Down
        L(1),  // Left
        LU(2), // Left-Up
        U(3),  // Up
        RU(4), // Right-Up
        R(5),  // Right
        RD(6), // Right-Down
        D(7);  // Down

        int dir_env_index;

        Direction(int i) {
            this.dir_env_index = i;
        }

        public int getValue() {
            return this.dir_env_index;
        }
    }

    void addNextField(Field.Direction dirs, Field field);
    Field nextField(Field.Direction dirs);



    int getCol();
    int getRow();
    boolean isEmpty();
    boolean put(Figure figure);
    boolean remove();
    Figure get();
}
