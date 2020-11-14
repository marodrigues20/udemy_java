package com.javathlon.section13;

import java.util.Objects;

public class Customer {

  private int id;
  private String name;
  private String surname;
  private String gender;
  private boolean isLicensed;

  public Customer(int id, String name, String surname, String gender, boolean isLicensed) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.gender = gender;
    this.isLicensed = isLicensed;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public boolean isLicensed() {
    return isLicensed;
  }

  public void setLicensed(boolean licensed) {
    isLicensed = licensed;
  }

  @Override
  public boolean equals(Object o) {

    if(o == null)
        return false;

    if(this.id == ((Customer)o).id){
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return 11 * id + 13 * name.hashCode();
  }

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", gender='" + gender + '\'' +
        ", isLicensed=" + isLicensed +
        '}';
  }
}
