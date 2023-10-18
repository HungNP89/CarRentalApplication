package com.example.carrentalapplication.contract;

import com.example.carrentalapplication.model.AvailableModel;
import com.example.carrentalapplication.model.HotDealModel;
import com.example.carrentalapplication.model.PopularModel;

import java.util.List;

public interface NormalCarContract {
    interface View {
        void showPopularNormalCar(List<PopularModel> list);
        void showHotDealNormalCar(List<HotDealModel> list);
        void showAvailableNormalCar(List<AvailableModel> list);
        void showError(String message);
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        void loadPopularNormalCar();
        void loadHotDealNormalCar();
        void loadAvailableNormalCar();
    }
}
