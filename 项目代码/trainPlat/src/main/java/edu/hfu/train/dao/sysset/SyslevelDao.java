package edu.hfu.train.dao.sysset;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.SysLevel;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class SyslevelDao {
	@Resource
	private BaseDaoImpl dao;

	public List<SysLevel> getAlllevel() throws Exception {
		String hql = "from SysLevel";
		return dao.find(hql);
	}

	public void saveSysLevel(SysLevel syslevel) throws Exception {
		dao.save(syslevel);
	}

	public void updSysLevel(SysLevel syslevel) throws Exception {
		dao.clear();
		dao.update(syslevel);
	}

	public void deleteSysLevel(SysLevel syslevel) throws Exception {
		String hql = "delete SysLevel where lvlId=?0";
		dao.bulkUpdate(hql, syslevel.getLvlId());
	}

	public SysLevel GetSysLevelById(SysLevel syslevel) throws Exception {
		String hql = "from SysLevel where lvlId = " + syslevel.getLvlId();
		return (SysLevel) dao.find(hql).get(0);
	}

	/**
	 * 根据条件查询等级，返回一个List结果集
	 * 
	 * @param stu
	 * @return
	 * @throws Exception
	 */
	public List<SysLevel> getLevelByCon(SysLevel level) throws Exception {
		String hql = "from SysLevel ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != level) {
			if (null != level.getLvlName() && !"".equals(level.getLvlName())) {
				hql += (index == 0 ? "where" : "and") + " lvlName =? " + index++;
				params.add(level.getLvlName());
			}

		}
		hql += " order by minScore";
		return dao.find(hql, params.toArray());
	}

}
