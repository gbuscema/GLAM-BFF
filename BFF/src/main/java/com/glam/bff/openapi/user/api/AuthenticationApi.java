package com.glam.bff.openapi.user.api;

import com.glam.bff.openapi.user.ApiClient;

import com.glam.bff.openapi.user.model.UserDAO;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-11-19T13:10:36.111141+01:00[Europe/Rome]")
@Component("com.glam.bff.openapi.user.api.AuthenticationApi")
public class AuthenticationApi {
    private ApiClient apiClient;

    public AuthenticationApi() {
        this(new ApiClient());
    }

    @Autowired
    public AuthenticationApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * API to get the user info in GLAM
     * Used to get the user info in GLAM
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @return UserDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserDAO getUserInfo(String userId) throws RestClientException {
        return getUserInfoWithHttpInfo(userId).getBody();
    }

    /**
     * API to get the user info in GLAM
     * Used to get the user info in GLAM
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @return ResponseEntity&lt;UserDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserDAO> getUserInfoWithHttpInfo(String userId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling getUserInfo");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userId", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<UserDAO> localReturnType = new ParameterizedTypeReference<UserDAO>() {};
        return apiClient.invokeAPI("/authentication/users/{userId}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to login the user in GLAM
     * Used to login the user in GLAM
     * <p><b>200</b> - OK
     * @param userDAO  (required)
     * @return UserDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserDAO loginUser(UserDAO userDAO) throws RestClientException {
        return loginUserWithHttpInfo(userDAO).getBody();
    }

    /**
     * API to login the user in GLAM
     * Used to login the user in GLAM
     * <p><b>200</b> - OK
     * @param userDAO  (required)
     * @return ResponseEntity&lt;UserDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserDAO> loginUserWithHttpInfo(UserDAO userDAO) throws RestClientException {
        Object localVarPostBody = userDAO;
        
        // verify the required parameter 'userDAO' is set
        if (userDAO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userDAO' when calling loginUser");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<UserDAO> localReturnType = new ParameterizedTypeReference<UserDAO>() {};
        return apiClient.invokeAPI("/authentication/login", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to register the user in GLAM
     * Used to register the user in GLAM
     * <p><b>200</b> - OK
     * @param userDAO  (required)
     * @return UserDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserDAO registerUser(UserDAO userDAO) throws RestClientException {
        return registerUserWithHttpInfo(userDAO).getBody();
    }

    /**
     * API to register the user in GLAM
     * Used to register the user in GLAM
     * <p><b>200</b> - OK
     * @param userDAO  (required)
     * @return ResponseEntity&lt;UserDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserDAO> registerUserWithHttpInfo(UserDAO userDAO) throws RestClientException {
        Object localVarPostBody = userDAO;
        
        // verify the required parameter 'userDAO' is set
        if (userDAO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userDAO' when calling registerUser");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<UserDAO> localReturnType = new ParameterizedTypeReference<UserDAO>() {};
        return apiClient.invokeAPI("/authentication/register", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to update the user info in GLAM
     * Used to update the user info in GLAM
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param userDAO  (required)
     * @return UserDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserDAO updateUserInfo(Integer userId, UserDAO userDAO) throws RestClientException {
        return updateUserInfoWithHttpInfo(userId, userDAO).getBody();
    }

    /**
     * API to update the user info in GLAM
     * Used to update the user info in GLAM
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param userDAO  (required)
     * @return ResponseEntity&lt;UserDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserDAO> updateUserInfoWithHttpInfo(Integer userId, UserDAO userDAO) throws RestClientException {
        Object localVarPostBody = userDAO;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling updateUserInfo");
        }
        
        // verify the required parameter 'userDAO' is set
        if (userDAO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userDAO' when calling updateUserInfo");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userId", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<UserDAO> localReturnType = new ParameterizedTypeReference<UserDAO>() {};
        return apiClient.invokeAPI("/authentication/users/{userId}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
