package com.example.projectsemester4;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<List<DataSurat>> _dataSurat =
            new MutableLiveData(new DataSurat(null, null));
    public LiveData<List<DataSurat>> getDataSurat() {
        return _dataSurat;
    }
    private final MutableLiveData<List<DataSurat>> _filteredDataSurat =
            new MutableLiveData(new DataSurat(null, null));
    public LiveData<List<DataSurat>> getFilteredDataSurat() {
        return _filteredDataSurat;
    }

    public MainViewModel() {
        Log.i("MainViewModel","ViewModel is Created !");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("MainViewModel","ViewModel is Destroyed !");
    }

    public void search(String query) {
        List<DataSurat> dataList = new ArrayList<>();
        if(query.length() != 0){
            for (DataSurat data : _dataSurat.getValue()) {
                if (data.getMataKuliah().toLowerCase().contains(query.toLowerCase()) ||
                        data.getNamaMhs().toLowerCase().contains(query.toLowerCase())) {
                    dataList.add(data);
                }
            }
        }else{
            dataList.addAll(_dataSurat.getValue());
        }
        _filteredDataSurat.setValue(dataList);
    }

    public void initDataList() {
        List<DataSurat> dataList = new ArrayList<>();
        // Add dummy data to the dataList
        for (int i = 1; i <= 10; i++) {
            String mataKuliah = "Mata Kuliah " + i;
            String namaMhs = "Nama Mahasiswa " + i;
            dataList.add(new DataSurat(mataKuliah, namaMhs));
        }
        _dataSurat.setValue(dataList);
        _filteredDataSurat.setValue(dataList);
    }

}