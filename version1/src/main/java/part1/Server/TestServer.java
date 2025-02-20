package part1.Server;


import part1.Server.server.RpcServer;
import part1.common.service.Impl.UserServiceImpl;
import part1.common.service.UserService;
import part1.Server.server.impl.SimpleRPCRPCServer;
import part1.Server.provider.ServiceProvider;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/11 19:39
 */
public class TestServer {
    public static void main(String[] args) {
        UserService userService=new UserServiceImpl();  //创建服务实现类

        ServiceProvider serviceProvider=new ServiceProvider();  //实例化服务注册中心，用于管理所有可供客户端调用的服务
        serviceProvider.provideServiceInterface(userService);   //注册服务

        RpcServer rpcServer=new SimpleRPCRPCServer(serviceProvider);    //实例化服务端
        rpcServer.start(9999);  //启动服务端
    }
}
