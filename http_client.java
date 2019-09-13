
import java.net.*;
import java.io.*; 

public class http_client {
	 public static void main(String[] args) {
		  HttpRequest request = HttpRequest.newBuilder()
                  .uri(URI.create(args[0]))
                  .GET()//used by default if we don't specify
                  .build();
//creating response body handler
HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

//sending request and receiving response via HttpClient
HttpClient client = HttpClient.newHttpClient();
CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, bodyHandler);
future.thenApply(HttpResponse::body)
.thenAccept(System.out::println)
.join();
	 }
}
