package trizio.ram.com.triziocomandroidgradlenuevo;

import android.widget.ArrayAdapter;

/**
 * Created by MONO on 21/06/2017.
 */

public class ItemCheck {

    String code = null;
    String name = null;
    String saldo = null;
    String cant = null;
    String val = null;
    String[] prices;
    boolean selected = false;
    int listaPrice;
    int index;

    public ItemCheck(String code, String name, boolean selected,String cant,String val,String saldo,String[] prices) {
        super();
        this.code = code;
        this.name = name;
        this.cant = cant;
        this.val = val;
        this.prices = prices;
        this.saldo = saldo;
        this.selected = selected;
    }

    public String getCode() {
        return code;
    }
    public String getSaldo() {
        return saldo;
    }
    public String getCant() {
        return cant;
    }
    public String getVal() {
        return val;
    }
    public String[] getPrices() {
        return prices;
    }
    public int getListaPrice() {
        return listaPrice + 1;
    }
    public void setListaPrice(int i) {
         this.listaPrice = i;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
    public void setCant(String cant) {
        this.cant = cant;
    }
    public void setVal(String val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
