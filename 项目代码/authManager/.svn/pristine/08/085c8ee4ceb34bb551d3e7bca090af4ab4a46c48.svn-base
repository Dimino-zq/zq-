/**
 * 
 */

$(document).ready(function(){
	var path=getRootPath();
	var gotoSys=null;
	initDeparts();
	initPosts();
	
	$("#txt_StaffPhone").textbox('textbox').bind('keyup', function(e){
		  $("#txt_StaffPhone").textbox('setValue', $(this).val().replace(/\D/g,''));

		});

	//点击确定，进行保存
	$("#btn_save").click(function(){
		var userCode=$("#txt_userCode").textbox('getValue');
		var userPass=$("#txt_userPass").textbox('getValue');
		var staffName=$("#txt_addStaffName").textbox('getValue');
		var staffPhone=$("#txt_StaffPhone").textbox('getValue');
		var staffAddr=$("#txt_staffAddr").textbox('getValue');
		 //去空格
		var addUserCode= userCode.replace(/^\s+/,'').replace(/\s+$/,'');
		var addUserPass= userPass.replace(/^\s+/,'').replace(/\s+$/,'');
		var addStaffName= staffName.replace(/^\s+/,'').replace(/\s+$/,'');
		var addStaffPhone= staffPhone.replace(/^\s+/,'').replace(/\s+$/,'');
		var addStaffAddr= staffAddr.replace(/^\s+/,'').replace(/\s+$/,'');
		if (addUserCode.length<=0){
			alert("工号不能为空");
			return false;
		}else if (addUserPass.length<=0){
			alert("密码不能为空");
			return false;
		}else if (addStaffName.length<=0){
			alert("姓名不能为空");
			return false;
		}else if ($("#txt_postType").textbox('getValue')==""){
            alert("职务类型不能为空");
            return false;
        }else if ($("#txt_parentDepartId").textbox('getValue')==""){
			alert("系部处不能为空");
			return false;
		}else if ($("#txt_staffBirthDay").textbox('getValue')==""){
			alert("出生日期不能为空");
			return false;
		}else if ($("#txt_staffNational").textbox('getValue')==""){
			alert("民族不能为空");
			return false;
		}else if ($("#txt_staffEducation").textbox('getValue')==""){
			alert("教育程度不能为空");
			return false;
		}else if ($("#txt_entryDate").textbox('getValue')==""){
			alert("入职日期不能为空");
			return false;
		}else if ($("#txt_postIds").textbox('getValue')==""){
			alert("职务不能为空");
			return false;
		}else if (addStaffPhone.length<=0){
			alert("联系方式不能为空");
			return false;
		}else if ($("#txt_staffTitle").textbox('getValue')==""){
			alert("职称不能为空");
			return false;
		}else if (addUserCode.length>15){
			alert("工号不能过长");
			return false;
		}else if (addUserPass.length>15){
			alert("密码不能过长");
			return false;
		}else if (addStaffName.length>15){
			alert("姓名不能过长");
			return false;
		}else if(!((/0\d{2,3}-\d{7,8}/.test(staffPhone)) || addStaffPhone.length == 7 || addStaffPhone.length == 8 || (/^1(3|4|5|6|7|8|9)\d{9}$/.test(staffPhone)))){
			alert("联系方式输入有误，请重新输入!");
			return false;
		}else if (addStaffAddr.length>50){
			alert("地址不能过长");
			return false;
		}else{
			$("#btn_save").linkbutton('disable');
		    var urls=path+"/sysset/registerStaff";
		    var postDatas=$("#saveStaff").serialize();
		    $.post(urls,postDatas,function(data){
			    $("#btn_save").linkbutton('enable');
			    var mess=eval(data).mess;
                var toUrl=eval(data).registerSucessToUrl;
			    if ("succ"==mess){
			    	var v=document.getElementById("txt_userCode").value;
			    	var b=document.getElementById("txt_userPass").value;
			    	$("#sp_usercode").text(v);
			    	$("#sp_userpass").text(b);
			    	$("#dlg_tipStaff").dialog("open");
			    }else if("error"==mess){
			    	alert("工号不能重复");
			    }else{
				    alert(mess);
				    }
			    });
		    }
		});
//	});
//	//点击取消，关闭增加窗口
//	$("#btn_savecancel").click(function(){
//		$('#dlg_saveStaff').dialog('close');
//	});
	
	//获取职务以及复选框
	function initPosts(){
		url = path + "/postgrant/getPost"
		$.post(url, "", function(data) {
			var lsPost = eval(data).posts;
			$("#txt_postIds").combobox({
			    data:lsPost,
			    valueField:'postId',
			    textField:'postName'
			});
		});
	}
	
	//获取部门以及复选框
	function initDeparts(){
		url=path + "/sysset/getOneDepart";
		$.post(url,"",function(data){	
			var lsDepart=eval(data).departs;
			$("#txt_parentDepartId").combobox({
			    data:lsDepart,
			    valueField:'departId',
			    textField:'departName',
			    onSelect: function(record){
			    	var departId=Number(record.departId);
			    	initGetDepart(departId);
				}
			});
		});
	}
	function initGetDepart(departId){
		url = path + "/sysset/getTwoDepart";
		var postData={"departId":departId};
		$.post(url, postData, function(data) {
			var departId = eval(data).rows;
			$("#txt_departId").combobox({
			    data:departId,
			    valueField:'departId',
			    textField:'departName'
			});
		});
	}
	
	$("#btn_deletecancel").click(function(){
        $('#dlg_tipStaff').dialog('close');
         window.close();
	});
	
});