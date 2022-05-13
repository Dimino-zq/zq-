package edu.hfu.train.config;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.hanb.filterJson.aspect.JsonReturnHandler;

import edu.hfu.train.util.StringToDateConverter;

@ComponentScan(basePackages = {"com.hanb.filterJson"},useDefaultFilters = true)
@Configuration
public class WebConfig extends WebMvcConfigurationSupport{
	
	@Autowired
    private RequestMappingHandlerAdapter handlerAdapter;
	
	@Bean
    public JsonReturnHandler jsonReturnHandler(){
        return new JsonReturnHandler();
    }
	
	/*
	 * @Override protected void
	 * addReturnValueHandlers(List<HandlerMethodReturnValueHandler>
	 * returnValueHandlers) { returnValueHandlers.add(jsonReturnHandler());
	 * super.addReturnValueHandlers(returnValueHandlers); }
	 */

	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		//指定静态资源目录
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");//
	}
	@Value("${beetl.templatesPath}") String templatesPath;//模板根目录 ，比如 "templates"
	@Bean(name = "beetlConfig")
	public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
		BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
	    //获取Spring Boot 的ClassLoader
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if(loader==null){
			loader = WebConfig.class.getClassLoader();
		}
		Properties extProperties=new Properties();
		try {
			InputStream in = getClass().getResourceAsStream("/extBeetlCfg.properties");
			extProperties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//#beetle 自定义函数
		//FN.hasPermission = edu.hfu.scre.beetl.fn.HasPermission
		extProperties.put("FN.hasPermission", "edu.hfu.train.beetl.fn.HasPermission");
		beetlGroupUtilConfiguration.setConfigProperties(extProperties);//额外的配置，可以覆盖默认配置，一般不需要
		ClasspathResourceLoader cploder = new ClasspathResourceLoader(loader,templatesPath);
		beetlGroupUtilConfiguration.setResourceLoader(cploder);
		beetlGroupUtilConfiguration.init();
		//如果使用了优化编译器，涉及到字节码操作，需要添加ClassLoader
		beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(loader);
		return beetlGroupUtilConfiguration;
	}

	@Bean(name = "beetlViewResolver")
	public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
		BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
		//beetlSpringViewResolver.setPrefix("/templates");
//      	   beetlSpringViewResolver.setSuffix(".btl");
		beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
		beetlSpringViewResolver.setOrder(0);
		beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
		return beetlSpringViewResolver;
	}
	
    /**
     * 增加字符串转日期的功能
     */

    @PostConstruct
    public void initEditableAvlidation() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer)handlerAdapter.getWebBindingInitializer();
        if(initializer.getConversionService()!=null) {
            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();           
            genericConversionService.addConverter(new StringToDateConverter());
        }
    }
}
