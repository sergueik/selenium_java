/*
 * {{{ header & license
 * Copyright (c) 2016 Farrukh Mirza
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
/**
 * @author Farrukh Mirza
 * 24/06/2016 
 * Dublin, Ireland
 */

package org.farrukh.mirza.pdf.test;

import org.farrukh.mirza.pdf.service.ConverterImpl;
import org.farrukh.mirza.pdf.service.TemplateDataTransformerImpl;
import org.farrukh.mirza.pdf.service.TestDataProviderImpl;
import org.farrukh.mirza.pdf.spi.Converter;
import org.farrukh.mirza.pdf.spi.TemplateDataTransformer;
import org.farrukh.mirza.pdf.spi.TestDataProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
	}

	@Bean
	public Converter getConverter(){
		return new ConverterImpl();
	}
	
	@Bean
	public TemplateDataTransformer getTemplateTransformer(){
		return new TemplateDataTransformerImpl();
	}
	
	@Bean
	public TestDataProvider getDataProvider(){
		return new TestDataProviderImpl();
	}
}
