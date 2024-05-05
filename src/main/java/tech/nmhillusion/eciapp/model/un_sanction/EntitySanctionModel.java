package tech.nmhillusion.eciapp.model.un_sanction;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public class EntitySanctionModel extends Stringeable {
    /*

    <DATAID>113226</DATAID>
            <VERSIONNUM>1</VERSIONNUM>
            <FIRST_NAME>AFGHAN SUPPORT COMMITTEE (ASC)</FIRST_NAME>
            <UN_LIST_TYPE>Al-Qaida</UN_LIST_TYPE>
            <REFERENCE_NUMBER>QDe.069</REFERENCE_NUMBER>
            <LISTED_ON>2002-01-11</LISTED_ON>
            <COMMENTS1>Associated with the Revival of Islamic Heritage Society (QDe.070). Abu Bakr al-Jaziri (QDi.058) served as finance chief of ASC. Review pursuant to Security Council resolution 1822 (2008) was concluded on 8 Jun. 2010. Review pursuant to Security Council resolution 2368 (2017) was concluded on 15 November 2021. INTERPOL-UN Security Council Special Notice web link:https://www.interpol.int/en/How-we-work/Notices/View-UN-Notices-Individuals</COMMENTS1>
            <LIST_TYPE>
                <VALUE>UN List</VALUE>
            </LIST_TYPE>
            <LAST_DAY_UPDATED>
                <VALUE>2011-12-13</VALUE>
                <VALUE>2021-11-15</VALUE>
            </LAST_DAY_UPDATED>
            <ENTITY_ALIAS>
                <QUALITY>a.k.a.</QUALITY>
                <ALIAS_NAME>Lajnat ul Masa Eidatul Afghania</ALIAS_NAME>
            </ENTITY_ALIAS>
            <ENTITY_ALIAS>
                <QUALITY>a.k.a.</QUALITY>
                <ALIAS_NAME>Jamiat Ayat-ur-Rhas al Islamiac</ALIAS_NAME>
            </ENTITY_ALIAS>
            <ENTITY_ALIAS>
                <QUALITY>a.k.a.</QUALITY>
                <ALIAS_NAME>Jamiat Ihya ul Turath al Islamia</ALIAS_NAME>
            </ENTITY_ALIAS>
            <ENTITY_ALIAS>
                <QUALITY>a.k.a.</QUALITY>
                <ALIAS_NAME>Ahya ul Turas</ALIAS_NAME>
            </ENTITY_ALIAS>
            <ENTITY_ADDRESS>
                <STREET>Headquarters – G.T. Road (probably Grand Trunk Road), near Pushtoon Garhi Pabbi</STREET>
                <CITY> Peshawar</CITY>
                <COUNTRY>Pakistan</COUNTRY>
            </ENTITY_ADDRESS>
            <ENTITY_ADDRESS>
                <STREET>Cheprahar Hadda, Mia Omar Sabaqah School</STREET>
                <CITY>Jalabad</CITY>
                <COUNTRY>Afghanistan</COUNTRY>
            </ENTITY_ADDRESS>

     */

    private String dataid;
    private String versionnum;
    private String first_name;
    private String un_list_type;
    private String reference_number;
    private String listed_on;
    private String comments1;
    private String list_type;
    private String last_day_updated;
    private String entity_alias;
    private String entity_address;

    public String getDataid() {
        return dataid;
    }

    public EntitySanctionModel setDataid(String dataid) {
        this.dataid = dataid;
        return this;
    }

    public String getVersionnum() {
        return versionnum;
    }

    public EntitySanctionModel setVersionnum(String versionnum) {
        this.versionnum = versionnum;
        return this;
    }

    public String getFirst_name() {
        return first_name;
    }

    public EntitySanctionModel setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public String getUn_list_type() {
        return un_list_type;
    }

    public EntitySanctionModel setUn_list_type(String un_list_type) {
        this.un_list_type = un_list_type;
        return this;
    }

    public String getReference_number() {
        return reference_number;
    }

    public EntitySanctionModel setReference_number(String reference_number) {
        this.reference_number = reference_number;
        return this;
    }

    public String getListed_on() {
        return listed_on;
    }

    public EntitySanctionModel setListed_on(String listed_on) {
        this.listed_on = listed_on;
        return this;
    }

    public String getComments1() {
        return comments1;
    }

    public EntitySanctionModel setComments1(String comments1) {
        this.comments1 = comments1;
        return this;
    }

    public String getList_type() {
        return list_type;
    }

    public EntitySanctionModel setList_type(String list_type) {
        this.list_type = list_type;
        return this;
    }

    public String getLast_day_updated() {
        return last_day_updated;
    }

    public EntitySanctionModel setLast_day_updated(String last_day_updated) {
        this.last_day_updated = last_day_updated;
        return this;
    }

    public String getEntity_alias() {
        return entity_alias;
    }

    public EntitySanctionModel setEntity_alias(String entity_alias) {
        this.entity_alias = entity_alias;
        return this;
    }

    public String getEntity_address() {
        return entity_address;
    }

    public EntitySanctionModel setEntity_address(String entity_address) {
        this.entity_address = entity_address;
        return this;
    }
}
