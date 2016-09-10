import java.io.IOException;

public class MyWikiCrawler {
	public static final String BASEURL="https://en.wikipedia.org";
	public static void main(String[] args) throws IOException, InterruptedException  
	{
		String seedUrl="/wiki/Human",fileName="MyWikiGraph.txt";
		String[] keywords={"human","culture"};
		int max=1000;
		WikiCrawler crawler = new WikiCrawler(seedUrl, keywords, max, fileName);
		crawler.crawl(); 
	}
	
}
						
