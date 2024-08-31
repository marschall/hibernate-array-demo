package com.github.marschall.hibernate.array.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Currency {

  @Id
  @Column(nullable = false, updatable = false)
  private String code;

  @Id
  @Column(name = "num", nullable = false, updatable = false)
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

  @Override
  public int hashCode() {
    return code.hashCode() * 31 + number.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Currency)) {
      return false;
    }
    Currency other = (Currency) obj;
    return this.number.equals(other.number)
            && this.code.equals(other.code);
  }

  @Override
  public String toString() {
    return '[' + this.code + '(' + this.number + ")]";
  }

}
