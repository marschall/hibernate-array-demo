package com.github.marschall.hibernate.array.demo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SqlGenerator {

  public static void main(String[] args) {
    System.out.println("INSERT INTO currency(number, code) VALUES");
    for (Currency currency : loadCurrencies()) {
      System.out.println("(" + currency.getNumber() + ", '" + currency.getCode() + "'),");
    }

  }
  
  private static List<Currency> loadCurrencies() {
    return java.util.Currency.getAvailableCurrencies().stream()
      .filter(currency -> currency.getNumericCode() != 0)
      .filter(currency -> currency.getNumericCode() != 999)
      .map(currency -> {
        Currency entity = new Currency();
        entity.setNumber(currency.getNumericCode());
        entity.setCode(currency.getCurrencyCode());
        return entity;
      })
      .sorted(Comparator.comparing(Currency::getNumber))
      .collect(Collectors.toList());
  }

}
