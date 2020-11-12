package be.vdab.luigi.repositories;

import be.vdab.luigi.domain.GastenBoekEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcGastenBoekRepository implements GastenBoekRepository {
    private final SimpleJdbcInsert insert;
    private JdbcTemplate template;

    public JdbcGastenBoekRepository(JdbcTemplate template) {
        this.insert = new SimpleJdbcInsert(template);
        insert.withTableName("gastenboek");
        insert.usingGeneratedKeyColumns("id" + "");
        this.template = template;
    }

    @Override
    public long create(GastenBoekEntry entry) {
        var kolomWaarden = Map.of("naam", entry.getNaam(),
                "datum", entry.getDatum(),
                "bericht", entry.getBericht());
        var id = insert.executeAndReturnKey(kolomWaarden);
        return id.longValue();
    }
    private final RowMapper<GastenBoekEntry> entryRowMapper =
            (result,rowNum)->new GastenBoekEntry(result.getLong("id"),
                    result.getString("naam"),
                    result.getDate("datum").toLocalDate(),
                    result.getString("bericht"));

    @Override
    public List<GastenBoekEntry> findAll() {
        var sql = "select id,naam,datum,bericht from  order by datum desc";
        return template.query(sql, entryRowMapper);
    }
}
