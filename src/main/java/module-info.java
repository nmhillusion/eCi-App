module app.netlify.nmhillusion.eciapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires n2mix.java;
    requires neon.di;
    requires org.slf4j;

    opens app.netlify.nmhillusion.eciapp to javafx.fxml, neon.di;
    exports app.netlify.nmhillusion.eciapp;
    opens app.netlify.nmhillusion.eciapp.controller to javafx.fxml, neon.di;
    exports app.netlify.nmhillusion.eciapp.controller;

    opens app.netlify.nmhillusion.eciapp.service_impl to neon.di;
    opens app.netlify.nmhillusion.eciapp.service_impl.politics_ruler to neon.di;
    opens app.netlify.nmhillusion.eciapp.model to n2mix.java;
    opens app.netlify.nmhillusion.eciapp.model.politics_ruler to n2mix.java;
    opens app.netlify.nmhillusion.eciapp.controller.wanted_people to javafx.fxml, neon.di;
    opens app.netlify.nmhillusion.eciapp.controller.pep to javafx.fxml, neon.di;
    opens app.netlify.nmhillusion.eciapp.controller.main to javafx.fxml, neon.di;
}