package allAlgorithm.BmodelLinkedList;

public class NodeD {

    public int val;

    public NodeD next;

    public NodeD pre;

    public NodeD(int val) {
        this.val = val;
    }

    public NodeD(int val, NodeD next, NodeD pre) {
        this.val = val;
        this.next = next;
        this.pre = pre;
    }
}
