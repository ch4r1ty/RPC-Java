package part2.Client.netty.nettyInitializer;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import part2.Client.netty.handler.NettyClientHandler;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/26 17:26
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();   //初始化，每个SocketChannel都有一个独立的管道(pipeline)，用于定义数据的处理流程


//        Netty 的 ChannelPipeline, 也就是下面的pipeline.addLast()结构和队列类似，但 它不仅仅是队列，而是一个双向责任链。


        //消息格式 【长度】【消息体】，解决沾包问题
        /*
        * 参数含义：
        * Integer.MAX_VALUE: 允许的最大帧长度
        * 0, 4: 表示长度字段的其实位置和长度
        * 0, 4: 去掉长度字段后，计算实际数据的偏移量
        * */
        pipeline.addLast(
                new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        //计算当前待发送消息的长度，写入到前4个字节中
        pipeline.addLast(new LengthFieldPrepender(4));
        //编码器
        //使用Java序列化方式，netty的自带的解码编码支持传输这种结构
        pipeline.addLast(new ObjectEncoder());
        //解码器
        //使用了Netty中的ObjectDecoder，它用于将字节流解码为 Java 对象。
        //在ObjectDecoder的构造函数中传入了一个ClassResolver 对象，用于解析类名并加载相应的类。
        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
            @Override
            public Class<?> resolve(String className) throws ClassNotFoundException {
                return Class.forName(className);
            }
        }));

        pipeline.addLast(new NettyClientHandler());
    }
}
