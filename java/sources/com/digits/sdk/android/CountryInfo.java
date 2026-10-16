package com.digits.sdk.android;

import java.text.Collator;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
class CountryInfo implements Comparable<CountryInfo> {
    private final Collator collator;
    public final String country;
    public final int countryCode;

    public CountryInfo(String str, int i) {
        Collator collator = Collator.getInstance(Locale.getDefault());
        this.collator = collator;
        collator.setStrength(0);
        this.country = str;
        this.countryCode = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CountryInfo countryInfo = (CountryInfo) obj;
        if (this.countryCode != countryInfo.countryCode) {
            return false;
        }
        String str = this.country;
        String str2 = countryInfo.country;
        return str == null ? str2 == null : str.equals(str2);
    }

    public int hashCode() {
        String str = this.country;
        return ((str != null ? str.hashCode() : 0) * 31) + this.countryCode;
    }

    public String toString() {
        return this.country + " +" + this.countryCode;
    }

    @Override // java.lang.Comparable
    public int compareTo(CountryInfo countryInfo) {
        return this.collator.compare(this.country, countryInfo.country);
    }
}
