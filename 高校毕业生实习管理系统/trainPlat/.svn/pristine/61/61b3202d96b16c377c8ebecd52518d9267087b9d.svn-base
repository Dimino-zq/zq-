package edu.hfu.train.service.student;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import edu.hfu.train.dao.sysset.ReviewLogDao;

import org.springframework.stereotype.Service;

import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentRecord;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.StudentWorkRec;
import edu.hfu.train.dao.student.StudentApplicationDao;
import edu.hfu.train.dao.student.StudentRecordDao;
import edu.hfu.train.dao.student.StudentSecApplicationDao;


@Service
@Transactional
public class StudentRecordService {
	@Resource
	StudentRecordDao recordDao;
	
	@Resource
	StudentApplicationDao applicationDao;

	@Resource
	StudentSecApplicationDao secApplicationDao;
	
	@Resource
	ReviewLogDao reviewLogDao;

	// private static final String RECORD_TYPE = "StudentRecord";		//审批记录类型

	/**
	 * 根据实习记录Id查询一条实习记录
	 * @param recordId
	 * @return 返回一条实习记录 StudentRecord
	 * @throws Exception
	 */
	public StudentRecord getRecordById(Integer recordId) throws Exception{
		return recordDao.getRecordById(recordId);
	}
	
	/**
	 * 根据学生查询该学生的所有实习记录
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public List<StudentRecord> getRecord(SysStudent student) throws Exception
	{
		if (student.getStudentId()==null)
            throw new Exception("查询失败：学生Id为空！");
        return recordDao.getRecord(student);
	}
	
	/**
	 * 获取学生的实习工作记录数量
	 * @param student
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getRecordCount(SysStudent student) throws Exception
	{
		if (student.getStudentId()==null)
            throw new Exception("查询失败：学生Id为空！");
        return recordDao.getRecordCount(student);
	}
	
    /**
     * 检查当前学生是否由已通过的实习申请或变更申请（变更申请通过时，原实习申请作废），
     * 若不满足条件则返回false，前端应无法进入实习工作记录界面
     * @param student
     * @return
     * @throws Exception
     */
    public Boolean checkApplication(SysStudent student) throws Exception
    {
        if (student.getStudentId()==null)
            throw new Exception("查询失败：学生Id为空！");
        List<StudentApplication> ls = applicationDao.getApplication(student);
        if (ls==null || ls.size()==0)
            throw new RuntimeException("实习申请不存在！");
        if (!ls.get(0).getStatus().equals("DApprove") && !ls.get(0).getStatus().equals("EApprove")) //不是 ”已通过“ 或 ”作废“ 状态
            throw new RuntimeException("没有已通过的申请！");
        return true;
    }
	
	/**
	 * 保存一条实习记录，同时级联保存相应的工作记录；计算本周实习开始和结束日期；<br>
	 * 若不存在“审批通过”的申请则不允许保存；若本周已提交实习记录则不许保存；
	 * @param record 实习记录
	 * @param workrec 工作记录
	 * @return String
	 * @throws Exception
	 */
	public String saveRecord(StudentRecord record, StudentWorkRec workrec ) throws Exception
	{
		//检查当前是否有已通过的实习（变更）申请
		List<StudentApplication> ls1 = applicationDao.getApplication(record.getStudent());
		//先判断是否存在实习申请
		out:if(null!=ls1 && 0!=ls1.size())
		{
			//判断实习申请状态是否为“审批通过”，若不是进一步判断
			if(!ls1.get(0).getStatus().equals("DApprove"))
				//判断是否为“作废”状态
				if(ls1.get(0).getStatus().equals("EApprove"))
				{
					//判断变更申请中是否有已通过的申请
					List<StudentSecApplication> ls2 = secApplicationDao.getSecApplication(record.getStudent());
					for(int i = 0;i<ls2.size();i++)
						if(ls2.get(i).getStatus().equals("DApprove"))
							break out;
					throw new RuntimeException("保存失败:您还未提交申请或还有未通过的变更申请！");
				}
				else
					throw new RuntimeException("保存失败：您还有未通过的实习申请！");
		}
		else
			throw new RuntimeException("保存失败：您从未开始过实习！");
		
		//获取当前时间
		Calendar calendar = Calendar.getInstance();
		//检测当前周是否已提交实习记录
//		List<StudentRecord> list = recordDao.getRecordByDate(record.getStudent(),calendar.getTime());
		if (null==record.getStartDate())
			throw new NullPointerException("保存失败：工作日期未填写！");
		List<StudentRecord> list = recordDao.getRecordByDate(record.getStudent(),record.getStartDate());
		
		if(null!=list && 0 != list.size())
			throw new RuntimeException("保存失败：本周已提交实习记录！");
		record.setCreateDate(calendar.getTime());	//StudentRecord创建时间
		record.setCreateUser(record.getStudent().getStudentNo());	//StudentRecord创建人
		record.setUpdDate(new Date());	//StudentRecord更新时间
		record.setUpdUser(record.getStudent().getStudentNo());	//StudentRecord修改人
		workrec.setCreateDate(calendar.getTime());	//workrec创建时间
		workrec.setCreateUser(record.getStudent().getStudentNo());	//workrec创建人
		workrec.setUpdDate(new Date());	//workrec更新时间
		workrec.setUpdUser(workrec.getStudent().getStudentNo());	//workrec修改人
		
//		//实习（工作）记录中填入开始日期
//		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-(calendar.get(Calendar.DAY_OF_WEEK)-2));	//计算当前周周一的日期
//		record.setStartDate(calendar.getTime());
//		workrec.setStartDate(calendar.getTime());
//		//实习（工作）记录中填入结束日期
//		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+7);	//计算当前周周日的日期
//		record.setEndDate(calendar.getTime());
//		workrec.setEndDate(calendar.getTime());
		
		//工作记录中填入周数
		List<StudentRecord> records = recordDao.getRecord(record.getStudent());
		if(records.size() == 0)
			workrec.setWeeks(records.size()+1+"");
		else if(records.get(records.size()-1).getStartDate().getTime()<record.getStartDate().getTime())
			workrec.setWeeks(Integer.parseInt(records.get(records.size()-1).getWorkRecord().getWeeks())+1+"");
		else
		{
			for(int i = 0; i<records.size(); i++)
			{
				if(records.get(i).getStartDate().getTime()<record.getStartDate().getTime())
					continue;
				else
				{
					workrec.setWeeks(i+1+"");
					break;
				}
			}
		}
		//工作记录中填入出勤天数（默认5天满勤）
		workrec.setFullHours(5);
		//实习（工作）记录中填入指导教师工号
		record.setTeacherNo(ls1.get(0).getTeacherNo());
		workrec.setTeacherNo(ls1.get(0).getTeacherNo());
		//实习记录中填入主要工作内容
		record.setContent(workrec.getWorkContents());
		//实习记录中填入完成情况（默认已完成）
		record.setCompleStatus("已完成");
		//在实习记录中绑定工作记录
		record.setWorkRecord(workrec);
		
		recordDao.saveRecord(record);
		return "保存成功";
	}

	/**
	 * 更新实习申请记录，同时级联更新工作记录；若完成情况和指导情况不为空则表示该记录已被指导老师或辅导员查看，此时不可修改实习记录；
	 * @param record
	 * @param workrec
	 * @param studentId
	 * @return String
	 * @throws Exception
	 */
	public String updateRecord(StudentRecord record, StudentWorkRec workrec, Integer studentId) throws Exception
	{
		//获取原实习记录
		StudentRecord studentRecord = getRecordById(record.getRecordId());
		//若查询不到原实习记录则抛出异常
		if (null == studentRecord)
			throw new NullPointerException("修改失败：未查找到该申请记录！");
		//判断当前用户的操作是否合法
		if (!studentRecord.getStudent().getStudentId().equals(studentId))
			throw new Exception("修改失败：信息不匹配，非法操作！");
		//获取实习记录中的完成情况和指导情况，判断是否为空，都为空则允许执行修改操作
		if (null!=studentRecord.getGuidStatus())
			throw new RuntimeException("修改失败：该实习记录已被指导老师查看，不允许修改！");
		
		//检测修改后的日期是否与已提交实习记录的时间重叠
//		List<StudentRecord> list = recordDao.getRecordByDate(record.getStudent(),calendar.getTime());
		if (null==record.getStartDate())
			throw new NullPointerException("修改失败：工作日期未填写！");
		List<StudentRecord> list = recordDao.getRecordByDate(studentRecord.getStudent(),record.getStartDate());
		
		if(null!=list && 0 != list.size())
			throw new RuntimeException("修改失败：实习记录的开始时间与其他记录时间重叠！");
		
		//获取原工作记录
		StudentWorkRec studentWorkRec = studentRecord.getWorkRecord();
		//工作记录中填入周数
		List<StudentRecord> records = recordDao.getRecord(studentRecord.getStudent());
		if(records.size() == 0)
			studentWorkRec.setWeeks(records.size()+1+"");
		else if(records.get(records.size()-1).getStartDate().getTime()<record.getStartDate().getTime())
			studentWorkRec.setWeeks(Integer.parseInt(records.get(records.size()-1).getWorkRecord().getWeeks())+1+"");
		else
		{
			for(int i = 0; i<records.size(); i++)
			{
				if(records.get(i).getStartDate().getTime()<record.getStartDate().getTime())
					continue;
				else
				{
					studentWorkRec.setWeeks(i+1+"");
					break;
				}
			}
		}
		studentWorkRec.setWorkContents(workrec.getWorkContents());
		studentWorkRec.setMaingains(workrec.getMaingains());
		studentWorkRec.setNotes(workrec.getNotes());
		studentWorkRec.setStartDate(workrec.getStartDate());
		studentWorkRec.setEndDate(workrec.getEndDate());
		studentWorkRec.setUpdDate(new Date());	//修改时间
		studentWorkRec.setUpdUser(studentRecord.getStudent().getStudentNo());	//修改人
		//将持久化的原实习表替换新的实习记录信息
		studentRecord.setWorkRecord(studentWorkRec);
		studentRecord.setComPost(record.getComPost());
		//暂定实习（工作）记录中的起止时间无法修改
		studentRecord.setStartDate(record.getStartDate());
		studentRecord.setEndDate(record.getEndDate());
		studentRecord.setContent(studentWorkRec.getWorkContents());
		studentRecord.setUpdDate(new Date());	//修改时间
		studentRecord.setUpdUser(studentRecord.getStudent().getStudentNo());	//修改人
		
		recordDao.updateRecord(studentRecord);
		return "修改成功！";
	}
	
	/**
	 * 根据实习记录Id删除实习记录，同时级联删除对应的工作记录；若完成情况和指导情况不为空则表示该记录已被指导老师或辅导员查看，此时不可删除实习记录；
	 * @param recordId
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public String deleteRecord(Integer recordId, Integer studentId)  throws Exception
	{
		StudentRecord studentRecord = getRecordById(recordId);
		if(null == studentRecord)
			throw new NullPointerException("删除失败：无效的申请表Id");
		//判断当前用户的操作是否合法
		if(!studentRecord.getStudent().getStudentId().equals(studentId))
			throw new Exception("删除失败：信息不匹配，非法操作！");
		//获取实习记录中的完成情况和指导情况，判断是否为空，都为空则允许执行删除操作
		if (null!=studentRecord.getGuidStatus())
			throw new RuntimeException("删除失败：该实习记录已被指导老师查看，不允许删除！");
		recordDao.deleteRecord(recordId);
		return "删除成功！";
	}

	/**
	 * 获取当前最新已通过申请
	 * @param student
	 * @return String
	 * @throws Exception
	 */
	public Object getLatestApplication(SysStudent student) throws Exception
	{
		//检查当前是否有已通过的实习（变更）申请
		List<StudentApplication> ls1 = applicationDao.getApplication(student);
		//先判断是否存在实习申请
		if(null!=ls1 && 0!=ls1.size())
		{
			//判断实习申请状态是否为“审批通过”，若不是进一步判断
			if(!ls1.get(0).getStatus().equals("DApprove"))
			{
				//判断是否为“作废”状态
				if(ls1.get(0).getStatus().equals("EApprove"))
				{
					//判断变更申请中是否有已通过的申请
					List<StudentSecApplication> ls2 = secApplicationDao.getSecApplication(student);
					for(int i = 0;i<ls2.size();i++)
						if(ls2.get(i).getStatus().equals("DApprove"))
							return ls2.get(i);	//返回已通过变更申请的新实习岗位
					throw new RuntimeException("您还未提交申请或还有未通过的变更申请！");
				}
				else
					throw new RuntimeException("您还有未通过的实习申请！");
			}
			else
				return ls1.get(0);	//返回已通过实习申请
		}
		else
			throw new RuntimeException("您从未开始过实习！");
	}

	/**
	 * 检查接受到的申请表字段是否符合要求
	 * @param record 实习记录
	 * @param workrec 工作记录
	 * @return Boolean 符合要求返回true，否则抛出相应异常信息
	 * @throws Exception
	 */
	public Boolean checkForm(StudentRecord record, StudentWorkRec workrec) throws Exception
	{
		if(null !=record && null!= workrec)
		{
			if(null == workrec.getStartDate() || null == workrec.getEndDate())
				throw new RuntimeException("请输入工作记录开始时间和结束时间！");
			if(null == record.getComPost() || "".equals(record.getComPost()))
				throw new RuntimeException("请输入实习岗位！");
			if(null == workrec.getWorkContents() || "".equals(workrec.getWorkContents()))
				throw new RuntimeException("请输入本周主要工作内容！");
			else if(workrec.getWorkContents().length()>100)
				throw new RuntimeException("本周主要工作内容不能超过100个字！");
			if(null == workrec.getMaingains() || "".equals(workrec.getMaingains()))
				throw new RuntimeException("请输入本周工作收获！");
			else if(workrec.getMaingains().length()>400)
				throw new RuntimeException("本周工作收获不能超过400个字！");
			else if(workrec.getNotes().length()>100)
				throw new RuntimeException("备注不能超过100个字！");
		}
		else
		{
			throw new Exception("参数缺失：record，workrec");
		}
		return true;
	}
	
}
