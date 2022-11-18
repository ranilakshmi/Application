package com.app3c.application;

import android.content.Context;

import androidx.annotation.NonNull;

import com.app3c.application.data.source.MedicineRepository;
import com.app3c.application.data.source.local.MedicinesLocalDataSource;


public class Injection {

    public static MedicineRepository provideMedicineRepository(@NonNull Context context) {
        return MedicineRepository.getInstance(MedicinesLocalDataSource.getInstance(context));
    }
}
