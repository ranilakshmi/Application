package com.app3c.application.alarm;

import com.app3c.application.BasePresenter;
import com.app3c.application.BaseView;
import com.app3c.application.data.source.History;
import com.app3c.application.data.source.MedicineAlarm;



public interface ReminderContract {

    interface View extends BaseView<Presenter> {

        void showMedicine(MedicineAlarm medicineAlarm);

        void showNoData();

        boolean isActive();

        void onFinish();

    }

    interface Presenter extends BasePresenter {

        void finishActivity();

        void onStart(long id);

        void loadMedicineById(long id);

        void addPillsToHistory(History history);

    }
}
