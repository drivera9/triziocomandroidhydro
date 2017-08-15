package trizio.ram.com.triziocomandroidgradlenuevo;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by MONO on 22/06/2017.
 */

public class EditTextWatcher implements TextWatcher {

    private ItemCheck data;

    public EditTextWatcher(ItemCheck data) {
        this.data = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        data.setCant(s.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {

    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

    }

}
