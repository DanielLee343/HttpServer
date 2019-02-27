import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	public static void main(String[] args) {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(8886);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		while(true){
			try{
				Socket s = ss.accept();
				HttpThread t = new HttpThread(s);
				t.start();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
	}
}
