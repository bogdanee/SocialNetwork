module com.example.SocialNetwork.main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.graphics;
    requires jdk.hotspot.agent;


    exports controller;
    opens controller;
    exports window;
    opens window to javafx.fxml;

    opens domain;

}