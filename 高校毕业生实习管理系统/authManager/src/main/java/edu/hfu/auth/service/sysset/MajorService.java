package edu.hfu.auth.service.sysset;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.auth.dao.sysset.MajorDao;
import edu.hfu.auth.entity.SysMajor;

/**
 * @author Shaocc
 *
 */
@Service
@Transactional
public class MajorService {
	@Resource
	MajorDao majorDao;
	

	public List<SysMajor> getMajorByCon(SysMajor major, int pageNo, int maxResults) throws Exception {
		return majorDao.getMajorByCon(major, pageNo, maxResults);
	}

	public Integer getMajorByConCount(SysMajor major) throws Exception {
		return majorDao.getMajorByConCount(major);
	}

	public Integer saveMajor(SysMajor major) throws Exception {
		Integer i1=majorDao.getMajorByName(major.getMajorName());
		if(i1==1) {
			return -1;
		}
		return majorDao.saveMajor(major);
	}

	public Integer updMajor(SysMajor major) throws Exception {
		SysMajor i1=getMajorByName(major.getMajorName());
		if(i1!=null&&!i1.getMajorId().equals(major.getMajorId())) {
			return -1;
		}else {
			if (i1 == null) {
				i1 = new SysMajor();
			}
			i1.setMajorId(major.getMajorId());
			i1.setMajorName(major.getMajorName());
			i1.setMajorExplain(major.getMajorExplain());
			i1.setDepart(major.getDepart());
			majorDao.updMajor(i1);
			return 0;
		}
		
	}

	public boolean delMajor(Integer majorId) throws Exception {
		return majorDao.delMajor(majorId);
	}

	/**
	 * 根据条件查询专业：专业名称，所属院系
	 * @param major
	 * @return
	 * @throws Exception
	 */
	public List<SysMajor> getMajorByCon(SysMajor major) throws Exception {
		return majorDao.getMajorByCon(major);
	}

	public SysMajor getMajorByName(String majorName) throws Exception {
		return majorDao.getMajorByNameUpd(majorName);
	}

}
