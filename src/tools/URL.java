package tools;

/**
 *
 * @author Shrob
 */
public class URL {

    private String address;
    private Ping ping;
    private Traceroute route;

    public URL(String addr) {
        address = addr;
    }

    public void testPing() throws Exception {
        ping = new Ping(address, 4);
    }

    public void testRoute() throws Exception {
        route = new Traceroute(address);
    }

    
    
    
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public Ping getPing() {
        return ping;
    }

    public Traceroute getRoute() {
        return route;
    }
}
