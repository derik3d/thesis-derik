package com.thesisderik.appthesis.converter;

import org.springframework.core.convert.converter.Converter;

import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainTask;


public class StringToPlainTaskConverter implements Converter<String, PlainTask> {

    @Override
    public PlainTask convert(String source) {

        PlainTask res = new PlainTask();
        res.setId(Long.parseLong(source));
        return res;
    }
}