package ru.solomatin.xmltask.Model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by altair on 25.07.2016.
 */
@Root(name = "E1WPW01", strict = false)
public class Product {

    @Attribute(name = "SEGMENT")
    private int segment;

    @Element(name = "FILIALE")
    private String filiale;

    @Element(name = "AENDKENNZ")
    private String aendkennz;

    @Element(name = "AENDDATUM")
    private String aenddatum;

    // Код товара
    @Element(name = "WARENGR")
    private String id;

    // Дата активации
    @Element(name = "AKTIVDATUM")
    private String activdatum;

    // Имя товара
    @Path("E1WPW02")
    @Element(name = "BEZEICH")
    private String name;

    @Path("E1WPW02")
    @Element(name = "HIERARCHIE")
    private String hierarchie;

    @Path("E1WPW02")
    @Element(name = "VERKNUEPFG", required = false)
    private String verknuepfg;

    @Override
    public boolean equals(Object object) {
        boolean isEqual= false;
        if (object != null && object instanceof Product) {
            isEqual = this.id.equals(((Product) object).id);
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.id);
    }

    public int getSegment() {
        return segment;
    }

    public String getFiliale() {
        return filiale;
    }

    public String getAendkennz() {
        return aendkennz;
    }

    public String getAenddatum() {
        return aenddatum;
    }

    public String getId() {
        return id;
    }

    public String getActivdatum() {
        return activdatum;
    }

    public String getName() {
        return name;
    }

    public String getHierarchie() {
        return hierarchie;
    }

    public String getVerknuepfg() {
        return verknuepfg;
    }
}
