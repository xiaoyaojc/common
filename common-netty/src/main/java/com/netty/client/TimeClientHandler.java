package com.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

/**
 * Created by jincong on 16/4/11.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    private static final Logger logger = Logger.getLogger(TimeClientHandler.class);

    private int counter;

    static final String EQ="Hi,xiaoyao.$_";

    public TimeClientHandler(){

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        for (int i=0;i<10;i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(EQ.getBytes()));
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        System.out.println("This is "+ ++counter+" times receive server :["+msg+"]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        logger.warn(cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
