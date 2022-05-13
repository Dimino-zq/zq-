$(document).ready(function(){
	var path=getRootPath();
	initSchool_detail();
	
	function initSchool_detail(){
		var url=path+"/sysset/querySchool";
		$.post(url,"",function(data){
			$("#upd_schoolId").val(data.rows[0].schoolId);
			$("#upd_schoolName").textbox("setValue",data.rows[0].schoolName);
			$("#upd_schoolEmail").textbox("setValue",data.rows[0].schoolEmail);
			$("#upd_schoolExplain").textbox("setValue",data.rows[0].schoolExplain);
			$("#upd_schoolDate").datebox("setValue",data.rows[0].schoolDate);
			$("#upd_schoolLead").textbox("setValue",data.rows[0].schoolLead);
			$("#upd_schoolType").textbox("setValue",data.rows[0].schoolType);
			$("#upd_schoolAddr").textbox("setValue",data.rows[0].schoolAddr);
			
			initSchool_true()
		});
	}
	//修改按钮
	$("#btn_edit").click(function(){
				   $("#div_btnUpd").attr("style","display:block;");//显示确定取消
				   $("#div_butEdit").attr("style","display:none;");//隐藏修改
				   initSchool_false();
	//			   $('#tb').textbox('readonly');	    // 启用只读模式
	//               $('#tb').textbox('readonly',true);	// 启用只读模式
	//               $('#tb').textbox('readonly',false);	// 禁用只读模式
	});
	//取消修改按钮
	$("#btn_cancelUpd").click(function(){			//取消修改
			 $("#div_btnUpd").attr("style","display:none;");//隐藏确定取消
			$("#div_butEdit").attr("style","display:block;");//显示修改
			 initSchool_true();
	});
	
	$("#btn_update").click(function(){
		 var email=$("#upd_schoolEmail").textbox('getValue');
		 var lead=$("#upd_schoolLead").textbox('getValue');
		 var name=$("#upd_schoolName").textbox('getValue');
		 var explain=$("#upd_schoolExplain").textbox('getValue');
		 var date=$("#upd_schoolDate").datebox('getValue');
		 var type=$("#upd_schoolType").textbox('getValue');
		 var addr=$("#upd_schoolAddr").textbox('getValue');
		
		 
		 var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
		 if(!reg.test(email)){
			 alert("邮箱格式不正确");
			 return false;
		 }else if(lead.length >10||lead.length<=0){
			 alert("名称格式不正确");
			 return false;
		 }
	//	 else if(name.length<=0){
	//		 alert("学校名称格式不正确");
	//		 return false;
	//	 }else if(explain.length<=0){
	//		 alert("学校简介格式不正确");
	//		 return false;
	//	 }else if(date.length <=0){
	//		 alert("建校日期格式不正确");
	//		 return false;
	//	 }else if(type.length <=0){
	//		 alert("学校类型格式不正确");
	//		 return false;
	//	 }else if(addr.length <=0){
	//		 alert("学校地址格式不正确");
	//		 return false;
	//	 }
		 else{
			 var url=path+"/sysset/updateSchool";  
				var postData=$("#updateForm").serializeJSON();
				$.post(url,postData,function(data){
					var mess=eval(data).mess;
			   if ("succ"==mess){
			    alert("修改成功！");
			    $("#div_butEdit").attr("style","display:block;");//显示修改
				 $("#div_btnUpd").attr("style","display:none;");//隐藏确定取消
				 initSchool_true();
			    $('#tbl_school_detail').datagrid('load');
			    $('#updateForm').datagrid('load');
	
			   }else{
			    alert(mess);
			   }
			});
		}
	 });
	function initSchool_true(){
		$("#upd_schoolName").textbox('readonly',true);
		$("#upd_schoolEmail").textbox('readonly',true);
		$("#upd_schoolExplain").textbox('readonly',true);
		$("#upd_schoolDate").datebox('readonly',true);
		$("#upd_schoolLead").textbox('readonly',true);
		$("#upd_schoolType").textbox('readonly',true);
		$("#upd_schoolAddr").textbox('readonly',true);
	}
	function initSchool_false(){
		$("#upd_schoolName").textbox('readonly',false);
		$("#upd_schoolEmail").textbox('readonly',false);
		$("#upd_schoolExplain").textbox('readonly',false);
		$("#upd_schoolDate").datebox('readonly',false);
		$("#upd_schoolLead").textbox('readonly',false);
		$("#upd_schoolType").textbox('readonly',false);
		$("#upd_schoolAddr").textbox('readonly',false);
	}
});