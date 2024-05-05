package tech.nmhillusion.eciapp.model;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */

public class WantedPeopleEntity extends Stringeable {
    private String fullName;
    private String birthday;
    private String livePlace;
    private String nameOfParents;
    private String crimeName;
    private String decisionDate;
    private String decisionOffice;

    public String getFullName() {
        return fullName;
    }

    public WantedPeopleEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public WantedPeopleEntity setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getLivePlace() {
        return livePlace;
    }

    public WantedPeopleEntity setLivePlace(String livePlace) {
        this.livePlace = livePlace;
        return this;
    }

    public String getNameOfParents() {
        return nameOfParents;
    }

    public WantedPeopleEntity setNameOfParents(String nameOfParents) {
        this.nameOfParents = nameOfParents;
        return this;
    }

    public String getCrimeName() {
        return crimeName;
    }

    public WantedPeopleEntity setCrimeName(String crimeName) {
        this.crimeName = crimeName;
        return this;
    }

    public String getDecisionDate() {
        return decisionDate;
    }

    public WantedPeopleEntity setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate;
        return this;
    }

    public String getDecisionOffice() {
        return decisionOffice;
    }

    public WantedPeopleEntity setDecisionOffice(String decisionOffice) {
        this.decisionOffice = decisionOffice;
        return this;
    }
}
