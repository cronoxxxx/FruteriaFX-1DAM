module javafx {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    opens domain to com.google.gson,javafx.base;
    requires lombok;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires datafaker;
    requires java.logging;

    exports uiFX;


    opens uiFX to javafx.fxml;


}