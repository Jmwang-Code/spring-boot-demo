import myCollections.MyArrayList;
import org.junit.Test;


public class MyCollections {

    @Test
    public void Java集合框架有那些接口(){
        MyArrayList<String> list = new MyArrayList();

        list.add("0");
        list.add("1");
        list.add("2");

        System.out.println(list.get(1));
        System.out.println(list.size());
        System.out.println(list.remove(1));
    }
}
