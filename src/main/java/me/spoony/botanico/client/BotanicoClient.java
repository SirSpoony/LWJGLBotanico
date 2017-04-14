package me.spoony.botanico.client;

import com.google.common.base.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.compression.FastLzFrameDecoder;
import io.netty.handler.codec.compression.FastLzFrameEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import me.spoony.botanico.client.views.menu.KickedView;
import me.spoony.botanico.common.net.*;
import me.spoony.botanico.client.views.GameView;

/**
 * Created by Colten on 11/19/2016.
 */
public class BotanicoClient {
    public GameView gameView;
    public Channel channel;
    public ClientConnectionConfig config;

    private ClientPlane localLevel;

    public ClientPlane getLocalLevel() {
        return localLevel;
    }

    public ClientEntityPlayer getLocalPlayer() {
        return localLevel.getLocalPlayer();
    }

    public ClientPacketHandler packetHandler;

    NioEventLoopGroup workerGroup;

    public BotanicoClient(GameView gameView, ClientConnectionConfig config) {
        this.gameView = gameView;

        this.config = config != null ? config : new ClientConnectionConfig("{self}", "localhost");

        packetHandler = new ClientPacketHandler(this);

        localLevel = new ClientPlane(this);
    }

    public ChannelFuture begin() {
        final BotanicoClient self = this;

        workerGroup = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
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
                                ctx.close();
                                log("Kicked from the server!");
                                BotanicoGame.setView(new KickedView(msg));
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

                                    if (packet instanceof IClientHandler) {
                                        ((IClientHandler) packet).onReceive(self);
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
                            log("CHANNEL CLOSED");
                            log("Connection to server closed");
                            BotanicoGame.setView(new KickedView("Connection to server closed"));
                        });

                        channel = ch;
                    }
                });

        final ChannelFuture channelFuture = b.connect(config.serverAddress, config.port);

        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                CPacketJoinRequest pjg = new CPacketJoinRequest();
                pjg.name = config.playerName;
                sendPacket(pjg).addListener((ChannelFutureListener) future1 -> {
                    if (future1.isSuccess()) {
                        log("Connecting Client: SUCCESS");
                    } else {
                        log("Connecting Client [Packet Join]: FAILED");
                    }
                });
            } else {
                future.cause().printStackTrace();
                log("Connecting Client: FAILED");
                close();
            }
        });

        return channelFuture;
    }

    public ChannelFuture sendPacket(Packet packet) {
        Preconditions.checkNotNull(channel != null, "Channel cannot equal null to send packet");

        return channel.writeAndFlush(packet);
    }

    public void log(Object obj) {
        System.out.println("[Client] " + obj.toString());
    }

    public void close() {
        channel.close();
        workerGroup.shutdownGracefully();
    }

    public void sendHeartbeatPolo() {
        sendPacket(new CPacketHeartbeatPolo());
    }
}
