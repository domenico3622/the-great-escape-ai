package it.unical.demacs.ai.utils;


/*
 * lascio i campi pubblici ispirandomi ai pair di c++
 */
public class Pair<T1, T2>
{
    public T1 first;
    public T2 second;

    public Pair(T1 _first, T2 _second)
    {
        first = _first;
        second = _second;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (!(obj instanceof Pair)) return false;

        Pair<?, ?> tmp = (Pair<?,?>) obj;

        return this.first == tmp.first && this.second == tmp.second;
    }
}