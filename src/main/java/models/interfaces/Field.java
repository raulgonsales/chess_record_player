package main.java.models.interfaces;

public interface Field {
    int getCol();
    int getRow();
    boolean isEmpty();
    boolean put(Figure figure);
    boolean remove();
    Figure get();
}
