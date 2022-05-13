$(document).ready(function(){
	//获取上下文
	var path=getRootPath();
	
	
	
	$("#addcompany").click(function(){
		  
		     

		        
		var defaultlogo=path+"/static/images/defaultcompanylogo.jpg";
		$('#pictureimg').attr('src', defaultlogo);
				
		getImgBase64(defaultlogo);
		
		
		
    	$("#dlg_company").dialog("open");
		           
		 	});
	
	$("#btn_closeaddcompany").click(function(){
		$("#btn_saveaddcompany").show();
		clearForm();
   		$("#dlg_company").dialog("close");
  	});
	

	
$("#btn_saveaddcompany").click(function(){
		
		$("#txt_signTime").attr("disabled","true");
		  var comName=$("#txt_comName").textbox("getValue");
		  
		  var file = $("#logofile").next().find('input[type=file]')[0].files[0];
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
	          $.messager.alert("提示","增加成功,请等待企业通过审核","info")
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
	
	function image2Base64(img) {
	    var canvas = document.createElement("canvas");
	    canvas.width = img.width;
	    canvas.height = img.height;
	    var ctx = canvas.getContext("2d");
	    ctx.drawImage(img, 0, 0, img.width, img.height);
	    var dataURL = canvas.toDataURL("image/jpeg");
	    return dataURL;
	}
	
	//清空表单功能
	function clearForm(){
		$("#txt_companyId").val("");
		$("#imgString").val("");	
		$("#txt_comName").textbox("setValue","");
		
		$("#txt_position").textbox("setValue","");	
	
		$("#txt_schContactphone").textbox("setValue","");
		$("#txt_updUser").textbox("setValue","");
		$("#txt_updDate").datebox("setValue","");
	
	
		$("#txt_schContact").textbox("setValue","");
		$("#txt_phone").textbox("setValue","");
		$("#txt_createUser").textbox("setValue","");
		$("#txt_createDate").datebox("setValue","");
		$("#txt_industry").textbox("setValue","");
		$("#txt_comcontacts").textbox("setValue","");
		
		$("#txt_comAddress").textbox("setValue","");
		$('#pictureimg').attr('src', "");
		$('#dlg_company').dialog('setTitle', '增加企业');
	}
	
	
		

})
