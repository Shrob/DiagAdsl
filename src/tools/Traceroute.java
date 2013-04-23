package tools;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;

/**
 *
 * @author Shrob
 */
public class Traceroute {

    private ArrayList<String> route = new ArrayList<>();
    private int timeout = 1000;
    
    public Traceroute(String address) throws Exception{
        InetAddress dstIP = destinationAddressAcq(address);
        this.doCommand(dstIP);
    }
    
     public Traceroute(InetAddress address) throws Exception{
        this.doCommand(address);
    }
    

    public void doCommand(InetAddress dstNameIP) throws Exception {

        //Lan informations
        NetParamFetch NPF = new NetParamFetch("lan");

        //Source IP acquiring
        InetAddress srcIP = NPF.getSourceAdress();

        //Gateway MAC adress acquiring
        byte[] gatewayMAC = NPF.getGatewayMACAdress();



        //ICMP packet forge
        ICMPPacket icmp = new ICMPPacket();
        icmp.type = ICMPPacket.ICMP_ECHO; // Type 8 Code 0
        icmp.seq = 789;
        icmp.id = 123;
        //Adding IP header
        icmp.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 0, 1, IPPacket.IPPROTO_ICMP, srcIP, dstNameIP);
        //Adding Ethernet Header
        EthernetPacket ethP = new EthernetPacket();
        ethP.frametype = EthernetPacket.ETHERTYPE_IP;
        ethP.src_mac = NPF.getSourceMACAdress();
        ethP.dst_mac = gatewayMAC;
        icmp.datalink = ethP; //Encapsulation


        //Interface acquiring
        NetworkInterface netInterface = JpcapCaptor.getDeviceList()[0];

        //Captor instantiation
        JpcapCaptor captor = JpcapCaptor.openDevice(netInterface, 3000, false, 30);

        //Setting the filter for receiving only icmp paquet with this machine as destination
        captor.setFilter("icmp and dst host " + srcIP.getHostAddress(), true);

        //Sender Instantiation
        JpcapSender sender = captor.getJpcapSenderInstance();


        while (true) {


            sender.sendPacket(icmp);
            ICMPPacket packetReceived = (ICMPPacket) captor.getPacket();
            long timerStart = System.currentTimeMillis();

            long packetArrivalTime = System.currentTimeMillis() - timerStart;
            if (packetReceived == null && packetArrivalTime >= timeout) {
                System.out.println("Timeout");
                icmp.hop_limit++;

            } else if ((packetReceived == null) && (packetArrivalTime < timeout)) {
                continue;
            } else if ((packetReceived != null) && (packetArrivalTime < timeout)) {

                if (packetReceived.type == ICMPPacket.ICMP_TIMXCEED) {
 
                    if(!route.contains((packetReceived.src_ip).getHostAddress())){
			route.add((packetReceived.src_ip).getHostAddress());}
                    icmp.hop_limit++;
                } else if (packetReceived.type == ICMPPacket.ICMP_UNREACH) {
                    if(!route.contains((packetReceived.src_ip).getHostAddress())){
			route.add((packetReceived.src_ip).getHostAddress());}

                    break;
                } else if (packetReceived.type == ICMPPacket.ICMP_ECHOREPLY) {
                    if(!route.contains((packetReceived.src_ip).getHostAddress())){
			route.add((packetReceived.src_ip).getHostAddress());}

                    break;

                }
            }
        }
        sender.close();
        captor.close();
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

    public ArrayList<String> getRoute() {
        return route;
    }
    
    
    
}
