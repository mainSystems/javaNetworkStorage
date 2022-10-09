package main.systems.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import systems.common.Message;

import java.util.function.Consumer;

public class ClientHandler extends SimpleChannelInboundHandler<Message> {

    private final Message message;
    private final Consumer<Message> callback;
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

    public ClientHandler(Message message, Consumer<Message> callback) {
        this.message = message;
        this.callback = callback;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //when connected to server
        ctx.writeAndFlush(message);
    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//        callback.accept(msg);
//        System.out.println(msg);
//    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        callback.accept(msg);
        System.out.println(msg.getCommandType());
        System.out.println(msg.getUsername());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // what we do if take exception, without this method we can get oom
        logger.error("We got exception");
        cause.printStackTrace();
    }
}
