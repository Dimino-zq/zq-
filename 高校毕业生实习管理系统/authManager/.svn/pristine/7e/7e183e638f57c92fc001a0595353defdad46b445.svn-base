/**
 * 
 */
$(document).ready(function(){
	var path=getRootPath();
	//初始化
	//initParentMajors();
	initMajor_detail();
	initDept();
	/**
	 * 查询
	 */
	$("#btn_search").click(function(){
		$("#btn_search").linkbutton('disable');
		var queryParams=$('#major_queryForm').serializeJSON();
		queryParams.onLoadSuccess=function(data){
			$("#btn_search").linkbutton('enable');
		};
		$('#tbl_major_detail').datagrid('load',queryParams);
	});
	/**
	 * 点击增加按钮
	 */
	$("#major_btn_add").click(function(){
		$('#major_save').dialog('open');
	});
	/**
	 * 增加
	 */	 
	 
	$("#major_btn_save").click(function(){
		 var majorName=$("#add_majorName").textbox('getValue');
		 var departId=$("#add_departId").combobox('getValue');
		 var majorExplain=$("#add_majorExplain").textbox('getValue');
		 var nmajorName= majorName.replace(/^\s+/,'').replace(/\s+$/,'');

		 if(nmajorName.length<=0){
			 alert("专业名称不能为空，请重新输入!");
			 return false;
		 }else if(null==departId || ""==departId){
			 alert("所属院系不能为空，请重新选择!");
			 return false;
		 }else if(nmajorName.length > 50){
			 alert("专业名字输入过长，请重新输入!");
			 return false;
		 }else if(majorExplain.length > 500){
			 alert("专业说明输入过长，请重新输入!");
			 return false;
		 }else{
//			 $("#major_btn_save").linkbutton('disable');
			var url=path+"/sysset/saveMajor";
			var postData=$("#major_saveForm").serializeJSON();
			$.post(url,postData,function(data){
				$("#major_btn_save").linkbutton('enable');
				var mess=eval(data).mess;
				var total=eval(data).total;
				if(total==-1){
					alert("专业名称已存在！");
				}else if ("succ"==mess){
					alert("保存成功！");
					$("#major_saveForm").form('reset');
					$('#tbl_major_detail').datagrid('load');
					$('#major_save').dialog('close');
				}else{
					alert(mess);
				}
			});
			
		 }
		 
		
	});
	/**
	 * 修改
	 */
	 
	 $("#major_btn_edit").click(function(){
		    var row = $('#tbl_major_detail').datagrid('getSelected');
//		    $("#major_btn_edit").linkbutton('disable');  //如果点击了修改，修改应变为不可点
		    if (row==null){
	    		alert("尚未选中任何记录！");
	    		return false;
	    	}else{
	    		$("#upd_majorId").val(row.majorId);
	    		$("#upd_majorName").textbox("setValue",row.majorName);
	    		if(row.depart)
	    			$("#upd_departId").combobox("setValue",row.depart.departId);
				$("#upd_majorExplain").textbox("setValue",row.majorExplain);
				
				$("#major_btn_edit").linkbutton('enable');  //设置修改按钮重新可点
				$("#major_update").dialog('open');
	    	}
	    	$("#major_btn_edit").linkbutton('enable');
	 });
	 
	 $("#major_btn_update").click(function(){
		 var majorName=$("#upd_majorName").textbox('getValue');
		 var departId=$("#upd_departId").combobox('getValue');
		 var majorExplain=$("#add_majorExplain").textbox('getValue');
		 var nmajorName= majorName.replace(/^\s+/,'').replace(/\s+$/,'');

		 if(nmajorName.length<=0){
			 alert("部门名称不能为空，请重新输入!");
			 return false;
		 }else if(null==departId || ""==departId){
			 alert("所属院系不能为空，请重新选择!");
			 return false;
		 }else if(nmajorName.length > 50){
			 alert("部门名字输入过长，请重新输入!");
			 return false;
		 }else if(majorExplain.length > 500){
			 alert("部门说明输入过长，请重新输入!");
			 return false;
		 }else {
			 var url=path+"/sysset/updateMajor";
			 var postData=$("#major_updateForm").serializeJSON();
			 $.post(url,postData,function(data){
				 var mess=eval(data).mess;
				 var total=eval(data).total;
				 if(total==-1){
						alert("部门名称已存在！");
				 }else if ("succ"==mess){
					 alert("修改成功！");
					 $("#major_updateForm").form('reset');
					 $('#tbl_major_detail').datagrid('load');
					 $("#major_btn_update").linkbutton('enable');
					 $('#major_update').dialog('close');
				 }else{
					 alert(mess);
				 }
			 });
		 }
	 }); 
	 /**
	  * 点击取消关闭页面
	  */
		$("#major_btn_cancel").click(function(){
			$('#major_save').dialog('close');
		});
		$("#major_btn_cancelUpd").click(function(){
			$('#major_update').dialog('close');
		});
		$("#major_btn_cancelDel").click(function(){
			$('#major_delect').dialog('close');
		});
	
		/**
		 * 删除
		 */
		 $("#major_btn_del").click(function(){
			    var row = $('#tbl_major_detail').datagrid('getSelected');
//			    $("#major_btn_del").linkbutton('disable');  //如果点击了修改，修改应变为不可点
			    var url=path+"/sysset/delMajor";  
			    var postData=$("#major_delectForm").serializeJSON();
			    $.post(url,postData,function(data){
			    	if (row==null){
			    		alert("尚未选中任何记录！");
			    	}else{
			    		$("#del_majorId").val(row.majorId);
						$("#major_btn_del").linkbutton('enable');  //设置修改按钮重新可点
						$("#major_delect").dialog('open');
			    	}
			    	$("#major_btn_del").linkbutton('enable');
			    });
		 });
		 $("#major_btn_delect").click(function(){
			 var url=path+"/sysset/delMajor";
				var postData=$("#major_delectForm").serializeJSON();
				$.post(url,postData,function(data){
				var mess=eval(data).mess;
			   if ("succ"==mess){
			    alert("删除成功！");
			    $('#tbl_major_detail').datagrid('load');
			    $("#major_btn_delect").linkbutton('enable');
			    $('#major_delect').dialog('close');
			   }else{
			    alert(mess);
			   }
			  });
			 });
		
		
	function initMajor_detail(){
		var url=path+"/sysset/queryMajor";
		//data-options="border:false,singleSelect:true,fit:true,fitColumns:true,rownumbers:true"
		$('#tbl_major_detail').datagrid({
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
		    queryParams: $('#major_queryForm').serializeJSON(),
		    columns:[[
				{field:'majorName',title:'专业名称',width:100},
				{field:'majorExplain',title:'专业说明',width:100},
				{field:'departName',title:'所属院系',width:100, formatter:function(val, row, index){
					 if(row.depart){
				          return row.depart.departName;
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

		    	$("#major_btn_edit").linkbutton('enable');
		    	$("#major_btn_del").linkbutton('enable');
		    },
		    onLoadSuccess:function(data){
		    	//alert(data);
		    	$("#major_btn_edit").linkbutton('disable');
		    	$("#major_btn_del").linkbutton('disable');
		    }
		});
	}
	
	//获取上级部门名称（修改）
	function initDept(){
		url = path + "/sysset/getDept";
		$.post(url, null, function(data) {
			var lsdepart = data.rows;
			$("#add_departId").combobox({
			    data:lsdepart,
			    valueField:'departId',
			    textField:'departName'
			});
			
			$("#upd_departId").combobox({
			    data:lsdepart,
			    valueField:'departId',
			    textField:'departName'
			});
		});
	}

});




