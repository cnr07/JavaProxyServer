
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
public class ProxyServer {
	static boolean isProxyOn=true;
	
	public static void main(String args[]) throws IOException  { 
		
		
		
		
		//Frame Oluþturma
        JFrame frame = new JFrame("Proxy Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        
        JPanel panel = new JPanel(); 
        JLabel label = new JLabel("Proxy Server is Running...");
        label.setFont(new Font("Arial",Font.ITALIC | Font.BOLD, 20));
        panel.setLayout(new GridBagLayout());
        panel.add(label); 
 
        //Menu componentleri oluþturma
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem(new AbstractAction("Start") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				label.setText("Proxy Server is Running...");
				start();
				
				
			}
        });
        JMenuItem m12 = new JMenuItem(new AbstractAction("Stop") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				label.setText("Proxy Server is Stopped...");
				stop();
				
			}
        });
        JMenuItem m13 = new JMenuItem(new AbstractAction("Report") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				report();
				
			}
        });
        JMenuItem m14 = new JMenuItem(new AbstractAction("Add host to filter") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addHostToFilter();	
			}
        });
        JMenuItem m15 = new JMenuItem(new AbstractAction("Display current filtered hosts") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				displayFilteredHosts();
			}
        });
        JMenuItem m16 = new JMenuItem(new AbstractAction("Exit") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				exit();	
			}
        });
        JMenuItem m21 = new JMenuItem(new AbstractAction("Can Erdoðan 20170702044") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("caner");
			}
        });
        JMenuItem m22 = new JMenuItem(new AbstractAction("can.erdogan@std.yeditepe.edu.tr") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("@std.yeditepe..");
			}
        });
        m1.add(m11);m1.add(m12);m1.add(m13);m1.add(m14);m1.add(m15);m1.add(m16);m2.add(m21);m2.add(m22);
        
        
        
 
        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.PAGE_START, mb);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
        
        ServerSocket welcomeSocket = new ServerSocket(8080);

		while (true) {
			
			Socket connectionSocket = welcomeSocket.accept();
			if(isProxyOn) {new ServerHandler(connectionSocket);}
			
		}
        
	}
	//////////////////////////////////////_FUNCTIONS_\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public static void start() {
		System.out.println("start");
		isProxyOn=true;
		
		
	}
	public static void stop() {
		System.out.println("stop");
		isProxyOn=false;
		
		
	}
	public static void report() {
		System.out.println("report");
	    Object result = JOptionPane.showInputDialog(null,"Enter client ip:","Report", JOptionPane.QUESTION_MESSAGE);
	    //System.out.println(result);
	    ////////
	    String clientIpaddr=result+"";
	    String logOfClient="";
	    File report = new File("report.txt");
		if(!report.exists()) {
			logOfClient="There is no report for client ip:"+clientIpaddr+" !!";
		}
		else {
			int flag1=0;int flagCounter=0;
			try {
				Scanner myReader = new Scanner(report);
			      while (myReader.hasNextLine()) {
			        String data = myReader.nextLine();
			        if(data.equals("----------")) {
			        	flag1=0;//okumayý býrak
			        	logOfClient+="----------\n";
			        }
			        if(clientIpaddr.contains(data)) {
			        	flag1=1;//okuyabilirsin
			        	flagCounter++;
			        }
			        if(flag1==1) {
			        	data+="\n";
			        	logOfClient+=data;
			        }
			      }
			      myReader.close();
			      if(flagCounter==0) {
			    	  logOfClient="There is no report for client ip:"+clientIpaddr+" !!";
			      }
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	    
	    
	    ////////
	    JTextArea area = new JTextArea(logOfClient);
        area.setRows(10);
        area.setColumns(50);
        area.setLineWrap(true);
        area.setEditable(false);
        JScrollPane pane = new JScrollPane(area);
        //
        JOptionPane.showConfirmDialog(null, pane, "Report",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    
	}
	public static void addHostToFilter() {
		System.out.println("add host to filter");
		Object result = JOptionPane.showInputDialog(null,"Enter domain/host to filter:","Add host to filter", JOptionPane.QUESTION_MESSAGE);
	    //System.out.println(result);
		
		String host=result+"";
	    
		if(!host.equals("null") && !host.equals("")) {
			File filteredHosts = new File("filteredHosts.txt");
			if(!filteredHosts.exists()) {
				try {
					filteredHosts.createNewFile();
					FileWriter fileWriter = new FileWriter(filteredHosts.getName());
					fileWriter.write(host+"\n");fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					
					FileWriter fileWriter = new FileWriter(filteredHosts.getName(),true);
					fileWriter.write(host+"\n");fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	    
	    
	    
	    
	    
	}
	public static void displayFilteredHosts() {
		System.out.println("display current filtered hosts");
		//pop up ile bastýr
		
		
		File filteredHosts = new File("filteredHosts.txt");
		if(!filteredHosts.exists()) {
			JOptionPane.showMessageDialog(null, "There is no filtered host!","Display Current Filtered Hosts",JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			String disp="";
			try {
				Scanner myReader = new Scanner(filteredHosts);
			      while (myReader.hasNextLine()) {
			        String data = myReader.nextLine();
			        String s=data+"\n";
			        disp+=s;
			      }
			      myReader.close();
			      if(disp.equals("")) {
			    	  JOptionPane.showMessageDialog(null, "There is no filtered host!","Display Current Filtered Hosts",JOptionPane.INFORMATION_MESSAGE);
			      }else {
			    	  JOptionPane.showMessageDialog(null, disp,"Display Current Filtered Hosts",JOptionPane.INFORMATION_MESSAGE);
			      }
			      
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
        
		
	}
	public static void exit() {
		System.out.println("exit");
		System.exit(0);
		
	}
	

}
/////////////////////////////////////////////////////////////////////////////////////////////
class ServerHandler implements Runnable {
	Socket clientSocket;
	String clientIPAddress;

	DataInputStream inFromClient;
	DataOutputStream outToClient;
	OutputStream outToClient1;
	
	

	String host;
	String path;
	
	String postData;
	String postContentType;
	
	public ServerHandler(Socket s) {
		try {
			clientSocket = s;
			clientIPAddress=clientSocket.getInetAddress().getLocalHost().getHostAddress()+"";
			//System.out.println("clientýp: "+clientIPAddress);
			
			System.out.println("A connection from a client is initiated...");
			inFromClient = new DataInputStream(s.getInputStream());
			outToClient = new DataOutputStream(s.getOutputStream());
			outToClient1 = s.getOutputStream();
			
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String hd = getHeader(inFromClient);
			//System.out.println(hd+"\n-----"); //header (postsa data falanda var)
			
			int startDataIndx=hd.indexOf("\r\n\r\n");
			postData=hd.substring(startDataIndx+4);
			
			//System.out.println(postData+" postdata "+"postdatalen "+postData.length());
			int sp1 = hd.indexOf(' ');
			int sp2 = hd.indexOf(' ', sp1 + 1);
			int eol = hd.indexOf('\r');

			String reqHeaderRemainingLines = hd.substring(eol + 2);
			//System.out.println(reqHeaderRemainingLines+" asdas dasd"); //requestten(get vs) den sonraki kalan header
			
			MimeHeader reqMH = new MimeHeader(reqHeaderRemainingLines);
			postContentType=reqMH.get("Content-Type");
			//System.out.println(postContentType.length());
			
			String url = hd.substring(sp1 + 1, sp2);
			
			
			if(!url.startsWith("http://")) {
				String http="http://";
				url = http+url;
			}
			//System.out.println(url+" url");//url: http....
			String method = hd.substring(0, sp1);
			host = reqMH.get("Host");
			reqMH.put("Connection", "close");
			//System.out.println(reqMH);
			
			//System.out.println(method+" method");
			URL u = new URL(url);//gönderiþ düzenlencek method a göre

			String tmpPath = u.getPath();

			String tmpHost = u.getHost();
			String tmpPort=u.getPort()+"";

			path = ((tmpPath == "") ? "/" : tmpPath);
			
			//System.out.println(tmpPath+" path "+tmpHost+" host");
			//System.out.println("host: "+host+" tmphost: "+tmpHost+" port:"+tmpPort+" url:"+url);
			String tmpHostWithPort="canerdogan";
			if(!tmpHost.equals(host)) {tmpHostWithPort=tmpHost+":"+tmpPort;}
			String disp="";
			File filteredHosts = new File("filteredHosts.txt");
			if(!filteredHosts.exists()) {
				
			}
			else {
				
				try {
					Scanner myReader = new Scanner(filteredHosts);
				      while (myReader.hasNextLine()) {
				        String data = myReader.nextLine();
				        String s=data+"\n";
				        disp+=s;
				      }
				      myReader.close();
				      
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			if (disp.contains(tmpHost)||disp.contains(tmpHostWithPort)) {
				//pC.add("Connection blocked to the host due to the proxy policy");
				//  ADD -> send a not authorized response to the client
				
				outToClient.writeBytes(createErrorPage(401, " Unauthorized", url));
				//System.out.println("YASAKK!!");
				
				
			} 
			//önce cache e bakmak lazým varsa ve deðiþmediyse direk vermek lazým client a
			//eger degistiyse güncelini sorgulayýp cache i güncelleyip günceli vermek lazm client a
			
			else if (host.equals(tmpHost) || host.equals(tmpHostWithPort)) {
				
				if (method.equalsIgnoreCase("get")) {
					//pC.add("Client requests...\r\nHost: " + host + "\r\nPath: " + path);
					handleProxy(url, reqMH,"GET");
					//System.out.println("get");
				}
				else if (method.equalsIgnoreCase("post")) {
					//pC.add("Client requests...\r\nHost: " + host + "\r\nPath: " + path);
					handleProxy(url, reqMH,"POST");
					//System.out.println("post");
				}
				else if (method.equalsIgnoreCase("head")) {
					//pC.add("Client requests...\r\nHost: " + host + "\r\nPath: " + path);
					handleProxy(url, reqMH,"HEAD");
					//System.out.println("head");
				}
				else if (method.equalsIgnoreCase("connect")) {
					//pC.add("Client requests...\r\nHost: " + host + "\r\nPath: " + path);
					handleProxy(url, reqMH,"CONNECT");
					//System.out.println("connect");
				} 
				else {
					//pC.add("Requested method " + method + " is not allowed on proxy server");
					outToClient.writeBytes(createErrorPage(405, " Method Not Allowed", method));
				}
			} else {
				//pC.add("Error for request: " + url);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void handleProxy(String url, MimeHeader reqMH, String gomethod) {
		try {
			
			Date d = new Date();
			TimeZone gmt = TimeZone.getTimeZone("GMT");
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz");
			sdf.setTimeZone(gmt);
			String sdf_format = sdf.format(d);
			
			File report = new File("report.txt");
			if(!report.exists()) {
				try {
					report.createNewFile();
					FileWriter fileWriter = new FileWriter(report.getName());
					fileWriter.write(clientIPAddress+"\n");
					fileWriter.write(sdf_format+"\n");
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					
					FileWriter fileWriter = new FileWriter(report.getName(),true);
					fileWriter.write(clientIPAddress+"\n");
					fileWriter.write(sdf_format+"\n");
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			
			
			//pC.add("\r\nInitiating the server connection");
			String realHost; int realPort;
			if(host.contains(":")) {
				int indxOfColon=host.indexOf(":");
				realHost=host.substring(0, indxOfColon);
				String port=host.substring(indxOfColon+1);
				realPort=Integer.parseInt(port);
				//System.out.println("realHost:"+realHost+" port:"+port);
			}
			else {realHost=host;realPort=80;}
			//System.out.println(realHost+"___"+realPort);
			
			try {
				
				FileWriter fileWriter = new FileWriter(report.getName(),true);
				fileWriter.write(realHost+"\n");
				fileWriter.write(path+"\n");
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Socket sSocket = new Socket(realHost, realPort); 
			DataInputStream inFromServer = new DataInputStream(sSocket.getInputStream());
			DataOutputStream outToServer = new DataOutputStream(sSocket.getOutputStream());

			reqMH.put("User-Agent", reqMH.get("User-Agent") + " via CanErdogan Proxy");

			//pC.add("\r\nSending to server...\r\n" + "GET " + path + " HTTP/1.1\r\n" + reqMH + "\r\n");

			
			File cache = new File("cache.txt");
			if(!cache.exists()) {
				try {
					cache.createNewFile();
					FileWriter fileWriter = new FileWriter(cache.getName());
					///fileWriter.write(host+"\n");fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					
					FileWriter fileWriter = new FileWriter(cache.getName(),true);
					///fileWriter.write(host+"\n");fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			boolean isCheckingCache=false;
			String inSSLResponse="";
			if(gomethod.equals("GET")) { 
				
				try {
					
					FileWriter fileWriter = new FileWriter(report.getName(),true);
					fileWriter.write("GET"+"\n");
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				boolean isCacheHas=false;
				//cache de var mý bakcaz.. varsa true
				if(!cache.exists()) {
					
				}
				else {
					String hmcnr=realHost+gomethod;
					String total="";
					try {
						Scanner myReader = new Scanner(cache);
					      while (myReader.hasNextLine()) {
					        String data = myReader.nextLine();
					        String s=data+"\n";
					        total+=s;
					        
					      }
					      myReader.close();
					      if(total.contains(hmcnr)) {
					    	  //isCacheHas=true;
					      }
					      
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				///
				if(isCacheHas) {
					isCheckingCache=true;
					outToServer.writeBytes("GET " + path + " HTTP/1.1\r\n" + "If-Modified-Since: Thu, 04 Nov 2021 18:27:33 GMT"+reqMH  +"\r\n");
				}else {
					outToServer.writeBytes("GET " + path + " HTTP/1.1\r\n" + reqMH + "\r\n");
				}
				
				
				
				
			}
			else if(gomethod.equals("POST")) {
				
				try {
					
					FileWriter fileWriter = new FileWriter(report.getName(),true);
					fileWriter.write("POST"+"\n");
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(sSocket.getOutputStream(), "UTF8"));
			    wr.write("POST " + path + " HTTP/1.0\r\n"+reqMH);
			    //wr.write("Content-Length: " + postData.length() + "\r\n");
			    //wr.write("Content-Type: "+postContentType+"\r\n");
			    wr.write("\r\n");

			    wr.write(postData);
			    wr.flush();
			}
			else if(gomethod.equals("HEAD")) {
				
				try {
					
					FileWriter fileWriter = new FileWriter(report.getName(),true);
					fileWriter.write("HEAD"+"\n");
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				OutputStream output = sSocket.getOutputStream();
	            PrintWriter writer = new PrintWriter(output, true);
	 
	            boolean isCacheHas=false;
	            //cache de varmý bakcaz varsa true..
	            if(!cache.exists()) {
					
				}
				else {
					String hmcnr=realHost+gomethod;
					String total="";
					try {
						Scanner myReader = new Scanner(cache);
					      while (myReader.hasNextLine()) {
					        String data = myReader.nextLine();
					        String s=data+"\n";
					        total+=s;
					        
					      }
					      myReader.close();
					      if(total.contains(hmcnr)) {
					    	  //isCacheHas=true;
					      }
					      
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	            ///
	            if(isCacheHas) {
	            	isCheckingCache=true;
	            	writer.println("HEAD " + path + " HTTP/1.1\n"+"If-Modified-Since: Thu, 04 Nov 2021 18:27:33 GMT"+reqMH);
	            }
	            else {
	            	writer.println("HEAD " + path + " HTTP/1.1\n"+reqMH);
	            }
	            
	            
	            
	            //writer.println("Host: " + host);
	            //writer.println("User-Agent: Simple Http Client");
	            //writer.println("Accept: text/html");
	            //writer.println("Accept-Language: en-US");
	            //writer.println("Connection: close");
	            writer.println();
			}
			else if(gomethod.equals("CONNECT")) {
				
				try {
					
					FileWriter fileWriter = new FileWriter(report.getName(),true);
					fileWriter.write("CONNECT"+"\n");
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				try {
					/*String httpsHost="https://"+realHost;
					URL urlhttps= new URL(httpsHost);
					HttpsURLConnection con = (HttpsURLConnection)urlhttps.openConnection();
					String resp=print_content(con);
					byte[] barr = resp.getBytes(Charset.forName("UTF-8"));
					int barrLen=barr.length;
					outToClient.write(barr,0,barrLen);*/
					
					/////////////
					SSLSocketFactory factoryy =(SSLSocketFactory)SSLSocketFactory.getDefault();
			        SSLSocket socket =(SSLSocket)factoryy.createSocket(sSocket,realHost, realPort,true);
			        
			        socket.startHandshake();

		            PrintWriter out = new PrintWriter(
		                                  new BufferedWriter(
		                                  new OutputStreamWriter(
		                                  socket.getOutputStream())));
		            
		            

		            out.println("GET "+path+" HTTP/1.0\n"+reqMH);
		            out.println();
		            out.flush();
		            
		            
		            
		            
		            if (out.checkError()) {
		            	System.out.println("SSLSocketClient:  java.io.PrintWriter error");
		            }
		            
		            
		            
		            BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                            socket.getInputStream()));
		            
		            
		            
		            String inputLine;
		            while ((inputLine = in.readLine()) != null) {
		            	
		            	String inp = inputLine+"\n";
		            	inSSLResponse+=inp;
		            	//System.out.print(inSSLResponse);
		            }
		            
		            
		            
		            in.close();
		            out.close();
		            socket.close();
					
					
					
		            
		            
					
					
					
					///////////////////////////
				}catch (Exception e) {
				     e.printStackTrace();
			    } 
	            	              
			}
			

			//pC.add("HTTP request sent to: " + host);

			ByteArrayOutputStream bAOS = new ByteArrayOutputStream(10000);
			ByteArrayOutputStream bAOS1 = new ByteArrayOutputStream(10000);
			int a;
			String rawResponse;
			int emptyLineIndex;
			byte[] buffer = new byte[1024];
			if(inSSLResponse.equals("")) {
				while ((a = inFromServer.read(buffer)) != -1) {
					bAOS.write(buffer, 0, a);
				}
				
				byte[] response = bAOS.toByteArray();
				rawResponse = new String(response);
				//System.out.println("**********rawResponse:"+rawResponse);
				emptyLineIndex = findEmptyLine(response);
				
				String responseHeader = rawResponse.substring(0, rawResponse.indexOf("\r\n\r\n"));//sýkýntýlý
				//System.out.println(responseHeader+"-sýkýntý kýsýmmmm");
				//pC.add("\r\nRaw Response \r\n" + rawResponse);
				
				int eol = responseHeader.indexOf('\r');
				
				String respFirstLine = responseHeader.substring(0,eol);
				int firstEmptyy=respFirstLine.indexOf(' ');
				String secPartwithEmptyy=respFirstLine.substring(firstEmptyy+1);
				int secEmptyy=secPartwithEmptyy.indexOf(' ');
				String codee=secPartwithEmptyy.substring(0,secEmptyy);
				try {
					
					FileWriter fileWriter = new FileWriter(report.getName(),true);
					fileWriter.write(codee+"\n");
					fileWriter.write("----------"+"\n");
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println(codee+"_codee");
				
				
				
				String respHeaderRemainingLines = responseHeader.substring(eol + 2);
				//System.out.println("BAK: "+respHeaderRemainingLines);
				
				MimeHeader respMH = new MimeHeader(respHeaderRemainingLines);
				
				//ADD-> add Server header using this format: "Name Surname CSE471 Lab2"
				respMH.put("Server", "CAN ERDOGAN 20170702044");
				String changedResponseHeader = respFirstLine + "\r\n" + respMH + "\r\n";
				//System.out.println(changedResponseHeader+"_resphdr");
				//pC.add("\r\nResponse Header\r\n" + changedResponseHeader);
						
				
				
				int dataLength=rawResponse.length()-responseHeader.length()-4;//ADD-> change zero to correct payload data length	
				
				//pC.add("\r\n\r\nGot " + response.length + " bytes of response data...\r\n"
				//+ "Sending it back changed response data with " + (changedResponseHeader.length()+dataLength) + " to the client...\r\n");
				
				outToClient.writeBytes(changedResponseHeader);
				
				outToClient.write(response, emptyLineIndex + 4, dataLength);
				
				
				if(!isCheckingCache && changedResponseHeader.contains("Last-Modified:")) {
					//cache koycazzz
					if(!cache.exists()) {
						
					}
					else {
						
						try {
							
							FileWriter fileWriter = new FileWriter(cache.getName(),true);
							
							fileWriter.write("-hmcaner"+"\n");
							fileWriter.write(realHost+gomethod+"\n");
							fileWriter.write("-lmcaner"+"\n");
							int indxOfLastModified=changedResponseHeader.indexOf("Last-Modified:");
							String lmcaner=changedResponseHeader.substring(indxOfLastModified+15);
							int indxOfNewLineLastMdfy=lmcaner.indexOf("\r\n");
							String lmcaner1=lmcaner.substring(0, indxOfNewLineLastMdfy);
							fileWriter.write(lmcaner1+"\n");
							fileWriter.write("-crhcaner"+"\n");
							fileWriter.write(changedResponseHeader+"\n");
							fileWriter.write("-rcaner"+"\n");
							String responseStr=new String(response);
							fileWriter.write(responseStr+"\n");
							fileWriter.write("-ecaner"+"\n");
							fileWriter.write(emptyLineIndex+"\n");
							fileWriter.write("-dcaner"+"\n");
							fileWriter.write(dataLength+"\n");
							fileWriter.write("----------"+"\n");
							
							fileWriter.close();
							
							
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				if(/*codee.equals("304") && isCheckingCache */false) {//degismediyse
					
					//cacheden al cevaplarý ve bastýrt bukadar
					String cacheChangedResponseHeader="";
					String cacheResponse="";
					String cacheEmptyLineIndx="";
					String cacheDataLength="";
					String hmcnrr=realHost+gomethod;
					if(!cache.exists()) {
						
					}
					else {
						int flagG=0;
						try {
							Scanner myReader = new Scanner(cache);
						      while (myReader.hasNextLine()) {
						        String data = myReader.nextLine();
						        String s=data+"\n";
						        if(data.equals(hmcnrr)) {
						        	flagG=1;
						        }
						        if(flagG==1 && data.equals("-crhcaner")) {
						        	flagG=2;
						        }
						        if(flagG==2 && data.equals("-rcaner")) {
						        	flagG=3;
						        }
						        if(flagG==3 && data.equals("-ecaner")) {
						        	flagG=4;
						        }
						        if(flagG==4 && data.equals("-dcaner")) {
						        	flagG=5;
						        }/////
						        if(flagG==2 && !data.equals("-crhcaner")) {
						        	cacheChangedResponseHeader+=s;
						        }
						        if(flagG==3 && !data.equals("-rcaner")) {
						        	cacheResponse+=s;
						        }
						        if(flagG==4 && !data.equals("-ecaner")) {
						        	cacheEmptyLineIndx+=s;
						        }
						        if(flagG==5 && !data.equals("-dcaner")) {
						        	cacheDataLength+=s;break;
						        }
						        
						        
						      }
						      myReader.close();
						      
						      
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(cacheEmptyLineIndx.contains("\n")) {
						String s=cacheEmptyLineIndx.replaceAll("\n", "");
						cacheEmptyLineIndx=s;
					}
					if(cacheDataLength.contains("\n")) {
						String s=cacheDataLength.replaceAll("\n", "");
						cacheDataLength=s;
					}
					
					changedResponseHeader=cacheChangedResponseHeader;
					//response=cacheResponse.getBytes(Charset.forName("UTF-8"));
					emptyLineIndex=Integer.parseInt(cacheEmptyLineIndx);
					
					dataLength=Integer.parseInt(cacheDataLength);
					
					
					//outToClient.writeChars(cacheResponse);
					//System.out.println("_"+changedResponseHeader+"_");
					//outToClient.writeBytes(changedResponseHeader);
					//System.out.println(cacheResponse+"_"+response.length+"_"+emptyLineIndex+"_"+dataLength+"_");
					//outToClient.write(response, emptyLineIndex + 4, dataLength);
					//String clearResp=rawResponse.substring(responseHeader.length()+4);
					if(cacheResponse.endsWith("\n\n")) {
						int lencacheres=cacheResponse.length();
						String s=cacheResponse.substring(0, lencacheres-2);
						cacheResponse=s;
						int indxofempt=cacheResponse.indexOf("\n\n");
						s=cacheResponse.substring(indxofempt+2);
						cacheResponse=s;
						//System.out.println("_"+cacheResponse+"_");
					}
					response=cacheResponse.getBytes();
					//PrintWriter writerr = new PrintWriter(outToClient1, true);
					//outToClient.writeInt(65);outToClient.flush();
					//outToClient.writeBytes(cacheResponse);
				}else if(isCheckingCache){//cache e baktým degismis
					if(gomethod.equals("GET")) {
						
						outToServer.writeBytes("GET " + path + " HTTP/1.1\r\n" + reqMH + "\r\n");
						
						
					}else if(gomethod.equals("HEAD")) {
						
						OutputStream output = sSocket.getOutputStream();
			            PrintWriter writer = new PrintWriter(output, true);
			            writer.println("HEAD " + path + " HTTP/1.1\n"+reqMH);
						
					}
					while ((a = inFromServer.read(buffer)) != -1) {
						bAOS1.write(buffer, 0, a);
					}

					response = bAOS1.toByteArray();
					rawResponse = new String(response);
					//System.out.println("**********rawResponse:"+rawResponse);
					emptyLineIndex = findEmptyLine(response);
					
					responseHeader = rawResponse.substring(0, rawResponse.indexOf("\r\n\r\n"));//sýkýntýlý
					//System.out.println(responseHeader+"-sýkýntý kýsýmmmm");
					//pC.add("\r\nRaw Response \r\n" + rawResponse);
					
					eol = responseHeader.indexOf('\r');
					
					respFirstLine = responseHeader.substring(0,eol);
					firstEmptyy=respFirstLine.indexOf(' ');
					secPartwithEmptyy=respFirstLine.substring(firstEmptyy+1);
					secEmptyy=secPartwithEmptyy.indexOf(' ');
					codee=secPartwithEmptyy.substring(0,secEmptyy);
					try {
						
						FileWriter fileWriter = new FileWriter(report.getName(),true);
						fileWriter.write(codee+"\n");
						fileWriter.write("----------"+"\n");
						fileWriter.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println(codee+"_codee");
					
					
					
					respHeaderRemainingLines = responseHeader.substring(eol + 2);
					//System.out.println("BAK: "+respHeaderRemainingLines);
					
					respMH = new MimeHeader(respHeaderRemainingLines);
					
					//ADD-> add Server header using this format: "Name Surname CSE471 Lab2"
					respMH.put("Server", "CAN ERDOGAN 20170702044");
					changedResponseHeader = respFirstLine + "\r\n" + respMH + "\r\n";
					//System.out.println(changedResponseHeader+"_resphdr");
					//pC.add("\r\nResponse Header\r\n" + changedResponseHeader);
							
					
					
					dataLength=rawResponse.length()-responseHeader.length()-4;//ADD-> change zero to correct payload data length	
					
					//pC.add("\r\n\r\nGot " + response.length + " bytes of response data...\r\n"
					//+ "Sending it back changed response data with " + (changedResponseHeader.length()+dataLength) + " to the client...\r\n");
					
					outToClient.writeBytes(changedResponseHeader);
					
					outToClient.write(response, emptyLineIndex + 4, dataLength);
					
					
					int deletedPartEndLine=-1;
					int deletedPartStartLine=-1;
					String cacheDeleted="";
					String deletedPartcache="";
					if(!cache.exists()) {
						
					}
					else {
						int counterLine=1;int flagDelete=0;
						try {
							Scanner myReader = new Scanner(cache);
						      while (myReader.hasNextLine()) {
						        String data = myReader.nextLine();
						        String hmcaner=realHost+gomethod;
						        if(data.equals(hmcaner)&&flagDelete==0) {
						        	deletedPartStartLine=counterLine-1;
						        }
						        if(deletedPartStartLine!=-1 && data.equals("----------")&&flagDelete==0) {
						        	deletedPartEndLine=counterLine;flagDelete=1;
						        }
						        String s=data+"\n";
						        
						        counterLine++;
						      }
						      myReader.close();
						      
						      counterLine=1;
						      Scanner myReader1 = new Scanner(cache);
						      while (myReader1.hasNextLine()) {
						        String data = myReader1.nextLine();
						        String s=data+"\n";
						        if(counterLine>=deletedPartStartLine && counterLine<=deletedPartEndLine) {
						        	
						        }else {
						        	cacheDeleted+=s;
						        }
						        
						        counterLine++;
						      }
						      myReader1.close();
						      
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//cache icini sildim..
					FileWriter fileDeleter = new FileWriter(cache.getName(),false);
					PrintWriter printDeleter = new PrintWriter(fileDeleter,false);
					printDeleter.flush();
					printDeleter.close();
					fileDeleter.close();
					
					//cache i güncelle!	
					if(!cache.exists()) {
						
					}
					else {
						
						try {
							
							FileWriter fileWriter = new FileWriter(cache.getName(),true);
							fileWriter.write(cacheDeleted);
							fileWriter.write("-hmcaner"+"\n");
							fileWriter.write(realHost+gomethod+"\n");
							fileWriter.write("-lmcaner"+"\n");
							int indxOfLastModified=changedResponseHeader.indexOf("Last-Modified:");
							String lmcaner=changedResponseHeader.substring(indxOfLastModified+15);
							int indxOfNewLineLastMdfy=lmcaner.indexOf("\r\n");
							String lmcaner1=lmcaner.substring(0, indxOfNewLineLastMdfy);
							fileWriter.write(lmcaner1+"\n");
							fileWriter.write("-crhcaner"+"\n");
							fileWriter.write(changedResponseHeader+"\n");
							fileWriter.write("-rcaner"+"\n");
							String responseStr=new String(response);
							fileWriter.write(responseStr+"\n");
							fileWriter.write("-ecaner"+"\n");
							fileWriter.write(emptyLineIndex+"\n");
							fileWriter.write("-dcaner"+"\n");
							fileWriter.write(dataLength+"\n");
							fileWriter.write("----------"+"\n");
							fileWriter.close();
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					
					
					
					
					
				}
			}
			else {
				
				rawResponse=inSSLResponse;
				byte[] response=rawResponse.getBytes();emptyLineIndex=-1;
				for(int h=0;h<response.length-1;h++) {
					if(response[h]=='\n' && response[h+1]=='\n') {
						emptyLineIndex=h;
					}
				}
				
				//System.out.println("**********rawResponse:"+rawResponse);
				String responseHeader = rawResponse.substring(0, rawResponse.indexOf("\n\n"));//sýkýntýlý
				//System.out.println("**********HeaderResponse:"+responseHeader);
				String respFirstLine = responseHeader.substring(0,responseHeader.indexOf("\n"));
				//System.out.println("**********respFirst:"+respFirstLine);
				int firstEmptyy=respFirstLine.indexOf(' ');
				String secPartwithEmptyy=respFirstLine.substring(firstEmptyy+1);
				int secEmptyy=secPartwithEmptyy.indexOf(' ');
				String codee=secPartwithEmptyy.substring(0,secEmptyy);
				try {
					
					FileWriter fileWriter = new FileWriter(report.getName(),true);
					fileWriter.write(codee+"\n");
					fileWriter.write("----------"+"\n");
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
				
				
				String respHeaderRemainingLines = responseHeader.substring(responseHeader.indexOf("\n")+1);
				//System.out.println("**********respHeaderRemainingLines:"+respHeaderRemainingLines);
				MimeHeader respMH = new MimeHeader(respHeaderRemainingLines);
				respMH.put("Server", "CAN ERDOGAN 20170702044");
				String changedResponseHeader = respFirstLine + "\r\n" + respMH + "\r\n";
				//System.out.println("***"+changedResponseHeader);
				int dataLength=rawResponse.length()-responseHeader.length()-2;
				//outToClient.writeBytes(changedResponseHeader);
				//System.out.println(dataLength+"datalen");
				//outToClient.write(response, emptyLineIndex + 2, dataLength);
				String clearResp=rawResponse.substring(responseHeader.length()+2);
				outToClient.writeBytes(clearResp);
			}
			

			
			
			
			

			outToClient.close();

			sSocket.close();

			//pC.add("Served http://" + host + path + "\r\nExiting ServerHelper thread...\r\n"
					//+ "\r\n----------------------------------------------------" + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 private String print_content(HttpsURLConnection con){
			if(con!=null){
					
			try {
				
			   System.out.println("****** Content of the URL ********");			
			   BufferedReader br = 
				new BufferedReader(
					new InputStreamReader(con.getInputStream()));
						
			   String input;
				String returnCon="";		
			   while ((input = br.readLine()) != null){
			      System.out.println(input);
			      String inpN=input+"\n";
			      returnCon+=inpN;
			   }
			   br.close();
				return returnCon;		
			} catch (IOException e) {
			   e.printStackTrace();
			   return "";
			}
					
		       }
			return "Connection Error!!";
				
	 }
	 
	 private String createErrorPage(int code, String msg, String address) {
			String html_page = "";
		//  ADD -> create html page using method parameters
			
			html_page = "<!DOCTYPE html>\r\n" + "<body>\r\n" + "<h1>"+code+" "+msg+"</h1>\r\n" +"Error when fetching URL: " +address+"\r\n" +"</body>\r\n"
					+ "</html>";
			int htmlSize= html_page.length();
			
			
			
			
			MimeHeader mh = makeMimeHeader("text/html", html_page.length());
			HttpResponse hr = new HttpResponse(code, msg, mh);
			return hr + html_page;
		}
	 
	 
	 private MimeHeader makeMimeHeader(String type, int length) {
			MimeHeader mh = new MimeHeader();
			Date d = new Date();
			TimeZone gmt = TimeZone.getTimeZone("GMT");
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz");
			sdf.setTimeZone(gmt);
			String sdf_format = sdf.format(d);
			//ADD-> add date, server, and content-type fields to mimeheader
					//
					//
			mh.put("Date", sdf_format);
			mh.put("Server", "CSE471 HTTP Server CAN ERDOGAN");
			mh.put("Content-type", type);
			if (length >= 0)
				mh.put("Content-Length", String.valueOf(length));
			return mh;
		}
	 
	
	private int findEmptyLine(byte[] arr) throws Exception {
		for (int i = 0; i < arr.length + 3; i++) {
			if (arr[i] == '\r' && arr[i + 1] == '\n' && arr[i + 2] == '\r' && arr[i + 3] == '\n') {
				return i;
			}
		}
		return -1;
	}
	
	
	public String getHeader(DataInputStream in) throws Exception {
		byte[] header = new byte[1024];

		int data;
		int h = 0;

		int inSize=in.available();
		int counterForinSize=0;
		while ((data = in.read()) != -1) {
			header[h++] = (byte) data;

			if (header[h - 1] == '\n' && header[h - 2] == '\r' && header[h - 3] == '\n' && header[h - 4] == '\r') {
				//break;
			}
			counterForinSize++;
			if(inSize==counterForinSize) {break;}
		}
		return new String(header, 0, h);
	}
	
	
	
}
