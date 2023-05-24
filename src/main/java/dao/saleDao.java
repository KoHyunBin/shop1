package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.Sale;

@Repository
public class saleDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<Sale> mapper = new BeanPropertyRowMapper<Sale>(Sale.class);
	private Map<String,Object> param = new HashMap<>();
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
	}

	public int getMaxSaleId() {
		return template.queryForObject("select ifnull(max(saleid),0) from sale", param, Integer.class);
	}

	public void insert(Sale sale) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(sale);
		String sql = "insert into sale (saleid, userid, saledate)"
				+ " values (:saleid, :userid, now())";
		template.update(sql, param);
	}

	public List<Sale> list(String userid) {
		String sql = "select * from sale where userid=:userid"
				+ " order by saleid desc";//최근 주문 순서로 조회
		param.clear();
		param.put("userid", userid);
		return template.query(sql, param,mapper);
	}
}
