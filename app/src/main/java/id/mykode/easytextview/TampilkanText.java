package id.mykode.easytextview;
import android.widget.*;
import android.content.*;
import android.util.*;
import android.os.*;
import android.database.*;
import android.provider.*;
import android.text.*;
import android.view.*;
import java.lang.reflect.*;

/**
 * Created by Suyono on 3/19/2016.
 * Copyright (c) 2016 by Suyono (ion).
 * All rights reserved.
 * This product is protected by copyright and distributed under
 * licenses restricting copying, distribution and decompilation.
 */
public class TampilkanText extends TextView
{
	/** mulai : kode wajib (Context) **/
    public TampilkanText(Context context) {
        super(context);
        if (!isInEditMode()){
            // menaruh/memanggil kode utama kita di konteks
            kodeUtama();
        }
    }
    public TampilkanText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()){
            // menaruh/memanggil kode utama kita di konteks
            kodeUtama();
        }
    }
    public TampilkanText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()){
            // menaruh/memanggil kode utama kita di konteks
            kodeUtama();
        }
    }
    /* selesai : kode wajib (Context) */

    @Override
    protected void onAttachedToWindow() {
        // TODO Auto-generated method stub
        super.onAttachedToWindow();
        /* mulai : menambahkan pengaturan baru untuk mengatur text */
        new SettingsObserver(new Handler()).observe();
        /* selesai : menambahkan pengaturan baru untuk mengatur text */
    }

    /* mulai : SettingsObserver class */
    class SettingsObserver extends ContentObserver{
        public SettingsObserver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }
        void observe(){
            ContentResolver resolver = TampilkanText.this.getContext().getContentResolver();
			/* mulai : uri = "uri_edit_isi_text",
			 kata kunci unik, setiap setting berbeda-beda uri
			 kita akan menamakan setting kita dengan uri_edit_isi_text
			 */
            resolver.registerContentObserver(Settings.System.getUriFor("uri_edit_isi_text"), false, this);
			/* selesai : uri = "uri_edit_isi_text" */
        }
        @Override
        public void onChange(boolean selfChange) {
            // TODO Auto-generated method stub
            //super.onChange(selfChange);
            kodeUtama();
        }
    }
    /* selesai : SettingsObserver class */

    /* mulai : kode utama kita */
    private void kodeUtama() {
        /* mulai : mengambil string dari uri = "uri_edit_isi_text"
		 * yang berasal dari setting di preferences */
        String stringtxt_dari_setting = Settings.System.getString(getContext().getContentResolver(), "uri_edit_isi_text");
        //jika string uri kosong
        if (TextUtils.isEmpty(stringtxt_dari_setting)) {
            //biarkan ksosong
            stringtxt_dari_setting = TampilkanText.this.getText().toString();
        }
        //set TampilkanText
        setText(stringtxt_dari_setting);
        /* selesai : mengambil string dari uri = "uri_edit_isi_text" */
        setOnLongClickListener(new OnLongClickListener() {
				@SuppressWarnings("ALL")
				@Override
				public boolean onLongClick(View v) {
					Intent intent = new Intent(getContext(), SettingTampilkanText.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					v.getContext().startActivity(intent);
					try {
						Object service = getContext().getSystemService("statusbar");
						Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
						int currentapiVersion = android.os.Build.VERSION.SDK_INT;
						if (currentapiVersion <= 16) {
							Method collapse = statusbarManager.getMethod("collapse");
							collapse.invoke(service);
						} else {
							Method collapse2 = statusbarManager.getMethod("collapsePanels");
							collapse2.invoke(service);
						}

					} catch (Exception ex) {
					}
					return true;
				}
			});
    }
}
