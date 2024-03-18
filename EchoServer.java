import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class EchoServer{
    public static void main(String [] args) throws IOException{
        HttpServer server= HttpServer.create(new InetSocketAddress(8080),0);
        server.createContext("/echo",new EchoHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }
    static class EchoHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if("POST".equals(exchange.getRequestMethod())){
                String requestBody= new String(exchange.getRequestBody().readAllBytes());
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
                exchange.sendResponseHeaders(200, requestBody.length());
                OutputStream os= exchange.getResponseBody();
                os.write(requestBody.getBytes());
                os.close();
            }else{
                exchange.sendResponseHeaders(405,-1 );
            }
        }

    }
}