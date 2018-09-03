package co.nuoya.JsonDB.dao;

import java.util.List;

import co.nuoya.JsonDB.model.Acount;

public interface AcountDAO extends BaseDAO {
	public List<Acount> select(String acount_id);
	public int insert(Acount acount);
	public int update(Acount acount);
	public int delete(String acount_id);
}
