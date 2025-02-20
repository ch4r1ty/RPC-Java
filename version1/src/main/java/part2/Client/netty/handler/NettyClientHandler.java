package part2.Client.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import part2.common.Message.RpcResponse;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/26 17:29
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {  //说明这个 Handler 只处理 RpcResponse 类型的消息
    //这是SimpleChannelInboundHandler的核心方法，用于读取服务端返回的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        // 接收到response, 给channel设计别名，让sendRequest里读取response
        // 将服务端返回的RpcResponse绑定到当前Channel的属性中，以便后续逻辑能通过Channel获取该响应数据
        AttributeKey<RpcResponse> key = AttributeKey.valueOf("RPCResponse");
        ctx.channel().attr(key).set(response);
        //关闭当前channel
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常处理
        cause.printStackTrace();
        ctx.close();
    }
}
