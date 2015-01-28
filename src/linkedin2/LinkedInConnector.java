/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package linkedin2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import javax.lang.model.element.Element;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.text.Document;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 *
 * @author Moiz
 */
public class LinkedInConnector {
    
    private final String API_KEY = "78lmopv2yvl34s";
    private final String API_SECRET = "k579LMXAGDYT5b00";
    private final String REDIRECT_URI = "http://localhost:3000";
    private final String STATE = "ABC1234";
    private final String SCOPE = "rw_nus";
    private String access_token;
    
    public LinkedInConnector()
    {
        access_token = "AQU6izWshZxy-b3gY8LLR_th8vRMndVXXIJ5XOua62gDUnvl86orUHzPJU2H3jQWBK772LEaC46Ebk8mOyQ8-gXQprBIiyuZKUVzRjNPYeXgbn_1Cr8wd9QOvY9aYve5FkcDJG6bZhlpTYimU4hbCkJWV3tq0LDs3a70ubLJhZJMw0gzDsQ";
    }
    
    public void getAccToken()
    {
        String code ="";
        Scanner in = new Scanner(System.in);
        System.out.println("Run the following link in browser");
        System.out.println("https://www.linkedin.com/uas/oauth2/authorization?"
                + "response_type=code&"
                + "client_id="+API_KEY+"&"
                + "scope="+SCOPE+"&"
                + "state="+STATE+"&"
                + "redirect_uri="+REDIRECT_URI);
        System.out.print("Copy and paste code here: ");
        code = in.nextLine();
        System.out.println("Now run the following link to get access token");
        System.out.println();
        System.out.println("https://www.linkedin.com/uas/oauth2/accessToken?"
                + "grant_type=authorization_code&"
                + "code="+code+"&"
                + "redirect_uri="+REDIRECT_URI+"&"
                + "client_id=" +API_KEY +"&"
                + "client_secret="+API_SECRET);
        System.out.print("Copy and paste access token here: ");
        access_token = in.nextLine();
    }
    
    public void getProfile() throws MalformedURLException, IOException
    {
        String url = "https://api.linkedin.com/v1/people/~";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Authorization", "Bearer " + access_token);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }
    
    public void postStatus() throws MalformedURLException, ProtocolException, IOException {
        String url = "https://api.linkedin.com/v1/people/~/shares";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n"
                + "<share> \n"
                + "  <comment>"
                + //comments against the content you want to share
                "H-1B Work Visa USA - Everything you need to know - Info, Tips, Guides, Stats, News, Updates, Recommendations, Community, Jobs and much more!"
                + "</comment> \n"
                + "  <content> \n"
                + "    <submitted-url>"
                + //URL of the content you want to share
                "http://h1b-work-visa-usa.blogspot.com"
                + "</submitted-url> \n"
                + "  </content> \n"
                + "  <visibility> \n"
                + "    <code>anyone</code> \n"
                + "  </visibility> \n"
                + "</share>\n";
        con.setRequestProperty("Content-Type", "text/xml");
        
        con.setRequestProperty("Authorization", "Bearer " + access_token);
       
        //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        //wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        
    }
    
    public void postP()
    {
        OAuthService service = new ServiceBuilder()
                .provider(LinkedInApi.class)
                .apiKey("78lmopv2yvl34s")
                .apiSecret("k579LMXAGDYT5b00")
                .build();
        String url = "https://api.linkedin.com/v1/people/~/shares?oauth2_access_token="+access_token;
        OAuthRequest request = new OAuthRequest(Verb.POST, url);
        request.addHeader("Content-Type", "text/xml");
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n"
                + "<share> \n"
                + "  <comment>"
                + //comments against the content you want to share
                "H-1B Work Visa USA - Everything you need to know - Info, Tips, Guides, Stats, News, Updates, Recommendations, Community, Jobs and much more!"
                + "</comment> \n"
                + "  <content> \n"
                + "    <submitted-url>"
                + //URL of the content you want to share
                "http://h1b-work-visa-usa.blogspot.com"
                + "</submitted-url> \n"
                + "  </content> \n"
                + "  <visibility> \n"
                + "    <code>anyone</code> \n"
                + "  </visibility> \n"
                + "</share>\n";
        request.addPayload(xml);

        System.out.println("Fetching the Request Token...");
        Token requestToken = service.getRequestToken();
        System.out.println("Got the Request Token!");
        System.out.println();

        System.out.println("Now go and authorize Scribe here:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        System.out.println("And paste the verifier here");
        System.out.print(">>");
        Scanner in = new Scanner(System.in);
        Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        Token accessToken = service.getAccessToken(requestToken, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken + " )");
        System.out.println();

// there is no body just a header
        Response response = request.send();
        System.out.println(response.getBody());
        System.out.println(response.getHeaders().toString());
    }
    
    public void postC()
    {
        OAuthService service = new ServiceBuilder()
                .provider(LinkedInApi.class)
                .apiKey("78lmopv2yvl34s")
                .apiSecret("k579LMXAGDYT5b00")
                .build();
        String url = "https://api.linkedin.com/v1/people/~/network/updates/key=UPDATE-162081240-5965419235474751488/update-comments?oauth2_access_token="+access_token;
        OAuthRequest request = new OAuthRequest(Verb.POST, url);
        request.addHeader("Content-Type", "text/xml");
        String xml = "<?xml version='1.0' encoding='UTF-8'?>"
                + "<update-comment>"
                + "<comment>LinkedIn API Test Comment</comment>"
                + "</update-comment>";  
        request.addPayload(xml);

        System.out.println("Fetching the Request Token...");
        Token requestToken = service.getRequestToken();
        System.out.println("Got the Request Token!");
        System.out.println();

        System.out.println("Now go and authorize Scribe here:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        System.out.println("And paste the verifier here");
        System.out.print(">>");
        Scanner in = new Scanner(System.in);
        Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        Token accessToken = service.getAccessToken(requestToken, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken + " )");
        System.out.println();

// there is no body just a header
        Response response = request.send();
        System.out.println(response.getBody());
        System.out.println(response.getHeaders().toString());
    }
}
