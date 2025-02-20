package part1.Client;


import part1.Client.proxy.ClientProxy;
import part1.common.service.UserService;
import part1.common.pojo.User;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/6 18:39
 */
public class TestClient {
    public static void main(String[] args) {
        // 创建 ClientProxy 对象：初始化 ClientProxy 对象，连接到指定的服务器地址和端口
        ClientProxy clientProxy=new ClientProxy("127.0.0.1",9999);
        // 获得UserService的代理对象
        UserService proxy=clientProxy.getProxy(UserService.class);

        User user = proxy.getUserByUserId(1);
        System.out.println("从服务端得到的user="+user.toString());

        User u=User.builder().id(100).userName("wxx").sex(true).build();
        Integer id = proxy.insertUserId(u);
        System.out.println("向服务端插入user的id"+id);
    }
}
