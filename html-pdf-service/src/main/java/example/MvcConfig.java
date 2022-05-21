package example;

import java.util.List;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcAutoConfigurationAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/public/**")
				.addResourceLocations("classpath:/public/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer arg0) {
		// TODO Auto-generated method stub
		super.configureContentNegotiation(arg0);
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		// super.configureMessageConverters(converters);
		boolean found = false;
		for (HttpMessageConverter<?> mc : converters) {
			if (mc instanceof MappingJackson2HttpMessageConverter) {
				((MappingJackson2HttpMessageConverter) mc)
						.setObjectMapper(new ObjectMapper());
				((MappingJackson2HttpMessageConverter) mc).setPrettyPrint(false);

				found = true;
				break;
			}
		}

		if (!found) {
			MappingJackson2HttpMessageConverter conv = new MappingJackson2HttpMessageConverter();
			conv.setObjectMapper(new ObjectMapper());

			converters.add(conv);
		}

		super.configureMessageConverters(converters);
	}

}
