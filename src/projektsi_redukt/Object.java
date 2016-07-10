/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektsi_redukt;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mateusz
 */
public class Object {
    private String name;
    private ArrayList<Attribute> attributes;
    
    @Override
    public String toString() {
        return "Object{" + "name=" + name + ", attributes=" + attributes + '}';
    }

    public Object(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

}
