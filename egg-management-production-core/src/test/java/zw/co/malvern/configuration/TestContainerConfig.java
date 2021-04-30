package zw.co.malvern.configuration;

import org.junit.ClassRule;
import org.testcontainers.containers.DockerComposeContainer;


import java.io.File;

public class TestContainerConfig {

    @ClassRule
    public static DockerComposeContainer composeContainer = new DockerComposeContainer<>(
            new File("src/test/resources/docker-compose.yml"))
            .withEnv("MYSQL_ROOT_PASSWORD", "developer@@");
}
