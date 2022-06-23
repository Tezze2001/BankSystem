package com.progetto.sistemabancario.model;

import java.io.Serializable;
import java.util.Random;
import java.util.regex.Pattern;

public class ExeID implements Serializable, Comparable<ExeID> {
    private String value;

    private ExeID(String id) {
        this.value = id;
    }

    public ExeID() {
        value = ExeID.getRandomHexString(20);
    }

    public String getId() {
        return value;
    }

    public static String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numchars);
    }

    public static ExeID fromString(String str) throws IllegalArgumentException {
        if (!Pattern.matches("^[0-9a-f]{20}$", str)) {
            throw new IllegalArgumentException();
        }
        return new ExeID(str);
    }

    @Override
    public int compareTo(ExeID id) {
        return this.value.compareTo(id.getId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExeID other = (ExeID) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

}
