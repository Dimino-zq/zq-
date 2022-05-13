package edu.hfu.train.service.remote;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.hfu.train.bean.SysDepart;
import edu.hfu.train.bean.SysMajor;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.dao.remote.AuthGrantDao;
import edu.hfu.train.util.CacheData;

@Service
public class AuthGrantService {
	@Resource
	AuthGrantDao auth;
	/**
	 * 获取所有1级 教育部分并缓存
	 * @return
	 */
	public List<SysDepart> getAllEduDepart(){

		List<SysDepart> ls=CacheData.getDepartOneList();
		if (null==ls||ls.size()==0) {
			ls=auth.getAllEduDepart(CacheData.getAccessCode());
			CacheData.setDepartOneList(ls);
		}
		return ls;
	}
	/**
	 * 根据教育部门的id  获取该部门下的所有科室
	 * @return
	 */
	public List<SysDepart> getDepartByLvl2(Integer parent_departId){
		List<SysDepart> ls=CacheData.getDepartTwoList(parent_departId);
		if (null==ls||ls.size()==0) {
			ls=auth.getDepartByLvl2(CacheData.getAccessCode(), parent_departId);
			CacheData.setDepartTwoList(parent_departId, ls);
		}
		return ls;
	}
	
	/**
	 * 获取所有院系并缓存
	 * @return
	 */
	public List<SysDepart> getAllEduDept(){

		List<SysDepart> ls=CacheData.getDeptList();
		if (null==ls||ls.size()==0) {
			ls=auth.getAllEduDept(CacheData.getAccessCode());
			CacheData.setDeptList(ls);
		}
		return ls;
	}
	
	/**
	 * 根据院系获取专业
	 * @return
	 */
	public List<SysMajor> getMajorByDept(Integer departId){
		List<SysMajor> ls=CacheData.getMajorList(departId);
		if (null==ls||ls.size()==0) {
			ls=auth.getMajorByDept(CacheData.getAccessCode(), departId);
			CacheData.setMajorList(departId,ls);
		}
		return ls;
	}
	
	
	/**
	 * 获取部门对应的员工
	 * @param departName
	 * @return
	 */
	public List<SysStaff> getTeacherByDepart(String departName){
		List<SysStaff> ls=CacheData.getTeacherList(departName);
		if (null==ls) {
			ls=auth.getTeacherByDepart(CacheData.getAccessCode(), departName);
			CacheData.setTeacherList(departName, ls);
		}
		return ls;
	}
	
	
	/**
	 * 判断是否存在输入的教育部门和专业
	 */
	public int checkEduDepartName(String eduDepart, String major)
	{
		List<SysDepart> departList = getAllEduDepart();
		for(SysDepart departLvl1 : departList){
			if(eduDepart.equals(departLvl1.getDepartName())){
				List<SysMajor> majorList = getMajorByDept(departLvl1.getDepartId());
				for(SysMajor ls : majorList){
					if(major.equals(ls.getMajorName())){
						return 1; //教育部门和专业都存在
					}
				}
				return 2; //仅教育部门存在
			}
		}
		return 3; //教育部门和专业都不存在
	}
	/**
	 * 判断是否存在输入的教育部门和专业
	 * 使用Java8 stream流对List进行遍历、过滤、查询、去重、排序
	 * @param eduDepart
	 * @param major
	 * @return
	 */
	public int checkEduDepartNameByStream(String eduDepart, String major)
	{
		//查找教育部门是否存在
		List<SysDepart> filterList =getAllEduDept().stream()
				.filter(departLvl1->departLvl1.getDepartName().equals(eduDepart))//lamda表达式
				.collect(Collectors.toList());
		if (null==filterList||filterList.size()==0) {
			return 3; //教育部门和专业都不存在
		}else {
			SysDepart departLvl1=filterList.get(0);
			Boolean isExist = getMajorByDept(departLvl1.getDepartId()).stream()
					.filter(departLvl2->departLvl2.getMajorName().equals(eduDepart))//lamda表达式
					.findAny().isPresent();
			if (!isExist) {
				return 2; //仅教育部门存在
			}else {
				return 1; //教育部门和专业都存在
			}
		}
	}
	
	
}
