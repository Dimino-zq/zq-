$(document).ready(function(){
	var path=getRootPath();
	nowwork();
	initCombobox();
	
	 

	//修改密码
	$("#btn_chgpassword").click(function(){
		$("#dialog_password").dialog("open");
	
	});
	
	
	
	$('#input_oldpassword').passwordbox({
			inputEvents: $.extend({}, $.fn.passwordbox.defaults.inputEvents, {
				keypress: function(e){
					var char = String.fromCharCode(e.which);
					$('#viewer').html(char).fadeIn(200, function(){
						$(this).fadeOut();
					});
				}
			})
		})
	
	
	 //保存密码框
    $("#btn_savepassword").click(function(){
    	 var postData=$('#form_passwordinfo').serializeJSON();
    	
    	var url = path + "/studentapp/updStudentPassword";
    	var npassword= $("#input_newpassword").val();
		if(npassword!=""){ 	
	    	$.messager.confirm(' ', '是否修改密码?', function(r){
				if (r){
							var newpassword = $("#input_newpassword").val();
							var repeatpassword = $("#input_secnewpassword").val();
							if(newpassword.length>=6 && repeatpassword.length>=6){
								
								if(newpassword == repeatpassword ){
							    	$.post(url, postData, function(data) {
							    		if (data == "succ")
							    		{		
											
													 $.messager.alert("提示", "修改密码成功", "info", function () {
						           					 window.location.reload();
												
				       					 		});	    						    				    			
								    			
							    		}else if(data = "passworderror")
							    		{
							    			$.messager.alert("提示","旧密码错误","info");
							    		}	
						    		});
								}else{
												
											$.messager.alert("提示","两次输入密码不一致！请检查后重新输入", "info",function () {
						           					 $("#input_secnewpassword").next("span").find("input[type='text']").focus();
												
				       					 		});	    	
											
											
										}
								}else{
									$.messager.alert("提示","您输入的密码小于六位！","info");
								}
					
					}
			});
		}else{
			$.messager.alert("提示","请输入您的旧密码！", "info",function () {
					 $("#input_oldpassword").next("span").find("input[type='text']").focus();
											
			});	    
		}
    });
	 //关闭修改密码框
    $("#btn_closepassword").click(function(){
		$("#dialog_password").dialog("close");
		
		
	});
	
	//修改个人信息
    $("#btn_chginformation").click(function(){
    	
    	 var cycleNum=$("#txt_cycleNum").text();
    	 var totalCycleNum=$("#txt_totalCycleNum").text();
    	 var studentNo=$("#txt_studentNo").text();
    	 var studentName=$("#txt_studentName").text();
    	 var studentSex=$("#txt_studentSex").text();
    	 var birthDay=$("#txt_birthDay").text();
    	 var depart=$("#txt_depart").text();
    	 var major=$("#txt_major").text();
    	 var studentGrade=$("#txt_studentGrade").text();
    	 var classNumber=$("#txt_classNumber").text();
    	 var studentGrade=$("#txt_studentGrade").text();
    	 var politics=$("#txt_politics").text();
    	 var nation=$("#txt_nation").text();
    	 var nativeplace=$("#txt_nativeplace").text();
    	 var identity=$("#txt_identity").text();
    	 var nation=$("#txt_nation").text();   	  	   
			$("#input_studentNo").textbox("setValue",studentNo);
			$("#input_studentName").textbox("setValue",studentName);
			
			console.log(studentSex);
			if ("男"==studentSex)
			{
				$("#rdo_studentSexboy").radiobutton("check",0);
			}
			else
			{
				$("#rdo_studentSexgirl").radiobutton("check",1);
			}
			$("#input_birthDay").datebox("setValue",birthDay);
			$("#input_depart").combobox("setValue",depart);
			$("#input_major").combobox("setValue",major);
			$("#input_studentGrade").textbox("setValue",studentGrade);
			$("#input_classNumber").textbox("setValue",classNumber);
			$("#input_studentPolSta").combobox("setValue",politics);
			$("#input_nationality").combobox("setValue",nation);
		    $("#input_nativeplace").textbox("setValue",nativeplace);
		    $("#input_identity").textbox("setValue",identity);
		$("#dialog_student").dialog("open");
	});
    
    //保存个人信息框
    $("#btn_save").click(function(){
    	var url = path + "/studentapp/updStudentInformation";
    	 var postData=$('#form_studentinfo').serializeJSON();
    	 console.log(postData.major);
     	if (postData.studentNo == "")
 		{
 			$.messager.alert("提示","请输入学号！","info",function (){
 				$("#input_studentNo").next("span").find("input[type='text']").focus();
 			});
 		}
 		else if (postData.studentName == "")
 		{
 			$.messager.alert("提示","请输入姓名！","info",function (){
 				$("#input_studentName").next("span").find("input[type='text']").focus();
 			});
 		}
 		else if (postData.depart == "")
 		{
 			$.messager.alert("提示","请选择院系！","info",function (){
 				$("#input_depart").next("span").find("a").click();
 			});
 		}
 		else if (postData.major == "")
 		{
 			$.messager.alert("提示","请选择专业！","info",function (){
 				$("#input_major").next("span").find("a").click();
 			});
 		}
 		else if (postData.studentGrade == "")
 		{
 			$.messager.alert("提示","请输入年级！","info",function (){
 				$("#input_studentGrade").numberbox().next('span').find('input').focus();
 			});
 		}
 		else if (postData.classNumber == "")
 		{
 			$.messager.alert("提示","请输入班级号！","info",function (){
 				$("#input_classNumber").numberbox().next('span').find('input').focus();
 			});
 		}
 		else if (postData.nativeplace == "")
 		{
 			$.messager.alert("提示","请输入籍贯！","info",function (){
 				$("#input_nativeplace").next("span").find("input[type='text']").focus();
 			});
 		}
 		else if (postData.politics == "")
 		{
 			$.messager.alert("提示","请选择政治面貌！","info",function (){
 				$("#input_studentPolSta").next("span").find("a").click();
 			});
 		}
 		else if (postData.identity =="")
 		{
 			$.messager.alert("提示","请输入身份证！","info",function (){
 				$("#input_identity").next("span").find("a").click();
 			});
 		}
 		else if (!$("#input_identity").textbox("isValid",postData.identity))
 		{
 			$.messager.alert("提示","请输入正确的身份证！","info",function (){
 				$("#input_identity").next("span").find("a").click();
 			});
 		}else {
    	
    	
    	
    	$.messager.confirm(' ', '是否保存个人信息?', function(r){
			if (r){
				
			    	$.post(url, postData, function(data) {
			    		if (data == "succ")
			    		{
			    			
			    			$("#dialog_student").dialog("close");
			    			window.location.reload();
			    			
			    		}
			    		else
			    		{
			    			$.messager.alert("提示","学号重复，修改失败","info");
			    		}
			    	});
				
			}
		});
    	
    	
    	
    	
 		}
    	
    
    
	});
    
    
    //关闭个人信息框
    $("#btn_close").click(function(){
		$("#dialog_student").dialog("close");
		
		
	});
	
  //初始化各个下拉列表框数据
    function initCombobox()
    {
    	//院系和专业下拉列表框数据的初始化以及联动
    	$("#input_depart").combobox({
    		valueField:'departName',
    		textField:'departName',
    		url:path+'/student/getdepart',
    		onSelect:function(record){
    			var param = record.departId;
    			$("#input_major").combobox({
    				value:null,
    				valueField:'majorName',
    				textField:'majorName',
    				url:path+'/student/getmajor',
    				queryParams:{"departId":param}
    			});
    		}
    	});
		
    	//政治面貌下拉列表框
		$("#input_studentPolSta").combobox({
			value:'群众',
	    	editable:false,
	    	textField: 'label',
	    	valueField: 'value',
	    	data: [{
	    	    label: '群众',
	    	    value: '群众'
	    	},{
	    	    label: '共青团员',
	    	    value: '共青团员'
	    	},{
	    	    label: '中共党员',
	    	    value: '中共党员'
	    	}]
	    });
		
		//民族下拉列表框
		$("#input_nationality").combobox({
			value:'汉族',
	    	editable:false,
	    	method:'get',
	    	url:path+'/static/data/nation.json',
	    	textField:'name',
	    	valueField:'name'
	    });
    }
	
	
	
	 function nowwork() {   
		 
		
		 var cycleNum=$("#txt_cycleNum").text();
		 var totalCycleNum=$("#txt_totalCycleNum").text();
		console.log(cycleNum);
		console.log(totalCycleNum);
		 
		 var aRow = [];
		 var aUpRow = [];
		 var aCol = [];
		 var aUpCol =[];
		 for (var j = cycleNum; j--;) {
             
             aCol.push("<td style=\"border-radius:10px;background:#69ff82;height:30px;width:60px;\">" +""+
                 "</td>");
         }
		 if(totalCycleNum!=cycleNum){			 		
         aCol.push("<td style=\"border-radius:10px;background:#70dce1;height:30px;width:60px;\">" +"</td>");	
              for (var i = (totalCycleNum-cycleNum-1);i>0; i--) {
             
                    aCol.push("<td style=\"border-radius:10px;background:#d9ecf4;height:30px;width:60px;\">" +""+
                 "</td>");
                 }  
              
              for (var i = cycleNum; i--;) {
                  
                  aUpCol.push("<td style=\"background:white;height:30px;width:60px;\">" +""+
                      "</td>");
              }         
              if(totalCycleNum!=cycleNum){
     	         aUpCol.push("<td style=\"background:white;height:30px;width:60px;\">" +"<img src=\"/train/static/images/runman.gif\" style=\"width:60px;height:60px; \" >"+"</td>");
              }
              for (var i = (totalCycleNum-cycleNum);i<=0; i--) {
                  
             	 aUpCol.push("<td style=\"background:white;height:30px;width:60px;\">" +""+
                      "</td>");
              }
              aUpRow.push("<tr>" + aUpCol.join("") + "</tr>");
		 }
         
		 aRow.push("<tr>" + aCol.join("") + "</tr>");
         
         $("#nowwork").append(aUpRow);
         $("#nowwork").append(aRow);
               
     };
	 
	 
	 
	 
	 
	
})
