package com.example.moc_homework3;

import java.util.ArrayList;
import java.util.List;

public class Contact implements Comparable<Contact> {
    String name;
    String number;

    Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    // sorted by name (ascending) and afterwards by telephone number (ascending)
    @Override
    public int compareTo(Contact o) {
        if(name.equals(o.name))
            return number.compareTo(o.number);
        return name.compareTo(o.name);
    }
}


