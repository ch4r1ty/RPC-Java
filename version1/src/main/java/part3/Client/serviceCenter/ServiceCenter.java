package part3.Client.serviceCenter;

import java.net.InetSocketAddress;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/5/3 21:42
 */
//服务中心接口
public interface ServiceCenter {
    //InetSocketAddress属于 java.net包。它表示了一个网络地址(包含ip地址和端口号)，通常用于在网络中标识一个计算机的端口
    //  查询：根据服务名查找地址
    InetSocketAddress serviceDiscovery(String serviceName);
}
