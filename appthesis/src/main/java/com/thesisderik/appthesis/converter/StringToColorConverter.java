package com.thesisderik.appthesis.converter;

import java.awt.Color;

import org.springframework.core.convert.converter.Converter;

import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainTask;
import com.thesisderik.appthesis.viz.DataMapperUtils;
import com.thesisderik.appthesis.viz.GeneralMapper;


public class StringToColorConverter implements Converter<String, Color> {

    @Override
    public Color convert(String source) {

        return GeneralMapper.stringToColor(source);
    }
}