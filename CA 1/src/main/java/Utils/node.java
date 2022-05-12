package Utils;

public class node<N> {
    public node<N> next, previous;
    private N contents; //ADT reference

    public N getContents() { return contents; }
    public void setContents(N c) { contents=c; }
}