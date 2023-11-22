
package model;

import java.io.Serializable;

/*
AUTHOR @OKHERGO
*/

public class Figure implements Serializable {
    private String id;
    private float height;
    private String material;
    private int amount;
    private int year;
    private String photo;
    private String manufacturer;

    //Constructor
    public Figure(String id, float height, String material, int amount, int year, String photo, String manufacturer) {
        this.id = id;
        this.height = height;
        this.material = material;
        this.amount = amount;
        this.year = year;
        this.photo = photo;
        this.manufacturer = manufacturer;
    }
    
    //Factory Method
    public static Figure factory(String[] tokens){
        if (tokens.length != 7) {
            return null;
        }
        float tmp1;
        int tmp2, tmp3;
        try {
            tmp1 = Float.parseFloat(tokens[1]);
            tmp2 = Integer.parseInt(tokens[3]);
            tmp3 = Integer.parseInt(tokens[4]);
            return new Figure(tokens[0], tmp1, tokens[2], tmp2, tmp3, tokens[5], tokens[6]);
        } catch (NumberFormatException e){
            return null;
        }
    }

    //Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    //Other methods
    public String delimitedBy(String delimiter) {
        return String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s",
                this.id,
                delimiter,
                this.height,
                delimiter,
                this.material,
                delimiter,
                this.amount,
                delimiter,
                this.year,
                delimiter,
                this.photo,
                delimiter,
                this.manufacturer);
    }
    
    public String asRow() {
        return String.format("%-10s | %-6s | %-20s | %-8s | %-5s | %-70s | %-20s",
                this.id,
                this.height,
                this.material,
                this.amount,
                this.year,
                this.photo,
                this.manufacturer);
    }
    
    public static String getHTMLRowHeader() {
        return "<tr>\n"
                + "    <th>Id</th>\n"
                + "    <th>Altura</th>\n"
                + "    <th>Material</th>\n"
                + "    <th>Cantidad</th>\n"
                + "    <th>Ano de compra</th>\n"
                + "    <th>Imagen</th>\n"
                + "    <th>Fabricante</th>\n"
                + "  </tr>";
    }
    
    public String asHTMLRow() {
        return String.format("<tr>\n"
                + "    <td>%-15s</td>\n"
                + "    <td>%-5s</td>\n"
                + "    <td>%-25s</td>\n"
                + "    <td>%-5s</td>\n"
                + "    <td>%-5s</td>\n"
                + "    <td><img src=\"%s\" width=\"80\" height=\"100\"></td>\n"
                + "    <td>%-25s</td>\n"
                + "  </tr>", 
                this.id, this.height, this.material, this.amount, this.year, this.photo, this.manufacturer);
    }
}
