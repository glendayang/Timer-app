package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AssistService extends Service {
    public AssistService() {
    }

    public class LocalBinder extends Binder
    {
        public AssistService getService()
        {
            return AssistService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
