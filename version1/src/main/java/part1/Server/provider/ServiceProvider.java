package part1.Server.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/16 17:35
 */
//本地服务存放器
public class ServiceProvider {
    //集合中存放服务的实例，key是服务接口全限定类名，即接口的完整路径名；value是服务实例，实现了该接口的对象
    private Map<String,Object> interfaceProvider;

    public ServiceProvider(){
        this.interfaceProvider=new HashMap<>();
    }
    //本地注册服务

    public void provideServiceInterface(Object service){
        String serviceName=service.getClass().getName();    // 获取服务对象的完整类名
        Class<?>[] interfaceName=service.getClass().getInterfaces();    // 获取服务对象实现的所有接口

        for (Class<?> clazz:interfaceName){
            interfaceProvider.put(clazz.getName(),service); // 将接口的全限定名和对应的服务的实例添加到map中
        }

    }
    //获取服务实例
    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
