package kr.co.projecta.gis.map.util;

import java.io.IOException;
import java.io.InputStream;

public class UnixCommand {
	
	public static String execute(String cmd) throws Exception {
		return execute(cmd, false);
	}
	
	public static String executeWithPrint(String cmd) throws Exception {
		return execute(cmd, true);
	}
	
	private static String execute(String cmd, final boolean printalbe) throws Exception {
		
		class BufferThread extends Thread {
			InputStream in;
			InputStream err;
			StringBuffer inResult = new StringBuffer();
			StringBuffer errResult = new StringBuffer();
			boolean running = false;
			
			public BufferThread(
					InputStream in, 
					InputStream err)
			{
				this.in = in;
				this.err = err;
			}
			public String getInResult() {
				return inResult.toString();
			}
			public String getErrResult() {
				return errResult.toString();
			}
			public boolean isRunning() {
				return running;
			}
			public void run() {
				Thread printThread = null;
				
				if (printalbe) {
					printThread = new Thread(new Runnable() {
						public void run() {
							try {
								while (true) {
									Thread.sleep(1000);
									System.err.print("+");
								}
							} catch (InterruptedException e) {
								System.err.println();
								return;
							}
						}
					});
					printThread.start();
				}
				
				try {
					running = true;
					int count;
					while (!Thread.currentThread().isInterrupted()) {
						while ((count = in.available()) > 0) {
							byte [] buffer = new byte[count];
							in.read(buffer, 0, count);
							inResult.append(new String(buffer));
						}
						while ((count = err.available()) > 0) {
							byte [] buffer = new byte[count];
							err.read(buffer, 0, count);
							errResult.append(new String(buffer));
						}
					}
				} catch (IOException e) {
					
				} finally {
					try {
						in.close();
						err.close();
					} catch (IOException e) {
					} finally {
						running = false;
						if (printThread != null) {
							printThread.interrupt();
						}
					}
				}
			}
		};
		
		Process process = null;
		BufferThread thread = null;
		boolean ok = false;
		try {
			process = Runtime.getRuntime().exec(cmd);
			process.getOutputStream().close();
			thread = new BufferThread(
					process.getInputStream(), 
					process.getErrorStream());
			
			thread.start();
			
			while (!thread.isRunning()) {
				Thread.sleep(10);
			}
			
			process.waitFor();
			
			ok = (process.exitValue() == 0) ? true : false;
			
			thread.interrupt();
			while (thread.isRunning()) {
				Thread.sleep(10);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (process != null) process.destroy();
		}
		return (ok) ? 
				thread.getInResult() : 
				thread.getErrResult();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(UnixCommand.execute("cat /etc/passwd", false));
		System.out.println(UnixCommand.execute("unzip", false));

	}
}
