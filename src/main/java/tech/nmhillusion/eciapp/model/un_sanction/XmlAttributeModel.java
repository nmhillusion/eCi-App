package tech.nmhillusion.eciapp.model.un_sanction;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public class XmlAttributeModel {
    private String name;
    private String qualifiedName;
    private String value;

    public String getName() {
        return name;
    }

    public XmlAttributeModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public XmlAttributeModel setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
        return this;
    }

    public String getValue() {
        return value;
    }

    public XmlAttributeModel setValue(String value) {
        this.value = value;
        return this;
    }
}
