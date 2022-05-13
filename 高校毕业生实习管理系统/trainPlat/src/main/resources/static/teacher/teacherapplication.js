$(document).ready(function(){
		var path=getRootPath();
		 teacherapp();
		//按照学生姓名查询
		$("#btn_query").click(function(){
	        var queryParams=$('#form_query').serializeJSON();
	        $('#tbl_tapp').datagrid('load',queryParams);
	        console.log(queryParams);
		});
	
		$("#tbl_tapp").datagrid({ 
                onClickRow: function (index, row) { 
				var status = row.statusVal;
				if (status=="审批通过"){
					$('#btn_seeDlg').linkbutton("disable");
					$('#btn_allInfo').linkbutton("enable");
				}else if (status=="未提交"){
					$('#btn_seeDlg').linkbutton("disable");
					$('#btn_allInfo').linkbutton("disable");
				}else{
					$('#btn_seeDlg').linkbutton("enable");
					$('#btn_allInfo').linkbutton("enable");
				}
		}
	});
		
		
		//查看详情
		$("#btn_allInfo").click(function(){
		  var row = $("#tbl_tapp").datagrid("getSelected");
		     if (row==null){
		      $.messager.alert("提示","请选中一行数据!","info");
		      return false;
		     }
		     else
		     {

		        var applicationId=row.applyId;
		        var url=path+"/studentapp/gotohtmlapplication";
		        //post跳转网页
		        var temp_form = document.createElement("form");
		        temp_form .action = url;
		        temp_form .target = "_self";
		        temp_form .method = "post";
		        temp_form .style.display = "none"; 
		        var opt = document.createElement("textarea");
		        opt.name = "applicationId";
		        opt.value = applicationId;
		        temp_form .appendChild(opt);
		        document.body.appendChild(temp_form);
		        temp_form .submit();
		       }    
		 	});
		
		
		
		//审批附件点击显示模块
		$("#btn_seeDlg").click(function(){
		  var row = $("#tbl_tapp").datagrid("getSelected");
		     if (row==null){
		      $.messager.alert("提示","请选择记录!","info");
		      return false;
		     }
		     else
		     {
		    	var row=$("#tbl_tapp").datagrid("getSelected");  
				var url=path+"/getRemoteImgFile?filePath="+row.filePath;
			    $("#image").attr("src",url);
			    $("#dialog_student").dialog("setTitle","查看"+row.student.studentName+"的附件");
				$("#dialog_student").dialog("open");
				$("#btn_refuse").click(function(){
					var row = $("#tbl_tapp").datagrid("getSelected");
					$("#dialog_reason").dialog("setTitle","请输入对"+row.student.studentName+"驳回的理由");
					$("#dialog_reason").dialog("open");
				});
			 }   
		 });
	
		//通过审批功能模块
    $("#btn_pass").click(function () {
        var row=$("#tbl_tapp").datagrid("getSelected");
          if (null!=row)
          {
            var applyId=row.applyId;
           var postData={"applyId":applyId};
           console.log(row);
           console.log(postData);
            $.post((path+"/teacher/passTeacher"),postData,function (data) {
              if ("succ"==data)
              {
            	  $("#dialog_student").dialog("close");
            	teacherapp();
                $.messager.alert("提示","通过！","info");          
            }
            else 
        {
          $.messager.alert("提示","失败!","info");
        }
                  
          });
        }
    }); 
//驳回审批功能模块
    $("#btn_yes").click(function () {
    	   var row = $("#tbl_tapp").datagrid("getSelected");
		   var reason = $("#reason").val();
	       if ($("#reason").textbox('getText') == "")
				{
					$.messager.alert("提示","请输入您拒绝"+row.student.studentName+"的理由！","info");
					$("#reason").next("span").find("textarea").focus();
				}
		  else if(reason.length > 200){
				$.messager.alert("提示","请将驳回理由字数限制为200字之内!","info");
			}
	       else{
	        	var row=$("#tbl_tapp").datagrid("getSelected");
				
		          if (null!=row)
		          {
		            var applyId=row.applyId;
		            var deptView = $("#reason").textbox('getValue')
		            $.post((path+"/teacher/refuseTeacher"),{"applyId":applyId,"deptView":deptView},function (data) {
		              if ("succ"==data)
		              {
		            	  $("#dialog_reason").dialog("close");
		                  $("#dialog_student").dialog("close");
		                  teacherapp();
		                	$.messager.alert("提示","成功驳回！","info");
		                
		            }
		            else 
		        {
		          $.messager.alert("提示","失败!","info");
		        }
		                  
		          });
		        }
	       }
	    });  
  
		  
		  
		//显示表格内容功能
		function teacherapp(){
		    var url=path+"/teacher/getStudentByCon";
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
			      {field:"surcomjob",title:"实习岗位",width:100},
			      {field:'phoneOrQQ',title:'学生联系方式',width:130,
			    	  formatter:function(value,row,index){
		                     if (row.student)
		                     {
		                         return row.phoneOrQQ;
		                     }
		                     else
		                     {
		                         return "";
		                     }
		                  }},
			      {field:'applydate',title:'申请日期',width:100},
				  {field:'endDate',title:'结束日期',width:100},
				  {field:'statusVal',title:'实习申请状态',width:100}
			      ]]
		    })
		}
	
});





