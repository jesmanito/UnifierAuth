package org.empre;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;


//@WebService
//@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class ServiceAuth {
    public ServiceAuth() {
    }
  //  private static final String USER_NAME = "$$jesus";
  //  private static final String PASSWORD = "Jesusjesus1";

    //public static void main(String args[]){
    /*
    public String servicio(){
            JsonNode tok = null;
        try {
            tok = getAuthTokenDetails();
        } catch (IOException e) {
        }
        System.out.println("token_: "+tok.get("token").asText());
        return tok.get("token").asText();
        }
*/
  //  @WebMethod(exclude = true)
    public static JsonNode getAuthTokenDetails(String endPoint,String user,String pass) throws  IOException{
        System.out.println("ENTRA EN AUTH: ");
        HttpURLConnection conn = null;
        try {
    
            // Generating the OAuth Token
            //URL tokenUrl = new URL(TOKEN_GENERATION_URL);
            URL tokenUrl = new URL("https://eu1.unifier.oraclecloud.com"+endPoint);
            conn = (HttpURLConnection) tokenUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            //conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/json");
            String userCredentials = user + ":" + pass;
            String base64Credentials = javax.xml.bind.DatatypeConverter.printBase64Binary(userCredentials.getBytes());
            String basicAuth = "Basic " + base64Credentials;
    
            conn.setRequestProperty("Authorization", basicAuth);
    
            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + " Error: " + readStreamData(conn.getErrorStream()));
            }else{
                 System.out.println("4: "+conn.getResponseMessage());
                }
            return new ObjectMapper().readValue(conn.getInputStream(), JsonNode.class);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static String readStreamData(InputStream is) throws IOException {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
        String output;
        StringBuilder buff = new StringBuilder();
        while ((output = br.readLine()) != null) {
            buff.append(output);
        }
        return buff.toString();
    }
    }
    

}
