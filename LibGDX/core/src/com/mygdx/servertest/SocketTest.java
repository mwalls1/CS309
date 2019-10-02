package com.mygdx.servertest;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class SocketTest {

   public static String getUser(String urlToRead) throws Exception {
      StringBuilder result = new StringBuilder();
      URL url = new URL(urlToRead);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = rd.readLine()) != null) {
         result.append(line);
      }
      rd.close();
      return result.toString();
   }
   public static void postUser(String urlToRead) throws Exception {
	   String urlParameters  = "param1=a&param2=b&param3=c";
	   byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
	   int    postDataLength = postData.length;
	   URL    url            = new URL( urlToRead );
	   HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
	   conn.setDoOutput( true );
	   conn.setInstanceFollowRedirects( false );
	   conn.setRequestMethod( "POST" );
	   conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
	   conn.setRequestProperty( "charset", "utf-8");
	   conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
	   conn.setUseCaches( false );
	   try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
	      wr.write( postData );
	   }
	   }

   public static void main(String[] args) throws Exception
   {
     System.out.println(getUser("http://coms-309-tc-1.misc.iastate.edu:8080/getUsers"));
   }
}