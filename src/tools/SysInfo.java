package tools;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.*;

/**
 *
 * @author Shrob
 */
public class SysInfo {

    private Hashtable infoTable = new Hashtable();

    public SysInfo() throws SigarException {
        Shell shell = new Shell();
        Sigar sigar = shell.getSigar();
        //General Infos
        infoTable.put("Hostname", getHostName());
        infoTable.put("Current user", System.getProperty("user.name"));
        OperatingSystem sys = OperatingSystem.getInstance();
        infoTable.put("OS description", sys.getDescription());
        infoTable.put("OS version", sys.getVersion());
        infoTable.put("OS patch level", sys.getPatchLevel());
        infoTable.put("OS vendor", sys.getVendor());

        //Memory Info
        Mem mem = sigar.getMem();
        //printf("RAM:    %10ls", new Object[] { mem.getRam() + "MB" });
        infoTable.put("RAM", mem.getRam() + "MB");

        //cpu info
        org.hyperic.sigar.CpuInfo[] infos =
                sigar.getCpuInfoList();

        CpuPerc[] cpus =
                sigar.getCpuPercList();

        org.hyperic.sigar.CpuInfo info = infos[0];
        long cacheSize = info.getCacheSize();
        infoTable.put("Vendor", info.getVendor());
        infoTable.put("Model", info.getModel());
        infoTable.put("Mhz", info.getMhz());
        infoTable.put("Total CPUs", info.getTotalCores());

    }

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }

    public void showInfos() {
        System.out.println(infoTable.toString());
    }

    public Hashtable getInfoTable() {
        return infoTable;
    }

    public String getFormatedInfo() {
        String s = new String();
        s = "Hostname : ";
        s += infoTable.get("Hostname");
        s += "\n";

        s += "Current user : ";
        s += infoTable.get("Current user");
        s += "\n";

        s += "OS description : ";
        s += infoTable.get("OS description");
        s += "\n";

        s += "OS version : ";
        s += infoTable.get("OS version");
        s += "\n";

        s += "OS patch level : ";
        s += infoTable.get("OS patch level");
        s += "\n";

        s += "OS vendor : ";
        s += infoTable.get("OS vendor");
        s += "\n";

        s += "Vendor : ";
        s += infoTable.get("Vendor");
        s += "\n";

        s += "Model : ";
        s += infoTable.get("Model");
        s += "\n";

        s += "Mhz : ";
        s += infoTable.get("Mhz");
        s += "\n";

        s += "Total CPUs : ";
        s += infoTable.get("Total CPUs");
        s += "\n";

        s += "RAM : ";
        s += infoTable.get("RAM");
        s += "\n";

        return s;
    }
}
