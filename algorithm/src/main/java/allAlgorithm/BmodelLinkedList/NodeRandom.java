package allAlgorithm.BmodelLinkedList;

/**
 * 允许节点中有random指针
 */
public class NodeRandom {

    public int val;

    public NodeRandom next;

    public NodeRandom random;

    public NodeRandom(int val) {
        this.val = val;
    }

    public NodeRandom(int val, NodeRandom next, NodeRandom random) {
        this.val = val;
        this.next = next;
        this.random = random;
    }
}
