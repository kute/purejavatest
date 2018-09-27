package com.kute.netty.demo.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by kute on 2018/07/26 22:37
 */

/**
 * @Sharable 标注一个channel handler可以被多个channel安全地共享，可以被添加到多个ChannelPipeline中，可以绑定到多个ChannelHandlerContext实例
 */
@ChannelHandler.Sharable
public class EchoServerChannelHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EchoServerChannelHandler.class);

    public EchoServerChannelHandler() {
        super();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelRegistered: channelid={}", ctx.channel().id());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelUnregistered: channelid={}", ctx.channel().id());
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive: channelid={}", ctx.channel().id());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive: channelid={}", ctx.channel().id());
        super.channelInactive(ctx);
    }

    /**
     * 传入channel的每条消息都会调用
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf in = (ByteBuf) msg;
        logger.info("channelRead msg:{}", in.toString(CharsetUtil.UTF_8));
        in.writeCharSequence("message by server", CharsetUtil.UTF_8);
        // write 异步
        ctx.write(in);
    }

    /**
     * 读取完最后一条消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelReadComplete: channelid={}", ctx.channel().id());
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("userEventTriggered: channelid={}", ctx.channel().id());
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelWritabilityChanged: channelid={}", ctx.channel().id());
        super.channelWritabilityChanged(ctx);
    }

    /**
     * 异常处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("exceptionCaught channelid={}", ctx.channel().id());
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void ensureNotSharable() {
        logger.info("ensureNotSharable");
        super.ensureNotSharable();
    }

    /**
     * 当添加了  @Sharable 时返回true
     *
     * @return
     */
    @Override
    public boolean isSharable() {
        logger.info("isSharable: {}", super.isSharable());
        return super.isSharable();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("handlerAdded: channelid={}", ctx.channel().id());
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("handlerRemoved: channelid={}", ctx.channel().id());
        super.handlerRemoved(ctx);
    }
}
