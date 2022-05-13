package edu.hfu.train.service.sysset;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import edu.hfu.train.bean.SysLevel;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.sysset.SyslevelDao;

@Service
@Transactional
public class SyslevelService {
	@Resource
	SyslevelDao leveldao;
	
	public List<SysLevel> getAlllevel(SysLevel level) throws Exception {
		return leveldao.getAlllevel();
	}
	//保存
	public String saveSysLevel(SysLevel syslevel) throws Exception {
		// TODO Auto-generated method stub
		SysLevel level=new SysLevel();
		level.setLvlName(syslevel.getLvlName());
		List<SysLevel> ls=getSysLevelByCon(level);
		if (null == ls || ls.size() == 0) {
			leveldao.saveSysLevel(syslevel);
			return "succ";
		} else {
			return syslevel.getLvlName() + "重复！";
		}
	}
	//修改
	public String updSysLevel(SysLevel syslevel) throws Exception {
		SysLevel level = new SysLevel();
		level.setLvlName(syslevel.getLvlName());
		List<SysLevel> ls = getSysLevelByCon(level);
		if (null == ls || ls.size() == 0) {
			leveldao.updSysLevel(syslevel);
			return "succ";
		} else {
			SysLevel lel = ls.get(0);
			if (lel.getLvlId()==syslevel.getLvlId()) {
			lel.setLvlName(syslevel.getLvlName());
			lel.setLvlPicPath(syslevel.getLvlPicPath());
			lel.setMinScore(syslevel.getMinScore());
			leveldao.updSysLevel(syslevel);
			return "succ";
			} else {
				return syslevel.getLvlName() + "重复！";
			}
		}
	}
	//删除
	public String deleteSysLevel(SysLevel syslevel) throws Exception {
		// TODO Auto-generated method stub
		leveldao.deleteSysLevel(syslevel);
		return "succ";
	}
	
	public List<SysLevel> getSysLevelByCon(SysLevel level) throws Exception {
		return leveldao.getLevelByCon(level);
	}

}
