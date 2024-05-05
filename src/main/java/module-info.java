module tech.nmhillusion.eciapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires com.almasb.fxgl.all;
    requires n2mix.java;
    requires neon.di;
    requires org.slf4j;
    requires pi.logger;

    opens tech.nmhillusion.eciapp to javafx.fxml, neon.di;
    exports tech.nmhillusion.eciapp;
    opens tech.nmhillusion.eciapp.controller to javafx.fxml, neon.di;
    exports tech.nmhillusion.eciapp.controller;

    opens tech.nmhillusion.eciapp.service_impl to neon.di;
    opens tech.nmhillusion.eciapp.service_impl.politics_ruler to neon.di;

    opens tech.nmhillusion.eciapp.model to n2mix.java;
    opens tech.nmhillusion.eciapp.model.politics_ruler to n2mix.java;

    opens tech.nmhillusion.eciapp.controller.wanted_people to javafx.fxml, neon.di;
    opens tech.nmhillusion.eciapp.controller.pep to javafx.fxml, neon.di;
    opens tech.nmhillusion.eciapp.controller.un_sanction to javafx.fxml, neon.di;

    opens tech.nmhillusion.eciapp.controller.main to javafx.fxml, neon.di;
}