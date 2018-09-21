package com.pancm.nio.mina.demo1;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author ZERO
 * @version 2017-3-28 上午10:44:55
 * 设置编码解码器
 */

public class MyTextLineCodecDecoder implements ProtocolDecoder {
		 private static Logger logger = Logger.getLogger(MyTextLineCodecDecoder.class);
		   
		 private Charset charset; // 编码格式
		   
		 private String delimiter;     // 文本分隔符
		 private IoBuffer delimBuf; // 文本分割符匹配的变量
		   
		 // 定义常量值，作为每个IoSession中保存解码任务的key值
		 private static String CONTEXT = MyTextLineCodecDecoder.class.getName() + ".context";  
		
		 public MyTextLineCodecDecoder(Charset charset,String delimiter){
			 this.charset=charset;
			 this.delimiter=delimiter;
		 }
	    
	 
		@Override
		public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
				throws Exception {
			Context ctx = getContext(session);
	        if (delimiter == null || "".equals(delimiter)) {
	            // 如果文本换行符未指定，使用默认值
	            delimiter = "\r\n";
	        }
	
	        if (charset == null) {
	            charset = Charset.forName("utf-8");
	        }
	        decodeNormal(ctx, in, out);
			
		}
	
		  private void decodeNormal(Context ctx, IoBuffer in, ProtocolDecoderOutput out) throws CharacterCodingException {
		       
		        // 取出未完成任务中已经匹配的文本换行符的个数
		        int matchCount = ctx.getMatchCount();
		   
		        // 设置匹配文本换行符的IoBuffer变量
		        if (delimBuf == null) {
		            IoBuffer tmp = IoBuffer.allocate(2).setAutoExpand(true);
		            tmp.putString(delimiter, charset.newEncoder());
		            tmp.flip();
		            delimBuf = tmp;
		        }

		        //解码的IoBuffer中数据的原始信息
		        int oldPos = in.position();  //输出值为0
		        int oldLimit = in.limit();   //输出值为1
		               
		        logger.info("******************************************************************************");       
		        logger.info("开始进入解码方法-----------------------------------------------------------------");
		        logger.info("");
		        logger.info("init Start--------------------------------------------------------------------");
		        logger.info("in.postion() = "+oldPos);
		        logger.info("in.Limit() = "+oldLimit);
		        logger.info("in.capacity() = "+in.capacity());
		        logger.info("matchCount = "+matchCount);
		        logger.info("init End---------------------------------------------------------------------");
		        logger.info("");
		       
		        //变量解码的IoBuffer
		        while (in.hasRemaining()) {           
		           
		            byte b = in.get();           
		            logger.info("");
		            logger.info("输入进来的字符为 = "+(char)b+",对应的ascii值 = "+b);
		            logger.info("in.position() = "+in.position()+",in.limit() = "+in.limit());
		            logger.info("");
		           
		            //当b的ascii值为13,10 即为\r,\n时,会进入下述if语句
		            if (delimBuf.get(matchCount) == b) {
		               
		                // b='\r'时,matchCount=1, b='\n'时,matchCount=2
		                matchCount++;   
		               
		                logger.info("matchCount = "+matchCount);
		                //当前匹配到字节个数与文本换行符字节个数相同，即 b='\n'时
		                //此时matchCount=2, delimBuf.limit()=2
		               
		                if (matchCount == delimBuf.limit()) {                       
		                   
		                        // 获得当前匹配到的position（position前所有数据有效）
		                        int pos = in.position();    //值为2           
		                        logger.info("pos = "+pos);
		                       
		                        in.limit(pos); //值为2
		                        // position回到原始位置
		                        in.position(oldPos); //值为0
		                       
		                        // 追加到Context对象未完成数据后面
		                        ctx.append(in); //将 \r\n这两个字符添加到 ctx.getBuf()中
		                       
		                        // in中匹配结束后剩余数据
		                        in.limit(oldLimit); //值为2
		                        in.position(pos); //值为2
		                                           
		                        IoBuffer buf = ctx.getBuf(); //此时是得到  he\r\n                       
		                        buf.flip(); //此时 buf.position=0,buf.limit()=4            
		                       
		                        buf.limit(buf.limit() - matchCount);  //4-2 = 2
		                        try{
		                            // 输出解码内容 ,即 he
		                            out.write(buf.getString(ctx.getDecoder()));
		                        }
		                        finally {
		                            buf.clear(); // 释放缓存空间
		                        }       
		                   
		                        matchCount = 0;
		                       
		                    }
		            }else { //h字符,e字符时,均会进入 此else逻辑判断中   

		                //把in中未解码内容放回buf中
		                //下面会在 输入的字符不是 \r\n时会需要保存使用
		                in.position(oldPos);
		                ctx.append(in);
		                ctx.setMatchCount(matchCount);
		            }
		        }           
		    }
		   
		    // 从IoSession中获取Context对象
		    private Context getContext(IoSession session) {
		        Context ctx;
		        ctx = (Context) session.getAttribute(CONTEXT);

		        if (ctx == null) {
		            ctx = new Context();
		            session.setAttribute(CONTEXT, ctx);
		        }
		        return ctx;
		    }
		   
		    public void dispose(IoSession arg0) throws Exception {
		        // TODO Auto-generated method stub

		    }

		    public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
		            throws Exception {
		        // TODO Auto-generated method stub
		    }


		    // 内部类，保存IoSession解码时未完成的任务
		    private class Context {
		       
		        private CharsetDecoder decoder;
		        private IoBuffer buf;
		        // 保存真实解码内容
		        private int matchCount = 0; // 匹配到的文本换行符个数
		        private Context() {
		            decoder = charset.newDecoder();
		            buf = IoBuffer.allocate(80).setAutoExpand(true);
		        }

		        // 重置
		        public void reset() {
		            matchCount = 0;
		            decoder.reset();
		        }

		        // 追加数据
		        public void append(IoBuffer in) {
		            getBuf().put(in);
		        }

		        public CharsetDecoder getDecoder() {
		            return decoder;
		        }

		        public IoBuffer getBuf() {
		            return buf;
		        }

		        public int getMatchCount() {
		            return matchCount;
		        }

		        public void setMatchCount(int matchCount) {
		            this.matchCount = matchCount;
		        }
		    }    
	
	
}
