package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.mygdx.userInfo.userInfo;
public class JsonParser {
	
	public static ArrayList<userInfo> parse(String s){
		ArrayList<userInfo> users = new ArrayList<userInfo>();
		s = s.substring(2, s.length()-2);
		
		s = s.replace("}", "-").replace("{", "-");
		System.out.println(s);
		String[] arr = s.split("-,-");
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].substring(1, arr[i].length()-1);
			userInfo user = new userInfo();
			String[] sarr = arr[i].split(",");
			System.out.println(Arrays.toString(sarr));
			user.setId(Integer.getInteger(sarr[0].split(":")[1]));
			user.setName(sarr[1].split(":")[1]);
			user.setPassword(sarr[2].split(":")[1]);
			users.add(user);
		}
		return users;
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
