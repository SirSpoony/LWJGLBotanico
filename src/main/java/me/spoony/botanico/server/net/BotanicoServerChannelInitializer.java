package me.spoony.botanico.server.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
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
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.net.*;

import java.util.Iterator;
import me.spoony.botanico.common.net.client.CPacketJoinRequest;
import me.spoony.botanico.common.net.server.SPacketMessage;
import me.spoony.botanico.common.net.server.SPacketNewEntity;
import me.spoony.botanico.common.net.server.SPacketPlayerEID;
import me.spoony.botanico.server.RemoteEntityPlayer;

/**
 * Created by Colten on 12/28/2016.
 */
public class BotanicoServerChannelInitializer extends ChannelInitializer<SocketChannel> {

  public ServerNetworkManager manager;

  public BotanicoServerChannelInitializer(ServerNetworkManager manager) {
    this.manager = manager;
  }

  @Override
  protected void initChannel(SocketChannel newChannel) throws Exception {
    newChannel.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(20));

    newChannel.pipeline().addLast(new FastLzFrameEncoder());
    newChannel.pipeline().addLast(new FastLzFrameDecoder());

    newChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1000000, 0, 4, 0, 4));
    newChannel.pipeline().addLast(new LengthFieldPrepender(4));

    newChannel.pipeline().addLast(new ChannelOutboundPacketEncoder() {
      @Override
      public void onCloseConnection(ChannelHandlerContext ctx, Throwable cause, String msg) {

      }
    });

    newChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
          ByteBuf receivedBuf = (ByteBuf) msg;
          int id = receivedBuf.readInt();
          Packet packet = Packets.getPacket(id);
          packet.decode(PacketDecoder.start(receivedBuf));

          if (packet instanceof CPacketJoinRequest) {
            CPacketJoinRequest request = (CPacketJoinRequest) packet;

            // when a new client sends a join request, this is where it is returned
            RemoteEntityPlayer eplayer = new RemoteEntityPlayer(request.name,
                manager.server.level.getOverworld()); // todo possibility of being in any plane
            eplayer.getPlane().addEntity(eplayer);

            manager.initializePlayer(ctx.channel(), eplayer);

            SPacketPlayerEID peid = new SPacketPlayerEID();
            peid.eid = eplayer.eid;
            manager.sendPacket(peid, eplayer);

            for (Entity ent : manager.server.level.getOverworld().getEntities()) {
              SPacketNewEntity temppne = new SPacketNewEntity();
              temppne.type = ent.getTypeID();
              temppne.eid = ent.eid;
              temppne.x = ent.posX;
              temppne.y = ent.posY;
              manager.sendPacket(temppne, ctx.channel());
            }

            eplayer.onJoin();

          } else {
            if (packet instanceof IServerHandler) {
              manager.receivePacket(packet, manager.players.inverse().get(ctx.channel()));
            }
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

    newChannel.closeFuture().addListener((ChannelFutureListener) future -> {
      manager.players.inverse().remove(newChannel).onLeave();
      manager.preChannels.remove(newChannel);
    });

    manager.preChannels.add(newChannel);

    System.out.println(
        "[ServerChannelInitializer] Client connected at [" + newChannel.remoteAddress() + "]");
  }
}
