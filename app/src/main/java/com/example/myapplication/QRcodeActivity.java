package com.example.myapplication;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.nio.channels.WritePendingException;

public class QRcodeActivity extends AppCompatActivity {

    private static final int QRCODE_WIDTH=400,QRCODE_HEIGHT=400;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Bitmap bitmap;
    ImageView ivQRcode;
    String strUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrcode);

        ivQRcode =findViewById(R.id.ivQRcode);
        preferences= PreferenceManager.getDefaultSharedPreferences(QRcodeActivity.this);
        editor=preferences.edit();

        strUsername =preferences.getString("Username","");

        try{
            createQRcode();

        }catch(WriterException e){
            Toast.makeText(QRcodeActivity.this,""+e,Toast.LENGTH_SHORT).show();
        }

    }

    private void createQRcode()throws WriterException {
        bitmap = textToImageEncode(strUsername);
        ivQRcode.setImageBitmap(bitmap);

    }

    private Bitmap textToImageEncode(String strUsername)throws WriterException {
        BitMatrix bitMatrix;
        bitMatrix = new MultiFormatWriter().encode(strUsername, BarcodeFormat.QR_CODE,QRCODE_WIDTH,QRCODE_HEIGHT);
        int[] pixels = new int[bitMatrix.getHeight()*bitMatrix.getWidth()];

        for (int x=0;x<bitMatrix.getWidth();x++){
            int offset = x*bitMatrix.getHeight();

            for (int y=0;y<bitMatrix.getHeight();y++){
                pixels[offset+y] = bitMatrix.get(x,y)?
                        getResources().getColor(R.color.red_Qr_shade):
                        getResources().getColor(R.color.white);
            }
        }
        bitmap = Bitmap.createBitmap(bitMatrix.getWidth(),bitMatrix.getHeight(),
                    Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels,0,QRCODE_HEIGHT,0,0,QRCODE_WIDTH,QRCODE_HEIGHT);

        return bitmap;
    }
}