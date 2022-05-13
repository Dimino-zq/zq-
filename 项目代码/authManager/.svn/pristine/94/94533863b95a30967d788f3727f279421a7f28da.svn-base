package edu.hfu.auth.service.sysset;

import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import edu.hfu.auth.dao.sysset.DepartDao;
import edu.hfu.auth.entity.SysDepart;


@Service
@Transactional
public class DepartService{
	@Resource
	DepartDao departDao;
	

	public List<SysDepart> getDepartByAddr(SysDepart depart, int pageNo, int maxResults) throws Exception {
		return departDao.getDepartByAddr(depart, pageNo, maxResults);
	}

	public Integer getDepartByAddrCount(SysDepart depart) throws Exception {
		return departDao.getDepartByAddrCount(depart);
	}

	public Integer saveDepart(SysDepart depart) throws Exception {
		Integer i1=departDao.getDepartByName(depart.getDepartName());
		if(i1==1) {
			return -1;
		}
		return departDao.saveDepart(depart);
	}

	public Integer updDepart(SysDepart depart) throws Exception {
		SysDepart i1=getDepartByName(depart.getDepartName());
		if(i1!=null&&!i1.getDepartId().equals(depart.getDepartId())) {
			return -1;
		}else {
			if (i1 == null) {
				i1 = new SysDepart();
			}
			i1.setDepartId(depart.getDepartId());
			i1.setDepartName(depart.getDepartName());
			i1.setDepartAddr(depart.getDepartAddr());
			i1.setDepartCharge(depart.getDepartCharge());
			i1.setDepartLevel(depart.getDepartLevel());
			i1.setDepartPhone(depart.getDepartPhone());
			i1.setDepartType(depart.getDepartType());
			i1.setDepartExplain(depart.getDepartExplain());
			i1.setParentDepart(depart.getParentDepart());
			i1.setSchool(depart.getSchool());
			departDao.updDepart(i1);
			return 0;
		}
		
	}

	public boolean delDepart(Integer departId) throws Exception {
		return departDao.delDepart(departId);
	}

	public List<SysDepart> getDepartByCon(SysDepart depart) throws Exception {
		return departDao.getDepart(depart);
	}

	public List<SysDepart> getDepartByLvl(Integer departLvl) throws Exception {
		return departDao.getDepartByLvl(departLvl);
	}
	public List<SysDepart> getDepartByLvl(Integer departLvl,String departType) throws Exception {
		return departDao.getDepartByLvl(departLvl,departType);
	}
	public List<SysDepart> getDepartByLv2(Integer departLv2) throws Exception {
		return departDao.getDepartByLv2(departLv2);
	}

	public SysDepart getDepartByName(String departName) throws Exception {
		return departDao.getDepartByNameUpd(departName);
	}
	
	public List<SysDepart> getDepartId(Integer parentDepart_departId) throws Exception {
		return departDao.getDepartId(parentDepart_departId);
	}
	
	/**
	 * 条件查询，不分页
	 * 条件有：系部地址，系部说明，系部级别，系部电话，系部名称
	 * @author Shaocc
	 * @param depart
	 * @return
	 * @throws Exception
	 */
	public List<SysDepart> getDept(SysDepart depart) throws Exception {
		return departDao.getDept(depart);
	}
}
