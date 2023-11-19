package com.glam.bff.openapi.wardrobe.api;

import com.glam.bff.openapi.wardrobe.ApiClient;

import java.io.File;
import com.glam.bff.openapi.wardrobe.model.GarmentDAO;
import com.glam.bff.openapi.wardrobe.model.GarmentPhotoDAO;

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
@Component("com.glam.bff.openapi.wardrobe.api.WardrobeApi")
public class WardrobeApi {
    private ApiClient apiClient;

    public WardrobeApi() {
        this(new ApiClient());
    }

    @Autowired
    public WardrobeApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * API to save an user&#39;s garment information in GLAM
     * Used to save the garment&#39;s information
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentDAO  (required)
     * @return GarmentDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GarmentDAO addUserGarmentInWardrobe(String userId, GarmentDAO garmentDAO) throws RestClientException {
        return addUserGarmentInWardrobeWithHttpInfo(userId, garmentDAO).getBody();
    }

    /**
     * API to save an user&#39;s garment information in GLAM
     * Used to save the garment&#39;s information
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentDAO  (required)
     * @return ResponseEntity&lt;GarmentDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GarmentDAO> addUserGarmentInWardrobeWithHttpInfo(String userId, GarmentDAO garmentDAO) throws RestClientException {
        Object localVarPostBody = garmentDAO;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling addUserGarmentInWardrobe");
        }
        
        // verify the required parameter 'garmentDAO' is set
        if (garmentDAO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garmentDAO' when calling addUserGarmentInWardrobe");
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

        ParameterizedTypeReference<GarmentDAO> localReturnType = new ParameterizedTypeReference<GarmentDAO>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}/garments/save", HttpMethod.POST, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to delete the user garment in GLAM
     * Used to delete the garment and its photo
     * <p><b>200</b> - OK
     * @param garmentId  (required)
     * @param userId  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUserGarment(String garmentId, String userId) throws RestClientException {
        deleteUserGarmentWithHttpInfo(garmentId, userId);
    }

    /**
     * API to delete the user garment in GLAM
     * Used to delete the garment and its photo
     * <p><b>200</b> - OK
     * @param garmentId  (required)
     * @param userId  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUserGarmentWithHttpInfo(String garmentId, String userId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'garmentId' is set
        if (garmentId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garmentId' when calling deleteUserGarment");
        }
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling deleteUserGarment");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("garmentId", garmentId);
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
        return apiClient.invokeAPI("/wardrobes/users/{userId}/garments/{garmentId}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to delete the user garment&#39;photo in GLAM
     * Used to delete the garment&#39;s photo
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentPhotoId  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUserGarmentPhoto(String userId, String garmentPhotoId) throws RestClientException {
        deleteUserGarmentPhotoWithHttpInfo(userId, garmentPhotoId);
    }

    /**
     * API to delete the user garment&#39;photo in GLAM
     * Used to delete the garment&#39;s photo
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentPhotoId  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUserGarmentPhotoWithHttpInfo(String userId, String garmentPhotoId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling deleteUserGarmentPhoto");
        }
        
        // verify the required parameter 'garmentPhotoId' is set
        if (garmentPhotoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garmentPhotoId' when calling deleteUserGarmentPhoto");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userId", userId);
        uriVariables.put("garmentPhotoId", garmentPhotoId);

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
        return apiClient.invokeAPI("/wardrobes/users/{userId}/garments/photo/{garmentPhotoId}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to get the user garment&#39;s photo in GLAM
     * Used to get the garment&#39;s photo
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentPhotoId  (required)
     * @return List&lt;byte[]&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<byte[]> getGarmentPhoto(String userId, String garmentPhotoId) throws RestClientException {
        return getGarmentPhotoWithHttpInfo(userId, garmentPhotoId).getBody();
    }

    /**
     * API to get the user garment&#39;s photo in GLAM
     * Used to get the garment&#39;s photo
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentPhotoId  (required)
     * @return ResponseEntity&lt;List&lt;byte[]&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<byte[]>> getGarmentPhotoWithHttpInfo(String userId, String garmentPhotoId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling getGarmentPhoto");
        }
        
        // verify the required parameter 'garmentPhotoId' is set
        if (garmentPhotoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garmentPhotoId' when calling getGarmentPhoto");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userId", userId);
        uriVariables.put("garmentPhotoId", garmentPhotoId);

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

        ParameterizedTypeReference<List<byte[]>> localReturnType = new ParameterizedTypeReference<List<byte[]>>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}/garments/photo/{garmentPhotoId}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to get the user garment&#39;s photo information in GLAM
     * Used to get the garment&#39;s photo information
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentPhotoId  (required)
     * @return GarmentPhotoDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GarmentPhotoDAO getGarmentPhotoInfo(String userId, String garmentPhotoId) throws RestClientException {
        return getGarmentPhotoInfoWithHttpInfo(userId, garmentPhotoId).getBody();
    }

    /**
     * API to get the user garment&#39;s photo information in GLAM
     * Used to get the garment&#39;s photo information
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentPhotoId  (required)
     * @return ResponseEntity&lt;GarmentPhotoDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GarmentPhotoDAO> getGarmentPhotoInfoWithHttpInfo(String userId, String garmentPhotoId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling getGarmentPhotoInfo");
        }
        
        // verify the required parameter 'garmentPhotoId' is set
        if (garmentPhotoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garmentPhotoId' when calling getGarmentPhotoInfo");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userId", userId);
        uriVariables.put("garmentPhotoId", garmentPhotoId);

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

        ParameterizedTypeReference<GarmentPhotoDAO> localReturnType = new ParameterizedTypeReference<GarmentPhotoDAO>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}/garments/photo/{garmentPhotoId}/info", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to get the user garment in GLAM
     * Used to get the garment and its photo
     * <p><b>200</b> - OK
     * @param garmentId  (required)
     * @param userId  (required)
     * @return GarmentDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GarmentDAO getUserGarment(String garmentId, String userId) throws RestClientException {
        return getUserGarmentWithHttpInfo(garmentId, userId).getBody();
    }

    /**
     * API to get the user garment in GLAM
     * Used to get the garment and its photo
     * <p><b>200</b> - OK
     * @param garmentId  (required)
     * @param userId  (required)
     * @return ResponseEntity&lt;GarmentDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GarmentDAO> getUserGarmentWithHttpInfo(String garmentId, String userId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'garmentId' is set
        if (garmentId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garmentId' when calling getUserGarment");
        }
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling getUserGarment");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("garmentId", garmentId);
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

        ParameterizedTypeReference<GarmentDAO> localReturnType = new ParameterizedTypeReference<GarmentDAO>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}/garments/{garmentId}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to get the user&#39;s wardrobe in GLAM
     * Used to get the garment list inside the user&#39;s wardrobe
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @return List&lt;GarmentDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GarmentDAO> getUserWardrobe(String userId) throws RestClientException {
        return getUserWardrobeWithHttpInfo(userId).getBody();
    }

    /**
     * API to get the user&#39;s wardrobe in GLAM
     * Used to get the garment list inside the user&#39;s wardrobe
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @return ResponseEntity&lt;List&lt;GarmentDAO&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GarmentDAO>> getUserWardrobeWithHttpInfo(String userId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling getUserWardrobe");
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

        ParameterizedTypeReference<List<GarmentDAO>> localReturnType = new ParameterizedTypeReference<List<GarmentDAO>>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to get the user&#39;s categories garments in GLAM
     * Used to get the garment list inside the user&#39;s wardrobe by CATEGORY
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param category  (required)
     * @return List&lt;GarmentDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GarmentDAO> getUserWardrobeCategoriesGarments(String userId, String category) throws RestClientException {
        return getUserWardrobeCategoriesGarmentsWithHttpInfo(userId, category).getBody();
    }

    /**
     * API to get the user&#39;s categories garments in GLAM
     * Used to get the garment list inside the user&#39;s wardrobe by CATEGORY
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param category  (required)
     * @return ResponseEntity&lt;List&lt;GarmentDAO&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GarmentDAO>> getUserWardrobeCategoriesGarmentsWithHttpInfo(String userId, String category) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling getUserWardrobeCategoriesGarments");
        }
        
        // verify the required parameter 'category' is set
        if (category == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'category' when calling getUserWardrobeCategoriesGarments");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userId", userId);
        uriVariables.put("category", category);

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

        ParameterizedTypeReference<List<GarmentDAO>> localReturnType = new ParameterizedTypeReference<List<GarmentDAO>>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}/categories/{category}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to completely save an user&#39;s garment in GLAM
     * Used to save the garment together with its photo
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garment  (required)
     * @param photo  (optional)
     * @return GarmentDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GarmentDAO saveUserGarmentInWardrobeComplete(String userId, String garment, File photo) throws RestClientException {
        return saveUserGarmentInWardrobeCompleteWithHttpInfo(userId, garment, photo).getBody();
    }

    /**
     * API to completely save an user&#39;s garment in GLAM
     * Used to save the garment together with its photo
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garment  (required)
     * @param photo  (optional)
     * @return ResponseEntity&lt;GarmentDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GarmentDAO> saveUserGarmentInWardrobeCompleteWithHttpInfo(String userId, String garment, File photo) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling saveUserGarmentInWardrobeComplete");
        }
        
        // verify the required parameter 'garment' is set
        if (garment == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garment' when calling saveUserGarmentInWardrobeComplete");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userId", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "garment", garment));

        if (photo != null)
            localVarFormParams.add("photo", new FileSystemResource(photo));

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<GarmentDAO> localReturnType = new ParameterizedTypeReference<GarmentDAO>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}/garments/complete/save", HttpMethod.POST, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to save an user garment&#39;s photo in GLAM
     * Used to save the garment&#39;s photo and retrieve the list of properties for the garment
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param photo  (optional)
     * @return GarmentDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GarmentDAO saveUserGarmentPhoto(String userId, File photo) throws RestClientException {
        return saveUserGarmentPhotoWithHttpInfo(userId, photo).getBody();
    }

    /**
     * API to save an user garment&#39;s photo in GLAM
     * Used to save the garment&#39;s photo and retrieve the list of properties for the garment
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param photo  (optional)
     * @return ResponseEntity&lt;GarmentDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GarmentDAO> saveUserGarmentPhotoWithHttpInfo(String userId, File photo) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling saveUserGarmentPhoto");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userId", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (photo != null)
            localVarFormParams.add("photo", new FileSystemResource(photo));

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<GarmentDAO> localReturnType = new ParameterizedTypeReference<GarmentDAO>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}/garments/photo/save", HttpMethod.POST, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to save the user&#39;s wardrobe in GLAM
     * Used to save the garment list inside the user&#39;s wardrobe
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentDAO  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void saveUserWardrobe(String userId, List<GarmentDAO> garmentDAO) throws RestClientException {
        saveUserWardrobeWithHttpInfo(userId, garmentDAO);
    }

    /**
     * API to save the user&#39;s wardrobe in GLAM
     * Used to save the garment list inside the user&#39;s wardrobe
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentDAO  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> saveUserWardrobeWithHttpInfo(String userId, List<GarmentDAO> garmentDAO) throws RestClientException {
        Object localVarPostBody = garmentDAO;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling saveUserWardrobe");
        }
        
        // verify the required parameter 'garmentDAO' is set
        if (garmentDAO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garmentDAO' when calling saveUserWardrobe");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userId", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = {  };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<Void> localReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}/overwrite", HttpMethod.POST, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to update the user garment in GLAM
     * Used to update the garment
     * <p><b>200</b> - OK
     * @param garmentId  (required)
     * @param userId  (required)
     * @param garmentDAO  (required)
     * @return GarmentDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GarmentDAO updateUserGarment(String garmentId, String userId, GarmentDAO garmentDAO) throws RestClientException {
        return updateUserGarmentWithHttpInfo(garmentId, userId, garmentDAO).getBody();
    }

    /**
     * API to update the user garment in GLAM
     * Used to update the garment
     * <p><b>200</b> - OK
     * @param garmentId  (required)
     * @param userId  (required)
     * @param garmentDAO  (required)
     * @return ResponseEntity&lt;GarmentDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GarmentDAO> updateUserGarmentWithHttpInfo(String garmentId, String userId, GarmentDAO garmentDAO) throws RestClientException {
        Object localVarPostBody = garmentDAO;
        
        // verify the required parameter 'garmentId' is set
        if (garmentId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garmentId' when calling updateUserGarment");
        }
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling updateUserGarment");
        }
        
        // verify the required parameter 'garmentDAO' is set
        if (garmentDAO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garmentDAO' when calling updateUserGarment");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("garmentId", garmentId);
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

        ParameterizedTypeReference<GarmentDAO> localReturnType = new ParameterizedTypeReference<GarmentDAO>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}/garments/{garmentId}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * API to update an user garment&#39;s photo in GLAM
     * Used to update the garment&#39;s photo and retrieve the list of properties for the garment
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentPhotoId  (required)
     * @param photo  (optional)
     * @return GarmentDAO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GarmentDAO updateUserGarmentPhoto(String userId, String garmentPhotoId, File photo) throws RestClientException {
        return updateUserGarmentPhotoWithHttpInfo(userId, garmentPhotoId, photo).getBody();
    }

    /**
     * API to update an user garment&#39;s photo in GLAM
     * Used to update the garment&#39;s photo and retrieve the list of properties for the garment
     * <p><b>200</b> - OK
     * @param userId  (required)
     * @param garmentPhotoId  (required)
     * @param photo  (optional)
     * @return ResponseEntity&lt;GarmentDAO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GarmentDAO> updateUserGarmentPhotoWithHttpInfo(String userId, String garmentPhotoId, File photo) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userId' when calling updateUserGarmentPhoto");
        }
        
        // verify the required parameter 'garmentPhotoId' is set
        if (garmentPhotoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'garmentPhotoId' when calling updateUserGarmentPhoto");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userId", userId);
        uriVariables.put("garmentPhotoId", garmentPhotoId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (photo != null)
            localVarFormParams.add("photo", new FileSystemResource(photo));

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<GarmentDAO> localReturnType = new ParameterizedTypeReference<GarmentDAO>() {};
        return apiClient.invokeAPI("/wardrobes/users/{userId}/garments/photo/{garmentPhotoId}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
