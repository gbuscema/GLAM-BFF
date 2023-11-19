package com.glam.bff.openapi.wardrobe.api;

import com.glam.bff.openapi.wardrobe.ApiClient;

import com.glam.bff.openapi.wardrobe.model.OutfitDAO;

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

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-11-19T16:15:08.532396+01:00[Europe/Rome]")
@Component("com.glam.bff.openapi.wardrobe.api.OutfitApi")
public class OutfitApi {
    private ApiClient apiClient;

    public OutfitApi() {
        this(new ApiClient());
    }

    @Autowired
    public OutfitApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * API to delete the user&#39;s outfit in GLAM
     * Used to delete the user&#39;s outfit
     * <p><b>200</b> - OK
     * @param outfitId  (required)
     * @param userId  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUserOutfit(String outfitId, String userId) throws RestClientException {
        deleteUserOutfitWithHttpInfo(outfitId, userId);
    }

    /**
     * API to delete the user&#39;s outfit in GLAM
     * Used to delete the user&#39;s outfit
     * <p><b>200</b> - OK
     * @param outfitId  (required)
     * @param userId  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUserOutfitWithHttpInfo(String outfitId, String userId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'outfitId' is set
        if (outfitId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'outfitId' when calling deleteUserOutfit");
        }
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling deleteUserOutfit");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("outfitId", outfitId);
        uriVariables.put("userId", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = {  };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<Void> localReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/outfits/{outfitId}/users/{userId}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to get the user&#39;s outfit in GLAM
     * Used to get the user&#39;s outfit
     * <p><b>200</b> - OK
     * @param outfitId  (required)
     * @param userId  (required)
     * @return OutfitDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OutfitDAO getUserOutfit(String outfitId, String userId) throws RestClientException {
        return getUserOutfitWithHttpInfo(outfitId, userId).getBody();
    }

    /**
     * API to get the user&#39;s outfit in GLAM
     * Used to get the user&#39;s outfit
     * <p><b>200</b> - OK
     * @param outfitId  (required)
     * @param userId  (required)
     * @return ResponseEntity&lt;OutfitDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OutfitDAO> getUserOutfitWithHttpInfo(String outfitId, String userId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'outfitId' is set
        if (outfitId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'outfitId' when calling getUserOutfit");
        }
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling getUserOutfit");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("outfitId", outfitId);
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

        ParameterizedTypeReference<OutfitDAO> localReturnType = new ParameterizedTypeReference<OutfitDAO>() {};
        return apiClient.invokeAPI("/outfits/{outfitId}/users/{userId}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to get the user outfits in GLAM
     * Used to get the user&#39;s outfits
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @return List&lt;OutfitDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<OutfitDAO> getUserOutfits(String userId) throws RestClientException {
        return getUserOutfitsWithHttpInfo(userId).getBody();
    }

    /**
     * API to get the user outfits in GLAM
     * Used to get the user&#39;s outfits
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @return ResponseEntity&lt;List&lt;OutfitDAO&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<OutfitDAO>> getUserOutfitsWithHttpInfo(String userId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling getUserOutfits");
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

        ParameterizedTypeReference<List<OutfitDAO>> localReturnType = new ParameterizedTypeReference<List<OutfitDAO>>() {};
        return apiClient.invokeAPI("/outfits/users/{userId}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to save the user outfit in GLAM
     * Used to save the user&#39;s outfit
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param outfitDAO  (required)
     * @return OutfitDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OutfitDAO saveUserOutfit(String userId, OutfitDAO outfitDAO) throws RestClientException {
        return saveUserOutfitWithHttpInfo(userId, outfitDAO).getBody();
    }

    /**
     * API to save the user outfit in GLAM
     * Used to save the user&#39;s outfit
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param outfitDAO  (required)
     * @return ResponseEntity&lt;OutfitDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OutfitDAO> saveUserOutfitWithHttpInfo(String userId, OutfitDAO outfitDAO) throws RestClientException {
        Object localVarPostBody = outfitDAO;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling saveUserOutfit");
        }
        
        // verify the required parameter 'outfitDAO' is set
        if (outfitDAO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'outfitDAO' when calling saveUserOutfit");
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

        ParameterizedTypeReference<OutfitDAO> localReturnType = new ParameterizedTypeReference<OutfitDAO>() {};
        return apiClient.invokeAPI("/outfits/users/{userId}/save", HttpMethod.POST, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to update the user&#39;s outfit in GLAM
     * Used to update the user&#39;s outfit
     * <p><b>200</b> - OK
     * @param outfitId  (required)
     * @param userId  (required)
     * @param outfitDAO  (required)
     * @return OutfitDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OutfitDAO updateUserOutfit(String outfitId, String userId, OutfitDAO outfitDAO) throws RestClientException {
        return updateUserOutfitWithHttpInfo(outfitId, userId, outfitDAO).getBody();
    }

    /**
     * API to update the user&#39;s outfit in GLAM
     * Used to update the user&#39;s outfit
     * <p><b>200</b> - OK
     * @param outfitId  (required)
     * @param userId  (required)
     * @param outfitDAO  (required)
     * @return ResponseEntity&lt;OutfitDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OutfitDAO> updateUserOutfitWithHttpInfo(String outfitId, String userId, OutfitDAO outfitDAO) throws RestClientException {
        Object localVarPostBody = outfitDAO;
        
        // verify the required parameter 'outfitId' is set
        if (outfitId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'outfitId' when calling updateUserOutfit");
        }
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling updateUserOutfit");
        }
        
        // verify the required parameter 'outfitDAO' is set
        if (outfitDAO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'outfitDAO' when calling updateUserOutfit");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("outfitId", outfitId);
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

        ParameterizedTypeReference<OutfitDAO> localReturnType = new ParameterizedTypeReference<OutfitDAO>() {};
        return apiClient.invokeAPI("/outfits/{outfitId}/users/{userId}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to set the outfit&#39;s favorite setting in GLAM
     * Used to set the outfit&#39;s favorite setting
     * <p><b>200</b> - OK
     * @param outfitId  (required)
     * @param userId  (required)
     * @param isFavorite  (required)
     * @return OutfitDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OutfitDAO updateUserOutfitFavorite(String outfitId, String userId, Boolean isFavorite) throws RestClientException {
        return updateUserOutfitFavoriteWithHttpInfo(outfitId, userId, isFavorite).getBody();
    }

    /**
     * API to set the outfit&#39;s favorite setting in GLAM
     * Used to set the outfit&#39;s favorite setting
     * <p><b>200</b> - OK
     * @param outfitId  (required)
     * @param userId  (required)
     * @param isFavorite  (required)
     * @return ResponseEntity&lt;OutfitDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OutfitDAO> updateUserOutfitFavoriteWithHttpInfo(String outfitId, String userId, Boolean isFavorite) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'outfitId' is set
        if (outfitId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'outfitId' when calling updateUserOutfitFavorite");
        }
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling updateUserOutfitFavorite");
        }
        
        // verify the required parameter 'isFavorite' is set
        if (isFavorite == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'isFavorite' when calling updateUserOutfitFavorite");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("outfitId", outfitId);
        uriVariables.put("userId", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "isFavorite", isFavorite));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<OutfitDAO> localReturnType = new ParameterizedTypeReference<OutfitDAO>() {};
        return apiClient.invokeAPI("/outfits/{outfitId}/users/{userId}/favorite", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
