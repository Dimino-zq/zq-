$(document).ready(function(){
	var path=getRootPath();
	//初始化 列表
	initStaff_detail();
	initDeparts();
	initPosts();
	//全选
	$("#btn_allGrant").click(function(){
		var url = path + "/postgrant/getAllGrantCode";
		var postData=$("#addGrantForm").serializeJSON();
		$.post(url, postData, function(data) {
			var grantCode = (data);
			for (var i = 0; i < grantCode.length; i++) {
				var node = $('#add_grantTree').tree('find', grantCode[i]);
				$('#add_grantTree').tree('check', node.target);
			}
	    });
	 });

	//全不选
	$("#btn_noAllGrant").click(function(){
		var url = path + "/postgrant/getAllGrantCode";
		var postData=$("#addGrantForm").serializeJSON();
		$.post(url, postData, function(data) {
			var grantCode = (data);
			for (var i = 0; i < grantCode.length; i++) {
				var node = $('#add_grantTree').tree('find', grantCode[i]);
				$('#add_grantTree').tree('uncheck', node.target);
	        }
	    });
	 }); 
	 //权限

    $("#btn_staffGrant").click(function(){
    	var row = $('#tbl_staff_detail').datagrid('getSelected');    	
    	if (row==null){
    		alert("尚未选中任何记录！");
    	}else{
    		setGrantTreeStatus('uncheck');
    		var grants = row.grants;
    		if (grants.length>0){//已经自定义了权限
    			for(var i=0;i<grants.length;i++){
        			if (grants[i].grantLvl==4){
    					var node = $('#add_grantTree').tree('find', grants[i].grantCode);
						if (node==null){
							//alert(grants[i].grantCode);
						}
    					$('#add_grantTree').tree('check', node.target);
    				}
    			}
    		}else{//取默认权限(职务的权限)
    			var posts=row.posts;
    			for(var i=0;i<posts.length;i++){
    				var grants = posts[i].grants;
    				for(var j=0;j<grants.length;j++){
            			if (grant[j].grantLvl==4){
        					var node = $('#add_grantTree').tree('find', grant[j].grantCode);
        					$('#add_grantTree').tree('check', node.target);
        				}
        			}
    			}
    		}
			$('#dlg_staffGrant').dialog('open');
    	}
    });
    //分配权限
    function setGrantTreeStatus(status){
		var tts = $('#add_grantTree').tree('getRoots');
		for(var i=0 ; i<tts.length ; i++) {
			var node = $('#add_grantTree').tree('find', tts[i]);
			$('#add_grantTree').tree(status, node.target);
		}
	}
	$("#btn_addgrant").click(function(){
		var row = $('#tbl_staff_detail').datagrid('getSelected');
	    $("#btn_addgrant").linkbutton('disable');	
		var url=path+"/sysset/saveGrants";
		var grants=$("#add_grantTree").tree("getChecked", ['checked','indeterminate']);
		var grantCodes="";
		for (var i = 0; i < grants.length; i++) {
			grantCodes += "'" + grants[i].id + "',";
	    }
	    if(grantCodes!=""){
	    	grantCodes=grantCodes.substr(0,grantCodes.length-1);
	    }
		var postData = {
				"grantCode" : grantCodes,
				"staffId" : row.staffId
		};
		$.post(url,postData,function(data){
			var mess=(data);
			if (mess=="succ"){
				alert("分配成功！");
				$('#tbl_staff_detail').datagrid('load');
				$("#btn_addgrant").linkbutton('enable');
				$('#dlg_staffGrant').dialog('close');
			}else{
				alert(mess);
			}
			
		});
	});
    
    $("#btn_cancelgrant").click(function(){
    	var j=$('#add_grantTree').tree('getRoots');
		$('#dlg_staffGrant').dialog('close');
	 });
	
	//整个页面的数据
	function initStaff_detail(){
		var url=path+"/sysset/queryStaff";	
		$('#tbl_staff_detail').datagrid({
			border:false,
			singleSelect:true,
			fit:true,
			fitColumns:true,
			rownumbers:true,
			autoRowHeight:false,
			nowrap:true,
			loadMsg:"正在加载，请稍后...",
			striped:true,
		    url:url,
		    pagination:true,
		    pageSize:10,
		    pageNumber:1,
		    queryParams: $('#queryForm').serializeJSON(),
		    columns:[[
		    	{field:'userCode',title:'工号',width:80},
				{field:'staffName',title:'姓名',width:80},
				{field:'staffTitle',title:'职称',width:80},
				{field:'depart',title:'所属部门',width:80, formatter:function(val, row, index){
					 if(row.depart){
				          return row.depart.departName;
				     }
				}},
				{field:'poststr',title:'职务',width:80},
				{field:'entryDate',title:'入职日期',width:80,formatter:function(val, row, index){
				    if (val != null) {
                        val=val.substr(0,10);
//			            var date = new Date(val);
//			            return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
				    	return val;
			        }
				}},
				{field:'staffPhone',title:'联系方式',width:100},
				{field:'staffEducation',title:'受教育程度',width:80},
				{field:'staffBirthDay',title:'出生日期',width:80,formatter:function(val, row, index){
				    if (val != null) {
				    	val=val.substr(0,10);
				    	return val;
			        }
				}},
				{field:'staffNational',title:'民族',width:60},			
				{field:'staffSex',title:'性别',width:60}	
		    ]],
		    onBeforeLoad:function(param){
		    	var pageNo = param.page; //保存下值
				delete param.page; //删掉
				param.pageNo = pageNo; //这里就是重新命名了！！！ ,j将page 改为了 pages
				var maxResults = param.rows;
				delete param.rows; //删掉
				param.maxResults = maxResults; //这里就是重新命名了！！！ ,j将page 改为了 pages			
		    },
		    onLoadSuccess:function(data){
		    }
		});
	}
	
	//查询操作
	$("#btn_search").click(function(){
		$("#btn_search").linkbutton('disable');
		var queryParams=$('#queryForm').serializeJSON();
		queryParams.onLoadSuccess=function(data){
			$("#btn_search").linkbutton('enable');
		};
		$('#tbl_staff_detail').datagrid('load',queryParams);
	});
	
	//点击增加，打开增加窗口
	$("#btn_add").click(function(){
		$('#dlg_saveStaff').dialog('open');
	});
	
	$("#txt_staffPhone").textbox('textbox').bind('keyup', function(e){
		  $("#txt_staffPhone").textbox('setValue', $(this).val().replace(/\D/g,''));

		});

	//点击确定，进行保存
	$("#btn_save").click(function(){	
		var userCode=$("#txt_userCode").textbox('getValue');
		var userPass=$("#txt_userPass").textbox('getValue');
		var staffName=$("#txt_addStaffName").textbox('getValue');
		var staffPhone=$("#txt_staffPhone").textbox('getValue');
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
		}else if ($("#txt_admin").textbox('getValue')==""){
			alert("用户类型不能为空");
			return false;				
		}else if ($("#txt_parentDepartId").textbox('getValue')==""){
			alert("所属系部处不能为空");
			return false;
		}else if ($("#txt_postIds").combobox('getValues')==""){
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
			var urls=path+"/sysset/saveStaff";
			var postDatas=$("#saveStaff").serialize();
			$.post(urls,postDatas,function(data){
				$("#btn_save").linkbutton('enable');
				var mess=eval(data).mess;
				if ("succ"==mess){
					alert("保存成功！");
				    $("#saveStaff").form('reset');
				    $("#txt_postIds").combobox('clear');
					$('#tbl_staff_detail').datagrid('load');
				    $('#dlg_saveStaff').dialog('close');
				}else if("error"==mess){
				    	alert("工号不能重复");
				    }else{
				    	alert(mess);
				    }
				    $('#btn_save').dialog('open');
				});

			}
		});
	//点击取消，关闭增加窗口
	$("#btn_savecancel").click(function(){
		$('#dlg_saveStaff').dialog('close');
	});

	$("#txt_updStaffPhone").textbox('textbox').bind('keyup', function(e){
		  $("#txt_updStaffPhone").textbox('setValue', $(this).val().replace(/\D/g,''));
		});

	//修改：将修改的内容显示在文本框里
	$("#btn_editStaff").click(function(){
		  var row = $('#tbl_staff_detail').datagrid('getSelected');
		  $("#btn_editStaff").linkbutton('disable');
		  if (row==null){
			  alert("尚未选中任何记录！");
		  }else{
			  $("#txt_updstaffId").val(row.staffId);
			  $("#txt_updUserCode").textbox("setValue",row.userCode);
			  $("#txt_updStaffName").textbox("setValue",row.staffName);
			  $("#txt_updStaffAddr").textbox("setValue",row.staffAddr);
			  $("#txt_updStaffPhone").textbox("setValue",row.staffPhone);
			  $("#txt_updStaffNational").combobox("setValue",row.staffNational);
			  $("#txt_updStaffTitle").combobox("setValue",row.staffTitle);
			  $("#txt_updStaffEducation").combobox("setValue",row.staffEducation);
			  $("#txt_updEntryDate").datebox("setValue",row.entryDate);
              $("#txt_updStaffBirthDay").datebox("setValue",row.staffBirthDay);
		      $("#txt_updPostType").textbox("setValue",row.postType);
              $("#txt_updStaffName").textbox("setValue",row.staffName);
		      if(null!=row.depart.parentDepart){
		    	  //说明是二级部门
		    	  $("#txt_updParentDepartId").textbox("setValue",row.depart.parentDepart.departId);
		    	  $("#txt_updParentDepartId").textbox("setText",row.depart.parentDepart.departName);
		    	  $("#txt_updDepartId").textbox("setValue",row.depart.departId);
		    	  $("#txt_updDepartId").textbox("setText",row.depart.departName);
		      }else{
		    	  //父级为null  说明它就是父级 部门
		    	  $("#txt_updParentDepartId").textbox("setValue",row.depart.departId);
		    	  $("#txt_updParentDepartId").textbox("setText",row.depart.departName);
		    	  $("#txt_updDepartId").textbox("setValue",null);
		    	  $("#txt_updDepartId").textbox("setText",null);
		      }
		      $("#txt_updPostIds").combobox('setValues',row.postId);				 
			  if (row.staffSex=='女'){
				  $('#rdo_staffSexWoMan').radiobutton("check",0);
			  }else if (row.staffSex=='男'){
				  $('#rdo_staffSexMan').radiobutton("check",0);
			  }
			  $('#dlg_updateStaff').dialog('open');    
		  }
		  $("#btn_editStaff").linkbutton('enable');
	});	
	
	//点击确定，进行修改
	$("#btn_update").click(function(){
		var userCode=$("#txt_updUserCode").textbox('getValue');
		var staffName=$("#txt_updStaffName").textbox('getValue');
		var staffPhone=$("#txt_updStaffPhone").textbox('getValue');	
		var staffAddr=$("#txt_updStaffAddr").textbox('getValue');
	 //去空格
		var updUserCode= userCode.replace(/^\s+/,'').replace(/\s+$/,'');
		var updStaffName= staffName.replace(/^\s+/,'').replace(/\s+$/,'');
		var updStaffPhone= staffPhone.replace(/^\s+/,'').replace(/\s+$/,'');	
		var updStaffAddr= staffAddr.replace(/^\s+/,'').replace(/\s+$/,'');
		if (updUserCode.length<=0){
			alert("工号不能为空");
			return false;
		}else if (updStaffName.length<=0){
			alert("姓名不能为空");
			return false;
		}else if (updStaffPhone.length<=0){
			alert("联系方式不能为空");
			return false;
		}else if ($("#txt_updStaffTitle").textbox('getValue')==""){
			alert("职称不能为空");
			return false;
		}else if ($("#txt_updPostType").textbox('getValue')==""){
            alert("职务类型不能为空");
            return false;
        }else if ($("#txt_updStaffEducation").textbox('getValue')==""){
			alert("教育程度不能为空");
			return false;
		}else if ($("#txt_updParentDepartId").textbox('getValue')==""){
			alert("所属系部处不能为空");
			return false;
		}else if ($("#txt_updPostIds").combobox('getValues')==""){
			alert("职务不能为空");
			return false;
		}else if (updUserCode.length>15){
			alert("工号不能过长");
			return false;
		}else if (updStaffName.length>15){
			alert("姓名不能过长");
			return false;	
		}else if(!((/0\d{2,3}-\d{7,8}/.test(staffPhone)) || updStaffPhone.length == 7 || updStaffPhone.length == 8 || (/^1(3|4|5|6|7|8|9)\d{9}$/.test(staffPhone)))){
			alert("联系方式输入有误，请重新输入!");
			return false;
		}else if (updStaffAddr.length>50){
			alert("地址不能过长");
			return false;
		}else{
			$("#btn_update").linkbutton('disable');
		    var url=path+"/sysset/updateStaff";
		    var postData=$("#updateStaff").serialize();
		    $.post(url,postData,function(data){
			    $("#btn_update").linkbutton('enable');
			    var mess=eval(data).mess;
			    if ("succ"==mess){
				    alert("修改成功！");
				    $("#updateStaff").form('reset');
				    $('#tbl_staff_detail').datagrid('load');
				    $('#dlg_updateStaff').dialog('close');
			    }else if("error"==mess){
			    	alert("工号不能重复");
			    }else{
			    	alert(mess);
			    }
			   // $("#btn_update").linkbutton('open');
		    });
		}
	});

	//点击取消，关闭修改窗口
	$("#btn_updatecancel").click(function(){
		$('#dlg_updateStaff').dialog('close');
		});
	
	//删除
	$("#btn_delStaff").click(function(){
	    var row = $('#tbl_staff_detail').datagrid('getSelected');
	    $("#btn_delStaff").linkbutton('disable');
	    var url=path+"/sysset/deleteStaff";
	    var postData=$("#deleteStaff").serializeJSON();
	    if (row==null){
	     alert("尚未选中任何记录！");
	    }else{
	     $("#del_staffId").val(row.staffId);
	     $("#dlg_deleteStaff").dialog('open');
	     }
	    $("#btn_delStaff").linkbutton('enable');
	 });	 
	 $("#btn_delete").click(function(){
		 $("#btn_delStaff").linkbutton('disable');
	     var url=path+"/sysset/deleteStaff";
	     var postData=$("#deleteStaff").serializeJSON();
	     $.post(url,postData,function(data){
	    	 var mess=eval(data).mess;
	         if ("succ"==mess){
	        	 alert("删除成功！");
	        	 $('#tbl_staff_detail').datagrid('load');
	        	 $("#btn_delStaff").linkbutton('enable');
	        	 $('#dlg_deleteStaff').dialog('close');
	        	 }else{
	        		 alert(mess);
	        		 }
	         $("#btn_delStaff").linkbutton('enable');
	         });
	     });
	 $("#btn_deletecancel").click(function(){
		 $('#dlg_deleteStaff').dialog('close');
		 });
	
	//获取职务以及复选框
	function initPosts(){
		url = path + "/postgrant/getAllPost"
		$.post(url, "", function(data) {
			var lsPost = eval(data).posts;
			$("#txt_postIds").combobox({
			    data:lsPost,
			    valueField:'postId',
			    textField:'postName'

			});			
			$("#txt_updPostIds").combobox({
			    data:lsPost,
			    valueField:'postId',
			    textField:'postName'
			  
			});
		});
	}
	
	//获取部门以及复选框
	function initDeparts(){
		url=path + "/sysset/getDepartOne";
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
			$("#txt_selParentDepartId").combobox({
			    data:lsDepart,
			    valueField:'departId',
			    textField:'departName',
			    onSelect: function(record){
			    	var departId=Number(record.departId);
			    	initGetDepart(departId);
				}
			});
			//初始化 修改里的复选框
			$("#txt_updParentDepartId").combobox({
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
		url = path + "/sysset/getDepartTwo";
		var postData={"departId":departId};
		$.post(url, postData, function(data) {
			var departId = eval(data).rows;
			$("#txt_departId").combobox({
			    data:departId,
			    valueField:'departId',
			    textField:'departName'
			});
			$("#txt_selDepartId").combobox({
			    data:departId,
			    valueField:'departId',
			    textField:'departName'
			});
			$("#txt_updDepartId").combobox({
			    data:departId,
			    valueField:'departId',
			    textField:'departName'
			});
		});
	}

});