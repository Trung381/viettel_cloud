package com.example.viettel_cloud.other_service.viettel_cloud;

import com.example.viettel_cloud.dto.request.viettel_cloud.SubscriptionRecord;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface ViettelCloudCommunicate {
    @GET("/api/subscriptions/get/")
    Call<List<SubscriptionRecord>> getSubscriptions(
            @Header("X-API-KEY") String apiKey,
            @Query("customer_id") String customerId,
            @Query("subscription_filters") List<String> subscriptionFilters,
            @Query("plan_id") String planId,
            @Query("status") String status
    );

    @GET("/api/subscriptions/{subscription_id}/get/")
    Call<SubscriptionRecord> getSubscriptionDetail(
            @Header("X-API-KEY") String apiKey,
            @Path("subscription_id") String subscriptionId
    );

    @PUT("/api/subscriptions/{subscription_id}/update/")
    Call<SubscriptionRecord> updateSubscription(
            @Header("X-API-KEY") String apiKey,
            @Path("subscription_id") String subscriptionId,
            @Body Map<String, Object> metadata
    );
}
