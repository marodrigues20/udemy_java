package com.javathlon.section11.excercise;

public class Customer {

  private int id;
  private String name;
  private String surname;
  private String gender;
  private boolean isLicense;

  public Customer(int id, String name, String surname, String gender, boolean isLicense) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.gender = gender;
    this.isLicense = isLicense;
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

  public boolean isLicense() {
    return isLicense;
  }

  public void setLicense(boolean license) {
    isLicense = license;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", gender='" + gender + '\'' +
        ", isLicense=" + isLicense +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Customer customer = (Customer) o;

    return id == customer.id;
  }

  @Override
  public int hashCode() {
    return 11 * id + 13 * name.hashCode();
  }
}
