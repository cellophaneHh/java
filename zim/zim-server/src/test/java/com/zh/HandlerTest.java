package com.zh;

import com.zh.model.DataWrapper;
import com.zh.model.LoginRequestDTO;
import com.zh.netty.handler.IdleHandler;
import com.zh.netty.handler.LoginHandler;
import com.zh.netty.handler.SecondDecoderHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhangheng
 * @date 2019/12/24
 */
public class HandlerTest {

    @Test
    public void testSecondDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline()
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new SecondDecoderHandler())
                .addLast(new TestLoginHandler());
        DataWrapper dataWrapper = DataWrapper.newBuilder()
                .setType(DataWrapper.DataType.login_request)
                .setLoginRequestDTO(LoginRequestDTO.newBuilder()
                        .setCode("测试code")
                        .setPassword("测试password")
                        .build())
                .build();
        channel.writeInbound(dataWrapper);
        Object object = channel.readInbound();
        Assert.assertNull(object);
    }

    static class TestLoginHandler extends SimpleChannelInboundHandler<LoginRequestDTO> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, LoginRequestDTO msg) throws Exception {
            System.out.println("收到登录请求");
            System.out.println(msg.getCode() + "-" + msg.getPassword());
        }
    }
}