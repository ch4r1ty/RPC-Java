package part1.Client;



import part1.common.Message.RpcRequest;
import part1.common.Message.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/4 18:31
 */


// IOClient, 用于建立 TCP 连接并发送 RPC 请求的类
// 底层通信做了哪些事情：建立连接、发送请求、接受相应、异常处理
public class IOClient {
    //这里负责底层与服务端的通信，发送request，返回response
    public static RpcResponse sendRequest(String host, int port, RpcRequest request){
        try {
            // 这一步会主动向服务器发起 TCP 连接，如果服务器监听 host:port 并允许连接，TCP 连接就会建立成功
            Socket socket=new Socket(host, port);   // 通过socket与服务器建立 tcp 连接
            ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());    // 将对象序列化发送到服务端
            ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());   // 接受并反序列化对象

            oos.writeObject(request);   // 将 rpcRequest对象序列化，并通过输出流发送到服务端
            oos.flush();    // 刷新输出流以确保数据完全发送

            RpcResponse response=(RpcResponse) ois.readObject();    // 从输入流中读取服务端返回的序列化对象，并反序列化为 RpcResponse
            return response;
            // 与网络通信相关的一场、反序列化对象时找不到对应类的异常
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
