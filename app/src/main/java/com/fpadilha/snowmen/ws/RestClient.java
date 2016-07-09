package com.fpadilha.snowmen.ws;

import com.fpadilha.snowmen.models.response.GetSnowmenResponse;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = "http://api.newbies.aplicativo.info/", converters = {MappingJackson2HttpMessageConverter.class}, requestFactory = HttpComponentsClientHttpRequestFactory.class)
@Accept(MediaType.APPLICATION_JSON)
public interface RestClient {

    @Get("snowmen/?latitude={latitude}&longitude={longitude}&radius={radius}")
    GetSnowmenResponse getSnowmen(@Path double latitude, @Path double longitude, @Path int radius);

}
