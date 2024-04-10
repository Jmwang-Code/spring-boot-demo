import java.nio.ByteBuffer;

public class JNIJava_native_interface {


    public static void main(String[] args) {
        // 创建一个直接字节缓冲区，大小为1GB
        ByteBuffer buffer = ByteBuffer.allocateDirect(1 * 1024 * 1024 * 1024);

        //一直沉睡
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 模拟使用直接字节缓冲区
        // 这里假设我们忘记释放直接字节缓冲区

        // 假设我们持续向直接字节缓冲区中写入数据，但不释放它
        // 例如，重复调用 buffer.put() 或向其中写入大量数据而不调用 buffer.clear() 或 buffer.release()

        // 导致内存泄漏或内存溢出
    }
}
