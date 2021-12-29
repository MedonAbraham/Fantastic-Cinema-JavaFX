package nl.inholland.javafx.UI;

import javafx.scene.Parent;
import javafx.scene.Scene;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class StylesScene extends Scene {

    public StylesScene(Parent parent){
        super(parent);

        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(this);

    }
}
