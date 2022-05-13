/**
 * 
 */

$(document).ready(function(){
	var path=getRootPath();
	//初始化
	initPost_detail();
	initPosts();
	
	$("#btn_search").click(function(){
		$("#btn_search").linkbutton('disable');
		var queryParams=$('#queryForm').serializeJSON();
		queryParams.onLoadSuccess=function(data){
			$("#btn_search").linkbutton('enable');
		};
		$('#tbl_post_detail').datagrid('load',queryParams);
	});
	
	$("#btn_addPost").click(function(){
		$('#dlg_postSave').dialog('open');
	});
	$("#btn_savePost").click(function(){
		var postName=$("#add_postName").textbox('getValue');
		var postLvl=$("#add_postLevel").textbox('getValue');
		var parentPost=$("#txt_postNames").textbox('getValue');
	    var npostName=postName.replace(/^\s+/,'').replace(/\s+$/,'');
	    var apostName=/^[u4e00-u9fa5]{0,11}$/.test(npostName);
	    if(npostName.length<=0){
	        alert("职务名称不能为空!");
	    	
	    }else if(postLvl == ""){
	    	alert("职务等级不能为空!");
	    	
	    }else if(apostName == true){
	    	alert("职务名称必须为汉字!");
	    	
	    }else if(parentPost == "" && postLvl != 1){
	    	alert("上级职务等级除一级职务外其余等级不能为空，请重新输入!");
	    	
	    }else{
	    	$("#btn_savePost").linkbutton('disable');
			var url=path+"/sysset/savePost";
			var postData=$("#saveFormPost").serializeJSON();
			$.post(url,postData,function(data){
				var mess=eval(data).mess;
				var total=eval(data).total;
				if(total==-1){
					alert("职务名称已存在！");
				}else if ("succ"==mess){
					alert("保存成功！");
					$("#saveFormPost").form('reset');
					$("#btn_savePost").linkbutton('enable');
					$('#tbl_post_detail').datagrid('load');
					$('#dlg_postSave').dialog('close');
					$("#saveFormPost")[0].reset();
				}else if("nameError"==mess){
					alert("职务名称不能为空");
				}else{
					alert(mess);
				}
			$('#dlg_postSave').dialog('close');
			$("#btn_savePost").linkbutton('enable');
			});
	    }
		
	});
	
	$("#btn_editPost").click(function(){
		  var row = $('#tbl_post_detail').datagrid('getSelected');
		  $("#btn_editPost").linkbutton('disable');
		  if (row==null){
		   alert("尚未选中任何记录！");
		  }else{
			  if(row.parentPost==null){
				  row.parentPost="";
			  }
		      $("#upd_postName").textbox("setValue",row.postName);
		      $("#upd_postId").val(row.postId);
		      $("#upd_postExplain").textbox("setValue",row.postExplain);
		      if(null!=row.parentPost){
					$("#upd_parentPost").textbox("setValue",row.parentPost.postId);
					$("#upd_parentPost").textbox("setText",row.parentPost.postName);
				}else{
					$("#upd_parentPost").textbox("setValue",null);
				}
		      $("#upd_postLvl").textbox("setValue",row.postLvl);
		      $("#dlg_updatePost").dialog('open');
		  }
		  $("#btn_editPost").linkbutton('enable');
	});
	
	$("#btn_updatePost").click(function(){
		var postId=$("#upd_postId").val();
		var postName=$("#upd_postName").textbox('getValue');
		var postLvl=$("#upd_postLvl").textbox('getValue');
		var parentPost=$("#upd_parentPost").textbox('getValue');
	    var npostName=postName.replace(/^\s+/,'').replace(/\s+$/,'');
		var apostName=/^[u4e00-u9fa5]{0,11}$/.test(npostName);
	    if(npostName.length<=0){
	        alert("职务名称不能为空，请重新输入!");
	    }else if(apostName == true){
	    	alert("职务名称必须为汉字!");
	    	$('#dlg_updatePost').dialog('close');
	    }else if(postId == parentPost){
	    	alert("修改失败!");
	    	$('#dlg_updatePost').dialog('close');
	    }else if(parentPost == "" && postLvl != 1){
	    	alert("上级职务等级除一级职务外其余等级不能为空，请重新输入!");
	    	$('#dlg_updatePost').dialog('close');
	    }else{
	    	$("#btn_updatePost").linkbutton('disable');
			var url=path+"/sysset/updatePost";
			var postData=$("#updateFormPost").serializeJSON();
			$.post(url,postData,function(data){
				var mess=eval(data).mess;
				var total=eval(data).total;
				if(total==-1){
					alert("职务名称已存在！");
				}else if ("succ"==mess){
					alert("修改成功！");
					$('#tbl_post_detail').datagrid('load');
					$("#btn_updatePost").linkbutton('enable');
					$('#dlg_updatePost').dialog('close');
				}else if("nameError"==mess){
					alert("职务名称不能为空");
				}else{
					alert(mess);
				}
				$("#btn_editPost").linkbutton('enable');
			});
	    }
	});
	
	
	$("#btn_cancelPost").click(function(){
		$('#dlg_postSave').dialog('close');
	});
	$("#btn_cancelUpdPost").click(function(){
		$('#dlg_updatePost').dialog('close');
	});
	$("#btn_cancelDelPost").click(function(){
		$('#dlg_deletePost').dialog('close');
	});
	
	$("#btn_delPost").click(function(){
		  var row = $('#tbl_post_detail').datagrid('getSelected');
		  $("#btn_delPost").linkbutton('disable');
		  if (row==null){
		   alert("尚未选中任何记录！");
		  }else{
		   $("#del_postId").val(row.postId);
		   $("#dlg_deletePost").dialog('open');
		  }
		  $("#btn_delPost").linkbutton('enable');
	});
	
	$("#btn_deletePost").click(function(){
		$("#btn_deletePost").linkbutton('disable');
		var url=path+"/sysset/deletePost";
		var postData=$("#deleteForm").serializeJSON();
		$.post(url,postData,function(data){
			var mess=eval(data).mess;
			if ("succ"==mess){
				alert("删除成功！");
				$('#tbl_post_detail').datagrid('load');
				$("#btn_deletePost").linkbutton('enable');
				$('#dlg_deletePost').dialog('close');
			}else{
				alert(mess);
			}
			$("#btn_deletePost").linkbutton('enable');
		});
	});
	
	
	
	
	function initPost_detail(){
		var url=path+"/sysset/queryPost";
		//data-options="border:false,singleSelect:true,fit:true,fitColumns:true,rownumbers:true"
		$('#tbl_post_detail').datagrid({
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
		    columns:[[
				{field:'postName',title:'职务名称',width:100},
				{field:'postExplain',title:'职务简介',width:100},
				{field:'parentPost',title:'上级职务',width:100,formatter:function(val,row,index){
				 if(row.parentPost){
				      return row.parentPost.postName;
				     }
				 }},
				{field:'postLvl',title:'职务等级',width:100}
				
		    ]],
		    onBeforeLoad:function(param){
		    	var pageNo = param.page; 
				delete param.page; 
				param.pageNo = pageNo; 
				
				var maxResults = param.rows;
				delete param.rows; 
				param.maxResults = maxResults; 
		    },
		    onLoadSuccess:function(data){
		    	//alert(data);
		    }
		});
	}
	//获取职务以及复选框
	function initPosts(){
		url = path + "/sysset/getAllPostName"
		$.post(url, "", function(data) {
			var lsPost = eval(data).posts;
			$("#add_postParentPost").combobox({
			    data:lsPost,
			    valueField:'postId',
			    textField:'postName'
			});
			$("#upd_parentPost").combobox({
			    data:lsPost,
			    valueField:'postId',
			    textField:'postName'
			});
		});
	}
	
	$('#add_postLevel').combobox({
		valueField: 'label',
		textField: 'value',
		data: [{
			label: '1',
			value: '1'
		},{
			label: '2',
			value: '2'
		},{
			label: '3',
			value: '3'
		}],
		onSelect: function(record){
			if(record!=null){
				var parentLvl=Number(record.value);
				if(parentLvl==1){
					$('#txt_postNames').combobox('clear');
				}else {
					parentLvl--;
					$('#txt_postNames').combobox('clear');
					initPostsAddName(parentLvl);
				}
			}
		}
	});
	//获取增加职务名称
	function initPostsAddName(parentLvl){
		url = path + "/sysset/getAllPostName";
		var postData={"parentLvl":parentLvl};
		$.post(url, postData, function(data) {
			var lsPost = eval(data).rows;
			$("#txt_postNames").combobox({
			    data:lsPost,
			    valueField:'postId',
			    textField:'postName'
			});
		});
	}
	

		//修改的级别列表
		$('#upd_postLvl').combobox({
			valueField: 'label',
			textField: 'value',
			data: [{
				label: '1',
				value: '1'
			},{
				label: '2',
				value: '2'
			},{
				label: '3',
				value: '3'
			}],
			onSelect: function(record){
				if(record!=null){
					var parentLvl=Number(record.value);
					if(parentLvl==1){
						$('#upd_parentPost').combobox('clear');
						$('#upd_parentPost').combobox('loadData', {});
					}else {
						parentLvl--;
						$('#upd_parentPost').combobox('clear');
						$('#upd_parentPost').combobox('loadData', {});
						initPostsUpdName(parentLvl);
					}
				}
			}
		});
	//获取修改职务名称
	function initPostsUpdName(parentLvl){
		url = path + "/sysset/getAllPostName";
		var postData={"parentLvl":parentLvl};
		$.post(url, postData, function(data) {
			var lsPost = eval(data).rows;
			$("#upd_parentPost").combobox({
			    data:lsPost,
			    valueField:'postId',
			    textField:'postName'
			});
		});
	}
	
});



