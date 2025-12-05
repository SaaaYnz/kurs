module org.example.amozov_kurs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.base;
    requires jbcrypt;
    requires org.example.amozov_kurs;
//    requires org.example.amozov_kurs;
//    requires org.example.amozov_kurs;
//    requires javafx.scene.control;
//    requires org.example.amozov_kurs;


    //opens org.example.amozov_kurs to javafx.fxml;
    exports org.example.amozov_kurs;
    exports org.example.amozov_kurs.Controllers;
    opens org.example.amozov_kurs.Controllers to javafx.fxml;
    opens org.example.amozov_kurs.Models to javafx.base;
}