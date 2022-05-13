package edu.hfu.train.dao.teacher;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class TeacherCheckCompanyDao {

	@Resource
	private BaseDaoImpl dao;

	public List<SysCompany> getTeacherCheckSysCompanyByCon(String datasource, SysCompany sysCompa, int pageNo,
			int pageSize) throws Exception {
		// TODO Auto-generated method stub
		String hql = "from SysCompany ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != sysCompa) {
			if (null != sysCompa.getComName() && !"".equals(sysCompa.getComName())) {
				hql += (index == 0 ? "where" : " and") + "  comName like ?" + index++;
				params.add('%' + sysCompa.getComName() + '%');
			}
			if (null != sysCompa.getIndustry() && !"".equals(sysCompa.getIndustry())) {
				hql += (index == 0 ? "where" : " and") + "  industry like ?" + index++;
				params.add('%' + sysCompa.getIndustry() + '%');
			}
			if (null != sysCompa.getSign() && !"".equals(sysCompa.getSign())) {
				hql += (index == 0 ? " where" : " and") + " sign=?" + index++;
				params.add(sysCompa.getSign());
			}
			if (null != sysCompa.getCheckstate() && !"".equals(sysCompa.getCheckstate())) {
				hql += (index == 0 ? " where" : " and") + " checkstate=?" + index++;
				params.add(sysCompa.getCheckstate());
			}
		}
		hql += (index == 0 ? "where" : " and") + "  datasource like ?" + index++;
		params.add('%' + datasource + '%');
		hql += " order by companyId DESC";
		return dao.findByPage(hql, params.toArray(), pageNo, pageSize);

	}

	public Integer getTeacherCheckSysCompanyCountByCon(String datasource, SysCompany sysCompa) throws Exception {
		// TODO Auto-generated method stub
		String hql = "from SysCompany  ";
		int index = 0;
		System.out.println(datasource);
		List<Object> params = new ArrayList<Object>();
		if (null != sysCompa) {
			if (null != sysCompa.getComName() && !"".equals(sysCompa.getComName())) {
				hql += (index == 0 ? "where" : " and") + "  comName like ?" + index++;
				params.add('%' + sysCompa.getComName() + '%');
			}
			if (null != sysCompa.getIndustry() && !"".equals(sysCompa.getIndustry())) {
				hql += (index == 0 ? "where" : " and") + "  industry like ?" + index++;
				params.add('%' + sysCompa.getIndustry() + '%');
			}
			if (null != sysCompa.getSign() && !"".equals(sysCompa.getSign())) {
				hql += (index == 0 ? " where" : " and") + " sign=?" + index++;
				params.add(sysCompa.getSign());
			}
		}
		hql += (index == 0 ? "where" : " and") + "  datasource like ?" + index++;
		params.add('%' + datasource + '%');
		System.out.println(hql);
		hql += " order by companyId DESC";
		return dao.queryBeanCountByHql(hql, params.toArray());
	}

	public List<Object> getStudentNo(String teacherNo) throws Exception {
		// TODO Auto-generated method stub
		String hql = "select studentNo as studentNo" + " from SysStudent where teacherNo='" + teacherNo + "'";
		return dao.find(hql);
	}

}
