import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiCrawler {
	public static final String BASEURL="https://en.wikipedia.org";
	public static final Pattern urlPattern = Pattern.compile("/wiki/(?:[A-Za-z0-9-._~!#$&'()*+,;=:@]|%[0-9a-fA-F]{2})*",
	        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	public String seedUrl,fileName;
	public String[] keywords;
	public int max;
	File file=null;
	FileWriter fw=null;
	PolitenessPolicies p=new PolitenessPolicies();
	QueueImpl qe=new QueueImpl();
	ArrayList<String> visited=new ArrayList<String>(); public boolean isseedurl=true;
	
	
	public WikiCrawler(String seedUrl,String[] keywords,int max,String fileName) throws IOException
	{
		this.seedUrl=seedUrl;
		this.max=max;
		this.fileName=fileName;
		this.keywords=keywords;
		file=new File(fileName);
		fw=new FileWriter(file);
	}
	
	/* Crawls the max number of pages*/
	
	public File crawl() throws IOException, InterruptedException
	{
		if (!file.exists())
			file.createNewFile();
		fw.flush();
		fw.append(new Integer(max).toString());
		fw.write(System.getProperty( "line.separator" ));
		System.out.println(seedUrl);
		
		/* Add the seedUrl to Queue and visited list*/
		
		qe.enqueue(" "+seedUrl);
		visited.add(" "+seedUrl);
		ArrayList<String> collectedlinks=new ArrayList<String>();
		/*Collect max number of relevant pages or vertices*/
		collectAllLinks(seedUrl,collectedlinks);
		System.out.println(qe.size());
		isseedurl=true;	int count=0;
		
		/*Collects the edges for the max number of vertices in the visited list*/
		
		for(String str:visited)
		{
			count++;
			if(count==100)
			{
				count=0;
				Thread.sleep(3000);
			}
			
			
			HashSet<String> set=new HashSet<String>();
			URL url=null;
			str=str.trim();
			try{ url=new URL(BASEURL+str);} 
			catch(Exception e){}
	 		InputStream inputs=url.openStream();
	 	    BufferedReader bReader=new BufferedReader(new InputStreamReader(inputs));
			int pCount1=0;String line;
			
				while ((line = bReader.readLine()) != null) 
				{
					if(line.contains("<p>")) pCount1++;
					if((pCount1>0) && line.contains("<a href="))
					{
						Matcher urlMatcher = urlPattern.matcher(line);
		            	while (urlMatcher.find())
		            	{
		            		String s=line.substring(urlMatcher.start(0),urlMatcher.end(0));
		            		if(visited.contains(s) && !set.contains(str+" "+s))
		            		{
		            			set.add(str+" "+s);
		            			fw.write(str+" "+s);
		            			fw.write(System.getProperty( "line.separator" )); 
		                  	    fw.flush();
		                  	    count++;
		            		}
		            		
		            	}
					}
					
				}
		}
	return file;
	}

	/*To collect max number of vertices*/
	private void collectAllLinks(String url,ArrayList<String> collectedlinks) throws MalformedURLException {
		try
		{
		while(!qe.isEmpty())
		{
			
			URL currentURL=new URL(BASEURL+qe.dequeue().trim());
			InputStream is=null;BufferedReader br=null;
			try {
				is = currentURL.openStream();
				 br=new BufferedReader(new InputStreamReader(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
	 	   
			int pCount=0;String lines; HashSet<String> check=new HashSet<String>();
			
				try {
					while ( (lines = br.readLine()) != null) 
					{
						if(lines.contains("<p>")) pCount++;
						if(pCount>0 && lines.contains("href="))
						{
							Matcher urlMatcher = urlPattern.matcher(lines);
							
					    	while (urlMatcher.find())
					    	{
					    		String text=null;
					    		String s=lines.substring(urlMatcher.start(0),urlMatcher.end(0));
					    		if(!s.contains(":") && !s.contains("#") && !s.equals(url))
					    		{
					    			String title=s.substring(s.lastIndexOf("/")+1);URL rawTextPage=null;
					    			BufferedReader bfReader = null;InputStream inputs=null;
					    			try
					    			{
					    			rawTextPage=new URL("https://en.wikipedia.org/w/index.php?title="+title+"&action=raw");
					    	        inputs = rawTextPage.openStream();
					    	        bfReader = new BufferedReader(new InputStreamReader(inputs));
					    	        String lines1=null;
					    	        while(( lines1 = bfReader.readLine()) != null){
					    	        	text=text+lines1;	
					    	           }
					    	        }
					    			catch (IOException e) {
					    	        	//Thread.sleep(5000);
					    				continue;
					    	        }
					    	        
					    	        if((text.toLowerCase().contains(keywords[1])) && (text.toLowerCase().contains(keywords[0])))
					    	        {
					    	        	if(!check.contains(s) && !visited.contains(s))
										{
					    	        		qe.enqueue(s);collectedlinks.add(s);
											visited.add(s);check.add(s);
										}
					    	        	
					    	        }
					    	        
					    	    } 
					    	       if(visited.size()==max) return; 
					    			
					    		}
					    		
					    	}
						}
				} catch (IOException e) {
					continue;
				}
					
		}
		
			}
		catch(Exception e){}}
	
	
	}
