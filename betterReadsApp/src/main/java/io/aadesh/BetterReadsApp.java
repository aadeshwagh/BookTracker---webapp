package io.aadesh;

import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import io.aadesh.connection.DatastacksAstraProperties;

@SpringBootApplication
@EnableConfigurationProperties(DatastacksAstraProperties.class)
public class BetterReadsApp {

	public static void main(String[] args) {
		SpringApplication.run(BetterReadsApp.class, args);
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DatastacksAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureconnectbundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
