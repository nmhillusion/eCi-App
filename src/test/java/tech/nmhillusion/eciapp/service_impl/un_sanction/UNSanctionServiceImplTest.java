package tech.nmhillusion.eciapp.service_impl.un_sanction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.nmhillusion.eciapp.model.un_sanction.SanctionModel;

import java.util.Objects;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
class UNSanctionServiceImplTest {

    @Test
    void testReadSanctionListFromFile() {
        final SanctionModel sanctionModel = Assertions.assertDoesNotThrow(() -> new UNSanctionServiceImpl().readSanctionListFromFile(
                Objects.requireNonNull(
                                getClass().getClassLoader()
                                        .getResource("union-nations/politicians/consolidated.xml")
                        )
                        .toURI()
                        .getPath()
        ));

        Assertions.assertNotNull(sanctionModel);
        Assertions.assertNotNull(sanctionModel.getIndividualSanctionModelList());
        Assertions.assertFalse(sanctionModel.getIndividualSanctionModelList().isEmpty());
        Assertions.assertNotNull(sanctionModel.getEntitySanctionModelList());
        Assertions.assertFalse(sanctionModel.getEntitySanctionModelList().isEmpty());
    }
}