package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

/**
 *
 * @author Shrob
 */
public class NetParamFetch {

    private NetworkInterface netInterface; 
    private InetAddress SourceAddress;
    private byte[] SourceMACAddress;
    private byte[] GatewayMACAddress=null;
    private String WanIP;

    
    public NetParamFetch(String mod) throws IOException{
        
        if ("lan".equals(mod)) {
            netInterface = JpcapCaptor.getDeviceList()[0];
            SourceAddress = sourceAddressAcq();
            SourceMACAddress = sourceMACAcq();
            GatewayMACAddress = gatewayMACAcq();
        }else if ("wan".equals(mod)){
            WanIP = IpFetchWan();
        }
        
    }
    
    private String IpFetchWan() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            in.close();
            return (ip);
        } catch (java.net.UnknownHostException e) {
            System.err.println("Host for WanIP check is unreachble");
        }
        return ("---.---.---.---");
    }

    private InetAddress sourceAddressAcq() {
        //Source IP acquiring
        InetAddress srcIP = null;
        for (NetworkInterfaceAddress addrs : netInterface.addresses) {
            if (addrs.address instanceof Inet4Address) {
                srcIP = addrs.address;
            }
        }
        return srcIP;
    }

    private InetAddress destinationAddressAcq(String adress) {
        //Destination IP acquiring after adress/name resolving
        InetAddress dstNameIP = null;
        try {
            dstNameIP = InetAddress.getByName(adress);
        } catch (UnknownHostException e) {
            System.err.println("l'adresse " + adress + " est inexistante ou injoignable. Donnez une destination valide.");
            System.exit(-1);
        }
        return dstNameIP;
    }

    private byte[] sourceMACAcq(){
        return netInterface.mac_address;
    }
    
    private byte[] gatewayMACAcq() throws IOException {
        //Captor instantiation
        JpcapCaptor captor = JpcapCaptor.openDevice(netInterface, 3000, false, 30);
        //Gateway MAC adress acquiring
        captor.setFilter("dst host " + destinationAddressAcq("www.google.com").getHostAddress(), true);
        byte[] gatewayMAC = null;
        while (true) {
            String url = "http://www.google.com";
             try {
            new URL(url).openStream().close();
             }catch (UnknownHostException e) {
                 System.err.println("Gateway MAC address acquiring problem 2");
                 System.exit(-1);
             }
            
            Packet ping = captor.getPacket();
            if (ping == null) {
                System.err.println("Gateway MAC address acquiring problem 2");
                System.exit(-1);
            } else if (Arrays.equals(((EthernetPacket) ping.datalink).dst_mac, netInterface.mac_address)) {
                continue;
            }
            gatewayMAC = ((EthernetPacket) ping.datalink).dst_mac;
            break;
        }
        captor.close();
        return gatewayMAC;
    }

    public NetworkInterface getNetInterface() {
        return netInterface;
    }

    public InetAddress getSourceAdress() {
        return SourceAddress;
    }

    public byte[] getSourceMACAdress() {
        return SourceMACAddress;
    }

    public byte[] getGatewayMACAdress() {
        return GatewayMACAddress;
    }

    public String getLanIP() {
        return getSourceAdress().getHostAddress();
    }

    public String getWanIP() {
        return WanIP;
    }
    
    
    
}
