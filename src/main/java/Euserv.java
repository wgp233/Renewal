import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import java.io.IOException;

public class Euserv {
    public static void main(String[] args) throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.CHROME,"127.0.0.1",8888);
        WebClientOptions options = webClient.getOptions();

        options.setJavaScriptEnabled(false);
        options.setCssEnabled(false);
        options.setUseInsecureSSL(true);

        HtmlPage page = webClient.getPage("https://my.freenom.com/clientarea.php");
        ((HtmlTextInput)page.getByXPath("//*[@id=\"username\"]").get(0)).setValueAttribute("wuguangping233@gmail.com");
        ((HtmlPasswordInput)page.getByXPath("//*[@id=\"password\"]").get(0)).setValueAttribute("13627914987");
        HtmlPage click = ((DomElement) page.getByXPath("/html/body/div[1]/section[2]/div/div/div[2]/form[1]/div[1]/input").get(0)).click();
        HtmlPage click2 = ((DomElement) click.getByXPath("/html/body/div[1]/section[1]/div/div/ul/li[1]").get(0)).click();
        HtmlPage click3 = ((DomElement) click2.getByXPath("/html/body/div[1]/section[1]/div/div/ul/li[1]/ul/li[2]/a").get(0)).click();
        System.out.println(click3.asXml());
    }
}
