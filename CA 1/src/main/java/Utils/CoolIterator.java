package Utils;

import java.util.Iterator;

public class CoolIterator<G> implements Iterator<G> {
    private node<G> pos;
    public CoolIterator(node<G> fnode){
        pos = fnode;
    }
    @Override
    public boolean hasNext() {
        return pos!= null;
    }

    @Override
    public G next() {
        node temp=pos;
        pos=pos.next;
        return (G) temp.getContents();
    }
}
