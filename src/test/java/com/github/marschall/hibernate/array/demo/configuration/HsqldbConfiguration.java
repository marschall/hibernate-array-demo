package com.github.marschall.hibernate.array.demo.configuration;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
public class HsqldbConfiguration {

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
            .generateUniqueName(true)
            .setType(HSQL)
            .addScript("sql/hsqldb/01_tables.sql")
            .addScript("sql/hsqldb/02_data.sql");
    return DataSourceUtils.wrapWithLogging(builder.build(), "hsqldb-test");
  }

}
