$(document).ready(function(){	
	var path=getRootPath();
	var planDetailObj=null;
	company();
	
	$('#file').filebox({  
        
        accept : [ 'image/jpg' ],
        onChange: function(value){
            var f = $(this).next().find('input[type=file]')[0];
            if (f.files && f.files[0]){
                var reader = new FileReader();
                reader.onload = function(e){		                              				                             		 
               		var imgStr=e.target.result.replace('data:image/jpeg;base64,','');
               		console.log(e.target.result);
               		
               		var strLength = imgStr.length;
					    var fileLength = parseInt(strLength - (strLength / 8) * 2);
					
					    
					    var size = "";
					    size = (fileLength / (1024*1024)).toFixed(2);
               		
					    if(size<1){
					    	 console.log(size);
		                    	$('#pictureimg').attr('src', e.target.result);
		                    	
		                    	$('#imgString').val(imgStr);
					    }else{
					    	$.messager.alert("提示","请上传小于1M的jpg文件！","info");
					    }
					   		                                  	                             
               }
               reader.readAsDataURL(f.files[0]);
           }
       }
		});
	
	$('#dg').datagrid({
	   	    fit:true,
	        rownumbers:true,
	   	    striped:true,
	   	    fitColumns:true,
	   	 	singleSelect:true,
	   	    columns:[[
	   	      		{field:'error',title:'错误行及错误内容',width:966}	   	      		
	   	          ]]
	 });
	//编辑计划
	$("#btn_editplan").click(function(){
		var  row=$('#tbl_company').datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中要维护招聘计划的企业","info");
			return false;
		}else{
			$("#txt_plan_companyId").val(row.companyId);
			//获取当前招聘计划
			var url=path+"/syscompany/getCurrentSysRecruitPlan"
			var postData={"companyId":row.companyId}
			$.post(url,postData,function(data){
				var mess=eval(data).mess;
				if ("succ"==mess){
					$("#txt_planId").val(eval(data).planId);
					$("#txt_planDesc").textbox("setValue",eval(data).planDesc);
					displanDetail(eval(data).details);
					$("#dialog_plan").dialog("open");
				}else if ("noPlan"){
					$("#txt_planId").val("");
					$("#txt_planDesc").textbox("setValue","");
					displanDetail([]);
					$("#dialog_plan").dialog("open");
				}else{
					$("#txt_planId").val("");
					$("#txt_planDesc").textbox("setValue","");
					$.messager.alert("提示",mess,"info");
					return false;
				}
			});
		}
	});
	$("#btn_addPlanRow").click(function(){
		$('#tbl_planDetail').datagrid("appendRow",{
			detailId:'',
		    stationName:'',
			majorName:'',
			planStu:''
		});
		var rows=$('#tbl_planDetail').datagrid("getRows");
		$('#tbl_planDetail').datagrid("beginEdit",rows.length-1);
	});
	$("#btn_delPlanRow").click(function(){
		var  row=$('#tbl_planDetail').datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中要删除的岗位计划！","info");
			return false;
		}else{
			var index=$('#tbl_planDetail').datagrid("getRowIndex",row);
			$('#tbl_planDetail').datagrid("deleteRow",index);
		}
	});
	
	//查看协议
	$("#btn_seesub").click(function(){
		var  row=$('#tbl_company').datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中要查看的行","info");
			return false;
		}else{
			$("#dialog_sunb").previewPdf({
							width:1024,
							height:530,
							title:"查看协议",
							data:row.protocolPath
		    });
		}
	});
	
	
	
	
	$("#btn_close1").click(function(){
		$("#dialog_plan").dialog("close");
	});
	$("#btn_addDlg").click(function(){
		
		var defaultlogo=path+"/static/images/defaultcompanylogo.jpg";
		$('#pictureimg').attr('src', defaultlogo);
				
		getImgBase64(defaultlogo);
		
		
		
    	$("#dlg_company").dialog("open");
    	$('#btn_save').linkbutton("enable");
  	});

	$("#btn_checkcompany").click(function(){
		var  row=$('#tbl_company').datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中要增加协议的企业","info");
			return false;
		}else{
			$("#txt_checkcompanyId").val(row.companyId);
		}
    	$("#checkcompany").dialog("open");
  	});

	$("#btn_addDlg_sub").click(function(){
		var  row=$('#tbl_company').datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中要增加协议的企业","info");
			return false;
		}else{
			$("#txt_companyIdpdf").val(row.companyId);
		}
    	$("#agreement").dialog("open");
  	});
	$("#btn_close").click(function(){
		$("#btn_save").show();
		clearForm();
   		$("#dlg_company").dialog("close");
  	});

	$("#btn_close1").click(function(){
   		$("#exlimport").dialog("close");
  	});

	//点击提交查询信息
    $("#btn_query").click(function(){
        var queryParams=$('#form_query').serializeJSON();
        $('#tbl_company').datagrid('load',queryParams);
    });

	//增加计划
	$("#btn_saveplan").click(function(){
		var planDesc=$("#txt_planDesc").textbox("getValue");
		if (planDesc==""){
			$.messager.alert("提示","概述内容不能为空！","info",function(){
					$("#txt_planDesc").next("span").find("input[type='text']").focus();
			});
			return false;
		}
		var stationNames="";
		var majorNames="";
		var planStus="";
		var rows=$('#tbl_planDetail').datagrid("getRows");
		for (var i=0;i<rows.length;i++){
			var stationName = $('#tbl_planDetail').datagrid('getEditor', {index:i,field:'stationName'});
			stationName=$(stationName.target).val();
			if (stationName==""){
				var add = i+1;
				$.messager.alert("提示","第"+add+"行的岗位名称不能为空！","info");
				return false;
			}else{
				stationNames+=stationName+"$";
			}
			var majorName = $('#tbl_planDetail').datagrid('getEditor', {index:i,field:'majorName'});
			majorName=$(majorName.target).val();
			if (majorName==""){
				var add = i+1;
				$.messager.alert("提示","第"+add+"行的所需专业名称不能为空！","info");
				return false;
			}else{
				majorNames+=majorName+"$";
			}
			var planStu = $('#tbl_planDetail').datagrid('getEditor', {index:i,field:'planStu'});
			planStu=$(planStu.target).val();
			if (planStu==""){
				var add = i+1;
				$.messager.alert("提示","第"+add+"行的招聘人数不能为空！","info");
				return false;
			}else{
				planStus+=planStu+"$";
			}
		}
		//保存数据
		if(""!=stationNames){
			stationNames=stationNames.substr(0,stationNames.length-1);
			majorNames=majorNames.substr(0,majorNames.length-1);
			planStus=planStus.substr(0,planStus.length-1);
		}
		var planId=$("#txt_planId").val();
		var companyId=$("#txt_plan_companyId").val();
		var postData={
				"companyId":companyId,
				"planDesc":planDesc,
				"planId":planId,
				"stationName":stationNames,
				"majorName":majorNames,
				"planStuString":planStus};
		var url=path+"/syscompany/saveSysPlanDetail";
		$.post(url,postData,function(data){
			if (data=="succ"){
				console.log(postData);
				$.messager.alert("提示","保存成功","info");
				$("#dialog_plan").dialog("close");
			}else if(data=="notrain"){
				$.messager.alert("提示","实训周期未开启，请设置进行中的实训周期。","info");
			}
			else{
				$.messager.alert("提示",data,"info");
			}
		});
		
	});
	
	$("#btn_save").click(function(){
		
		$("#txt_signTime").attr("disabled","true");
		  var comName=$("#txt_comName").textbox("getValue");
		  
		  var file = $("#file").next().find('input[type=file]')[0].files[0];
		  if(null==file){
			  $.messager.alert("提示","企业logo为默认logo","info");
		  }else{
			  var fileName = file.name;
				console.log(fileName);
		        var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
		        if (file_typename == '.jpg'){
		        
		        }else{
		        	$.messager.alert("提示","请上传jpg格式文件类型错误","info");
		        	
		        	return false;
		        }
		  }
		 
		  
		  if (comName==""){
			$.messager.alert("提示","请输入实习单位名称!","info");
			$("#txt_comName").next("span").find("input[type='text']").focus();
			return  false;
		  }else if(comName.length > 30){
			  $.messager.alert("提示","输入的实习单位名称过长!","info");
				$("#txt_comName").next("span").find("input[type='text']").focus();
				return  false;
				  
			  }
		  
	      /*var signTime=$("#txt_signTime").datebox("getValue");
		  if (signTime==""){
			alert("请输入签协议时间!");
			$("#txt_signTime").next("span").find("input[type='text']").focus();
			return  false;
		  }
		  var effeDate=$("#txt_effeDate").textbox("getValue");
		  if (effeDate==""){
			alert("请输入有效期!");
			$("#txt_effeDate").next("span").find("input[type='text']").focus();
			return  false;
		  }*/
	      var industry=$("#txt_industry").textbox("getValue");
		  if (industry==""){
			$.messager.alert("提示","请输入所属行业!","info");
			$("#txt_industry").next("span").find("input[type='text']").focus();
			return  false;
		  }	else if(industry.length > 50){
			  $.messager.alert("提示","输入的所属行业过长!","info");
				$("#txt_industry").next("span").find("input[type='text']").focus();
				return  false;
				  
			  }
		  var schContact=$("#txt_schContact").textbox("getValue");
		  if (schContact==""){
			$.messager.alert("提示","请输入校内联系人!","info");
			$("#txt_schContact").next("span").find("input[type='text']").focus();
			return  false;
		  }else if(schContact.length > 7){
		  $.messager.alert("提示","输入的校内联系人过长!","info");
			$("#txt_schContact").next("span").find("input[type='text']").focus();
			return  false;
			  
		  }
		  var position=$("#txt_position").textbox("getValue");
		  if (position==""){
			$.messager.alert("提示","请输入所任职务!","info");
			$("#txt_position").next("span").find("input[type='text']").focus();
			return  false;
		  }else if(position.length > 10){
			  $.messager.alert("提示","输入的职务过长!","info");
				$("#txt_position").next("span").find("input[type='text']").focus();
				return  false;
				  
			  }
		  
		  var mobile = $("#txt_schContactphone").val();
                        if (mobile && /[1][3,4,5,7,8][0-9]{9}$/.test(mobile)) {
                            if (mobile.length > 11 || mobile.length < 11) {
                                $.messager.alert("提示","请输入11位的手机号码","info");
                                return;
                            }
                        } else {
                            $.messager.alert("提示","请输入正确校内联系人方式!","info");
							$("#txt_schContactphone").next("span").find("input[type='text']").focus();
                            return;
                        }
		  var comcontacts=$("#txt_comcontacts").textbox("getValue");
		  if (comcontacts==""){
			$.messager.alert("提示","请输入企业联系人!","info");
			$("#txt_comcontacts").next("span").find("input[type='text']").focus();
			return  false;
		  }else if(comcontacts.length > 7){
			  $.messager.alert("提示","输入的企业联系人过长!","info");
				$("#txt_comcontacts").next("span").find("input[type='text']").focus();
				return  false;
				  
			  }
		  var mobile = $("#txt_phone").val();
                        if (mobile && /[1][3,4,5,7,8][0-9]{9}$/.test(mobile)) {
                            if (mobile.length > 11 || mobile.length < 11) {
                                $.messager.alert("提示","请输入11位的手机号码","info");
                                return;
                            }
                        } else {
                            $.messager.alert("提示","请输入正确企业联系人方式","info");
							$("#txt_phone").next("span").find("input[type='text']").focus();
                            return;
                        }
	  	  
		  var comAddress=$("#txt_comAddress").textbox("getValue");
		  if (comAddress==""){
			$.messager.alert("提示","请输入实习单位地址!","info");
			$("#txt_comAddress").next("span").find("input[type='text']").focus();
			return  false;
		  }else if(comAddress.length > 50){
			  $.messager.alert("提示","输入的地址过长!","info");
				$("#txt_comAddress").next("span").find("input[type='text']").focus();
				return  false;
				  
			  }
		  $('#btn_save').linkbutton("disable");
		  var txt_companyId=$("#txt_companyId").val();
		  if (txt_companyId==""){
	      var url=path+"/syscompany/saveSysCompany";
	      var postData=$('#form_add').serializeJSON();
	      $.post(url,postData,function(data){
	        if (data=="succ"){
	          $.messager.alert("提示","增加成功","info")
	          $("#dlg_company").dialog("close");
	          $("#btn_query").click();
	          clearForm();
	        }else if(data=="lvNo"){
	        	$.messager.alert("提示","企业等级系统未开启","info");
				  
	        	
	        }/*系统维护中，请稍后重试
	        else if(data=="重复"){
	          $.messager.alert("提示","实习单位重复，请更改后重试","info");
			  $("#txt_comName").next("span").find("input[type='text']").focus();	
	        }*/else{
	        $.messager.alert("提示","实习单位重复，请更改后重试","info");
	        }
	      });
		}else{
			var url=path+"/syscompany/updSysCompany";
			var postData=$('#form_add').serializeJSON();
			$.post(url,postData,function(data){
				if (data=="succ"){
					$.messager.alert("提示","修改成功","info");
					$("#dlg_company").dialog("close");
					$("#btn_query").click();
					$("#form_add").form("reset");
					clearForm();
				}else if(data=="重复"){
					$.messager.alert("提示","实习单位名称重复","info");
				}else{
			        $.messager.alert("提示","系统维护中，请稍后重试","info");
		        }
			});
		}
	});
	
	
	//点击修改
	$("#btn_updDlg").click(function(){
		var  row=$('#tbl_company').datagrid("getSelected");
		
		if (row==null){
			$.messager.alert("提示","请选中要修改的行","info");
			return false;
		}else{
			$('#btn_save').linkbutton("enable");
			$("#txt_companyId").val(row.companyId);
			$('#pictureimg').attr('src', "data:image/jpeg;base64,"+row.logoPath);
			$("#imgString").val(row.logoPath);	
			$("#txt_comName").textbox("setValue",row.comName);
			$("#txt_datasource").textbox("setValue",row.datasource);
			$("#txt_position").textbox("setValue",row.position);
			$("#txt_signTime").datebox("setValue",row.signTime);	
			$("#txt_schContactphone").textbox("setValue",row.schContactphone);
			$("#txt_updUser").textbox("setValue",row.updUser);
			$("#txt_updDate").datebox("setValue",row.updDateVal);
			$("#txt_effeDate").textbox("setValue",row.effeDate);
			$("#txt_checkstate").combobox("setValue",row.checkstate);
			$("#txt_schContact").textbox("setValue",row.schContact);
			$("#txt_phone").textbox("setValue",row.phone);
			$('#dlg_company').dialog('setTitle', '修改企业信息');
			$("#txt_sign").combobox("setValue",row.sign);
			$("#txt_industry").textbox("setValue",row.industry);
			$("#txt_comcontacts").textbox("setValue",row.comcontacts);
			$("#txt_comAddress").textbox("setValue",row.comAddress);
			$("#txt_updDate").datebox("setValue",row.updDate);
			$("#dlg_company").dialog("open");
		}
		
	});
	//删除
	$("#btn_delDlg").click(function () {
    var row=$("#tbl_company").datagrid("getSelected");
        if (null!=row)
        {
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){
				if (r){
	          		var companyId=row.companyId;
	          		var postData={"companyId":companyId};
	          		$.post((path+"/syscompany/deleteSysCompany"),postData,function (data) {
	              		if ("deletesuccess"==data){
	                  		$.messager.alert("提示","删除成功！","info");
	                  		 $("#btn_query").click();
	              		}else{
							$.messager.alert("提示","该实习基地正在使用!","info");
				  		}
	                  
	          		});
				}
			});
        }
        else
        {
            $.messager.alert("提示","请选中一行！","info");
			return false;
        }
    });
	
	//点击导入
	$("#btn_inputDlg").click(function(){
		 $("#exlimport").dialog("open");		
		 $('#upload').filebox('clear');
	});
	
	//查看字段
	$("#btn_seeDlg").click(function(){
		//$("#btn_save").hide();
		var  row=$('#tbl_company').datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中需要查看的单位","info");
			return false;
		}else{
			$("#txt_companyId").val(row.companyId);
			$("#imgString").val(row.logoPath);	
			$("#txt_comName1").textbox("setValue",row.comName);
			$("#txt_datasource1").textbox("setValue",row.datasource);
			$("#txt_position1").textbox("setValue",row.position);	
			$("#txt_signTime1").textbox("setValue",row.signTime);	
			$("#txt_schContactphone1").textbox("setValue",row.schContactphone);
			$("#txt_updUser1").textbox("setValue",row.updUser);
			$("#txt_updDate1").textbox("setValue",row.updDateVal);
			$("#txt_effeDate1").textbox("setValue",row.effeDate);
			$("#txt_checkstate1").textbox("setValue",row.checkstate);
			$("#txt_schContact1").textbox("setValue",row.schContact);
			$("#txt_phone1").textbox("setValue",row.phone);
			$("#txt_createUser1").textbox("setValue",row.createUser);
			$("#txt_createDate1").textbox("setValue",row.createDateVal);
			$("#txt_sign1").textbox("setValue",row.sign);
			$("#txt_industry1").textbox("setValue",row.industry);
			$("#txt_comcontacts1").textbox("setValue",row.comcontacts);
			$("#txt_comAddress1").textbox("setValue",row.comAddress);
			$("#txt_updDate1").textbox("setValue",row.updDate);
			$("#pictureimg1").attr('src', "data:image/jpeg;base64,"+row.logoPath);
			$("#dialog_compnytinfo").dialog("open");
			$("#dialog_compnytinfo").dialog("setTitle","查看"+row.comName+"的信息");
			//see();
		}
	});
	
	//联动禁用日期框
	$("#txt_sign").combobox({ 
		    valueField: 'value',
		    textField: 'label' ,
		    onSelect: function (data) {   
				var ai =data.label;
				if(ai =='否'){
					$('#txt_effeDate').textbox({
						disabled:true
					});
					$('#txt_signTime').datebox({
						disabled:true
					});
					
				}else{
					$('#txt_signTime').datebox({
						disabled:false
					});
					$('#txt_effeDate').textbox({
						disabled:false
					});
				}
		 }  

	});

	$.extend($.fn.validatebox.defaults.rules, {
		// filebox验证文件大小的规则函数
		// 如：validType : ['fileSize[1,"MB"]']
		fileSize : {
			validator : function(value, array) {
				var size = array[0];
				var unit = array[1];
				//var unit = "MB";
				if (!size || isNaN(size) || size == 0) {
					$.error('验证文件大小的值不能为 "' + size + '"');
				} else if (!unit) {
					$.error('请指定验证文件大小的单位');
				}
				var index = -1;
				var unitArr = new Array("bytes", "kb", "mb", "gb", "tb", "pb", "eb", "zb", "yb");
				for (var i = 0; i < unitArr.length; i++) {
					if (unitArr[i] == unit.toLowerCase()) {
						index = i;
						break;
					}
				}
				if (index == -1) {
					$.error('请指定正确的验证文件大小的单位：["bytes", "kb", "mb", "gb", "tb", "pb", "eb", "zb", "yb"]');
				}
				// 转换为bytes公式
				var formula = 1;
				while (index > 0) {
					formula = formula * 1024;
					index--;
				}
				console.log(formula);
				// this为页面上能看到文件名称的文本框，而非真实的file
				// $(this).next()是file元素
				return $(this).next().get(0).files[0].size < parseFloat(size) * formula;
			},
			//message : '文件大小必须小于 {0} MB'
			message : '文件大小必须小于 {0}{1}'
		}
	});
	
	
	
	
	$("#aaaa").click(function(){
		
		var file = $("#upload_sub").next().find('input[type=file]')[0].files[0];
	        console.log(file);
	        var effeDate = $("#txt_effeDate").textbox("getValue");
	        if (effeDate=="") {
				$.messager.alert("提示","请输入有效期！","info",function (){
				$("#txt_effeDate").next("span").find("input[type='text']").focus();
			});
				return false;
				}
			var signTime = $("#txt_signTime").datebox("getValue");
			if (signTime=="") {
				$.messager.alert("提示","请选择签署协议时间！","info",function (){
				$("#txt_signTime").next("span").find("a").click();
			});
				return false;
				}
			else if (file == null)
	        {
	        	$.messager.alert("提示",'错误，请选择文件',"info");
	        	return;
	        }
	        var fileName = file.name;
			console.log(fileName);
	        var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
			if (file_typename == '.pdf'){
				
				//计算文件大小
	            var fileSize = 0;
	            //如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB
	            if (file.size > 1024 * 1024) {
	            	fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;

	            		if (fileSize > 5) {
	            			$.messager.alert("提示",'错误，文件超过5MB，禁止上传！',"info");
	                     return;
	                }
					fileSize = fileSize.toString() + 'MB';
	            }
	            else {
	                fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
	            }
				
				ajaxFileUploadSub(file);
				$.messager.alert("提示",'上传成功',"info",function(){
	       			$("#btn_query").click();
				});
		       	$("#agreement").dialog("close");   
	        }else{
	        	$.messager.alert("提示","文件类型错误","info");
	        	$("#agreement").dialog("close");
	        } 
		
		
		});
		
		function ajaxFileUploadSub(file) {
			var formData = new FormData($('#form_addpdf')[0]);
	        /*formData.append("file", file);
			formData.append("saveform",postData);*/
	        $.ajax({
	            url : path+ "/syscompany/updSysCompanyPdf",
	            type : "post",
	            async : false,
				cache:false,
	            data : formData,
	            processData : false,
	            contentType : false,
	            beforeSend : function() {
	                console.log("正在进行，请稍候");
	            },
				success : function(data) {
	            	 var thisData=eval(data);   
	                 console.log(thisData);
						for(var key in thisData){
								if(key=="code"){        			 
									// $("#pictureimg").attr("src","data:image/jpeg;base64,"+thisData[key]);         			 
				           		 }else{
				           			
				           		 } 
								if(key=="backmess"){
										if (thisData[key]=="succ"){
								        	$.messager.alert("提示","增加成功","info")
								        	$("#addgoods").dialog("close");
								        	$("#btn_query").click();
								        	clearForm();
								        }else{
								          $.messager.alert("提示","增加失败请更改后重试","info");
										  $("#txt_brandName").next("span").find("input[type='text']").focus();	
								        } 
								} 
							
						}
	            },
	            dataType : "json"
	        });
		}
		
		
	//点击上传按钮导入文件
	$("#bbbbb").click(function(){
		
        var file = $("#upload").next().find('input[type=file]')[0].files[0];
        console.log(file);
        if (file == null)
        {
        	$.messager.alert("提示",'错误，请选择文件',"info");
        	return;
        }
        var fileName = file.name;
        var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
        if (file_typename == '.xlsx')
        {
			 
        	ajaxFileUpload(file);
        	$("#exlimport").dialog("close");      	
        }
        else
        {
        	$.messager.alert("提示","文件类型错误","info");
        	$("#exlimport").dialog("close");
        }      
	});
	//上传文件函数
    function ajaxFileUpload(file) {
        var formData = new FormData();
        formData.append("file", file);

        $.ajax({
            url : path+ "/syscompany/importCustomerList",
            type : "post",
            async : false,
			cache:false,
            data : formData,
            processData : false,
            contentType : false,
            beforeSend : function() {
            	
            	
            	
            },
            success : function(data) {
            	
            	$("#importDialog").dialog("open");
            	 var thisData=eval(data);   
                 var errorCount=0;
                 var totalCount=0;
                 var errorMsg="";
                 
                 
           	 for(var key in thisData){
           		 if(key=="total"){        			 
           			totalCount=thisData[key];           			 
           		 }else{
           			errorMsg="第"+key+"行"+thisData[key]+"<br/>";
           			errorCount++;
           			$('#dg').datagrid('appendRow',{error: errorMsg,});
           		 }                   
      	
          }
           if(errorCount==0){
        	   $.messager.alert("导入情况","总共导入"+totalCount+"条数据<br/>"+
             			 "导入成功的数据有"+(totalCount)+"条<br/>"/*+
             			 "导入失败的数据有"+(errorCount)+"条<br/>"*/
             			 );
        	   $("#importDialog").dialog("close");
        	   $("#exlimport").dialog("close");
        	   $("#btn_query").click();
        	   
           }else{
        	   $.messager.alert("导入情况","总共导入"+totalCount+"条数据<br/>"+
             			 "导入成功的数据有0条<br/>"+
             			 "导入失败的数据有"+(errorCount)+"条<br/>"
             			 );
           }   	
           
                
            },
            dataType : "json"
        });
    }
	
    
    $("#checkcom_yes").click(function(){
    	
	      var url=path+"/syscompany/checkSysCom";
	      var postData=$('#form_checkcompany').serializeJSON();
	      $.post(url,postData,function(data){
	        if (data=="succ"){
	          $.messager.alert("提示","增加成功","info")
	          $("#checkcompany").dialog("close");
	          $("#btn_query").click();
	          
	        }else{
	        $.messager.alert("提示","系统维护中，请稍后重试","info");
	        }
	      });
		  
    });

	//清空表单功能
	function clearForm(){
		$("#txt_companyId").val("");
		$("#imgString").val("");	
		$("#txt_comName").textbox("setValue","");
		$("#txt_datasource").textbox("setValue","");
		$("#txt_position").textbox("setValue","");	
		$("#txt_signTime").datebox("setValue","");	
		$("#txt_schContactphone").textbox("setValue","");
		$("#txt_updUser").textbox("setValue","");
		$("#txt_updDate").datebox("setValue","");
		$("#txt_effeDate").textbox("setValue","");
		$("#txt_checkstate").combobox("setValue","已审批");
		$("#txt_schContact").textbox("setValue","");
		$("#txt_phone").textbox("setValue","");
		$("#txt_createUser").textbox("setValue","");
		$("#txt_createDate").datebox("setValue","");
		$("#txt_industry").textbox("setValue","");
		$("#txt_comcontacts").textbox("setValue","");
		$("#txt_sign").combobox("setValue","是");
		$("#txt_comAddress").textbox("setValue","");
		$('#pictureimg').attr('src', "");
		$('#dlg_company').dialog('setTitle', '增加企业');
	}
	
	function company(){
	    var url=path+"/syscompany/getSysCompanyByCon";
	    $("#tbl_company").datagrid({
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
            pageList:[10,20,50],
            pageNumber:1,
            columns:[[
		      {field:"comName",title:"实习单位名称",width:100},
		      {field:"addupScore",title:"积分",width:60},
		      {field:"currentLvl",title:"企业等级",width:85},
		      {field:'datasource',title:'数据来源',width:150},
		      {field:'industry',title:'所属行业',width:300},
		  	  {field:'comcontacts',title:'企业联系人',width:100},
			  {field:'phone',title:'联系方式',width:80},		  
			  {field:'signTime',title:'签约时间',width:80},
		      {field:'comAddress',title:'实习单位地址',width:150},
		      {field:'checkstate',title:'审核状态',width:80,formatter:function(value,row,index){
                  if (row.checkstate=="已审批")
                  {
                      return row.checkstate;
                  }
                  else
                  {
                      return "未审批";
                  }
               }},
			  {field:'sign',title:'签署协议',width:80,formatter:function(value,row,index){
                  if (row.sign=="是")
                  {
                      return row.sign;
                  }
                  else
                  {
                      return "否";
                  }
               }}
		      ]],
		    onClickRow: function (index, row) { 
				var sign = row.sign;
				
		    }
	    })
	}
	
	function displanDetail(details){
		if (null==planDetailObj){
			planDetailObj=$('#tbl_planDetail').datagrid({
			    title:'招聘详情',
			    toolbar:'#div_btnaddplan',
			    border:false,
			    striped:true,
			    rownumbers:true,
			    autoRowHeight:false,
			    singleSelect:true,
			    checkOnSelect:true,
			    selectOnCheck:true,
			    fitColumns:true,
			    fit:true,
			    loadMsg:'正在加载，请稍后...',
			   	data:details,
			    columns:[[
			        {field:'detailId',checkbox:true},
			        {field:'stationName',title:'招聘岗位',width:120,editor:{
					     type:"text"}},
			        {field:'majorName',title:'所需专业',width:180,editor:{
					     type:"text"}},
			        {field:'planStu',title:'招聘人数',width:50,editor:{
					     type:"numberbox"}}
			    ]],
			    onLoadSuccess:function (data) {
					if (null==details||null==data||data.total==0){
						$(this).datagrid("appendRow",{
												detailId:'',
											    stationName:'',
												majorName:'',
												planStu:''
											});
					}
					for(var i=0;i<data.total;i++){
	                   	$(this).datagrid("beginEdit",i);
	                }
	                
	            }
	
			});
		}else{
			$('#tbl_planDetail').datagrid("loadData",details);
		}
	}
	function image2Base64(img) {
	    var canvas = document.createElement("canvas");
	    canvas.width = img.width;
	    canvas.height = img.height;
	    var ctx = canvas.getContext("2d");
	    ctx.drawImage(img, 0, 0, img.width, img.height);
	    var dataURL = canvas.toDataURL("image/jpeg");
	    return dataURL;
	}

	function getImgBase64(defaultlogo){
	    var base64="";
	    var img = new Image();
	    img.src=defaultlogo;
	    img.onload=function(){
	        base64 = image2Base64(img);
	        console.log(base64);
	        var imgStr=base64.replace('data:image/jpeg;base64,','');
	        $("#imgString").val(imgStr);
	    }
	    return base64;
	}
	/**
	 * @author tomset
	 * 点击按钮打开招聘计划查看对话框
	 */
	$("#btn_allPlan").click(function(){
	    var  row=$('#tbl_company').datagrid("getSelected");
	    if (row==null){
	    	$.messager.alert("提示","请选中要查看的单位！","info");
	    	return false;
	    }else{
	    	var companyId = row.companyId;
	    	$("#tbl_allPlan").datagrid({
	    		loadMsg: "加载数据中......",
	    		url: path+'/syscompany/getallplan',
		        queryParams:{"companyId" : companyId},
		        border: false,
		        striped: true,
		        fit: true,
		        rownumbers: true,
		        autoRowHeight: false,
		        singleSelect: true,
		        fitColumns: true,
		        columns: [[
		        	{field:'startschoolyear',title:'学年度',width:120,fixed:true,align:'center',
		        		formatter:function(value, row, index){
		        			if(row && row.cycle) {
		        				return row.cycle.startschoolyear+" - "+row.cycle.endschoolyear;
		        			}}
		        	},
	        		{field:'planDesc',title:'招聘摘要',width:80,
	        			formatter: function(value,row,index){  
	        				return "<span title="+value+">"+value+"</span>";  
	        			}},
		        ]],
		        view: detailview, 
		        detailFormatter:function(index,row){//严重注意喔
		        	return "<div><table id='ddv-" + index + "' ></table></div>"; 
		        }, 
		        onExpandRow: function(index,row){//嵌套第一层，严重注意喔
		        	var flag_resize = 0;
		        	if(null!=row && null!=row.details && 0!=row.details.length) {
		        		var ddv = $(this).datagrid('getRowDetail',index).find('#ddv-'+index);//严重注意喔 
		        		ddv.datagrid({ 
		        			data:row.details,
		        			border: false,
		        			autoRowHeight:true,
		        			rownumbers: true,
		        			fitColumns:true,//改变横向滚动条 
		        			singleSelect:true,//去掉选中效果 
		        			loadMsg:'', 
		        			width: '100%',
		        			height:'auto', 
		        			scrollbarSize:0,
		        			columns:[[
		        				{field: "stationName",title: "岗位名称",width: 100,
		        					formatter: function(value,row,index){  
		        						return "<span title="+value+">"+value+"</span>";  
		        					}},
		        				{field: "majorName",title: "所需专业",width: 200,
	        						formatter: function(value,row,index){  
	        							return "<span title="+value+">"+value+"</span>";  
	        						}},
        						{field: "planStu",title: "计划招聘人数",width: 100,fixed:true,align:'center'},
        						{field: "actualStu",title: "已招聘人数",width: 100,fixed:true,align:'center'},
        					]],
        					onLoadSuccess:function(){
        						setTimeout(function(){
        							$('#tbl_allPlan').datagrid('fixDetailRowHeight', index);
        						},0);
        					},
		        		});
		        		$('#tbl_allPlan').datagrid('fixDetailRowHeight', index);
		        	}
		        },
		        onBeforeSelect:function(){
		        	return false;
		        },
		        onLoadSuccess:function(data) {
		        	$("#tbl_allPlan").datagrid("expandRow",0);
		        }
	    	});
	    	$("#dlg_allPlan").dialog("open");
	    }
	});
	
	/**
	 * @author tomset
	 * 点击按钮更新企业的等级
	 */
	$("#btn_updLvl").click(function(){
		var row = $("#tbl_company").datagrid("getSelected");
		//检查是否选中数据
		if (row == null) {
			$.messager.alert("提示", "请选中要更新等级的单位!", "info");
		} else {
			$.messager.confirm("确认", "您确认更新该单位等级吗？", function (r) {
				if (r) {
					var companyId = row.companyId;
					var postData = {"companyId": companyId};
					var url = path + "/syscompany/updCompanyLvl";
					$.post(url, postData, function (data) {
						if (null != data) {
							if (data.tip != "succ") {
								$.messager.alert("错误", data.tip, "error");
							}
							else {
								$.messager.alert("提示", "更新成功！", "info", function () {
									company();
								});
							}
						} else {
							$.messager.alert("错误", "未知错误！", "error");
						}
					});
				}
			});
		}
	});
	
});