module tech.nmhillusion.eciapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires n2mix.java;
    requires neon.di;
    requires org.slf4j;
    requires pi.logger;
    requires java.desktop;

    opens tech.nmhillusion.eciapp to javafx.fxml, neon.di;
    exports tech.nmhillusion.eciapp;
    opens tech.nmhillusion.eciapp.controller to javafx.fxml, neon.di;
    exports tech.nmhillusion.eciapp.controller;

    opens tech.nmhillusion.eciapp.service_impl to neon.di;
    opens tech.nmhillusion.eciapp.service_impl.politics_ruler to neon.di;
    opens tech.nmhillusion.eciapp.service_impl.wanted_people to neon.di;
    opens tech.nmhillusion.eciapp.service_impl.un_sanction to neon.di;

    opens tech.nmhillusion.eciapp.model to n2mix.java;
    opens tech.nmhillusion.eciapp.model.politics_ruler to n2mix.java;
    opens tech.nmhillusion.eciapp.model.un_sanction to n2mix.java;

    opens tech.nmhillusion.eciapp.controller.wanted_people to javafx.fxml, neon.di;
    opens tech.nmhillusion.eciapp.controller.pep to javafx.fxml, neon.di;
    opens tech.nmhillusion.eciapp.controller.un_sanction to javafx.fxml, neon.di;

    opens tech.nmhillusion.eciapp.controller.main to javafx.fxml, neon.di;
    opens tech.nmhillusion.eciapp.service_impl.un_sanction.parser to neon.di;
}