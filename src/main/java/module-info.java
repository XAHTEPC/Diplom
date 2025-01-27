module com.example.diplom {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.mkammerer.argon2.nolibs;


    opens com.example.diplom to javafx.fxml;
    exports com.example.diplom;
    exports com.example.diplom.Client;
    opens com.example.diplom.Client to javafx.fxml;
    exports com.example.diplom.Server;
    opens com.example.diplom.Server to javafx.fxml;
    exports com.example.diplom.XLAM;
    opens com.example.diplom.XLAM to javafx.fxml;
}