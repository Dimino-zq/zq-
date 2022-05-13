/**
 * 
 */
$(document).ready(function(){
	var path=getRootPath();
	//初始化
	initDictionary_detail();
	$("#btn_search").click(function(){
		$("#btn_search").linkbutton('disable');
		var queryParams=$('#queryForm').serializeJSON();
		queryParams.onLoadSuccess=function(data){
			$("#btn_search").linkbutton('enable');
		};
		$('#tbl_dictionary_detail').datagrid('load',queryParams);
	});
	
	$("#btn_add").click(function(){
		$('#dlg_save').dialog('open');
	});
	
	$("#btn_save").click(function(){
		$("#btn_save").linkbutton('disable');
		var url=path+"/system/dict!saveDictionary.do";
		var postData=$("#saveForm").serializeJSON();
		$.post(url,postData,function(data){
			var mess=eval(data).mess;
			if ("succ"==mess){
				alert("保存成功！");
				$('#tbl_dictionary_detail').datagrid('load');
				$("#btn_save").linkbutton('enable');
				$('#dlg_save').dialog('close');
			}else{
				alert(mess);
			}
			
		});
	});
	
	$("#btn_edit").click(function(){
		var row = $('#tbl_dictionary_detail').datagrid('getSelected');
		if (row==null){
			alert("尚未选中任何记录！");
		}else{
			$("#upd_dictKey").textbox("setValue",row.dictKey);
			$("#upd_dictId").val(row.dictId);
			$("#upd_dictValue").textbox("setValue",row.dictValue);
			$("#upd_dictValue1").textbox("setValue",row.dictValue1);
			$("#dlg_update").dialog('open');
		}
	});
	$("#btn_cancel").click(function(){
		$('#dlg_save').dialog('close');
	});
	$("#btn_cancelUpd").click(function(){
		$('#dlg_update').dialog('close');
	});
	
	function initDictionary_detail(){
		var url=path+"/sysset/queryDictionary";
		//data-options="border:false,singleSelect:true,fit:true,fitColumns:true,rownumbers:true"
		$('#tbl_dictionary_detail').datagrid({
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
		    pagination:false,
		    queryParams: $('#queryForm').serializeJSON(),
		    columns:[[
				{field:'dictKey',title:'Key',width:100},
				{field:'dictValue',title:'Value',width:100},
				{field:'dictValue1',title:'Value1',width:100},
				{field:'dictType',title:'字典分类',width:100}
		    ]],
		    onLoadSuccess:function(data){
		    	//alert(data);
		    }
		});
	}
	
});
