/**
 * 
 */
$(document).ready(function(){
	var path=getRootPath();
	//初始化
	//initParentDeparts();
	initDepart_detail();
	/**
	 * 查询
	 */
	$("#btn_search").click(function(){
		$("#btn_search").linkbutton('disable');
		var queryParams=$('#depart_queryForm').serializeJSON();
		queryParams.onLoadSuccess=function(data){
			$("#btn_search").linkbutton('enable');
		};
		$('#tbl_depart_detail').datagrid('load',queryParams);
	});
	/**
	 * 点击增加按钮
	 */
	$("#depart_btn_add").click(function(){
		$('#depart_save').dialog('open');
	});
	/**
	 * 增加
	 */
	
	 $("#add_departPhone").textbox('textbox').bind('keyup', function(e){
			
		  $("#add_departPhone").textbox('setValue', $(this).val().replace(/[^\d\-\d]/g,''));
		  
		});
	 
	 
	 
	$("#depart_btn_save").click(function(){
		 var departCharge=$("#add_departCharge").textbox('getValue');
		 var departAddr=$("#add_departAddr").textbox('getValue');
		 var departName=$("#add_departName").textbox('getValue');
		 var departLevel=$("#add_departLevel").combobox('getValue');
		 var departType=$("#add_departType").combobox('getValue');
		 var departPhone=$("#add_departPhone").textbox('getValue');
		 var parentDepart=$("#add_parentDepart").combobox('getValue');
		 var departExplain=$("#add_departExplain").textbox('getValue');
		 var ndepartName= departName.replace(/^\s+/,'').replace(/\s+$/,'');
		 var ndepartAddr= departAddr.replace(/^\s+/,'').replace(/\s+$/,'');
		 var ndepartPhone= departPhone.replace(/^\s+/,'').replace(/\s+$/,'');
		 var ndepartCharge= departCharge.replace(/^\s+/,'').replace(/\s+$/,'');
		 if(ndepartName.length<=0){
			 alert("部门名称不能为空，请重新输入!");
			 return false;
		 }else if(ndepartAddr.length<=0){
			 alert("部门地址不能为空，请重新输入!");
			 return false;
		 }else if(ndepartPhone.length<=0){
			 alert("部门电话不能为空，请重新输入!");
			 return false;
		 }else if(departType == ""){
			 alert("部门类型不能为空，请重新输入!");
			 return false;
		 }else if(departLevel == ""){
			 alert("部门等级不能为空，请重新输入!");
			 return false;
		 }else if(ndepartCharge.length<=0){
			 alert("负责人不能为空，请重新输入!");
			 return false;
		 }else if(parentDepart == "" && departLevel != 1){
			 alert("上级部门等级除一级部门外其余等级不能为空，请重新输入!");
			 return false;
		 }else if(ndepartName.length > 50){
			 alert("部门名字输入过长，请重新输入!");
			 return false;
		 }else if(ndepartAddr.length > 100){
			 alert("部门地址输入过长，请重新输入!");
			 return false;
		 }else if(!((/0\d{2,3}-\d{7,8}/.test(departPhone)) || ndepartPhone.length == 7 || ndepartPhone.length == 8 || (/^1(3|4|5|6|7|8|9)\d{9}$/.test(departPhone)))){
			 alert("部门电话输入有误，请重新输入!");
			 return false;
		 }else if(departExplain.length > 500){
			 alert("部门说明输入过长，请重新输入!");
			 return false;
		 }else{
//			 $("#depart_btn_save").linkbutton('disable');
			 var url=path+"/sysset/saveDepart";
			var postData=$("#depart_saveForm").serializeJSON();
			$.post(url,postData,function(data){
				$("#depart_btn_save").linkbutton('enable');
				var mess=eval(data).mess;
				var total=eval(data).total;
				if(total==-1){
					alert("部门名称已存在！");
				}else if ("succ"==mess){
					alert("保存成功！");
					$("#depart_saveForm").form('reset');
					$('#tbl_depart_detail').datagrid('load');
					$('#depart_save').dialog('close');
				}else if ("error"==mess){
					alert("已拥有下级部门，请先删除下级部门");
				}else{
					alert(mess);
				}
			});
			
		 }
		 
		
	});
	/**
	 * 修改
	 */
	 $("#upd_departPhone").textbox('textbox').bind('keyup', function(e){
		  $("#upd_departPhone").textbox('setValue', $(this).val().replace(/[^\d\-\d]/g,'')); 
	});
	 
	 $("#depart_btn_edit").click(function(){
		    var row = $('#tbl_depart_detail').datagrid('getSelected');
//		    $("#depart_btn_edit").linkbutton('disable');  //如果点击了修改，修改应变为不可点
		    if (row==null){
	    		alert("尚未选中任何记录！");
	    		return false;
	    	}else{
	    		$("#upd_departId").val(row.departId);
	    		$("#upd_departName").textbox("setValue",row.departName);
	    		$("#upd_departCharge").textbox("setValue",row.departCharge);
	    		$("#upd_departAddr").textbox("setValue",row.departAddr);
	    		$("#upd_departPhone").textbox("setValue",row.departPhone);
				$("#upd_departLevel").textbox("setValue",row.departLevel);
				$("#upd_departType").textbox("setValue",row.departType);
				$("#upd_departExplain").textbox("setValue",row.departExplain);
				if(null!=row.parentDepart){
					$("#upd_parentDepart").textbox("setValue",row.parentDepart.departId);
					$("#upd_parentDepart").textbox("setText",row.parentDepart.departName);
				}else{
					$("#upd_parentDepart").textbox("setValue",null);
				}
				$("#depart_btn_edit").linkbutton('enable');  //设置修改按钮重新可点
				$("#depart_update").dialog('open');
	    	}
	    	$("#depart_btn_edit").linkbutton('enable');
	 });
	 
	 $("#depart_btn_update").click(function(){
		 var departAddr=$("#upd_departAddr").textbox('getValue');
		 var departName=$("#upd_departName").textbox('getValue');
		 var departCharge=$("#upd_departCharge").textbox('getValue');
		 var departLevel=$("#upd_departLevel").combobox('getValue');
		 var departType=$("#upd_departType").combobox('getValue');
		 var parentDepart=$("#upd_parentDepart").combobox('getValue');
		 var departPhone=$("#upd_departPhone").textbox('getValue');
		 var departExplain=$("#upd_departExplain").textbox('getValue');
		 var ndepartName= departName.replace(/^\s+/,'').replace(/\s+$/,'');
		 var ndepartAddr= departAddr.replace(/^\s+/,'').replace(/\s+$/,'');
		 var ndepartPhone= departPhone.replace(/^\s+/,'').replace(/\s+$/,'');
		 var ndepartCharge= departCharge.replace(/^\s+/,'').replace(/\s+$/,'');
		 if(ndepartName.length<=0){
			 alert("部门名称不能为空，请重新输入!");
			 return false;
		 }else if(ndepartAddr.length<=0){
			 alert("部门地址不能为空，请重新输入!");
			 return false;
		 }else if(ndepartCharge.length<=0){
			 alert("负责人不能为空，请重新输入!");
			 return false;
		 }else if(ndepartPhone.length<=0){
			 alert("部门电话不能为空，请重新输入!");
			 return false;
		 }else if(departType == ""){
			 alert("部门类型不能为空，请重新输入!");
			 return false;
		 }else if(departLevel == ""){
			 alert("部门等级不能为空，请重新输入!");
			 return false;
		 }else if(parentDepart == "" && departLevel != 1){
			 alert("上级部门等级除一级部门外其余等级不能为空，请重新输入!");
			 return false;
		 }else if(ndepartName.length > 50){
			 alert("部门名字输入过长，请重新输入!");
			 return false;
		 }else if(ndepartAddr.length > 100){
			 alert("部门地址输入过长，请重新输入!");
			 return false;
		 }else if(!((/0\d{2,3}-\d{7,8}/.test(departPhone)) || ndepartPhone.length == 7 || ndepartPhone.length == 8 || (/^1(3|4|5|6|7|8|9)\d{9}$/.test(departPhone)))){
			 alert("部门电话输入有误，请重新输入!");
			 return false;
		 }else if(departExplain.length > 500){
			 alert("部门说明输入过长，请重新输入!");
			 return false;
		 }else {
			 var url=path+"/sysset/updateDepart";
			 var postData=$("#depart_updateForm").serializeJSON();
			 $.post(url,postData,function(data){
				 var mess=eval(data).mess;
				 var total=eval(data).total;
				 if(total==-1){
						alert("部门名称已存在！");
				 }else if ("succ"==mess){
					 alert("修改成功！");
					 $("#depart_updateForm").form('reset');
					 $('#tbl_depart_detail').datagrid('load');
					 $("#depart_btn_update").linkbutton('enable');
					 $('#depart_update').dialog('close');
				 }else{
					 alert(mess);
				 }
			 });
		 }
	 }); 
	 /**
	  * 点击取消关闭页面
	  */
		$("#depart_btn_cancel").click(function(){
			$('#depart_save').dialog('close');
		});
		$("#depart_btn_cancelUpd").click(function(){
			$('#depart_update').dialog('close');
		});
		$("#depart_btn_cancelDel").click(function(){
			$('#depart_delect').dialog('close');
		});
	
		/**
		 * 删除
		 */
		 $("#depart_btn_del").click(function(){
			    var row = $('#tbl_depart_detail').datagrid('getSelected');
//			    $("#depart_btn_del").linkbutton('disable');  //如果点击了修改，修改应变为不可点
			    var url=path+"/sysset/delDepart";  
			    var postData=$("#depart_delectForm").serializeJSON();
			    $.post(url,postData,function(data){
			    	if (row==null){
			    		alert("尚未选中任何记录！");
			    	}else{
			    		$("#del_departId").val(row.departId);
						$("#depart_btn_del").linkbutton('enable');  //设置修改按钮重新可点
						$("#depart_delect").dialog('open');
			    	}
			    	$("#depart_btn_del").linkbutton('enable');
			    });
		 });
		 $("#depart_btn_delect").click(function(){
			 var url=path+"/sysset/delDepart";
				var postData=$("#depart_delectForm").serializeJSON();
				$.post(url,postData,function(data){
				var mess=eval(data).mess;
			   if ("succ"==mess){
			    alert("删除成功！");
			    $('#tbl_depart_detail').datagrid('load');
			    $("#depart_btn_delect").linkbutton('enable');
			    $('#depart_delect').dialog('close');
			   }else{
			    alert(mess);
			   }
			  });
			 });
		
		
	function initDepart_detail(){
		var url=path+"/sysset/queryDepart";
		//data-options="border:false,singleSelect:true,fit:true,fitColumns:true,rownumbers:true"
		$('#tbl_depart_detail').datagrid({
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
		    queryParams: $('#depart_queryForm').serializeJSON(),
		    columns:[[
				{field:'departName',title:'部门名称',width:100},
				{field:'departCharge',title:'负责人',width:100},
				{field:'departAddr',title:'部门地址',width:100},
				{field:'departPhone',title:'部门电话',width:100},
				{field:'departLevel',title:'部门级别',width:100},
				{field:'departExplain',title:'部门说明',width:100},
				{field:'departType',title:'部门类型',width:100},
				{field:'school',title:'所在学校名称',width:100, formatter:function(val, row, index){
					 if(row.school){
				          return row.school.schoolName;
				     }
				}},
				{field:'parentDepart',title:'父级部门名称',width:100, formatter:function(val, row, index){
					 if(row.parentDepart){
				          return row.parentDepart.departName;
				     }
				}}
		    ]],
		    onBeforeLoad:function(param){  //这个param就是queryString 
		    	var pageNo = param.page; //保存下值
				delete param.page; //删掉
				param.pageNo = pageNo; //这里就是重新命名了！！！ ,j将page 改为了 pages
				var maxResults = param.rows;
				delete param.rows; //删掉
				param.maxResults = maxResults; //这里就是重新命名了！！！ ,j将page 改为了 pages
//				param["pageNo"] = param.page;
//              param["maxResults"] = param.rows;
		    },
		    onClickRow:function(rowIndex, rowData){

		    	$("#depart_btn_edit").linkbutton('enable');
		    	$("#depart_btn_del").linkbutton('enable');
		    },
		    onLoadSuccess:function(data){
		    	//alert(data);
		    	$("#depart_btn_edit").linkbutton('disable');
		    	$("#depart_btn_del").linkbutton('disable');
		    }
		});
	}
	// 添加的类型下拉列表
	$("#add_departType").combobox({
		valueField: 'label',
		textField: 'value',
		data: [{label: '行政',value: '行政'},
				{label: '教学',value: '教学'}]
	});
	// 添加得部门等级
	$("#add_departLevel").combobox({
		valueField: 'label',
		textField: 'value',
		data: [{label: '1',value: '1'},
				{label: '2',value: '2'},
				{label: '3',value: '3'}],
				onSelect: function(record){
					if(record!=null){
						var parentLvl=Number(record.value);
						if(parentLvl==1){
							$('#add_parentDepart').combobox('clear');
						}else {
							parentLvl--;
							$('#add_parentDepart').combobox('clear');
							initUpDepartName(parentLvl);
						}
					}
				}
	});
	//获取上级部门名称
	function initUpDepartName(parentLvl){
		url = path + "/sysset/getUpDepartName";
		var postData={"parentLvl":parentLvl};
		$.post(url, postData, function(data) {
			var lsdepart = eval(data).rows;
			$("#add_parentDepart").combobox({
			    data:lsdepart,
			    valueField:'departId',
			    textField:'departName'
			});
		});
	}
/**
 * 修改页面获取上级部门
 */
	// 修改的类型下拉列表
	$("#upd_departType").combobox({
		valueField: 'label',
		textField: 'value',
		data: [{label: '行政',value: '行政'},
				{label: '教学',value: '教学'}]
	});
	$("#upd_departLevel").combobox({
		valueField: 'label',
		textField: 'value',
		data: [{label: '1',value: '1'},
				{label: '2',value: '2'},
				{label: '3',value: '3'}],
				onSelect: function(record){
					if(record!=null){
						var parentLvl=Number(record.value);
						if(parentLvl==1){
							$('#upd_parentDepart').combobox('clear');
						}else {
							parentLvl--;
							$('#upd_parentDepart').combobox('clear');
							initUpDepartName1(parentLvl);
						}
					}
				}
	});
	//获取上级部门名称（修改）
	function initUpDepartName1(parentLvl){
	url = path + "/sysset/getUpDepartName";
	var postData={"parentLvl":parentLvl};
	$.post(url, postData, function(data) {
		var lsdepart = eval(data).rows;
		$("#upd_parentDepart").combobox({
		    data:lsdepart,
		    valueField:'departId',
		    textField:'departName'
		});
	});
	}
});




