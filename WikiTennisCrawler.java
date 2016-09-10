
import java.io.IOException;

public class WikiTennisCrawler {
	public static final String BASEURL="https://en.wikipedia.org";
	public static void main(String[] args) throws IOException, InterruptedException  
	{
		String seedUrl="/wiki/Tennis",fileName="WikiTennisGraph.txt";
		String[] keywords={"tennis","grand slam"};
		int max=100;
		WikiCrawler crawler = new WikiCrawler(seedUrl, keywords, max, fileName);
		crawler.crawl(); 
	}
	
}
						
