package com.cryptography.dto;

public class ToBeSignedDto {
    private String text;

    public ToBeSignedDto() {
    }

    public ToBeSignedDto(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
