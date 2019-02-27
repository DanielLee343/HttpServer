import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class HttpThread extends Thread{
	private Socket s;
	BufferedReader reader;
    PrintStream writer;
    
    public HttpThread(Socket s){
    	this.s = s;
    }
    
    public void run(){
    	try {
    		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
    		String firstLineOfRequest = reader.readLine();
    		
    		String uri = firstLineOfRequest.split(" ")[1];
    		OutputStream  os = s.getOutputStream();
    		writer = new PrintStream(os);
    		
			
    		System.out.println(firstLineOfRequest);
    		
		
			writer.flush();
			
			//Deal with information from client
			if(uri.endsWith(".html")){
				writer.println("Content-Type:text/html");
			}else if(uri.endsWith(".jpg")){
				writer.println("Content_Type:image/jpeg");
			}else if(uri.endsWith(".wma")){
				writer.println("Content-Type:audio/x-ms-wma");
			}else if(uri.endsWith(".java")){
                writer.println("Content-Type:java/*");
            }else if(uri.endsWith(".mp3")){
                writer.println("Content-Type:audio/mp3");
            }else if(uri.endsWith(".mp4")){
                writer.println("Content-Type:video/mpeg4");
            }else{
                writer.println("Content-Tpye:application/octet-stream");
            }
            writer.flush();
			
            try{
            	
            	//Return file's content-length
            	InputStream inputStream = new FileInputStream("C:/Users/lenovo/Desktop/what"+uri);
            	/*InputStream inputStream = new FileInputStream("what"+uri);*/
            	writer.println("HTTP/1.1 200 OK");
            	writer.println("Content-Length: "+inputStream.available());
            	writer.println();
    			writer.flush();
    			//Send response data
    			
    			
    			byte[] b = new byte[1024];
    			int len = 0;
    			len = inputStream.read(b);
    			System.out.println(len);
    			while(len != -1){
    				os.write(b);
    				len = inputStream.read(b);
    				if(len < 1024){
    					break;
    				}
    			}
    			os.flush();
            }
            catch(Exception e){
            	//If file does not exit,send back pure text information
            	//head
                writer.println("HTTP/1.1 404 Not Found");
                writer.println("Content-Type:text/plain");
                writer.println("Content-Length:7");
                writer.println();
                //body
                
                writer.print("404 Not Found");
                writer.flush();
            }
            writer.close();
            s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
