
package controller;

import java.util.HashMap;
import model.Model;
import model.Languages;

/*
AUTHOR @OKHERGO
*/

public class Controller {
    Model m = new Model();
    
    public HashMap<String, String> getLanguageDictionary(Languages language) throws Exception{
        return m.getLanguageDict(language);
    }
    
    public int openDataBin() throws Exception{
        return m.openDataBin();
    }
    
    public void saveDataBin() throws Exception{
        m.saveDataBin();
    }
    
    public int importCSV() throws Exception{
        return m.importCSV();
    }
    
    public int exportCSV() throws Exception{
        return m.exportCSV();
    }
    
    public int exportHTML(){
        return m.exportHTML();
    }
    
    public boolean addFigure(String [] data){
        return m.addFigure(data);
    }
    
    public String seeFigure(String id){
        return m.seeFigure(id);
    }
    
    public void modifyFigure(String id, String data, String i){
        m.modifyFigure(id,data, i);
    }
    
    public void deleteFigure(String id){
        m.deleteFigure(id);
    }
    
    public void sortById(){
        m.sortById();
    }
    
    public void sortByYearAndId(){
        m.sortByYearAndId();
    }
    
    public void sortByManufacturerAndYear(){
        m.sortByManufacturerAndYear();
    }
    
    public String[] printInventory(){
        return m.printInventory();
    }
}
