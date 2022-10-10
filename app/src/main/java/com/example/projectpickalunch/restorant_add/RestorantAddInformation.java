package com.example.projectpickalunch.restorant_add;

import java.util.HashMap;
import java.util.Map;

public class RestorantAddInformation {
    public String restorant_name;
    public String restorant_address;
    public String restorant_tel;
    public String restorant_category;

    public RestorantAddInformation(){}

    public RestorantAddInformation(String restorant_name, String restorant_address, String restorant_tel, String restorant_category){
        this.restorant_name = restorant_name;
        this.restorant_address = restorant_address;
        this.restorant_tel = restorant_tel;
        this.restorant_category = restorant_category;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("restorant_name", restorant_name);
        result.put("restorant_address", restorant_address);
        result.put("restorant_tel", restorant_tel);
        result.put("restorant_category", restorant_category);
        return result;
    }

}
