import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

public class Euserv {
    public static void main(String[] args) throws IOException {

        String euserv="https://support.euserv.com/index.iphp";

//        创建带cookie的客户端
        BasicCookieStore cookieStore = new BasicCookieStore();
        ArrayList<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36"));
        headers.add(new BasicHeader("origin","https://www.euserv.com"));
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(headers).setDefaultCookieStore(cookieStore).build();
//        设置phpsessid
        HttpGet index = new HttpGet(euserv);
        CloseableHttpResponse execute = httpClient.execute(index);
        execute.close();

        String phpsessid=null;
        for (Cookie cookie : cookieStore.getCookies()) {
            if ("PHPSESSID".equals(cookie.getName())) {
                phpsessid=cookie.getValue();
            }
        }

        System.out.println(phpsessid);
//        登录
        HttpPost login = new HttpPost(euserv);
        login.setHeader(new BasicHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36"));
        login.setHeader(new BasicHeader("origin","https://www.euserv.com"));
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("email", "palpanden@magim.be"));
        params.add(new BasicNameValuePair("password", "XRay2021"));
        params.add(new BasicNameValuePair("form_selected_language", "en"));
        params.add(new BasicNameValuePair("Submit", "Login"));
        params.add(new BasicNameValuePair("subaction", "login"));
        params.add(new BasicNameValuePair("sess_id", phpsessid));
        login.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = httpClient.execute(login);

        String s = EntityUtils.toString(response.getEntity());
        System.out.println(phpsessid);
        System.out.println(cookieStore.getCookies().get(0).getValue());

        System.out.println(s);

//        response.close();
        httpClient.close();

        /*//初始化
        Connection euserv = Jsoup.connect("https://support.euserv.com/index.iphp");
        //设置sessid
        List<String> headers = euserv.execute().headers("Set-Cookie");
        String phpsessid = headers.get(1);
        phpsessid = phpsessid.substring(phpsessid.indexOf("=")+1,phpsessid.indexOf(";"));
        euserv.cookie("PHPSESSID", phpsessid);

        System.out.println(phpsessid);
        //登录
        euserv.url("https://support.euserv.com/index.iphp?sessid="+phpsessid).get();
        Document login = Jsoup.connect("https://support.euserv.com/index.iphp")
                .cookie("PHPSESSID", phpsessid)
                .data("email", "palpanden@magim.be")
                .data("password", "XRay2021")
                .data("form_selected_language", "en")
                .data("Submit", "Login")
                .data("subaction", "login")
                .data("sess_id", phpsessid)
                .get();

        System.out.println(login);*/
    }
}
