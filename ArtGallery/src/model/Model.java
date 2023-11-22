
package model;

/*
AUTHOR @OKHERGO
*/

import static com.coti.tools.OpMat.*;
import static java.lang.System.out;
import com.coti.tools.Rutas;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {  
    final String fileName = "figuras.bin";
    final String htmlFileName = "figuras.html";
    final String csvFileName = "datos_figuras.csv";
    private List<Figure> figures = new ArrayList<>();
    
    public HashMap<String, String> getLanguageDict(Languages language) throws Exception{
        File f = new File(language.fileName);
        try {
            String[][] tableStrings = importFromDisk(f,"#");        
            HashMap<String, String> dictionary = new HashMap<>();
            for (String[] s : tableStrings) {
                dictionary.put(s[0], s[1]);
            }
            return dictionary;           
        } catch (Exception ex) {
            throw new IOException("Error: Load Language File", ex);
        } 
    }

    //Binary files functions
    public int openDataBin() throws Exception {
        File f = Rutas.fileToFileInFolderOnDesktop("datos_figuras", fileName);
        if (f.exists()) {
            FileInputStream fis;
            BufferedInputStream bis;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(f);
                bis = new BufferedInputStream(fis);
                ois = new ObjectInputStream(bis);
                this.figures = (ArrayList<Figure>) ois.readObject();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "Binary File Not Found", ex);
                return 1;
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                return 1;
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return 0;
        } else {
            return 1;
        }
    }
    
    public void saveDataBin() throws Exception {
        File f = Rutas.fileToFileInFolderOnDesktop("datos_figuras", fileName);
        FileOutputStream fos;
        BufferedOutputStream bos;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(f);
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            if (this.figures != null) oos.writeObject(this.figures);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "Binary File Not Found", ex);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    //CSV files functions
    public int importCSV() {
        final String DELIMITER = ";";
        File f = Rutas.fileToFileInFolderOnDesktop("datos_figuras", csvFileName);
        if (f.exists()){
            String[][] tmp;
            try {
                tmp = importFromDisk(f, DELIMITER);
            } catch (Exception ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
            for (String[] tokens : tmp){
                Figure o = Figure.factory(tokens);
                if (o != null) figures.add(o);
            }
        } else {
            return -1;
        }
        return figures.size();
    }

    public int exportCSV() {
        final String DELIMITER = ";";
        File f = Rutas.fileToFileInFolderOnDesktop("datos_figuras", csvFileName);
        if (figures == null) return -1;
        List<String> tmp = new ArrayList<>();
        for (Figure o : figures) {
            tmp.add(o.delimitedBy(DELIMITER));
        }
        try {
            Files.write(f.toPath(), tmp, Charset.defaultCharset(), StandardOpenOption.CREATE);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return figures.size();
    }
    
    //HTML files functions
    public int exportHTML(){
        try {
            String htmlDocString = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "%%CSS%%"
                + "<head>\n"
                + "<title>%%TITLE%%</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "\n"
                + "<h1>%%HEADER%%</h1>\n"
                + "%%TABLE%%\n"
                + "\n"
                + "</body>\n"
                + "</html>";
            
            htmlDocString = htmlDocString.replace("%%TITLE%%", "Art Gallery");
            htmlDocString = htmlDocString.replace("%%HEADER%%", "INVENTARIO");
            
            String CSS = """
                         <style>
                         * {
                            font-family: Arial;
                         }
                         table {
                           text-align: left;
                           position: relative;
                           border-collapse: collapse; 
                           background-color: #4eebc2;
                         }
                         td, th {
                           border: 1px solid #999;
                           padding: 20px;
                         }
                         th {
                           background: black;
                           color: white;
                           border-radius: 0;
                           position: sticky;
                           top: 0;
                           padding: 10px;
                         }
                         </style>""";
            
            htmlDocString = htmlDocString.replace("%%CSS%%", CSS);
            StringBuilder tableStringBuilder = new StringBuilder();
            tableStringBuilder.append("<table>");
            tableStringBuilder.append(Figure.getHTMLRowHeader());
            for (Figure o : figures) {
                tableStringBuilder.append(o.asHTMLRow());
            }
            tableStringBuilder.append("</table>");
            htmlDocString = htmlDocString.replace("%%TABLE%%", tableStringBuilder);
            File f = Rutas.fileToFileInFolderOnDesktop("datos_figuras", htmlFileName);
            Files.writeString(f.toPath(), htmlDocString, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "Files Related Problem", ex);
        }
        return figures.size();
    }
    
    //Managment functions
    public boolean addFigure(String [] data){
        boolean flag = false;
        for (Figure o : figures){
            if (o.getId().equals(data[0])) flag = true;
        }
        if (flag == false){
            Figure f = Figure.factory(data);
            figures.add(f);
        }
        return flag;
    }
    
    public String seeFigure(String id){
        for (Figure o : figures){
            if (o.getId().equals(id)) return o.asRow();
        }
        return null;
    }
    
    public void modifyFigure(String id, String data, String i){
        for (Figure o : figures){
            if (o.getId().equals(id)){
                switch (i){
                    case "1" -> o.setHeight(Float.parseFloat(data));
                    case "2" -> o.setMaterial(data);
                    case "3" -> o.setAmount(Integer.parseInt(data));
                    case "4" -> o.setYear(Integer.parseInt(data));
                    case "5" -> o.setPhoto(data);
                    case "6" -> o.setManufacturer(data);
                }
            }
        }
    }
    
    public void deleteFigure(String id){
        Iterator<Figure> i = figures.iterator();
        while (i.hasNext()) {
            Figure tmp = i.next();
            if (tmp.getId().equals(id)) {
                i.remove();
                break;
            }
        }
    }
    
    //Sorting functions
    public void sortById(){
        Comparator <Figure> c1 = Comparator.comparing(Figure::getId);
        Collections.sort(figures, c1);
    }
    
    public void sortByYearAndId(){
        Comparator <Figure> c1 = Comparator.comparing(Figure::getYear);
        Comparator <Figure> c2 = c1.reversed();
        Comparator <Figure> c3 = c2.thenComparing(Figure::getId);
        Collections.sort(figures, c3);
    }
    
    public void sortByManufacturerAndYear(){
        Comparator <Figure> c1 = Comparator.comparing(Figure::getManufacturer);
        Comparator <Figure> c2 = c1.thenComparing(Figure::getYear);
        Collections.sort(figures, c2);
    }
    
    public String [] printInventory(){
        Iterator<Figure> i = figures.iterator();
        String [] data = new String [figures.size()];
        int count = 0;
        while (i.hasNext()) {
            Figure tmp = i.next();
            data[count] = tmp.asRow();
            count++;
        }
        return data;
    }
}
