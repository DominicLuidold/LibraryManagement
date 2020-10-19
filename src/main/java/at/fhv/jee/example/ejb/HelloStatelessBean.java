package at.fhv.jee.example.ejb;

import javax.ejb.Stateless;

@Stateless
public class HelloStatelessBean {

    public String sayHello() {
        return String.format("Hello EJB World. Timestamp: %d", System.currentTimeMillis());
    }
}
