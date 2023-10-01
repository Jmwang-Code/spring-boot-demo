package allAlgorithm.CmodelBinaryTree;

public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

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
