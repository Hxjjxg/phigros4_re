package com.google.android.gms.drive.widget;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.internal.GmsLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataBufferAdapter<T> extends BaseAdapter {
    private static final GmsLogger zzbx = new GmsLogger("DataBufferAdapter", "");
    private final int fieldId;
    private final int resource;
    private final Context zzgu;
    private int zzmj;
    private final List<DataBuffer<T>> zzmk;
    private final LayoutInflater zzml;
    private boolean zzmm;

    public DataBufferAdapter(Context context, int i) {
        this(context, i, 0, new ArrayList());
    }

    public DataBufferAdapter(Context context, int i, int i2) {
        this(context, i, i2, new ArrayList());
    }

    public DataBufferAdapter(Context context, int i, int i2, List<DataBuffer<T>> list) {
        this.zzmm = true;
        this.zzgu = context;
        this.zzmj = i;
        this.resource = i;
        this.fieldId = i2;
        this.zzmk = list;
        this.zzml = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public DataBufferAdapter(Context context, int i, int i2, DataBuffer<T>... dataBufferArr) {
        this(context, i, i2, Arrays.asList(dataBufferArr));
    }

    public DataBufferAdapter(Context context, int i, List<DataBuffer<T>> list) {
        this(context, i, 0, list);
    }

    public DataBufferAdapter(Context context, int i, DataBuffer<T>... dataBufferArr) {
        this(context, i, 0, Arrays.asList(dataBufferArr));
    }

    private final View zza(int i, View view, ViewGroup viewGroup, int i2) {
        if (view == null) {
            view = this.zzml.inflate(i2, viewGroup, false);
        }
        try {
            int i3 = this.fieldId;
            TextView textView = i3 == 0 ? (TextView) view : (TextView) view.findViewById(i3);
            T item = getItem(i);
            textView.setText(item instanceof CharSequence ? (CharSequence) item : item.toString());
            return view;
        } catch (ClassCastException e) {
            zzbx.e("DataBufferAdapter", "You must supply a resource ID for a TextView", e);
            throw new IllegalStateException("DataBufferAdapter requires the resource ID to be a TextView", e);
        }
    }

    public void append(DataBuffer<T> dataBuffer) {
        this.zzmk.add(dataBuffer);
        if (this.zzmm) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        Iterator<DataBuffer<T>> it = this.zzmk.iterator();
        while (it.hasNext()) {
            it.next().release();
        }
        this.zzmk.clear();
        if (this.zzmm) {
            notifyDataSetChanged();
        }
    }

    public Context getContext() {
        return this.zzgu;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        Iterator<DataBuffer<T>> it = this.zzmk.iterator();
        int count = 0;
        while (it.hasNext()) {
            count += it.next().getCount();
        }
        return count;
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return zza(i, view, viewGroup, this.zzmj);
    }

    @Override // android.widget.Adapter
    public T getItem(int i) throws CursorIndexOutOfBoundsException {
        int i2 = i;
        for (DataBuffer<T> dataBuffer : this.zzmk) {
            int count = dataBuffer.getCount();
            if (count > i2) {
                try {
                    return dataBuffer.get(i2);
                } catch (CursorIndexOutOfBoundsException unused) {
                    throw new CursorIndexOutOfBoundsException(i, getCount());
                }
            }
            i2 -= count;
        }
        throw new CursorIndexOutOfBoundsException(i, getCount());
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return zza(i, view, viewGroup, this.resource);
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.zzmm = true;
    }

    public void setDropDownViewResource(int i) {
        this.zzmj = i;
    }

    public void setNotifyOnChange(boolean z) {
        this.zzmm = z;
    }
}
