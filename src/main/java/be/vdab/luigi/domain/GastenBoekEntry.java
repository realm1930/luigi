package be.vdab.luigi.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class GastenBoekEntry {
    private final long id;
    @NotBlank
    private final String naam;
    @NotNull
    @DateTimeFormat (style = "s-")
    private final LocalDate datum;
    @NotBlank
    private final String bericht;

    public GastenBoekEntry(long id, @NotBlank String naam, @NotNull LocalDate datum, @NotBlank String bericht) {
        this.id = id;
        this.naam = naam;
        this.datum = datum;
        this.bericht = bericht;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getBericht() {
        return bericht;
    }
}
