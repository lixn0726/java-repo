package indl.lixn.lx7xl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author listen
 **/
@Component
public class ServiceManager {

    @Autowired
    private Map<String, Service> serviceMap;

    @PostConstruct
    public void show() {
        for (Map.Entry<String, Service> en : serviceMap.entrySet()) {
            System.out.println(en.getKey() + " : " + en.getValue());
        }
    }

}
