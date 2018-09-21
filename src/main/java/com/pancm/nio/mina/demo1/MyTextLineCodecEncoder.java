package com.pancm.nio.mina.demo1;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * @author ZERO
 * @version 2017-3-28 上午11:30:55
 * 设置编码构造器
 */
public class MyTextLineCodecEncoder implements ProtocolEncoder{

	 private static Logger logger = Logger.getLogger(MyTextLineCodecEncoder.class);
	   
	    private Charset charset; // 编码格式
	    private String delimiter; // 文本分隔符
	    public MyTextLineCodecEncoder(Charset charset, String delimiter) {
	        this.charset = charset;
	        this.delimiter = delimiter;
	    }

	    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
	        logger.info("开始进入编码方法-----------------------------------------------------------------");
	        // 如果文本换行符未指定，使用默认值
	        if (delimiter == null || "".equals(delimiter)) {
	            delimiter = "\r\n";
	        }

	        if (charset == null) {
	            charset = Charset.forName("utf-8");
	        }

	        String value = message.toString();        
	        IoBuffer buf = IoBuffer.allocate(value.length()).setAutoExpand(true);       
	        //真实数据
	        buf.putString(value, charset.newEncoder()); 
	        //文本换行符
	        buf.putString(delimiter, charset.newEncoder());
	        buf.flip();
	        out.write(buf);
	    }

	    public void dispose(IoSession session) throws Exception {}
	
}
