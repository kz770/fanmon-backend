package com.example.fanmon_be.domain.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class JsoupController {

    @GetMapping("/jsoup")
    public String jsoup() throws IOException {
        try {
            while (true) {
                String url = "https://weverse.io/";
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select(".HomeArtistListSlotView_artist_item__a6gAX");
                if(elements.size()== 0) {
                    break;
                }
                for(Element e : elements) {
                    String name = String.valueOf(e.getElementsByClass(".MarqueeView_content__2Qs2H"));
                    System.out.println(name);
                }
            }
        } catch (Exception e) {
            System.out.println("jsoup 예외발생 : "+e.getMessage());
        }
        return "jsoup";
    }
}
