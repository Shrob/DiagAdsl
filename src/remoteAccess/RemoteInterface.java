package remoteAccess;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 *
 * @author Shrob
 */
public interface RemoteInterface extends Remote {
    
    public ArrayList getRemoteHistoryTable() throws RemoteException;
    
    public boolean sendLocalHistoryTable(ArrayList table) throws RemoteException;
    
}
