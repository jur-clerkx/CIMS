/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.InputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Jur
 */
public class SSLClient {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        SSLSocket client = (SSLSocket) SSLSocketFactory.getDefault().createSocket("145.93.96.229", 4443);
        client.setEnabledCipherSuites(new String[]{"SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA"});
        InputStream in = client.getInputStream();
        byte[] data = new byte[1024];
        int len = in.read(data);
        System.out.println(new String(data, 0, len));
    }

}
