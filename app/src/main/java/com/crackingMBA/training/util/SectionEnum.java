package com.crackingMBA.training.util;

/**
 * Created by MSK on 29-01-2017.
 */
public enum SectionEnum {

    quant("quant","Quant Section"),
    verbal("verbal","Verbal Section"),
    dilr("dilr","DI & LR Section");

    public String key = null;
    public String value = null;

    SectionEnum(String cat, String val){
        key = cat;
        value = val;
    }

    public static String getSectionName(String sectionKey){
        for(SectionEnum s : SectionEnum.values()){
            if(s.key.equals(sectionKey)){
                return  s.value;
            }
        }
        return null;
    }
}
