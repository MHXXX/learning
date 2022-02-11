package com.xmh;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * .
 *
 * @author 谢明辉
 * @date 2022/1/12
 */

public class UdpClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        protected void initChannel(NioDatagramChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MyHandler());
                        }
                    });
            Channel channel = bootstrap.bind(40002).sync().channel();
            InetSocketAddress address = new InetSocketAddress("localhost", 39999);
            ByteBuf byteBuf = Unpooled.copiedBuffer("你好服务器".getBytes(Charset.forName("GB2312")));
            channel.writeAndFlush(new DatagramPacket(byteBuf, address)).sync();
            channel.closeFuture().await();
            System.out.println("===========================================");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    private static class MyHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
            System.out.println("服务端接收到消息：" + packet.content().toString(Charset.forName("GB2312")));

            ByteBuf byteBuf = Unpooled.copiedBuffer("你好服务器".getBytes(Charset.forName("GB2312")));
            ctx.writeAndFlush(new DatagramPacket(byteBuf, packet.sender()));

        }
    }

}
