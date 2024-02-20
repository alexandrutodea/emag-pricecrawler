package me.alextodea.webcrawler.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Data
public class PricePredictionDto {
    @FutureOrPresent
    private LocalDateTime dateTime;
}
