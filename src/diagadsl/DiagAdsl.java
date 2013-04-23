package diagadsl;

import UI.MainView;
import UI.SysTray;
import tools.SysInfo;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import jpcap.JpcapCaptor;
import localHistory.ConnectionHistory;
import localHistory.TableElement;






/**
 *
 * @author Shrob
 */
public class DiagAdsl {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception{
        //Ping p=new Ping();
       // p.doCommand("www.google.com",1);
        //Traceroute t= new Traceroute();
        //t.doCommand("192.168.1.254");
       // System.out.println(p.getStatus());
        //SysTray.paff();
        
//       tools.Ping ping=new tools.Ping("www.google.tn",4);
//        
//                System.out.println("nbre de paquet envoye : " +ping.getPacketNb());
//		System.out.println("taux de perte :  " + ping.getLoss() );
//		System.out.println("temps max : " + ping.getMaximum() );
//		System.out.println("temps min :  " + ping.getMinimum());
//		System.out.println("temps moy :  " + ping.getAverage());
        
//                diagadsl.tools.Traceroute trace=new diagadsl.tools.Traceroute("www.google.com");
//		ArrayList<String> List1 = trace.getRoute();
//		System.out.println("le liste des routeurs est : ");
//		for(int i = 0; i < List1.size(); i++)
//		{
//			System.out.println(List1.get(i).toString());
//		}
        
        
    //    MainView m = new MainView();
     //   m.setVisible(true);
        
  
        
        //SysInfo s=new SysInfo();
       // s.showInfos();
        //System.out.println("Hostname" + s.getInfoTable().get("Hostname"));
        
//                diagadsl.tools.Traceroute trace=null;
//		try {
//			trace = new diagadsl.tools.Traceroute("www.tunisietuning.com");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		ArrayList<String> List1 = trace.chemin;
//		System.out.println("le liste des routeurs est : ");
//		for(int i = 0; i < List1.size(); i++)
//		{
//			System.out.println(List1.get(i).toString());
//                        System.out.println(trace.chemin.size());
//		}
        
        ConnectionHistory ch = new ConnectionHistory();
        TableElement element1 = new TableElement();
        element1.status="merdique";
        element1.latency="999 ms";
        element1.flow="999 mo/s";
        element1.wanIP="255.255.255.255";
        ch.updateTable(element1);
    }
 
    
}
