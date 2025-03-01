package at.wifi.swdev.foodoraapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl("http://localhost:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // In der Service-Klasse definieren wir die API-Endpunkte
    private static final FoodoraApiService API_SERVICE = RETROFIT.create(FoodoraApiService.class);

    public static FoodoraApiService getApiService() {
        return API_SERVICE;
    }
}
