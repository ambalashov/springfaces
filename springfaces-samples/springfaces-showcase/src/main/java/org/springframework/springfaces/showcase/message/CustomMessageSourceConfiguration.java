package org.springframework.springfaces.showcase.message;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.StaticMessageSource;

/**
 * Defines a single {@link StaticMessageSource} for use by {@link MessageExampleController}.
 * 
 * @author Phillip Webb
 */
@Configuration
public class CustomMessageSourceConfiguration {

	@Bean
	public MessageSource exampleMessageSource() {
		StaticMessageSource source = new StaticMessageSource();
		source.addMessage("pages.message.definedsource.hello", Locale.getDefault(), "Defined Source Hello");
		return source;
	}
}