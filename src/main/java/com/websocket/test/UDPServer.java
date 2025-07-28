package com.websocket.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:UDPServer
 * @author: qm
 * @Description:
 * @date:2025-07-28
 */
public class UDPServer {
    private static  final Map<InetSocketAddress, ChannelHandlerContext> clients = new HashMap<>();

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new SimpleChannelInboundHandler<DatagramPacket>(true) {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
                            String message = packet.content().toString(CharsetUtil.UTF_8);
                            InetSocketAddress sender = packet.sender();
                            System.out.println("Received from " + sender + ": " + message);

                            // 存储客户端信息
                            clients.putIfAbsent(sender, ctx);

                            // 示例：如果消息以 "@<ip>:<port> " 开头，则发送给指定客户端
                            if (message.startsWith("@")) {
                                String[] parts = message.split(" ", 2);
                                if (parts.length == 2) {
                                    String target = parts[0].substring(1); // 移除 @
                                    String content = parts[1];
                                    String[] addrParts = target.split(":");
                                    if (addrParts.length == 2) {
                                        String ip = addrParts[0];
                                        int port = Integer.parseInt(addrParts[1]);
                                        InetSocketAddress targetAddr = new InetSocketAddress(ip, port);
                                        DatagramPacket response = new DatagramPacket(
                                                Unpooled.copiedBuffer(content, CharsetUtil.UTF_8),
                                                targetAddr
                                        );
                                        ctx.writeAndFlush(response).addListener(future -> {
                                            if (future.isSuccess()) {
                                                System.out.println("Sent to " + targetAddr + ": " + content);
                                            }
                                        });
                                    }
                                }
                            }
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                            cause.printStackTrace();
                            ctx.close();
                        }
                    });

            // 绑定端口
            b.bind(22233).sync().channel().closeFuture().await();
        } finally {
            group.shutdownGracefully();
        }
    }
}
