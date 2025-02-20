package part1.Client.proxy;


import lombok.AllArgsConstructor;
import part1.Client.IOClient;
import part1.common.Message.RpcRequest;
import part1.common.Message.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/6 16:49
 */

// ClientProxy 是 JDK 动态代理 的实现类
@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    // 传入参数service接口的class对象，反射封装成一个request
    // 初始化代理类时传入 host 和 port
    private String host;
    private int port;

    //jdk动态代理，每一次代理对象调用方法，都会经过此方法增强（反射获取request对象，socket发送到服务端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //构建request
        RpcRequest request=RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsType(method.getParameterTypes()).build();
        //IOClient.sendRequest 和服务端进行数据传输，把请求发出去，并接受 RpcResponse响应
        RpcResponse response= IOClient.sendRequest(host,port,request);
        //获取服务端返回的结果，并返回给调用者
        return response.getData();
    }
     public <T>T getProxy(Class<T> clazz){
        //使用 Proxy.newProxyInstance 方法动态创建一个代理对象，传入类加载器、需要代理的接口和调用处理程序
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}
