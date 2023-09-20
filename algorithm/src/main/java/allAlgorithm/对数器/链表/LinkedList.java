package allAlgorithm.对数器.链表;

import allAlgorithm.BmodelLinkedList.NodeD;
import allAlgorithm.BmodelLinkedList.NodeRandom;
import allAlgorithm.BmodelLinkedList.NodeS;

public interface LinkedList {

    //
    default NodeD processNodeD(NodeD val) {
        return val;
    }

    default NodeS processNodeS(NodeS val){
        return val;
    }

    default NodeRandom processNodeRandom(NodeRandom val){
        return val;
    }
}
