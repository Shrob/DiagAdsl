package localHistory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.StringTokenizer;
import sun.nio.ch.DirectBuffer;

/**
 *
 * @author Shrob
 */
public class ConnectionHistory {

    private ArrayList localHistoryTable = new ArrayList<>();

    public ConnectionHistory() throws FileNotFoundException, IOException {
        String tableString = new String();
        FileInputStream stream = new FileInputStream(new File("Connection_Table.txt"));
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            tableString = Charset.defaultCharset().decode(bb).toString();
            sun.misc.Cleaner cleaner = ((DirectBuffer) bb).cleaner();
            cleaner.clean();
        } finally {
            stream.close();
        }

        StringTokenizer st = new StringTokenizer(tableString, "*");
        int counter = 0;
        TableElement element = new TableElement();
        
        while (st.hasMoreTokens()) {
            
            counter++;
            if (counter == 5 || counter ==1 ) {
               element = new TableElement();
                counter = 1;
            }

            String txtElement = st.nextToken();
            switch (counter) {

                case 1:
                    element.status = txtElement;
                    break;

                case 2:
                    element.latency = txtElement;
                    break;

                case 3:
                    element.flow = txtElement;
                    break;

                case 4:
                    element.wanIP = txtElement;
                    break;

            }
            if (counter == 4) {
                localHistoryTable.add(element);
            }
        }
    }

    public void updateTable(TableElement element) throws FileNotFoundException{
        localHistoryTable.remove(localHistoryTable.size()-1);
        localHistoryTable.add(0,element);
        
        String text=new String();
        text="";
        int i;
        for(i=0;i<localHistoryTable.size();i++){
            TableElement element1 = new TableElement();
            element1=(TableElement) localHistoryTable.get(i);
            text+="*" + element1.status + "*" + element1.latency + "*" + element1.flow + "*" + element1.wanIP;
        }
        text+="*";
        PrintWriter out = new PrintWriter("Connection_Table.txt");
        out.println(text);
        out.close();
    };
    
    public ArrayList getLocalHistoryTable() {
        return localHistoryTable;
    }
    
    
}
