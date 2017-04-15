package me.spoony.botanico.common.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.ReadTimeoutException;

/**
 * Created by Colten on 12/4/2016.
 */
public class ChannelOutboundPacketEncoder extends ChannelOutboundHandlerAdapter {

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
      throws Exception {
    try {
      ByteBuf bb = ctx.alloc().buffer();
      int id = Packets.getID(((Packet) msg).getClass());
      bb.writeInt(id); // write packet id first

      PacketEncoder packetEncoder = PacketEncoder.start(bb);
      ((Packet) msg).encode(packetEncoder);

      ctx.write(packetEncoder.finish(), promise);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    if (cause instanceof ReadTimeoutException) {
      System.out.println("[ChannelOutboundPacketEncoder] Timed out, closing connection");
      onCloseConnection(ctx, cause, "Connection timed out");
    } else {
      if (cause.getMessage().startsWith("An existing connection was forcibly closed")) {
        System.out.println("[ChannelOutboundPacketEncoder] Closing connection");
        onCloseConnection(ctx, cause,
            "An existing connection was forcibly closed by the remote host");
      } else {
        System.out.println("[ChannelOutboundPacketEncoder] Error");
        cause.printStackTrace();
      }
    }
  }

  @Override
  public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
    super.disconnect(ctx, promise);
    System.out.println("outboundpacketencoder disconnect");
  }

  @Override
  public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
    super.deregister(ctx, promise);
    System.out.println("outboundpacketencoder deregister");
  }

  public void onCloseConnection(ChannelHandlerContext ctx, Throwable cause, String message) {
  }
}
