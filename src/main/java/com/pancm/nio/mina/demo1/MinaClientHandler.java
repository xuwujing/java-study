package com.pancm.nio.mina.demo1;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * @author ZERO
 * @version 2017-3-27 下午6:01:31
 * 业务逻辑过滤器代码
 */
public class MinaClientHandler extends IoHandlerAdapter {
    private static Logger logger = Logger.getLogger(MinaClientHandler.class);

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        String msg = message.toString();
        logger.info("客户端接收到的信息为：" + msg);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        logger.error("客户端发生异常...", cause);
    }
}
