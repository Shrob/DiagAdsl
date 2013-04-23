package tools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
public class Ping {

    private long minimum = 0;
    private long maximum = 0;
    private long average = 0;
    private int loss = 0;
    private int packetNb = 4;
    private int timeout = 1000;
    public String cnxStatus;

    public Ping(String address, int m_packetNb) throws Exception {
        packetNb = m_packetNb;
        InetAddress dstIP = destinationAddressAcq(address);
        this.doCommand(dstIP);
    }

    public Ping(InetAddress address, int m_packetNb) throws Exception {
        packetNb = m_packetNb;
        this.doCommand(address);
    }
    

    private void doCommand(InetAddress dstNameIP) throws IOException {
        
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
        icmp.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 10001, 255, IPPacket.IPPROTO_ICMP, srcIP, dstNameIP);
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

        //Initialising intermediate ping variables
        int packetSentNb = 0;
        int packetLost = 0;
        long min = 0;
        long max = 0;
        long transactionTime = 0;
        long timerStart = System.currentTimeMillis();

        //First packet sending
        JpcapSender sender = captor.getJpcapSenderInstance();
        sender.sendPacket(icmp);
        packetSentNb++;

        //Packet Handler
        Boolean firstPacket = true;
        while (packetSentNb <= packetNb) {

            ICMPPacket packetReceived = (ICMPPacket) captor.getPacket();
            long packetArrivalTime = System.currentTimeMillis() - timerStart;
            if ((packetReceived == null) && (packetArrivalTime >= timeout)) {
                packetLost++;
            } else if ((packetReceived == null) && (packetArrivalTime < timeout)) {
                continue;
            } else if ((packetReceived != null) && (packetArrivalTime < timeout)) {
                if (packetReceived.type == ICMPPacket.ICMP_ECHOREPLY) {
                    if (packetArrivalTime >= 0) {
                        transactionTime += packetArrivalTime;
                        if (firstPacket) {
                            min = packetArrivalTime;
                            max = packetArrivalTime;
                            firstPacket = false;
                        } else {
                            if (packetArrivalTime < min) {
                                min = packetArrivalTime;
                            }
                            if (packetArrivalTime > max) {
                                max = packetArrivalTime;
                            }
                        }
                    }
                }
            }
            sender.sendPacket(icmp);
            timerStart = System.currentTimeMillis();
            packetSentNb++;

        }
        sender.close();
        captor.close();

        //Result Handler
        minimum = min;
        maximum = max;
        loss = (packetLost * 100) / (packetSentNb - 1);
        average = transactionTime / packetNb;
        if (loss < 30) {
            cnxStatus = "good";
        } else {
            cnxStatus = "bad";
        }
        if (loss == 100){
            cnxStatus = "noCnx";
        }

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

    public long getMinimum() {
        return minimum;
    }

    public long getMaximum() {
        return maximum;
    }

    public long getAverage() {
        return average;
    }

    public int getLoss() {
        return loss;
    }

    public int getPacketNb() {
        return packetNb;
    }

    public String getCnxStatus() {
        return cnxStatus;
    }
    
}
