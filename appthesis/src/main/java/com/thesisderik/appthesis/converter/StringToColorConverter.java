package com.thesisderik.appthesis.converter;

import java.awt.Color;

import org.springframework.core.convert.converter.Converter;

import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainTask;
import com.thesisderik.appthesis.viz.DataMapperUtils;


public class StringToColorConverter implements Converter<String, Color> {

    @Override
    public Color convert(String source) {

        return DataMapperUtils.stringToColor(source);
    }
}