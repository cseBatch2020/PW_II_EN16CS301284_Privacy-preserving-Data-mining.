/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed_treeclient;

/**
 *
 * @author JAI GANESH
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 * @author ashraf
 *
 */
public class CsvFileWriter {

    public static DefaultTableModel enc_dtm = new DefaultTableModel();
    //Delimiter used in CSV file
    private static final String NEW_LINE_SEPARATOR = "\n";
    public static Paillier paillier=new Paillier();
    public static HashMap<String,String>mapItemWithVal=new HashMap<String,String>();
    public  void writeFile(DefaultTableModel dtm, String fileName, int key) throws IOException {
        enc_dtm = new DefaultTableModel();
        String attr = "";
        for (int i = 0; i < dtm.getColumnCount(); i++) {
            attr = attr + "," + dtm.getColumnName(i);
            enc_dtm.addColumn(dtm.getColumnName(i));
        }
        FileWriter writer = null;
        writer = new FileWriter(fileName);
        writer.append(attr.substring(1));
        writer.append(NEW_LINE_SEPARATOR);
        int row_index = 0;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            StringBuffer row = new StringBuffer();
            for (int j = 0; j < dtm.getColumnCount(); j++) {

                String data = (String) dtm.getValueAt(i, j);
                //data = Distributed_Data.DoEnc((byte) key, data);
          
                if(!mapItemWithVal.containsKey(data.trim()))
                {
                 paillier.KeyGeneration(512, 64);
                 String val=paillier.Encryption(data).toString();
                 mapItemWithVal.put(data,val);
                }
                row.append("," +mapItemWithVal.get(data.trim()));
            }
            String[] r = row.substring(1).split(",");
            enc_dtm.addRow(r);
            writer.append(row.substring(1));
            writer.append(NEW_LINE_SEPARATOR);
        }
        writer.flush();
        writer.close();
    }
}
