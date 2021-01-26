package com.viesca.csvprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CsvRecord {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("city")
    private String city;

    @JsonProperty("county")
    private String county;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("phone1")
    private String phone1;

    @JsonProperty("phone2")
    private String phone2;

    @JsonProperty("email")
    private String email;

    @JsonProperty("web")
    private String web;

    public CsvRecord() {
    }

    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @XmlElement
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlElement
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @XmlElement
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @XmlElement
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlElement
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @XmlElement
    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    @XmlElement
    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("companyName", companyName)
                .append("address", address)
                .append("city", city)
                .append("county", county)
                .append("state", state)
                .append("zip", zip)
                .append("phone1", phone1)
                .append("phone2", phone2)
                .append("email", email)
                .append("web", web)
                .toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CsvRecord csvRecord = (CsvRecord) o;

        return new EqualsBuilder()
                .append(zip, csvRecord.zip)
                .append(firstName, csvRecord.firstName)
                .append(lastName, csvRecord.lastName)
                .append(companyName, csvRecord.companyName)
                .append(address, csvRecord.address)
                .append(city, csvRecord.city)
                .append(county, csvRecord.county)
                .append(state, csvRecord.state)
                .append(phone1, csvRecord.phone1)
                .append(phone2, csvRecord.phone2)
                .append(email, csvRecord.email)
                .append(web, csvRecord.web)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstName)
                .append(lastName)
                .append(companyName)
                .append(address)
                .append(city)
                .append(county)
                .append(state)
                .append(zip)
                .append(phone1)
                .append(phone2)
                .append(email)
                .append(web)
                .toHashCode();
    }
}
