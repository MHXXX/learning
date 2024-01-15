package com.xmh.tests;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetAddress;
import java.nio.charset.Charset;

/**
 * .
 *
 * @author 谢明辉
 * @date 2022/1/12
 */

public class UdpServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    // 主线程处理
                    .channel(NioDatagramChannel.class)
                    // 广播
                    .option(ChannelOption.SO_BROADCAST, true)
                    // 设置读缓冲区为2M
                    .option(ChannelOption.SO_RCVBUF, 2048 * 1024)
                    // 设置写缓冲区为1M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {

                        @Override
                        protected void initChannel(NioDatagramChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NioEventLoopGroup(), new MyHandler());
                        }
                    });

            ChannelFuture f = bootstrap.bind(39999).sync();
            System.out.println("服务器正在监听......");
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


    private static class MyHandler extends SimpleChannelInboundHandler<DatagramPacket> {


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
            System.out.println("服务端接收到消息：" + packet.content().toString(Charset.forName("GB2312")));
            ByteBuf byteBuf = Unpooled.copiedBuffer("你好客户端".getBytes(Charset.forName("GB2312")));
            ctx.writeAndFlush(new DatagramPacket(byteBuf, packet.sender()));
        }
    }

}
