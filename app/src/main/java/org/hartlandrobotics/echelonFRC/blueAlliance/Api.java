package org.hartlandrobotics.echelonFRC.blueAlliance;

import android.content.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hartlandrobotics.echelonFRC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFRC.configuration.AdminSettingsViewModel;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Api {
    private static String tbaApiKey;
    private static String userAgentPrefix = "Echelon/1.0 ";
    private static ApiInterface api;


    public static ApiInterface getApiClient(Context context) {
        if(api == null) {
            AdminSettingsViewModel vm = AdminSettingsProvider.getAdminSettings(context);
            String userAgent = userAgentPrefix + " " + vm.getTeamNumber();
            tbaApiKey = vm.getBlueAllianceApiKey();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("User-Agent", userAgent)
                        .header("X-TBA-Auth-Key", tbaApiKey)
                        .build();
                return chain.proceed(request);
            });

            OkHttpClient client = httpClient.build();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://thebluealliance.com/api/v3/")
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .client(client)
                    .build();

            api = retrofit.create(ApiInterface.class);
        }
        return api;
    }
}
