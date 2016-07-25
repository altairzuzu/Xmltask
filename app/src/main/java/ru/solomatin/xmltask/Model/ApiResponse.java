package ru.solomatin.xmltask.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Ответ от API
 */
@Root(name="WPDWGR01", strict = false)
public class ApiResponse {

    @Path("IDOC")
    @ElementList(inline = true)
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public ApiResponse() {}


}
