package com.github.marschall.hibernate.array.demo;

import jakarta.persistence.Entity;

@Entity
public class Currency {

  private String code;

  private Integer number;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

}
