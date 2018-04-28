package goodmorningalexREST;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

public class HB_TopThemen_Parse {

    public HashMap<Integer, NewsDk>  getTopThemen() {

        HashMap<Integer, NewsDk> map = new HashMap<Integer, NewsDk>();
        final AtomicLong counter = new AtomicLong();

        try {
            String url = "http://www.handelsblatt.com/contentexport/feed/top-themen";
            System.out.println("loading xml: " + url);
            InputStream fis = new URL(url).openStream();
            XMLInputFactory xmlInFact = XMLInputFactory.newInstance();
            XMLStreamReader reader = xmlInFact.createXMLStreamReader(fis);
            NewsDk news = new NewsDk();
            while (reader.hasNext()) {
                int next = reader.next();
                String currentElement;
                if (next == XMLStreamReader.START_ELEMENT) {
                    currentElement = reader.getLocalName();

                    if (currentElement.equals("title")) {
                        news = new NewsDk();
                        news.setTitle(reader.getElementText());
                    } else if (currentElement.equals("link")) {
                        news.setLink(reader.getElementText());
                    } else if (currentElement.equals("description")) {
                        news.setDescription(reader.getElementText());
                    } else if (currentElement.equals("category")) {
                        news.setCategory(reader.getElementText());
                        news.setId(Long.valueOf(counter.incrementAndGet()).intValue());
                        map.put(news.getId(), news);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
