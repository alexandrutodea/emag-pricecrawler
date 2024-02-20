package me.alextodea.webcrawler.utils;

import org.junit.jupiter.api.Test;

import javax.print.attribute.ResolutionSyntax;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    public void getNumericalRepresentationTest() {

        LocalDateTime currentTime = LocalDateTime.now();
        double dateNumericalRepresentation = Utils.getDateNumericalRepresentation(currentTime);
        System.out.println(dateNumericalRepresentation);
        System.out.println();

    }

}