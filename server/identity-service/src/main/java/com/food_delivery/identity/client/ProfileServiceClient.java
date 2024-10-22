//package com.food_delivery.identity.client;
//
//import com.food_delivery.identity.dto.response.ApiResponse;
//import com.food_delivery.identity.dto.response.ProfileServiceResponse;
//import com.food_delivery.identity.security.AuthenticationRequestInterceptor;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@FeignClient(
//        name = "profile-service",
//        configuration = {AuthenticationRequestInterceptor.class}
//)
//public interface ProfileServiceClient {
//
//    @PostMapping(
//            value = "/profile/internal/create",
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    ApiResponse<ProfileServiceResponse> createProfile(
//            @RequestBody ProfileCreateRequest profileCreateRequest
//    );
//}
