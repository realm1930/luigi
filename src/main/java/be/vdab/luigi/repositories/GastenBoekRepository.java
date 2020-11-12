package be.vdab.luigi.repositories;

import be.vdab.luigi.domain.GastenBoekEntry;

import java.util.List;

public interface GastenBoekRepository {
    long create(GastenBoekEntry entry);
    List<GastenBoekEntry> findAll();
}
