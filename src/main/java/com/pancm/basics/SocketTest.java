package com.pancm.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @Title: SocketTest
 * @Description:Socket测试
 * @Version:1.0.0
 * @author pancm
 * @date 2017年11月16日
 */
public class SocketTest {
	/** 端口 */
	private static final int portNumber = 6789;

	private static String request = null;
	private static String response = null;

	public static void main(String[] args) {
		request = "Hello";
		socketTest(request);

	}

	/**
	 * socket简单连接测试
	 * 
	 * @param request
	 */
	private static void socketTest(String request) {
		try {
			// 创建一个新的 ServerSocket， 用 以监听指定端口上的连接请求
			ServerSocket serverSocket = new ServerSocket(portNumber);
			// 对 accept()方法的调用将被阻塞，直到一个连接建立
			Socket clientSocket = serverSocket.accept();
			// 这些流对象都派生于该套接字的流对象
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
					true);
			// 循环处理开始
			while ((request = in.readLine()) != null) {
				// 如果客户端发送了“Done”，则退出处理循环
				if ("Done".equals(request)) {
					break;
				}
				// 请求被传递给服务器的处理方法
				response = processRequest(request);
				System.out.println("response:" + response);
				// 服务器的响应被发送给了客户端
				out.println(response);
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private static String processRequest(String request) {
		return "hi";
	}

	/**
	 * socket简单并发测试
	 * @param port
	 * @throws IOException
	 */
	public void serve(int port) throws IOException {
		// 将服务器绑定到指定端口
		final ServerSocket socket = new ServerSocket(port);
		try {
			for (;;) {
				// 接受连接
				final Socket clientSocket = socket.accept();
				System.out.println("Accepted connection from " + clientSocket);
				// 创建一个新的线程来处理该连接
				new Thread(new Runnable() {
					@Override
					public void run() {
						OutputStream out;
						try {
							out = clientSocket.getOutputStream();
							// 将消息写给已连接的客户端
							out.write("Hi!\r\n".getBytes(Charset
									.forName("UTF-8")));
							out.flush();
							clientSocket.close();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								clientSocket.close();
							} catch (IOException ex) {
								// ignore on close
							}
						}
					}
				}).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * socket 实现非阻塞 I/O
	 * @param port
	 * @throws IOException
	 */
	public void serve1(int port) throws IOException {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		ServerSocket ssocket = serverChannel.socket();
		InetSocketAddress address = new InetSocketAddress(port);
		ssocket.bind(address);
		//打开Selector来处理Channel
		Selector selector = Selector.open();
		//将ServerSocket 注册到Selector以接受连接
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
		for (;;) {
			try {
				//等待需要处理的新事件； 阻 塞 将一直持续到下一个传入事件
				selector.select();
			} catch (IOException ex) {
				ex.printStackTrace();
				break;
			}
			//获取所有接收事件的Selection-Key实例
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				try {
					//检查事件是否是一个新的已经就绪可以被接受的连接
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						//接受客户端，并将它注册到选择器
						client.register(selector, SelectionKey.OP_WRITE
								| SelectionKey.OP_READ, msg.duplicate());
						System.out.println("Accepted connection from " + client);
					}
					//检查套接字是否已经准备好写数据
					if (key.isWritable()) {
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						//将数据写到已连接的客户端
						while (buffer.hasRemaining()) {
							if (client.write(buffer) == 0) {
								break;
							}
						}
						client.close();
					}
				} catch (IOException ex) {
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException cex) {
						// ignore on close
					}
				}
			}
		}
	}

}
