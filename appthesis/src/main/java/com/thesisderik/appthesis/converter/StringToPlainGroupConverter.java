package com.thesisderik.appthesis.converter;

import org.springframework.core.convert.converter.Converter;

import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;


public class StringToPlainGroupConverter implements Converter<String, PlainGroup> {

    @Override
    public PlainGroup convert(String source) {

        PlainGroup res = new PlainGroup();
        res.setId(Long.parseLong(source));
        return res;
    }
}