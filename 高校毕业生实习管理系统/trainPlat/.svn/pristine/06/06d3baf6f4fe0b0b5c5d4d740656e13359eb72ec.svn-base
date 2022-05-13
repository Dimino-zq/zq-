$(document).ready(function(){
	var path=getRootPath();
	secteacherapp();

		//按照学生姓名查询
		$("#btn_query").click(function(){
			
	        var queryParams=$('#form_query').serializeJSON();
	        $('#tbl_tapp').datagrid('load',queryParams);
	        console.log(queryParams);
	       
		}
 	);
		
		$("#tbl_tapp").datagrid({ 
            onClickRow: function (index, row) { 
			var status = row.statusVal;
			if (status=="未提交"){
				$('#btn_allInfo').linkbutton("disable");
			}
			
			else{
				$('#btn_allInfo').linkbutton("enable");
			}
			
	
	}});
		
		
		
		//通过审批功能模块
 $("#btn_pass").click(function () {
     var row=$("#tbl_tapp").datagrid("getSelected");
       if (null!=row)
       {
         
        var postData={"secApplyId":row.secApplyId,"applyId":row.application.applyId};
        //{"applyId":applyId,"deptView":deptView}
        
         $.post((path+"/teacher/passTeacherSec"),postData,function (data) {
           if ("succ"==data)
           {
         	  $("#dialog_student").dialog("close");
         	secteacherapp();
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
	   var row=$("#tbl_tapp").datagrid("getSelected");
    if ($("#reason").textbox('getText') == "")
			{
				$.messager.alert("提示","请输入您的拒绝理由！","info");
				$("#reason").next("span").find("textarea").focus();
			} 
    else{
   
       if (null!=row)
       {var teacherview = $("#reason").textbox('getValue');
      // alert(111);
         $.post((path+"/teacher/refuseTeacherSec"),{"secApplyId":row.secApplyId,"applyId":row.application.applyId,"teacherview":row.teacherview},function (data) {
           if ("succ"==data)
           {
         	  $("#dialog_reason").dialog("close");
               $("#dialog_student").dialog("close");
               secteacherapp();
             $.messager.alert("提示","成功驳回！","info");         
         }
         else 
     {
       $.messager.alert("提示","驳回失败!","info");
     }
               
       });
     }
    }
 });  
		  
	/*$("#tbl_tapp").datagrid({ 
                onClickRow: function (index, row) { 
				var status = row.statusVal;
				if (status=="作废"){
					$('#btn_seeDlg').linkbutton("disable");
				}
				
				else{
					$('#btn_seeDlg').linkbutton("enable");
				}
				
		
		}
	});*/
	
	
/*	//审批附件点击显示模块seeFile   $("#btn_seeDlg")
   // <a class="easyui-linkbutton" id="btn_seeDlg" href="#" data-options="iconCls:'icon-edit'">查看审核申请</a>
   $("#btn_seeDlg").click(function(){
		  var row = $("#tbl_tapp").datagrid("getSelected");
		     if (row==null){
		      $.messager.alert("提示","请选择要审核的学生文件!","info");
		      return false;
		     }
		     else
		     {
		    	 var row=$("#tbl_tapp").datagrid("getSelected");
			     
			     
	//		     console.log(row.filepath);
			     
			     //给图片url赋值
			     console.log(row);
			     console.log(row.filePath);
			     console.log("${ctxPath}"+	row.filePath);
			     console.log(path+	row.filePath);
			     console.log(path+row.filePath);
			     $("#image").attr("src",path+row.filePath);
			 //src="${ctxPath}/static/apply/08-16-30-image.jpg"
				 $("#dialog_student").dialog("setTitle","查看"+row.application.student.studentName+"的附件");
			     $("#dialog_student").dialog("open");
			     $("#btn_refuse").click(function(){
						$("#dialog_reason").dialog("setTitle","请输入对"+row.application.student.studentName+"驳回的理由");
						$("#dialog_reason").dialog("open");
						
					});
			//     $("#dialog_student").dialog("setTitle","查看学生附件");
			   
			 }   
		     
		     
		 });
	*/
	//查看详情
	$("#btn_allInfo").click(function(){
	  var row = $("#tbl_tapp").datagrid("getSelected");
	     if (row==null){
	      $.messager.alert("提示","请选中一行数据!","info");
	      return false;
	     }
	     else
	     {

	        var secApplicationId=row.secApplyId;
	        var url=path+"/teacher/gotohtmlapplication2";
	        //post跳转网页
	        var temp_form = document.createElement("form");
	        temp_form .action = url;
	        temp_form .target = "_self";
	        temp_form .method = "post";
	        temp_form .style.display = "none"; 
	        var opt = document.createElement("textarea");
	        opt.name = "secApplicationId";
	        opt.value = secApplicationId;
	        temp_form .appendChild(opt);
	        document.body.appendChild(temp_form);
	        temp_form .submit();
	       }    
	 	});
	
	//显示表格内容功能
	function secteacherapp(){
		    var url=path+"/teacher/getStudentSecByCon";
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
                     if (row.application.student)
                     {
                         return row.application.student.studentName;
                     }
                     else
                     {
                         return "";
                     }
                  }},
			      {field:'phoneOrQQ',title:'学生联系方式',width:130,
                	  formatter:function(value,row,index){
                          if (row.application.student)
                          {
                              return row.application.phoneOrQQ;
                          }
                          else
                          {
                              return "";
                          }
                       }},
			      {field:'newComName',title:'新实习单位',width:100,
     	                  formatter:function(value,row,index){
    	                      if (row.newCompany.comName)
    	                      {
    	                          return row.newCompany.comName;
    	                      }
    	                      else
    	                      {
    	                          return "";
    	                      }
    	                   }},
				  {field:'oldComName',title:'原实习单位',width:100},
				  {field:'teacherView',title:'老师意见',width:100},
				
				  {field:'deptView',title:'系主任意见',width:100},		
				  {field:'statusVal',title:'实习变更状态',width:100}
			      ]]
		    })
		}
	
});