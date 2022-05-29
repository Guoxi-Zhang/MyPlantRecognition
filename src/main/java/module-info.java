module com.example.myplant {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;

    opens com.example.myplant to javafx.fxml;
    exports com.example.myplant;
}