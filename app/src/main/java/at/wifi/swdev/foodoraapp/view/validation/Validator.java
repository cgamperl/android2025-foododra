package at.wifi.swdev.foodoraapp.view.validation;

import android.text.Editable;
import android.text.TextWatcher;

public interface Validator extends TextWatcher {

    @Override
    default void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Egal
    }

    @Override
    void onTextChanged(CharSequence charSequence, int i, int i1, int i2);

    @Override
    default void afterTextChanged(Editable editable) {
        // Egal
    }
}
