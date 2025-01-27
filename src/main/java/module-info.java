module com.example.diplom {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.mkammerer.argon2.nolibs;


    opens com.example.diplom to javafx.fxml;
    exports com.example.diplom;
}