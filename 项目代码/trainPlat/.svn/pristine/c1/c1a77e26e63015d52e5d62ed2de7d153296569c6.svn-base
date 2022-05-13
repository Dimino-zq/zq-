package com.hanb.filterJson.aspect;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanb.filterJson.annotation.PowerJsonFilter;
import com.hanb.filterJson.annotation.PowerJsonFilters;
import com.hanb.filterJson.spring.ComplexJacksonPropertyFilter;

public class JsonReturnHandler implements HandlerMethodReturnValueHandler{
	@Override
    public void handleReturnValue(Object returnObject, MethodParameter paramter,
            ModelAndViewContainer container, NativeWebRequest request) throws Exception {
        container.setRequestHandled(true);
        if(paramter.hasMethodAnnotation(PowerJsonFilter.class)) {
        	PowerJsonFilter powerjsonFilterElem = paramter.getMethodAnnotation(PowerJsonFilter.class);
    		PowerJsonFilter[] powerFiterArray = null;
    		PowerJsonFilters powerFilters = null;
    		if (powerjsonFilterElem != null) {
    			powerFiterArray = new PowerJsonFilter[1];
    			powerFiterArray[0] = powerjsonFilterElem;
    		} else if ((powerFilters = paramter.getMethodAnnotation(PowerJsonFilters.class)) != null) {
    			powerFiterArray = powerFilters.value();
    		}
    		ComplexJacksonPropertyFilter serializer=new ComplexJacksonPropertyFilter(powerFiterArray);
    		ObjectMapper objectMapper=new ObjectMapper();
    		objectMapper.setFilterProvider(serializer);
    		for(PowerJsonFilter power:powerFiterArray) {
        		objectMapper.addMixIn(power.clazz(), serializer.getClass());
        	}
    		HttpServletResponse response = request.getNativeResponse(HttpServletResponse.class);
    		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    		response.getWriter().write(objectMapper.writeValueAsString(returnObject));
        }
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.hasMethodAnnotation(PowerJsonFilter.class);
    }
}
