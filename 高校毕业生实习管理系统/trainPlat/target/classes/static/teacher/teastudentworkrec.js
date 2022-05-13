$(document).ready(function(){
		var path=getRootPath();
		teacherstudentworkrec();
	clearForm();
	
	
	$("#tbl_tapp").datagrid({ 
        onClickRow: function (index, row) { 
		var guidStatus = row.guidStatus;
		if (guidStatus=="已指导"){
			$('#btn_talkTeacher').linkbutton("disable");
		}
		
		else{
			$('#btn_talkTeacher').linkbutton("enable");
		}
		

}});
	
	
	
		//点击显示指导模块
		$("#btn_talkTeacher").click(function(){
			var row = $("#tbl_tapp").datagrid("getSelected");	
			
			
		     if (row==null){
		      $.messager.alert("提示","请选中一行数据!","info");
		      return false;
		     }
		     else{
		    $("#txt_recordId").val(row.recordId);
			$("#txt_workLogId").val(row.workRecord.workLogId);
			$("#dialog_talkTeacher").dialog("setTitle",row.student.studentName+" 实习记录"+"审批");
    		$("#dialog_talkTeacher").dialog("open");
				 }
  		});
		
		function clearForm(){
			$("#input_write").textbox("setValue","");
			$("#rdo_chooce1").radiobutton('clear');
			$("#rdo_chooce2").radiobutton('clear');
			$("#rdo_chooce3").radiobutton('check');
			$("#rdo_chooce4").radiobutton('clear');
			
		}
		
		//指导确定模块
	   	$("#btn_talkYes").click(function () {
		   var reaSon=$("#input_write").textbox("getValue");
			var reaSon1 = $("#input_write").val();
	        if (reaSon==""){
	        $.messager.alert("提示","请输入教师指导的主要内容!","info");
	        $("#input_write").next("span").find("input[type='textarea']").focus();
	        return  false;
	        }
			else if (reaSon1.length > 200){
				$.messager.alert("提示","请将您的评语字数限制为200字以内！","info");
				return  false;
			}
	        var row=$("#tbl_tapp").datagrid("getSelected");
	          if (null!=row)
	          {	           
	            var postData = $('#form_studentinfo').serializeJSON();
	            console.log(postData);
	            $.post((path+"/teacher/passWorkRecTeacher"),postData,function (data) {
	              if ("succ"==data)
	              { $("#dialog_talkTeacher").dialog("close");
	                teacherstudentworkrec();
	            	//$("#btn_query").click();
	                $.messager.alert("提示","通过！","info");	    }
	            else {	 $.messager.alert("提示","失败!","info");	 }    });  } clearForm(); }); 
		
		//按照学生姓名查询
		$("#btn_query").click(function(){
			
	        var queryParams=$('#form_query').serializeJSON();
	        $('#tbl_tapp').datagrid('load',queryParams);
	        console.log(queryParams);
	       
		}
		
    	);


	//查看详情
		$("#btn_allInfo").click(function(){
		  var row = $("#tbl_tapp").datagrid("getSelected");
		     if (row==null){
		      $.messager.alert("提示","请选中一行数据!","info");
		      return false;
		     }
		     else
		     {

		        var workLogId=row.workRecord.workLogId;
		        var url=path+"/teacher/workrec";
		        //post跳转网页
		        var temp_form = document.createElement("form");
		        temp_form .action = url;
		        temp_form .target = "_self";
		        temp_form .method = "post";
		        temp_form .style.display = "none"; 
		        var opt = document.createElement("textarea");
		        opt.name = "teastudentworkrecId";
		        opt.value = workLogId;
		        temp_form .appendChild(opt);
		        document.body.appendChild(temp_form);
		        temp_form .submit();
		       }    
		 	});
		
 
  
		  
		  
		//显示表格内容功能
		function teacherstudentworkrec(){
		    var url=path+"/teacher/getTeacherWorkRecByCon";
		    $("#tbl_tapp").datagrid({
	    	loadMsg:"加载数据中......",
            url:url,
            border:false,
            striped:true,
            fit:true,
            rownumbers:true,
            autoRowHeight:false,
            singleSelect:true,
            fitColumns:true,
            pagePosition:"bottom",
            pagination:true,
            pageSize:10,
            pageList:[2,10,20,50],
            pageNumber:1,
			    columns:[[
				  {field: 'studentName',title: '学生姓名',width: 100,
                  formatter:function(value,row,index){
                     if (row.student)
                     {
                         return row.student.studentName;
                     }
                     else
                     {
                         return "";
                     }
                  }},
			      {field:"comPost",title:"实习岗位",width:100},
			      {field:"weeks",title:"周数",width:100,
			    	  formatter:function(value,row,index){
		                     if (row.workRecord)
		                     {
		                         return row.workRecord.weeks;
		                     }
		                     else
		                     {
		                         return "";
		                     }
		                  }},
			      {field:'startDate',title:'记录开始日期',width:100},
				  {field:'endDate',title:'结束日期',width:100},
				  {field:'tutorComate',title:'老师指导情况',width:100,
			    	  formatter:function(value,row,index){
		                     if (row.workRecord.tutorComate)
		                     {
		                         return "已指导";
		                     }
		                     else
		                     {
		                         return "未指导";
		                     }
		                   }}
			      ]]
		    })
		}
	
});





