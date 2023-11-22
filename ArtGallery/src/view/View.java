
package view;

/*
AUTHOR @OKHERGO
*/

import static com.coti.tools.Esdia.*;
import static com.coti.tools.DiaUtil.*;
import controller.Controller;
import java.util.HashMap;
import model.Languages;

public class View {
    Controller c = new Controller();
    HashMap<String, String> dictString;
    
    public void loadDefaultLanguage() throws Exception{
        dictString = c.getLanguageDictionary(Languages.SPANISH);
    }
    
    public void openData() throws Exception{
        int info = c.openDataBin();
        if(info == 1){
            System.out.printf(dictString.get("ImportData"));
            boolean tmp = siOno(dictString.get("ImportCSVYesOrNo"));
            if (tmp == true) this.importCSV();
        }
    }
    
    public void saveData() throws Exception{
        c.saveDataBin();
    }
    
    //Main menu
    public void runMenu() throws Exception{
        boolean exit = false;
        do {
            System.out.printf(dictString.get("MainMenu"));          
            String option = readString(dictString.get("ChooseOption")).toLowerCase();
            switch (option) {
                case "1" -> this.runFilesMenu(dictString.get("FilesMenu"));
                case "2" -> this.runManagmentMenu(dictString.get("ManagmentMenu"));
                case "3" -> this.runListMenu(dictString.get("ListMenu"));
                case "4" -> exit = siOno(dictString.get("YesOrNo"));
                default -> System.out.printf(dictString.get("InvalidOption"));
            }
        } while (!exit);
    }
    
    //Files menu
    private void runFilesMenu(String filesMenu) throws Exception{
        boolean exit = false;
        do {
            String option = readString(filesMenu+dictString.get("ChooseOption")).toLowerCase();
            switch (option) {
                case "a" -> {exit = true; this.importCSV();}
                case "b" -> {exit = true; this.exportCSV();}
                case "c" -> {exit = true; this.exportHTML();}
                default -> System.out.printf(dictString.get("InvalidOption"));
            }
        } while(!exit);
    }
    
    public void importCSV() throws Exception{
        int info = c.importCSV();
        if (info >= 0){
            System.out.printf(dictString.get("FileRead"),info);
        } else{
            System.out.printf(dictString.get("FileNotRead"));
        }
    }
    
    public void exportCSV()throws Exception{
        int info = c.exportCSV();
        if (info >= 0){
            System.out.printf(dictString.get("FileWritten"),info);
        } else{
            System.out.printf(dictString.get("FileNotWritten"));
        }
    }
    
    public void exportHTML()throws Exception{
        int info = c.exportHTML();
        if (info >= 0){
            System.out.printf(dictString.get("FileWritten"),info);
        } else{
            System.out.printf(dictString.get("FileNotWritten"));
        }
    }
    
    //Gallery Managment menu
    private void runManagmentMenu(String managmentMenu){
        boolean exit = false;
        do {
            String option = readString(managmentMenu+dictString.get("ChooseOption")).toLowerCase();
            switch (option) {
                case "a" -> {exit = true; this.addFigure();}
                case "b" -> {exit = true; this.seeFigure();}
                case "c" -> {exit = true; this.modifyFigure();}
                case "d" -> {exit = true; this.deleteFigure();}
                case "s" -> exit = true;
                default -> System.out.printf(dictString.get("InvalidOption"));
            }
        } while(!exit);
    }
    
    public void addFigure(){
        String [] data = new String [7];
        System.out.printf(dictString.get("AddFigure"));
        data[0] = addId();
        data[1] = addHeight();
        data[2] = addMaterial();
        data[3] = addAmount();
        data[4] = addYear();
        data[5] = addPhoto();
        data[6] = addManufacturer();
        boolean info = c.addFigure(data);
        if (info == false){
            System.out.printf(dictString.get("CorrectlyAdded"));
        } else {
            System.out.printf(dictString.get("NotAdded"));
        }
    }
    
    public String addId(){
        String data = null;
        do {
            data = readString(dictString.get("Id"));
        } while (data == null);
        return data;
    }
    
    public String addHeight(){
        String data = null;
        do {
            float tmp = readFloat(dictString.get("Height"));
            if (tmp >= 0 && tmp <= 1.5){
                data = Float.toString(tmp);
            }
        } while (data == null);
        return data;
    }
    
    public String addMaterial(){
        String data = null;
        do {
            data = readString(dictString.get("Material"));
        } while (data == null);
        return data;
    }
    
    public String addAmount(){
        String data = null;
        do {
            int tmp = readInt(dictString.get("Amount"));
            if (tmp > 1){
                data = Integer.toString(tmp);
            }
        } while (data == null);
        return data;
    }
    
    public String addYear(){
        String data = null;
        do {
            int tmp = readInt(dictString.get("Year"));
            if (tmp > 0 && tmp < 2100){
                data = Integer.toString(tmp);
            }
        } while (data == null);
        return data;
    }
    
    public String addPhoto(){
        String data = null;
        do {
            data = readString(dictString.get("Photo").toLowerCase());
        } while (!data.endsWith(".png"));
        return data;
    }
    
    public String addManufacturer(){
        String data = null;
        do {
            data = readString(dictString.get("Manufacturer"));
        } while (data == null);
        return data;
    }
    
    public void seeFigure(){
        String id = readString(dictString.get("IdToSee"));
        String info = c.seeFigure(id);
        if (info != null){
            System.out.printf("%n%s%n", info);
        } else {
            System.out.printf(dictString.get("FigureDoesNotExist"));
            runManagmentMenu(dictString.get("ManagmentMenu"));
        }
    }
    
    public void modifyFigure(){
        String id = readString(dictString.get("IdToModify"));
        String info = c.seeFigure(id);
        String data;
        if (info != null){
            System.out.printf("%n%s%n", info);
            boolean exit = false;
            do {
                System.out.printf(dictString.get("AttributeToModify"));
                String option = readString(dictString.get("ChooseOptionToModify")).toLowerCase();
                switch (option) {
                    case "1" -> {data = addHeight(); c.modifyFigure(id,data,option);}
                    case "2" -> {data = addMaterial(); c.modifyFigure(id,data,option);}
                    case "3" -> {data = addAmount(); c.modifyFigure(id,data,option);}
                    case "4" -> {data = addYear(); c.modifyFigure(id,data,option);}
                    case "5" -> {data = addPhoto(); c.modifyFigure(id,data,option);}
                    case "6" -> {data = addManufacturer(); c.modifyFigure(id,data,option);}
                    default -> System.out.printf(dictString.get("InvalidOption"));
                }
                exit = siOno(dictString.get("ModifyExit"));
            } while(exit);   
        } else {
            System.out.printf(dictString.get("FigureDoesNotExist"));
            runManagmentMenu(dictString.get("ManagmentMenu"));
        }
    }
    
    public void deleteFigure(){
        String id = readString(dictString.get("IdToDelete"));
        String info = c.seeFigure(id);
        if (info != null){
            System.out.printf("%n%s%n", info);
            boolean exit = siOno(dictString.get("DeleteYesOrNo"));
            if (exit == true){
                c.deleteFigure(id);
            }
        } else {
            System.out.printf(dictString.get("FigureDoesNotExist"));
            runManagmentMenu(dictString.get("ManagmentMenu"));
        }
    }
    
    //List menu
    public void runListMenu(String listMenu){
        boolean exit = false;
        do {
            String option = readString(listMenu+dictString.get("ChooseOption")).toLowerCase();
            switch (option) {
                case "a" -> {exit = true; this.sortByID();}
                case "b" -> {exit = true; this.sortByYearAndId();}
                case "c" -> {exit = true; this.sortByManufacturerAndYear();}
                case "s" -> exit = true;
                default -> System.out.printf(dictString.get("InvalidOption"));
            }
        } while(!exit);
    }
    
    public void sortByID(){
        c.sortById();
        printInventory();
    }
    
    public void sortByYearAndId(){
        c.sortByYearAndId();
        printInventory();
    }
    
    public void sortByManufacturerAndYear(){
        c.sortByManufacturerAndYear();
        printInventory();
    }
    
    public void printInventory(){
        System.out.printf(dictString.get("InventoryTitle"));
        String [] data = c.printInventory();
        
        for (int i = 0; i < data.length; i++) {
               System.out.printf("%s%n",data[i]);
        }
    }
}
