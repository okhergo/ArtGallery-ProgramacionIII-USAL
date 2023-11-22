
package artgallery;

/*
AUTHOR @OKHERGO
*/

import view.View;

public class ArtGallery {
    public static void main(String[] args) throws Exception {
        View v = new View ();
        v.loadDefaultLanguage();
        v.openData();
        v.runMenu();
        v.saveData();
    }
}
