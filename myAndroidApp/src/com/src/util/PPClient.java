package com.src.util;

import java.io.FileOutputStream;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;


public class PPClient {
	private Socket mSocket;

	public PPClient() {
		mSocket = new Socket();
	}

	public void connect(String host, String port) {
		InetSocketAddress addr = new InetSocketAddress("192.168.16.169", 5223);
		try {
			mSocket.connect(addr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void test() {

		// 连接未知的主机地址222.168.30.101", 80端口 返回RST JAVA异常connect refuse
		// 连接未知的主机地址222.168.232.101", 80端口 无返回 JAVA异常 连接超时
		InetSocketAddress addr = new InetSocketAddress("220.181.111.148", 80);
		

//		InetSocketAddress addr = new InetSocketAddress("192.168.99.22", 80);
		
		OutputStream outstream = null;
		InputStream istream = null;
		FileOutputStream fout = null;

		try {
			mSocket.setSoTimeout(5 * 1000);
			
			//设置接受滑动窗口的大小(通知窗口大小)
			mSocket.setReceiveBufferSize(4096);
			mSocket.setKeepAlive(false);
			//设置发送缓存区大小
			mSocket.setSendBufferSize(4096);
			mSocket.connect(addr, 5 * 1000);
			InetSocketAddress selfAddr = (InetSocketAddress) mSocket
					.getLocalSocketAddress();
			String host = selfAddr.getHostName();
			int port = selfAddr.getPort();
			System.out.println("----");
			System.out.println(host);
			System.out.println("----");
			System.out.println(port);
			System.out.println("----");

			// URL url = new URL("http://220.181.112.143");
			// HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			// conn.setRequestMethod("GET" );
			// conn.setConnectTimeout(5000 ); // 5��

			// InputStream inputStream = conn.getInputStream();
			// OutputStream outStream = conn.getOutputStream();
			// byte[] byteBuf = new byte[4096];
			// inputStream.read(byteBuf, 0, 4096);
			//
			// System.out.println(byteBuf.toString());

			for (int i = 0; i < 1; ++i) {
				outstream = mSocket.getOutputStream();
//				String str = "GET / HTTP/1.1\r\n"
//						+ "Accept: application/x-ms-application, image/jpeg, application/xaml+xml, image/gif, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n"
//						+ "Accept-Language: zh-CN\r\n"
//						+ "User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; MALN; InfoPath.2; .NET4.0C)\r\n"
//						+ "Host: www.baidu.com\r\n"
//					//	+ "Connection: keep-alive\r\n"
//						+ "Cookie: BAIDUID=C4F3CB88B665B7634A42B0DF4C8E06DF:FG=1; MCITY=-%3A\r\n\r\n";

				
				String str="GET / HTTP/1.1\r\n" +
						"User-Agent: Java/1.6.0_38-ea\r\n"+
						"Host: 220.181.111.148\r\n"+
						"Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n"+
						"Connection: keep-alive\r\n\r\n";
				
				
				// 修改Connection keep-alive 无法收到文件流结尾 -1 标记 
				// 读取超时 然后能进行正常的4路关闭
				// 改Connection close 可以收到文件流结尾 -1 标记 
				// 客户端发送FIN 服务器只回应ACK 不再发送FIN
				
				byte[] sendbuf = str.getBytes();
				outstream.write(sendbuf);
				outstream.flush();
				int isRead = 0;
				istream = mSocket.getInputStream();
				
				System.out.println(istream.getClass().getName());
				
				// InputStreamReader ireader = new InputStreamReader(istream,
				// "gbk");

//				fout = new FileOutputStream("ret.txt");
				// OutputStreamWriter owrite = new OutputStreamWriter(fout);

				// ByteArrayOutputStream outStream = new
				// ByteArrayOutputStream();
				// outStream.close();

				/**
				 * HTTP 协议是用 Content-Encoding对内容进行编码 如果Accept-Encoding是gzip
				 * 则返回是用gzip进行解压 百度服务器不直接断开连接 需要自己处理 或者在Connection 里面是用close主动断开
				 * 或者根据内容大小进行断开 否则会出现读取超时(如果需要保持连接的话 读取超时 可以作为重连的一个标志)
				 */

				// socket 读取不到 -1 （ 服务器断开连接收到结束）
				// 服务器没有发送EOF
				byte[] buffer = new byte[4096];
				isRead = istream.read(buffer, 0, 1024);
				while (isRead != -1) {
//					fout.write(buffer, 0, isRead);
					String ret = new String(buffer, 0, isRead, "gbk");
//					fout.flush();
					
					isRead = istream.read(buffer, 0, 1024);
					
					System.out.println(ret);
				}

				// owrite.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				
				//shutdown也会发送fin
//				mSocket.shutdownOutput();
				
				// 如果对方已经关闭连接 shutdowninput会报错 因为连接已关闭
				// 调用这个 可以防止read超时 调用这个不发送FIN
				// 关闭输入不发送 关闭输出发送FIN
				mSocket.shutdownInput();
				
				if (outstream != null && istream != null) {
					
					//关闭输出流也会导致发送FIN
					outstream.close();
					
					//socket直接关闭输入流 发送FIN
					istream.close();
//					fout.close();
				}


				
				//close 发送FIN 报文分组
				mSocket.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void test2() throws IOException {
		HttpURLConnection httpconn = null;
		InputStream inPs = null;
		OutputStream outPs = null;
		try {
			
			//去掉长连接属性 可以马上关闭当前HTTP连接
			// 客户端发送带有 Connection: close 属性
			//服务器会执行主动关闭
//			System.setProperty("http.keepAlive", "false");
			
			//默认情况下 客户端发送的属性包带有
			//Connection: keep-alive
			//需要客户端主动关闭服务器 进行4路关闭
			//HttpURLConnection 无论是否执行disconnect
			//都将发送FIN 延迟10秒或者5秒
			
			URL url = new URL("HTTP", "220.181.111.148", 80, "/");
			httpconn = (HttpURLConnection) url.openConnection();
			httpconn.setConnectTimeout(10 * 1000);
			httpconn.setReadTimeout(10 * 1000);
			httpconn.setRequestMethod("GET");

			// 设置true 发送post请求
			httpconn.setDoOutput(true);

			// httpconn.addRequestProperty("Connection", "keep-alive");

			// 设置false 不允许调用 getOutputStream
			// outPs = httpconn.getOutputStream();

			int responseCode = httpconn.getResponseCode();

			System.out.println("responseCode" + responseCode);

			if (responseCode != 200) {

			} else {
				inPs = httpconn.getInputStream();
				
				System.out.println("inPs="+ inPs.getClass().getName());
				
				//为什么这里可以读取循环（为什么啊）
				
				byte[] bytes = getByteByStream(inPs);
				String receiveData = new String(bytes, "UTF-8");
				System.out.println(receiveData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (inPs != null) {
					inPs.close();
					inPs = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (outPs != null) {
					outPs.close();
					outPs = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 发送fin 4路关闭
			if (httpconn != null) {
				httpconn.disconnect();
				httpconn = null;
			}
		}
	}
	
	public static byte[] getByteByStream(InputStream is) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte[] buf = new byte[1024];
			int num;
			num = is.read(buf, 0, buf.length);
			while (num != -1) {
				out.write(buf, 0, num);
				num = is.read(buf, 0, buf.length);
			}
			out.close();
			return out.toByteArray();
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	

	public static void main(String[] args) {

		// new Thread(){
		// @Override
		// public void run() {
		// super.run();
		//
		// PPClient client = new PPClient();
		// try {
		// client.test2();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }.start();

		PPClient client = new PPClient();
		client.test();
//		 try {
//		 client.test2();
//		 } catch (IOException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }
//		 
		 
//		 FileInputStream foutput;
//	 
//		 try {
//			 foutput = new FileInputStream("diff-1.xml");
//			byte[] bigbyte = getByteByStream(foutput);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		String str = "";
		synchronized (str) {
			try {
				str.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// client.test();
	}

}
