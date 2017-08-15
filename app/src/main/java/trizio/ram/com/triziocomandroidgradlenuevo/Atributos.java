package trizio.ram.com.triziocomandroidgradlenuevo;

/**
 * Created by MONO on 22/03/2017.
 */

public class Atributos {
    String code = null;
    String name = null;
    String formula = null;
    boolean selected = false;

    public Atributos(String code, String name, String formula) {
        super();
        this.code = code;
        this.name = name;
        this.formula = formula;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFormula() {
        return formula;
    }
    public void setFormula(String formula) {
        this.formula = formula;
    }

}
