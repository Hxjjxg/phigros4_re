package com.digits.sdk.android;

import android.R;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
class CountryListAdapter extends ArrayAdapter<CountryInfo> implements SectionIndexer {
    private final HashMap<String, Integer> alphaIndex;
    private String[] sections;

    @Override // android.widget.SectionIndexer
    public int getSectionForPosition(int i) {
        return 0;
    }

    public CountryListAdapter(Context context) {
        super(context, R.layout.dgts__country_row, R.id.text1);
        this.alphaIndex = new LinkedHashMap();
    }

    public void setData(List<CountryInfo> list) {
        int i = 0;
        for (CountryInfo countryInfo : list) {
            String upperCase = countryInfo.country.substring(0, 1).toUpperCase(Locale.getDefault());
            if (!this.alphaIndex.containsKey(upperCase)) {
                this.alphaIndex.put(upperCase, Integer.valueOf(i));
            }
            i++;
            add(countryInfo);
        }
        this.sections = new String[this.alphaIndex.size()];
        this.alphaIndex.keySet().toArray(this.sections);
        notifyDataSetChanged();
    }

    @Override // android.widget.SectionIndexer
    public Object[] getSections() {
        return this.sections;
    }

    @Override // android.widget.SectionIndexer
    public int getPositionForSection(int i) {
        String[] strArr = this.sections;
        if (strArr == null || i <= 0) {
            return 0;
        }
        if (i >= strArr.length) {
            i = strArr.length - 1;
        }
        return this.alphaIndex.get(strArr[i]).intValue();
    }
}
