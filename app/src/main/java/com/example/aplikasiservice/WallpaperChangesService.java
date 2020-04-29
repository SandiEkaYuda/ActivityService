package com.example.aplikasiservice;

import android.app.Service;
import android.app.WallpaperManager;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class WallpaperChangesService extends Service implements Runnable {

    private int wallpaper[] = {R.drawable.wallpaper1, R.drawable.wallpaper2};

    private int waktu;

    private int FLAG=0;
    private Thread t;

    private Bitmap gambar;
    private Bitmap gambar1;
    private int[] wallpaperId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Bundle bundle=intent.getExtras();
        waktu = bundle.getInt("Durasi");
        gambar=BitmapFactory.decodeResource(getResources(),wallpaperId[0]);
        gambar=BitmapFactory.decodeResource(getResources(),wallpaperId[1]);
        t = new Thread(WallpaperChangesService.this);
        t.start();
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    @Override
    public void run() {
        WallpaperManager myWallpaper;
        myWallpaper = WallpaperManager.getInstance(this);
        try {
            while (true) {
                if (FLAG == 0) {
                    myWallpaper.setBitmap(gambar);
                    FLAG++;
                } else {
                    myWallpaper.setBitmap(gambar1);
                    FLAG--;
                }
                Thread.sleep(100 * waktu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}