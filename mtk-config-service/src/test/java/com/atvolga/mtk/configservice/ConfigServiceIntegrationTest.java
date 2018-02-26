package com.atvolga.mtk.configservice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ConfigServiceIntegrationTest {

  @LocalServerPort
  private int port;

  @Test
  public void verifyConfigServiceTestYmlFileRequest() {
    // given
    YamlPropertiesFactoryBean yamlConfig = new YamlPropertiesFactoryBean();

    // when
    String rawYamlProps = new TestRestTemplate()
        .getForObject("http://localhost:" + port + "/test-dev.yml", String.class);
    yamlConfig.setResources(new ByteArrayResource(rawYamlProps.getBytes()));

    // then
    assertThat("Yaml test property", yamlConfig.getObject().getProperty("test.message"),
        is("Hello, Cloud-Config!"));
  }

  @Test
  public void verifyConfigServiceMissedYmlFileRequest() {
    // given
    YamlPropertiesFactoryBean yamlConfig = new YamlPropertiesFactoryBean();

    // when
    String rawYamlProps = new TestRestTemplate()
        .getForObject("http://localhost:" + port + "/dev-dev.yml", String.class);
    yamlConfig.setResources(new ByteArrayResource(rawYamlProps.getBytes()));

    // then
    assertThat("Missed yaml property", yamlConfig.getObject().getProperty("test.message"),
        isEmptyOrNullString());
  }
}
