/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektsi_redukt;

/**
 *
 * @author Mateusz
 */
public class Attribute {
    private String name;
    private String value;
    private boolean decisionMaking;
    
    @Override
    public String toString() {
        if (!decisionMaking)
            return "Attribute{" + "name=" + name + ", value=" + value + '}';
        else return "Attribute{" + "name=" + name + ", value=" + value + "decisionMaking=" + decisionMaking + '}';
    }

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    public boolean isDecisionMaking() {
        return decisionMaking;
    }

    public void setDecisionMaking(boolean decisionMaking) {
        this.decisionMaking = decisionMaking;
    }
}
