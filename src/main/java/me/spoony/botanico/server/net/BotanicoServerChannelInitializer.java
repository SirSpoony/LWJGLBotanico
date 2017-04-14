package me.spoony.botanico.server.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.compression.FastLzFrameDecoder;
import io.netty.handler.codec.compression.FastLzFrameEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import me.spoony.botanico.common.net.*;
import me.spoony.botanico.server.RemoteClient;

import java.util.Iterator;

/**
 * Created by Colten on 12/28/2016.
 */
public class BotanicoServerChannelInitializer extends ChannelInitializer<SocketChannel> {

  public ServerNetworkManager manager;

  public BotanicoServerChannelInitializer(ServerNetworkManager manager) {
    this.manager = manager;
  }

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(10));

    ch.pipeline().addLast(new FastLzFrameEncoder());
    ch.pipeline().addLast(new FastLzFrameDecoder());

    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1000000, 0, 4, 0, 4));
    ch.pipeline().addLast(new LengthFieldPrepender(4));

    ch.pipeline().addLast(new ChannelOutboundPacketEncoder() {
      @Override
      public void onCloseConnection(ChannelHandlerContext ctx, Throwable cause, String msg) {
        for (RemoteClient findrc : manager.getRemoteClients()) {
          if (findrc.isSelf(ctx.channel())) {
            findrc.closeConnection();
          }
        }
      }
    });

    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
          ByteBuf receivedBuf = (ByteBuf) msg;
          int id = receivedBuf.readInt();
          Packet packet = Packets.getPacket(id);
          packet.decode(PacketDecoder.start(receivedBuf));

          RemoteClient rc = null;
          for (RemoteClient findrc : manager.getRemoteClients()) {
            if (findrc.isSelf(ctx.channel())) {
              rc = findrc;
            }
          }

          if (packet instanceof IServerHandler) {
            manager.receivePacket(packet, rc);
          }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          ((ByteBuf) msg).release();
        }
      }


      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
      }
    });

    ch.closeFuture().addListener((ChannelFutureListener) future -> {
      Iterator<RemoteClient> rci = manager.getRemoteClients().iterator();
      while (rci.hasNext()) {
        RemoteClient findrc = rci.next();
        if (findrc.isSelf(ch)) {
          findrc.closeConnection();
          rci.remove();
        }
      }
    });

    RemoteClient rc = new RemoteClient(ch);
    manager.getRemoteClients().add(rc);

    System.out.println("[ServerChannelInitializer] Client connected at [" + ch.remoteAddress() + "]");
  }
}
