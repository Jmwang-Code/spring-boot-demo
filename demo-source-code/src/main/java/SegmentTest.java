import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SegmentTest {

    @Test
    public void ConcurrentHashMap_Segment(){

        Map map = new ConcurrentHashMap();
        map.put(1,1);
    }
}
