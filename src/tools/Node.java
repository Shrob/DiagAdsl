package tools;

import java.net.InetAddress;

/**
 *
 * @author Shrob
 */
public class Node {
    
    private InetAddress IP;
    private Ping ping;
    private Traceroute route;
    
    public Node(InetAddress address){
        IP=address;
    }
   
    
    
    public void testPing() throws Exception{
        ping=new Ping(IP,4);
    }
    
    public void testRoute() throws Exception{
        route=new Traceroute(IP);
    }

    
    
    
    public void setIP(InetAddress IP) {
        this.IP = IP;
    }

    public InetAddress getIP() {
        return IP;
    }

    public Ping getPing() {
        return ping;
    }

    public Traceroute getRoute() {
        return route;
    }
    
}
