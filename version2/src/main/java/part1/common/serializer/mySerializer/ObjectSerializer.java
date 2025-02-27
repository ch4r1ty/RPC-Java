package part1.common.serializer.mySerializer;

import java.io.*;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/6/2 22:36
 */
public class ObjectSerializer implements Serializer {
    //利用Java io 对象 -》字节数组
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes=null;
        //创建一个内存中的输出流，用于存储序列化后的字节数组
        //ByteArrayOutputStream是一个可变大小的字节数据缓冲区，数据都会写入这个缓冲区中
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try {

//            ✅ oos 负责 把 Java 对象转换成字节流，写入 bos
//✅ bos 里存的 就是序列化后的数据，可以用 toByteArray() 取出
//✅ Java 默认序列化 生成 二进制数据，但可以改用 JSON、Kryo 等更高效的方式！
//
//🚀 简单来说：
//
//            oos 负责编码
//            bos 负责存数据
//            最终 bos.toByteArray() 里存的就是 Java 对象的二进制格式！ 🎯

            //是一个对象输出流，用于将 Java 对象序列化为字节流，并将其连接到bos上
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            //刷新 ObjectOutputStream，确保所有缓冲区中的数据都被写入到底层流中。
            oos.flush();
            //将bos其内部缓冲区中的数据转换为字节数组
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //字节数组 -》对象
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    //0 代表Java 原生序列器
    @Override
    public int getType() {
        return 0;
    }
}

