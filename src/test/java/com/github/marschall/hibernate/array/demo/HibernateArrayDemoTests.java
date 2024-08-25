package com.github.marschall.hibernate.array.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.SharedCacheMode;

class HibernateArrayDemoTests {

  private SessionFactory sessionFactory;

  @BeforeEach
  void setUp() throws IOException {
    this.initializeSessionFactory();
  }
  
  @AfterEach
  void tearDown() {
    this.closeSessionFactory();
  }

  void initializeSessionFactory() {
    sessionFactory =
        new Configuration()
            // entities
            // H2 Sakila
            .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:h2:mem:sakila;INIT=RUNSCRIPT FROM 'src/test/resources/sakila.sql'")
            // Credentials
            .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "sa")
            .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "sa")
            .setProperty(AvailableSettings.STATEMENT_FETCH_SIZE, 10)
            .setProperty("hibernate.type.java_time_use_direct_jdbc", true)
            .setProperty(AvailableSettings.SHOW_SQL, true)
            .setSharedCacheMode(SharedCacheMode.NONE)
            .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
            .buildSessionFactory();
  }
  
  void closeSessionFactory() {
    this.sessionFactory.close();
  }
  
  @Test
  void arrayFunctions() {
    this.sessionFactory.inStatelessSession(session -> {
      Transaction transaction = session.beginTransaction();
      try {
        List<Integer> codes = Arrays.asList(392, 756, 826, 840, 978);
        List<Currency> currencies = session.createQuery(
                "FROM Currency "
                + "WHERE array_contains(:codes, code)",
                  Currency.class)
                .setParameter("codes", codes.toArray(Integer[]::new))
                .getResultList();
        assertNotNull(currencies);
        assertFalse(currencies.isEmpty());
        assertEquals(5, currencies.size());

      } finally {
        transaction.rollback();
      }
    });
  }
  
}
