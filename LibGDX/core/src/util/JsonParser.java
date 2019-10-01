package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.mygdx.objects.*;
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
	
}
