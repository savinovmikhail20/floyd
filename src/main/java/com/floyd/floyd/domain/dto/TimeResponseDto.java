package com.floyd.floyd.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeResponseDto {
    public String datetime;


    public String getTime() {

        return datetime.substring(0, 19);
    }

    public void setTime(String time) {
        this.datetime = time;
    }
}
