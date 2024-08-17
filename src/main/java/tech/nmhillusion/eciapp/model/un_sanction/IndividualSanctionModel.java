package tech.nmhillusion.eciapp.model.un_sanction;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public class IndividualSanctionModel extends Stringeable {
    /*
            <DATAID>110554</DATAID>
            <VERSIONNUM>1</VERSIONNUM>
            <FIRST_NAME>ABDUL LATIF</FIRST_NAME>
            <SECOND_NAME>MANSUR</SECOND_NAME>
            <UN_LIST_TYPE>Taliban</UN_LIST_TYPE>
            <REFERENCE_NUMBER>TAi.007</REFERENCE_NUMBER>
            <LISTED_ON>2001-01-31</LISTED_ON>
            <NAME_ORIGINAL_SCRIPT>عبد اللطيف منصور </NAME_ORIGINAL_SCRIPT>
            <COMMENTS1>Taliban Shadow Governor for Logar Province as of late 2012. Believed to
be in Afghanistan/Pakistan border area. Belongs to Sahak tribe (Ghilzai). Review
pursuant to Security Council resolution 1822 (2008) was concluded on 27 Jul.
2010. INTERPOL-UN Security Council Special Notice web link:https://www.interpol.int/en/How-we-work/Notices/View-UN-Notices-Individuals</COMMENTS1>
            <TITLE>
                <VALUE>Maulavi</VALUE>
            </TITLE>
            <DESIGNATION>
                <VALUE>Minister of Agriculture under the Taliban regime</VALUE>
            </DESIGNATION>
            <NATIONALITY>
                <VALUE>Afghanistan</VALUE>
            </NATIONALITY>
            <LIST_TYPE>
                <VALUE>UN List</VALUE>
            </LIST_TYPE>
            <LAST_DAY_UPDATED>
                <VALUE>2003-09-03</VALUE>
                <VALUE>2007-07-18</VALUE>
                <VALUE>2007-09-21</VALUE>
                <VALUE>2012-02-13</VALUE>
                <VALUE>2012-05-18</VALUE>
                <VALUE>2013-04-22</VALUE>
            </LAST_DAY_UPDATED>
            <INDIVIDUAL_ALIAS>
                <QUALITY>Good</QUALITY>
                <ALIAS_NAME>Abdul Latif Mansoor</ALIAS_NAME>
            </INDIVIDUAL_ALIAS>
            <INDIVIDUAL_ALIAS>
                <QUALITY>Good</QUALITY>
                <ALIAS_NAME>Wali Mohammad</ALIAS_NAME>
            </INDIVIDUAL_ALIAS>
            <INDIVIDUAL_ALIAS>
                <QUALITY/>
                <ALIAS_NAME/>
            </INDIVIDUAL_ALIAS>
            <INDIVIDUAL_ADDRESS>
                <COUNTRY/>
            </INDIVIDUAL_ADDRESS>
            <INDIVIDUAL_DATE_OF_BIRTH>
                <TYPE_OF_DATE>APPROXIMATELY</TYPE_OF_DATE>
                <YEAR>1968</YEAR>
            </INDIVIDUAL_DATE_OF_BIRTH>
            <INDIVIDUAL_PLACE_OF_BIRTH>
                <CITY>Zurmat District</CITY>
                <STATE_PROVINCE>Paktia Province</STATE_PROVINCE>
                <COUNTRY>Afghanistan</COUNTRY>
            </INDIVIDUAL_PLACE_OF_BIRTH>
     */
    private String dataid;
    private String versionnum;
    private String first_name;
    private String second_name;
    private String third_name;
    private String un_list_type;
    private String reference_number;
    private String listed_on;
    private String name_original_script;
    private String comments1;
    private String title;
    private String designation;
    private String nationality;
    private String list_type;
    private String last_day_updated;
    private String individual_alias;
    private String individual_address;
    private String individual_date_of_birth;
    private String individual_place_of_birth;

    public String getDataid() {
        return dataid;
    }

    public IndividualSanctionModel setDataid(String dataid) {
        this.dataid = dataid;
        return this;
    }

    public String getVersionnum() {
        return versionnum;
    }

    public IndividualSanctionModel setVersionnum(String versionnum) {
        this.versionnum = versionnum;
        return this;
    }

    public String getFirst_name() {
        return first_name;
    }

    public IndividualSanctionModel setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public String getSecond_name() {
        return second_name;
    }

    public IndividualSanctionModel setSecond_name(String second_name) {
        this.second_name = second_name;
        return this;
    }

    public String getThird_name() {
        return third_name;
    }

    public IndividualSanctionModel setThird_name(String third_name) {
        this.third_name = third_name;
        return this;
    }

    public String getUn_list_type() {
        return un_list_type;
    }

    public IndividualSanctionModel setUn_list_type(String un_list_type) {
        this.un_list_type = un_list_type;
        return this;
    }

    public String getReference_number() {
        return reference_number;
    }

    public IndividualSanctionModel setReference_number(String reference_number) {
        this.reference_number = reference_number;
        return this;
    }

    public String getListed_on() {
        return listed_on;
    }

    public IndividualSanctionModel setListed_on(String listed_on) {
        this.listed_on = listed_on;
        return this;
    }

    public String getName_original_script() {
        return name_original_script;
    }

    public IndividualSanctionModel setName_original_script(String name_original_script) {
        this.name_original_script = name_original_script;
        return this;
    }

    public String getComments1() {
        return comments1;
    }

    public IndividualSanctionModel setComments1(String comments1) {
        this.comments1 = comments1;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public IndividualSanctionModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public IndividualSanctionModel setDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    public IndividualSanctionModel setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public String getList_type() {
        return list_type;
    }

    public IndividualSanctionModel setList_type(String list_type) {
        this.list_type = list_type;
        return this;
    }

    public String getLast_day_updated() {
        return last_day_updated;
    }

    public IndividualSanctionModel setLast_day_updated(String last_day_updated) {
        this.last_day_updated = last_day_updated;
        return this;
    }

    public String getIndividual_alias() {
        return individual_alias;
    }

    public IndividualSanctionModel setIndividual_alias(String individual_alias) {
        this.individual_alias = individual_alias;
        return this;
    }

    public String getIndividual_address() {
        return individual_address;
    }

    public IndividualSanctionModel setIndividual_address(String individual_address) {
        this.individual_address = individual_address;
        return this;
    }

    public String getIndividual_date_of_birth() {
        return individual_date_of_birth;
    }

    public IndividualSanctionModel setIndividual_date_of_birth(String individual_date_of_birth) {
        this.individual_date_of_birth = individual_date_of_birth;
        return this;
    }

    public String getIndividual_place_of_birth() {
        return individual_place_of_birth;
    }

    public IndividualSanctionModel setIndividual_place_of_birth(String individual_place_of_birth) {
        this.individual_place_of_birth = individual_place_of_birth;
        return this;
    }
}
