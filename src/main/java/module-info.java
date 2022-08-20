module ru.bstu.it192.galkin.lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens ru.bstu.it192.galkin.lab5 to javafx.fxml;
    exports ru.bstu.it192.galkin.lab5;
}