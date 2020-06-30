package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

  private final String TABLE_NAME = "position";
  private final String ID_COLUMN_NAME = "accountId";

  private JdbcTemplate jdbcTemplate;

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public List<Position> findById(Integer id) {
    List<Position> positions = new ArrayList<>();
    String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";

    try {
      positions = Arrays.asList(jdbcTemplate.queryForObject(selectSql,
          BeanPropertyRowMapper.newInstance(Position[].class), id));
    } catch (EmptyResultDataAccessException ex) {
      logger.error("Empty result on findById: " + id, ex);
    }

    return positions;
  }

  public Iterable<Position> findAll() {
    String selectSql = "SELECT * FROM " + TABLE_NAME;
    return jdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(Position.class));
  }

  public long count() {
    String countSql = "SELECT COUNT(*) FROM " + TABLE_NAME;
    return jdbcTemplate.queryForObject(countSql, Long.class);
  }
}