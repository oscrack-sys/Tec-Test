package com.example.myapplication;

public class MyArray {
    public class Address{
        public String street;
        public String suite;
        public String city;
        public String zipcode;
        public Geo geo;
    }

    public class Company{
        public String name;
        public String catchPhrase;
        public String bs;
    }

    public class Geo{
        public String lat;
        public String lng;
    }

    public class Root{
        public int id;
        public String name;
        public String username;
        public String email;
        public Address address;
        public String phone;
        public String website;
        public Company company;
    }


}
