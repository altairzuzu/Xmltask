package ru.solomatin.xmltask.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by altair on 25.07.2016.
 */
@Root(name = "E1WPW01", strict = false)
public class Product {

    @Element(name = "E1WPW02")
    private ProductName name;

    @Element(name = "WARENGR")
    private String id;

    @Element(name = "AKTIVDATUM")
    private String actDateStr;

    public Product() {}


    @Root
    private class ProductName {

        @Element(name = "BEZEICH")
        private String nameStr;

        @Element(name = "HIERARCHIE")
        private String hierarchie;

        @Element(name = "VERKNUEPFG")
        private String parentId;
    }
}
