import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;


public class SSL {

	public SSL() {

	}

	public void getHttps(String urlString) throws IOException, NoSuchAlgorithmException, KeyManagementException {

		// Get HTTPS URL connection
		URL url = new URL(urlString);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

		/*
		 * // Set Hostname verification conn.setHostnameVerifier(new
		 * HostnameVerifier() {
		 * 
		 * @Override public boolean verify(String hostname, SSLSession session)
		 * { // Ignore host name verification. It always returns true. return
		 * true; }
		 * 
		 * });
		 */
		try {
			KeyStore trustStore = KeyStore.getInstance("BKS");
			String path = "/home/kinow/download/unetsystem.bks";
			String caPwd = "a123456A";
			trustStore.load(new FileInputStream(path), caPwd.toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);			
			TrustManager[] tms = (TrustManager[]) tmf.getTrustManagers();
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, null, new SecureRandom());

			// Connect to host
			conn.connect();
			conn.setInstanceFollowRedirects(true);

			// Print response from host
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.printf("%s\n", line);
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		SSL test = new SSL();
		test.getHttps("https://10.10.30.14:8443/Utils/ssl_test.jsp");

	}

}
