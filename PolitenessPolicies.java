import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class PolitenessPolicies
{
	public boolean robotSafe(URL url,String seedurl) 
	{
	    String host = url.getHost();
	    String thisuseragent="Web Crawler";
	    String strRobot = "https://" + host + "/robots.txt";
	    URL robotURL;
	    try { robotURL = new URL(strRobot);
	    } catch (MalformedURLException e) {
	        return false;
	    }
	    try 
	    {
	         URLConnection con = robotURL.openConnection();
	         HttpURLConnection connection = null;
	         if(con instanceof HttpURLConnection)
	         {
	            connection = (HttpURLConnection) con;
	         }
	         else
	         {
	            return false;
	         }
	         BufferedReader in = new BufferedReader(
	         new InputStreamReader(connection.getInputStream()));
	         String strCommands = "";
	         String current;
	         ArrayList<String> splitlines=new ArrayList<String>();
	         while((current = in.readLine()) != null)
	         {	
	        	 strCommands += current;
	        	 splitlines.add(current);
	         }
	    /*Fetching all rules into a list*/
	    if (strCommands.toLowerCase().contains("disallow")) // if there are no "disallow" values, then they are not blocking anything.
	    {
	        List<RobotTextRule> robotRules = new ArrayList<>();
	        String mostRecentUserAgent = null;
	        for(String s:splitlines)
	    	{
	        	 if (s.toLowerCase().startsWith("user-agent")) 
		            {
		                int start = s.indexOf(":") + 1;
		                int end   = s.length();
		                mostRecentUserAgent = s.substring(start, end).trim();
		            }
		            else if (s.toLowerCase().startsWith("disallow")) {
		                if (mostRecentUserAgent != null) {
		                   RobotTextRule r = new RobotTextRule();
		                    r.userAgent = mostRecentUserAgent;
		                    int start = s.indexOf(":") + 1;
		                    int end   = s.length();
		                    r.rule = s.substring(start, end).trim();
		                    robotRules.add(r);
		                    }
		                }
	    	}
	      /* To check the which pages are disallowed*/
	        for (RobotTextRule robotRule : robotRules)
	        {
	            if(((robotRule.userAgent.equals("*"))|(robotRule.userAgent.equals(thisuseragent))) & robotRule.rule.equals("/"))
	            {
	            	 System.out.println(robotRule.userAgent);
	            	return false;
	            }
	            else if(robotRule.userAgent=="*" && robotRule.rule==seedurl)
	            {
	            	return false;
	            }
	           
	        }
	    }
	    }catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	    return true;
}
	
}