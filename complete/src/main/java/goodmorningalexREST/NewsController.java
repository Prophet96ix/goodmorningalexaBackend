package goodmorningalexREST;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class NewsController {

    @RequestMapping("/news_overview")
    public @ResponseBody ArrayList<NewsOverviewDk> myFunction(HttpServletResponse response) {
        response.setContentType("application/json");

        HB_TopThemen_Parse topThemenParse = new HB_TopThemen_Parse();
        ArrayList<NewsOverviewDk> overview = new ArrayList<>();

        topThemenParse.getTopThemen().forEach((k, v) -> {
            NewsOverviewDk dk = new NewsOverviewDk();
            dk.setId(k);
            dk.setTitle(v.getTitle());
            overview.add(dk);
        });

        return overview;
    }

    @RequestMapping("/article")
    public String article(@RequestParam(value="title_id", defaultValue="1") String title_id, @RequestParam(value="subitem") String subitem) {

        HB_TopThemen_Parse topThemenParse = new HB_TopThemen_Parse();
        HashMap<Integer, NewsDk> map = topThemenParse.getTopThemen();

        String info = new String();
        NewsDk dk = topThemenParse.getTopThemen().get(Integer.valueOf(String.format(title_id)));

        switch (String.format(subitem)) {
            case "title":
                info = dk.getTitle();
                break;
            case "link":
                info = dk.getLink();
                break;
            case "description":
                info = dk.getDescription();
                break;
            case "category":
                info = dk.getCategory();
                break;
        }

        return info;
    }

}
