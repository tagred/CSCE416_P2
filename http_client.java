
//Taylor Gordon
/* A Java program for a Client */
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.net.HttpURLConnection;

public class http_client {
	String fileName = "http_client_output";

	public http_client(String address) {
		URL url;
		String tof = "Printing HTTP header info from ";
		try {
			url = new URL(address);
			// HttpURLConnection.setFollowRedirects(true);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);

			boolean redirect = false;
			int check = conn.getResponseCode();
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			// checks for redirects.
			if (check != HttpURLConnection.HTTP_OK) {
				if (check == HttpURLConnection.HTTP_MOVED_TEMP || check == HttpURLConnection.HTTP_MOVED_PERM
						|| check == HttpURLConnection.HTTP_SEE_OTHER)
					redirect = true;
			}
			// updates url to redirect and opens new connection
			if (redirect) {
				//System.out.println("url redirected");
				String newurl = conn.getHeaderField("Location");
				url = new URL(newurl);
				conn = (HttpURLConnection) url.openConnection();
				writer.write("URL redirected to " + url);
				writer.newLine();
			}

			String header = conn.getHeaderField(0);

			writer.write(tof + url);
			int i = 0;
			while ((header = conn.getHeaderField(i)) != null) {
				String key = conn.getHeaderFieldKey(i);
				writer.newLine();
				writer.write(((key == null) ? "" : key + ": ") + header);
				// System.out.println(((key == null) ? "" : key + ": ") +header);
				i++;
			}
			writer.newLine();
			writer.write("\nURL content...");
			writer.newLine();
			String content = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next();
			writer.write(content);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("URL input required");
		} else {
			http_client client = new http_client(args[0]);
		}
	}

}