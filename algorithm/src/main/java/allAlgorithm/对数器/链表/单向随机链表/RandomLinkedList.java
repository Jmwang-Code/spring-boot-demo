package allAlgorithm.对数器.链表.单向随机链表;

import allAlgorithm.BmodelLinkedList.NodeRandom;
import allAlgorithm.BmodelLinkedList.NodeS;

public class RandomLinkedList {

    public static void printLinkedList(NodeRandom random){
        System.out.print("Linked List: ");
        while (random != null) {
            System.out.print(random.val + " ");
            random = random.next;
        }
        System.out.println();
    }

    //isEquals
    public static boolean isEquals(NodeRandom head1, NodeRandom head2){
        while (head1 != null && head2 != null){
            if (head1.val != head2.val){
                return false;
            }
            if (head1.random!=null && head2.random!=null){
                if (head1.random.val != head2.random.val){
                    return false;
                }
            }
            head1 = head1.next;
            head2 = head2.next;
        }
        return head1 == null && head2 == null;
    }

}
