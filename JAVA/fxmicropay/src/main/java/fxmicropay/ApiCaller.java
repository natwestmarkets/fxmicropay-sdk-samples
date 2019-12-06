/*
Copyright [2019] [NatWest Markets Plc]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package fxmicropay;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class ApiCaller {

    private String certLocation;
    private String certPassword;

    public ApiCaller(String certLocation, String certPassword) {
        this.certLocation = certLocation;
        this.certPassword = certPassword;
    }

    public Response call(String uri, Object requestPayload) {

        TrustManager trustAllCerts = new TrustManager();
        try {
        SSLContext sslContext = null;
        sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new javax.net.ssl.TrustManager[]{trustAllCerts}, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e ) {
            throw new RuntimeException("Failed to create SSL Context", e);
        }

        System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
        System.setProperty("javax.net.ssl.keyStore", certLocation);
        System.setProperty("javax.net.ssl.keyStorePassword", certPassword);

        Client client = ClientBuilder.newClient();

        System.out.println("Initiating request at " + uri);
        Response response = client.target(uri)
                .request()
                .post(Entity.entity(requestPayload, MediaType.APPLICATION_JSON));

        System.out.println("Response Status: " + response.getStatus());
        return response;
    }

}
