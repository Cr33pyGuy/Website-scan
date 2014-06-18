package web_scanner;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

public class Main{
	public static void main(String[] args){
		
		//establish a variable that stores the value prompted from the user
		String URLInitial = JOptionPane.showInputDialog("Enter the domain which you want to scan");
		
		//ask the user strings of which length they would like to test.
		String lInitial = JOptionPane.showInputDialog("For strings up to the length of how many characters would you like to scan?");
		
		//convert into an int.
		final int l = Integer.parseInt(lInitial);
		
		//calculate the factorial of l.
		int lFactorial = 0;
		
		for(int o = 1; o < l; o++){
			lFactorial *= o;
		}
		
		/*
		 * convert into properly formatted URL
		 * (adds HTTP://, or HTTP://www.
		 * to the URL as needed.
		 */
		String URLActual = URLConvert(URLInitial);
		
		//perform a URL lookup on the string
		try{
			//convert into URL object.
			URL url = new URL(URLActual);
			
			//open connection to URL
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			//set requestmethod, we don't need the whole website
			connection.setRequestMethod("HEAD");
			
			//store the response of the website in a variable.
			int responce = connection.getResponseCode();
			
			if(responce == 200){
				//Creates a PrintWriter (.txt file) where all the URLs will be stored
				PrintWriter text = new PrintWriter("List_Of_URLs.txt", "UTF-8");
				
				//Creates a count that is printed in the end (page count)
				int i = 1;
				for(int x = 0; x <= lFactorial; x++){
					
					String s = Next(set, "", setLength, l);
					
					URL trial = new URL(URLActual + "/" + s);
					
					try{
						//open connection to URL
						HttpURLConnection tryConnect = (HttpURLConnection) trial.openConnection();
						
						//again, no need for the whole page
						tryConnect.setRequestMethod("HEAD");
						
						//store the response of the website in a variable.
						int doesRespond = tryConnect.getResponseCode();
						
						//check if there's an error while connecting
						if(doesRespond == 200){
							//prints the URL that was connected to into the document.
							text.print(url + "\n");
						}else{
							//stub.
						}
						
					//BEGIN CATCH BLOCK
					}catch(MalformedURLException e){
						System.out.println("The URL you entered is not valid.");
						System.out.println(e);
					}catch(IOException e){
						System.out.println("There was an error when connecting");
						System.out.println(e);
					}
					//END CATCH BLOCK
					
				}
				//print the pagecount, and close the document
				text.print(i);
				text.close();
			}
			
		//BEGIN CATCH BLOCK
		}catch(MalformedURLException e){
			System.out.println("The URL you entered is not valid.");
			System.out.println(e);
		}catch(IOException e){
			System.out.println("There was an error when connecting");
			System.out.println(e);
		}
		//END CATCH BLOCK
	}
	
	
	
	//checks if the URL is properly formatted.
	public static String URLConvert(String string){
		//if the URL already has the HTTP:// part, return as is.
		if(string.charAt(0) == 'h' && 
		   string.charAt(1) == 't' &&
		   string.charAt(2) == 't' &&
		   string.charAt(3) == 'p')
		{	
			return string;
		}
		//if the url has no http://, but does have the www. part,
		//return http:// followed by the URL
		else if(string.charAt(0) == 'w' &&
				string.charAt(1) == 'w' &&
				string.charAt(2) == 'w')
		{
			String stringHTTP = "http://" + string;
			return stringHTTP;
		}
		//in this case the URL has neither www. or http://
		//so add both and then return.
		else{
			String stringHTTPwww = "http://www." + string;
			return stringHTTPwww;
		}
	}
	
	static char set[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'};
	
	static int setLength = set.length;
	
	public static String Next( char set[], String prefix, int n, int y){
		
		//creates the string which will then be defined through calculation
		String extension = "";
		
		//define the variable extension
		for(int i = 0; i < n; i++){
			//
			String newPrefix = prefix + set[i];
			
			Next(set, newPrefix, n, --y);
		}
		
		//return extension
		return extension;
	}
}
