package me.alextodea.webcrawler.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class PotentialMatchesDto {
    @Size(min = 3)
    private String productName;
}
