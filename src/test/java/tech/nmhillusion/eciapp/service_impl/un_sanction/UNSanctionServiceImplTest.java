package tech.nmhillusion.eciapp.service_impl.un_sanction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tech.nmhillusion.eciapp.model.un_sanction.SanctionModel;
import tech.nmhillusion.eciapp.service.UNSanctionService;
import tech.nmhillusion.n2mix.helper.log.LogHelper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
class UNSanctionServiceImplTest {
    final UNSanctionService unSanctionService = new UNSanctionServiceImpl();
    private SanctionModel cachedResult;

    private synchronized SanctionModel parseTestFile() throws Exception {
        if (null == cachedResult) {
            cachedResult = unSanctionService.readSanctionListFromFile(
                    Objects.requireNonNull(
                                    getClass().getClassLoader()
                                            .getResource("union-nations/politicians/consolidated.xml")
                            )
                            .toURI()
                            .getPath()
            );
        }
        return cachedResult;
    }

    @Test
    @Order(1)
    void testReadSanctionListFromFile() {
        final SanctionModel sanctionModel = Assertions.assertDoesNotThrow(this::parseTestFile);

        Assertions.assertNotNull(sanctionModel);
        Assertions.assertNotNull(sanctionModel.getIndividualSanctionModelList());
        Assertions.assertFalse(sanctionModel.getIndividualSanctionModelList().isEmpty());
        Assertions.assertNotNull(sanctionModel.getEntitySanctionModelList());
        Assertions.assertFalse(sanctionModel.getEntitySanctionModelList().isEmpty());
    }

    @Test
    @Order(2)
    void testWriteSanctionListToFile() {
        final SanctionModel sanctionModel = Assertions.assertDoesNotThrow(this::parseTestFile);
        final Path targetFilePath = Path.of("target", "outData-un-sanction.xlsx");

        final Path outputPath = Assertions.assertDoesNotThrow(() -> unSanctionService.writeSanctionListToFile(sanctionModel, targetFilePath.toString()));

        Assertions.assertNotNull(outputPath);
        Assertions.assertTrue(Files.exists(outputPath));
        LogHelper.getLogger(this).info("output path: %s".formatted(outputPath));
    }
}