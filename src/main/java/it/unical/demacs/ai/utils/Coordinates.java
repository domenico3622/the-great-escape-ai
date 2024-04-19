package it.unical.demacs.ai.utils;


/*
 * lascio i campi pubblici ispirandomi ai pair di c++
 */
public class Coordinates{
    public int row;
    public int column;

    public Coordinates(int row, int column){
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (!(obj instanceof Coordinates)) return false;
        Coordinates tmp = (Coordinates) obj;
        return this.row == tmp.row && this.row == tmp.row;
    }
}