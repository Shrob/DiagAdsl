package remoteAccess;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import tools.URL;



/**
 *
 * @author Shrob
 */
public class RemoteConnector {
    private ArrayList remoteHistoryTable;
    private URL server;
    
    public RemoteConnector(ArrayList table){
    remoteHistoryTable = table;
}
    
 public boolean historyTableAcquire(){
     try {
            Registry registry = LocateRegistry.getRegistry(10000);
            RemoteInterface stub = (RemoteInterface) registry.lookup("serverMethods");
            remoteHistoryTable=stub.getRemoteHistoryTable();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        
 };
    
 public boolean sendLocalHistoryTable(ArrayList table){
     try {
            Registry registry = LocateRegistry.getRegistry(10000);
            RemoteInterface stub = (RemoteInterface) registry.lookup("serverMethods");
            return stub.sendLocalHistoryTable(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;   
 };
 
    public Boolean testServer() throws Exception{
        server.testPing();
        if ("noCnx".equals(server.getPing().cnxStatus)){
            return false;
        }
        else{
            return true;
        }
    }

    public ArrayList getRemoteHistoryTable() {
        return remoteHistoryTable;
    }

}

    

