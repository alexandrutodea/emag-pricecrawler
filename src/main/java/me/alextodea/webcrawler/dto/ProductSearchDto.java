package me.alextodea.webcrawler.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ProductSearchDto {
    @Size(min = 25)
    private String emagUrl;
}
