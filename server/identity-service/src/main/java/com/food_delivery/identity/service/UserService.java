package com.food_delivery.identity.service;

import com.food_delivery.identity.dto.request.GoogleSignInRequest;
import com.food_delivery.identity.dto.request.UserSignInRequest;
import com.food_delivery.identity.dto.request.UserSignUpRequest;
import com.food_delivery.identity.dto.response.UserResponse;


public interface UserService {
    UserResponse signUp(UserSignUpRequest userSignUpRequest);
    UserResponse signIn(UserSignInRequest userSignInRequest);
    UserResponse googleSignIn(GoogleSignInRequest googleSignInRequest);
}
