package com.github.marschall.hibernate.array.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@SpringJUnitConfig
abstract class AbstractHibernateArrayDemoTests {

  private SessionFactory sessionFactory;

  @Autowired
  private DataSource dataSource;

  @BeforeEach
  void setUp() {
    this.initializeSessionFactory();
  }

  @AfterEach
  void tearDown() {
    this.closeSessionFactory();
  }

  void initializeSessionFactory() {
    Configuration builder = new Configuration()
            // entities
            .addAnnotatedClass(Currency.class)
            .setProperty("hibernate.type.java_time_use_direct_jdbc", true)
            .setSharedCacheMode(SharedCacheMode.NONE)
            .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
    builder.getProperties().put(AvailableSettings.JAKARTA_NON_JTA_DATASOURCE, this.dataSource);
    sessionFactory =builder.buildSessionFactory();
  }

  void closeSessionFactory() {
    this.sessionFactory.close();
  }

  @Test
  void arrayFunctions() {
    this.sessionFactory.inStatelessSession(session -> {
      Transaction transaction = session.beginTransaction();
      try {
        List<Currency> currencies = null;
        List<Integer> numbers = Arrays.asList(392, 756, 826, 840, 978);
//        List<Currency> currencies = session.createQuery(
//                "FROM Currency "
//                        + "WHERE array_contains(:numbers, number)",
//                        Currency.class)
//                .setParameter("numbers", numbers.toArray(Integer[]::new))
//                .getResultList();
//        assertNotNull(currencies);
//        assertFalse(currencies.isEmpty());
//        assertEquals(5, currencies.size());

        List<String> codes = Arrays.asList("JPY", "CHF", "GBP", "USD", "EUR");
        currencies = session.createQuery(
                "FROM Currency "
                        + "WHERE array_contains(:codes, code)",
                        Currency.class)
                .setParameter("codes", codes.toArray(String[]::new))
                .getResultList();
        assertNotNull(currencies);
        assertFalse(currencies.isEmpty());
        assertEquals(5, currencies.size());

      } finally {
        transaction.rollback();
      }
    });
  }
  
  @Test
  void arrayFunctionsCriteriaApi() {
    this.sessionFactory.inStatelessSession(session -> {
      Transaction transaction = session.beginTransaction();
      try {
        List<Currency> currencies = null;
        List<Integer> numbers = Arrays.asList(392, 756, 826, 840, 978);

        CriteriaQuery<Currency> query = session.getCriteriaBuilder().createQuery(Currency.class);
        Root<Currency> root = query.from(Currency.class);

//        List<Currency> currencies = session.createQuery(
//                query.where(
//                        root.get(Currency_.number).in(numbers.toArray())))
//                .getResultList();
//        assertNotNull(currencies);
//        assertFalse(currencies.isEmpty());
//        assertEquals(5, currencies.size());

        List<String> codes = Arrays.asList("JPY", "CHF", "GBP", "USD", "EUR");
        currencies = session.createQuery(
                query.where(
                        root.get(Currency_.code).in(codes.toArray())))
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
