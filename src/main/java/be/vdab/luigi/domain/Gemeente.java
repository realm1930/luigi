package be.vdab.luigi.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Gemeente {
    @Min(1000)
    @Max(9999)
    private short postcode;
}
