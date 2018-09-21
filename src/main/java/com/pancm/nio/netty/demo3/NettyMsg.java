package com.pancm.nio.netty.demo3;

/**
 * 
* Description: Netty 自定义消息 
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyMsg {  
    

	/** 类型  系统编号 0xAB 表示A系统，0xBC 表示B系统  */
    private byte type;  
      
    /** 信息标志  0xAB 表示心跳包    0xBC 表示超时包  0xCD 业务信息包   */
    private byte flag;  
      
    /** 主题信息的长度 */ 
    private int length;  
      
    /** 主题信息  */ 
    private String body;  
      
    public NettyMsg() {  
          
    }  
    
    
    /**
     *  
     * @param type    类型  系统编号 0xAB 表示A系统，0xBC 表示B系统
     * @param flag    信息标志  0xAB 表示心跳包    0xBC 表示超时包  0xCD 业务信息包
     * @param length  主题信息的长度
     * @param body    主题信息
     */
    public NettyMsg(byte type, byte flag, int length, String body) {  
        this.type = type;  
        this.flag = flag;  
        this.length = length;  
        this.body = body;  
    }  
  
    public byte getType() {  
        return type;  
    }  
  
    public void setType(byte type) {  
        this.type = type;  
    }  
  
    public byte getFlag() {  
        return flag;  
    }  
  
    public void setFlag(byte flag) {  
        this.flag = flag;  
    }  
  
    public int getLength() {  
        return length;  
    }  
  
    public void setLength(int length) {  
        this.length = length;  
    }  
  
    public String getBody() {  
        return body;  
    }  
  
    public void setBody(String body) {  
        this.body = body;  
    }  
  
    /** 
	 * 重写toString方法，方便打印日志
	 */
	@Override
	public String toString() {
		return "NettyMsg [type=" + type + ", flag=" + flag + ", length="
				+ length + ", body=" + body + "]";
	}
}  
