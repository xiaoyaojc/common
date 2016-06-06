package com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


/**
 * Created by jincong on 16/4/10.
 */
public class TimerServer {
    public void bind(int port) throws Exception{
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossGroup,workGroup).channel(NioSctpServerChannel.class).option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new childChannelHandler());
            ChannelFuture f=b.bind(port).sync();
            f.channel().closeFuture().sync();
        }
        catch (Exception e){

        }
        finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class childChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ByteBuf delimiter= Unpooled.copiedBuffer("$_".getBytes());
            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new TimerServerHandler());
        }
    }

    public static void main(String [] args) throws Exception{
        int port=8080;
        if(args!=null&&args.length>0){
            try{
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }
        new TimerServer().bind(port);
    }


}
