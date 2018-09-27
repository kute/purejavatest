package com.kute.netty.demo.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by kute on 2018/07/28 18:14
 *
 * isSharable()
 *    -> handlerAdded()
 *        -> channelRegistered()
 *            -> channelActive()
 *                -> channelActive()
 *                    -> channelReadComplete()
 *                        -> channelInactive()
 *                            -> channelUnregistered()
 *                                -> handlerRemoved()
 *
 */
public class EchoClientChannelHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EchoClientChannelHandler.class);

    public EchoClientChannelHandler() {
        super();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelRegistered channelid={}", ctx.channel().id());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelUnregistered channelid={}", ctx.channel().id());
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive channelid={}", ctx.channel().id());
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty channel active message by client", CharsetUtil.UTF_8));

        for(int i=0; i<10; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("message from client, num=" + i, CharsetUtil.UTF_8));
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive channelid={}", ctx.channel().id());
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channelRead channelid={}", ctx.channel().id());

        ByteBuf in = (ByteBuf) msg;
        logger.info("channelRead msg:{}", in.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelReadComplete channelid={}", ctx.channel().id());
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("userEventTriggered channelid={}", ctx.channel().id());
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelWritabilityChanged channelid={}", ctx.channel().id());
        super.channelWritabilityChanged(ctx);
    }

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

    @Override
    public boolean isSharable() {
        logger.info("isSharable:{}", super.isSharable());
        return super.isSharable();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("handlerAdded channelid={}", ctx.channel().id());
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("handlerRemoved channelid={}", ctx.channel().id());
        super.handlerRemoved(ctx);
    }
}
