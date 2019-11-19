package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.mygdx.objects.score;
import com.mygdx.objects.userInfo;
public class JsonParser {
	
	public static ArrayList<userInfo> parseUserInfo(String s){
		ArrayList<userInfo> users = new ArrayList<userInfo>();
		s = s.substring(2, s.length()-2);
		
		s = s.replace("}", "-").replace("{", "-");
		System.out.println(s);
		String[] arr = s.split("-,-");
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].substring(1, arr[i].length()-1);
			userInfo user = new userInfo();
			String[] sarr = arr[i].split(",");
			user.setId(Integer.getInteger(sarr[0].split(":")[1]));
			user.setName(sarr[1].split(":")[1]);
			user.setPassword(sarr[2].split(":")[1]);
			users.add(user);
		}
		return users;
	}
	
	public static ArrayList<score> parseScore(String s){
		ArrayList<score> scores = new ArrayList<score>();
		s = s.substring(2, s.length()-2);
		s = s.replace("}", "-").replace("{", "-");
		String[] arr = s.split("-,-");
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].substring(1, arr[i].length()-1);
			score sc = new score();
			String[] sarr = arr[i].split(",");
			sc.setId(Integer.getInteger(sarr[0].split(":")[1]));
//			System.out.println(Integer.parseInt(sarr[0].split(":")[1]));
//			System.out.println(Integer.getInteger(sarr[1].split(":")[1]));
			sc.setScore(Integer.parseInt(sarr[1].split(":")[1]));
			sc.setName(sarr[2].split(":")[1]);
			scores.add(sc);
		}
		return scores;
	}
	
	public static String getHTML(String urlToRead) throws Exception {
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
	
	public static String sendHTML(String methurd, String para) throws Exception{
		String urlToSend = "http://coms-309-tc-1.misc.iastate.edu:8080/" + methurd + "?";
		URL url = new URL(urlToSend);
		String urlParameters = para;
		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }
		StringBuilder content;
		try (BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {

            String line;
            content = new StringBuilder();

            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }

        System.out.println(content.toString());
		conn.disconnect();

		return content.toString();
	}
	
}
