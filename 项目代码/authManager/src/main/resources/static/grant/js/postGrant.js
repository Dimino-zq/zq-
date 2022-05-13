/**
 * 
 */
$(document).ready(function() {
	var path = getRootPath();
	// 初始化
	initPostGrant_detail();
	// 查询
	$("#btn_searchpostgrant").click(function() {
		$("#btn_searchpostgrant").linkbutton('disable');
		var queryParams = $('#postGrantForm').serializeJSON();
		queryParams.onLoadSuccess = function(data) {
			$("#btn_searchpostgrant").linkbutton('enable');
		};
		$('#tbl_postGrant_detail').datagrid('load', queryParams);
	});

	// 全选
	$("#btn_allpostgrant").click(function() {
		setGrantTreeStatus('check');
	});

	// 分配权限
	$("#btn_addpostgrant").click(function() {
		var row = $('#tbl_postGrant_detail').datagrid('getSelected');
		$("#btn_addpostgrant").linkbutton('disable');
		var url = path + "/postgrant/saveGrants";
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
			"postId" : row.postId
		};
		$.post(url, postData, function(data) {
			var mess = (data);
			if (mess == "succ") {
				alert("分配成功！");
				$('#tbl_postGrant_detail').datagrid('load');
				$("#btn_addpostgrant").linkbutton('enable');
				$('#dlg_addpostgrant').dialog('close');
			} else {
				alert(mess);
			}

		});
	});

	
	// 全不选
	$("#btn_noallpostgrant").click(function() {
		setGrantTreeStatus('uncheck');
	});
	function setGrantTreeStatus(status){
		var tts = $('#add_grantTree').tree('getRoots');
		for(var i=0 ; i<tts.length ; i++) {
			var node = $('#add_grantTree').tree('find', tts[i]);
			$('#add_grantTree').tree(status, node.target);
		}
	}
	
	// 职务权限分配
	$("#btn_addGrantTree").click(function() {
		var row = $('#tbl_postGrant_detail').datagrid('getSelected');
		if (row == null) {
			alert("尚未选中任何记录！");
		} else {
			//清除上一次的状态
			setGrantTreeStatus('uncheck');
			var grant = row.grants;
			for(var i=0;i<grant.length;i++){
				if (grant[i].grantLvl==4){
					var node = $('#add_grantTree').tree('find', grant[i].grantCode);
					$('#add_grantTree').tree('check', node.target);
				}
			}
			$('#dlg_addpostgrant').dialog('open');
		}
	});

	$("#btn_cancelpostgrant").click(function() {
		$('#dlg_addpostgrant').dialog('close');
	});

	function initPostGrant_detail() {
		var url = path + "/postgrant/queryPostGrant";
		// data-options="border:false,singleSelect:true,fit:true,fitColumns:true,rownumbers:true"
		$('#tbl_postGrant_detail').datagrid({
			border : false,
			singleSelect : true,
			fit : true,
			fitColumns : true,
			rownumbers : true,
			autoRowHeight : false,
			nowrap : true,
			loadMsg : "正在加载，请稍后...",
			striped : true,
			url : url,
			pagination : true,
			pageSize : 10,
			queryParams : $('#postGrantForm').serializeJSON(),
			columns : [ [ {
				field : 'postName',
				title : '职务名称',
				width : 100
			}, {
				field : 'postExplain',
				title : '职务内容',
				width : 100
			}, {
				field : 'parentPost',
				title : '上级职务',
				width : 100,
				formatter : function(val, row, index) {
					if (row.parentPost) {
						return row.parentPost.postName;
					}
				}
			}, {
				field : 'postLvl',
				title : '职务等级',
				width : 100
			}

			] ],
			onBeforeLoad : function(param) { // 这个param就是queryString
				var pageNo = param.page; // 保存下值
				delete param.page; // 删掉
				param.pageNo = pageNo; // 这里就是重新命名了！！！ ,j将page 改为了 pages

				var maxResults = param.rows;
				delete param.rows; // 删掉
				param.maxResults = maxResults; // 这里就是重新命名了！！！ ,j将page 改为了
												// pages

				// param["pageNo"] = param.page;
				// param["maxResults"] = param.rows;
			},
			onLoadSuccess : function(data) {
				// alert(data);
			}
		});
	}

});
