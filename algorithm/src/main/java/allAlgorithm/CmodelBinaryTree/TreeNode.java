package allAlgorithm.CmodelBinaryTree;

public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    //保留中间状态，两个节点进行连接，其实就是cur.right = pre; pre = cur; cur.left = null;
    static TreeNode minNode;

    public static TreeNode convertBiNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        //遍历右子树
        convertBiNode(root.right);
        //遍历根节点
        root.right = minNode;
        minNode = root;
        //遍历左子树
        convertBiNode(root.left);
        //置空左指针
        root.left = null;
        return minNode;
    }

    //按照层次遍历的方式打印二叉树
    public static void printTree(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.val);
        printTree(root.left);
        printTree(root.right);
    }
    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(5);
        TreeNode left1 = new TreeNode(1);
        TreeNode right1 = new TreeNode(3);
        root.left = left;
        root.right = right;
        left.left = left1;
        left.right = right1;

        TreeNode treeNode = convertBiNode(root);
    }
}
