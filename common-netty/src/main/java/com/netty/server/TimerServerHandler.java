package com.netty.server;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * Created by jincong on 16/4/10.
 */
public class TimerServerHandler extends ChannelHandlerAdapter{
    int counter=0;

    @Override
    public void channelRead(ChannelHandlerContext context,Object msg) throws Exception{
        String body=(String)msg;
        System.out.println("This is "+counter+"times receive client:["+body+"]");
        body+="$_";
        ByteBuf buf=Unpooled.copiedBuffer(body.getBytes());
        context.writeAndFlush(buf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx,Throwable throwable){
        ctx.close();
    }
}
