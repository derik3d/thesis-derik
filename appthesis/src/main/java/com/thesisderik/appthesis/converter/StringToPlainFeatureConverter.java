package com.thesisderik.appthesis.converter;

import org.springframework.core.convert.converter.Converter;

import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainFeature;


public class StringToPlainFeatureConverter implements Converter<String, PlainFeature> {

    @Override
    public PlainFeature convert(String source) {

        PlainFeature res = new PlainFeature();
        res.setId(Long.parseLong(source));
        return res;
    }
}