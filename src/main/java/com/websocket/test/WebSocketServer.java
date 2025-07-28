package com.websocket.test;

/**
 * @ClassName:WebSocketServer
 * @author: qm
 * @Description:
 * @date:2025-07-23
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServer {
    private final int port;
    private final ConcurrentHashMap<String, Channel> clients = new ConcurrentHashMap<>();

    public WebSocketServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // Boss group for accepting connections
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // Worker group for handling connections

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true));
                            pipeline.addLast(new WebSocketFrameHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            ChannelFuture f = b.bind(port).sync();
            System.out.println("WebSocket server started on port " + port);

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            String clientId = ctx.channel().id().asLongText();
            clients.put(clientId, ctx.channel());
            System.out.println("Client connected: " + clientId + ", Total clients: " + clients.size());
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) {
            String clientId = ctx.channel().id().asLongText();
            clients.remove(clientId);
            System.out.println("Client disconnected: " + clientId + ", Total clients: " + clients.size());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
            String message = frame.text();
            System.out.println("Received: " + message + " from " + ctx.channel().id().asLongText());
            // Echo message back to the client
            ctx.channel().writeAndFlush(new TextWebSocketFrame("Server: " + message));
            // Broadcast to all clients (optional)
            clients.values().forEach(client -> {
                if (client.isActive() && client != ctx.channel()) {
                    client.writeAndFlush(new TextWebSocketFrame(message));
                }
            });
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        new WebSocketServer(port).start();
    }
}