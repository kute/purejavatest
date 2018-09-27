package com.kute.netty.demo.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

/**
 * created by kute on 2018/07/28 16:56
 */
public class EchoServer {

    public static void main(String[] args) throws InterruptedException, SSLException, CertificateException {
        new EchoServer(false, 5007).start();
    }

    private boolean ssl;
    private int port;

    public EchoServer(boolean ssl, int port) {
        this.ssl = ssl;
        this.port = port;
    }

    public void start() throws InterruptedException, CertificateException, SSLException {

        final SslContext sslContext;
        if(ssl) {
            SelfSignedCertificate certificate = new SelfSignedCertificate();
            sslContext = SslContextBuilder.forServer(certificate.certificate(), certificate.privateKey()).build();
        } else {
            sslContext = null;
        }

        EventLoopGroup parentGroup = new NioEventLoopGroup(1);
        EventLoopGroup childGroup = new NioEventLoopGroup();
        final EchoServerChannelHandler serverChannelHandler = new EchoServerChannelHandler();

        try{
            ServerBootstrap server = new ServerBootstrap()
                    .group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 100)
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    .localAddress(new InetSocketAddress(this.port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            if(null != sslContext) {
                                pipeline.addLast(sslContext.newHandler(ch.alloc()));
                            }
                            pipeline.addLast(serverChannelHandler);
                        }
                    });
            ChannelFuture channelFuture = server.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally{
            parentGroup.shutdownGracefully().sync();
            childGroup.shutdownGracefully().sync();
        }

    }
}
