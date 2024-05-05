package tech.nmhillusion.eciapp.model.un_sanction;

import tech.nmhillusion.n2mix.type.Stringeable;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public class SanctionModel extends Stringeable {
    private List<IndividualSanctionModel> individualSanctionModelList;
    private List<EntitySanctionModel> entitySanctionModelList;

    public List<IndividualSanctionModel> getIndividualSanctionModelList() {
        return individualSanctionModelList;
    }

    public SanctionModel setIndividualSanctionModelList(List<IndividualSanctionModel> individualSanctionModelList) {
        this.individualSanctionModelList = individualSanctionModelList;
        return this;
    }

    public List<EntitySanctionModel> getEntitySanctionModelList() {
        return entitySanctionModelList;
    }

    public SanctionModel setEntitySanctionModelList(List<EntitySanctionModel> entitySanctionModelList) {
        this.entitySanctionModelList = entitySanctionModelList;
        return this;
    }
}
