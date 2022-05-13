$(document).ready(function(){
	var path=getRootPath();
	
	
	//初始化
	initAuthGrant_detail();
	function initAuthGrant_detail(){
		var url=path+"/grant/queryAuthGrant";
		$('#tbl_grant_detail').datagrid({
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
		    	{field:'grantCode',title:'权限编号',width:40},
				{field:'grantName',title:'权限名称',width:40},
				{field:'parentGrantCode',title:'父级权限编号',width:40},
				{field:'parentGrantName',title:'父级权限名称',width:40},
				{field:'belongSys',title:'所属系统',width:40},
				{field:'grantLvl',title:'权限级别',width:40}
				
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
		    onLoadSuccess:function(data){
		    	//alert(data);
		    }
		});
	}
	
	//查询中所属系统的下拉列表
	$("#txt_belongSys").combobox({
		valueField: 'dictKey',
		textField: 'dictKey',
		url:path+'/sysset/getBelongSys',
	});
	
	/**
	 * 增加
	 */
	$("#btn_search").click(function(){
		$("#btn_search").linkbutton('disable');
		var queryParams=$('#queryForm').serializeJSON();
		queryParams.onLoadSuccess=function(data){
			$("#btn_search").linkbutton('enable');
		};
		$('#tbl_grant_detail').datagrid('load',queryParams);
	});
	     
	
	$("#btn_add").click(function(){
		$('#dlg_save').dialog('open');
//  清空所有选项
		$("#add_grantName").textbox('clear');
	    $("#add_grantCode1").textbox('clear');
		$("#add_grantCode2").textbox('clear');
		$("#add_grantCode3").textbox('clear');
		$("#add_grantCode4").textbox('clear');
		$('#add_parentGrantCode').combobox('clear');
		$('#add_belongSys').combobox('clear');
		$('#add_grantLvl').combobox('clear');
	
	});
	$("#btn_cancel").click(function(){
		$('#dlg_save').dialog('close');
	});
	
	
	// 添加的所属系统下拉列表
	$("#add_belongSys").combobox({
		valueField: 'dictValue1',
		textField: 'dictKey',
		url:path+'/sysset/getBelongSys',
		onChange:function(){
			$("#add_grantName").textbox('clear');
			$("#add_grantCode1").textbox('clear');
			$("#add_grantCode2").textbox('clear');
			$("#add_grantCode3").textbox('clear');
			$("#add_grantCode4").textbox('clear');
			
			$('#add_grantName').textbox('textbox').attr('readonly',false);  //设置输入框为禁用
			$('#add_grantCode1').textbox('textbox').attr('readonly',false);  //设置输入框为禁用
			$('#add_grantCode2').textbox('textbox').attr('readonly',false);  //设置输入框为禁用
			$('#add_grantCode3').textbox('textbox').attr('readonly',false);  //设置输入框为禁用
			$('#add_grantCode4').textbox('textbox').attr('readonly',false);  //设置输入框为禁用
			
			$('#add_grantLvl').combobox('clear');
			$('#add_parentGrantCode').combobox('clear');
			
		}
	});
	
	//获取父级权限编号
	function initUpParentGrantCode(parentLvl,belongSys){
		url = path + "/grant/getUpParentGrantCode";
		var postData={"parentLvl":parentLvl,"belongSys":belongSys};
		$.post(url, postData, function(data) {
			var lsauth = eval(data).rows;
			$("#add_parentGrantCode").combobox('loadData',lsauth);
		});
	}

	
	
	// 添加的权限级别下拉列表
	$("#add_grantLvl").combobox({
		valueField: 'label',
		textField: 'value',
		data: [ {label: '1',value: '1'},
			    {label: '2',value: '2'},
			    {label: '3',value: '3'},
				{label: '4',value: '4'}],
				onSelect: function(record){
					if(record!=null){
						var parentLvl=Number(record.value);
						if(parentLvl==1){
							
							$('#add_parentGrantCode').combobox('clear');
							$('#add_parentGrantCode').combobox('disable');
							$("#add_grantCode1").textbox('setValue',$("#add_belongSys").combobox('getValue'));
							$("#add_grantName").textbox('setValue',$("#add_belongSys").combobox('getText'));
						    $('#add_grantName').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
							$('#add_grantCode1').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
							$('#add_grantCode2').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
						    $('#add_grantCode3').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
							$('#add_grantCode4').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
						}else {
							$("#add_grantName").textbox('clear');
							parentLvl=parentLvl-1;
							
							$('#add_parentGrantCode').combobox('enable');
							$('#add_parentGrantCode').combobox('clear');
							initUpParentGrantCode(parentLvl,$("#add_belongSys").combobox('getText'));
						}
					}
				}
	});
	
	//当父级权限选择后自动生成部分权限编号
	$("#add_parentGrantCode").combobox({  
		onChange: function(){ 
			   $("#add_grantName").textbox('clear');
			   $("#add_grantCode1").textbox('clear');
			   $("#add_grantCode2").textbox('clear');
			   $("#add_grantCode3").textbox('clear');
			   $("#add_grantCode4").textbox('clear');
			   
			   
			   $('#add_grantName').textbox('textbox').attr('readonly',false);  //设置输入框为禁用
			   $('#add_grantCode1').textbox('textbox').attr('readonly',false);  //设置输入框为禁用
			   $('#add_grantCode2').textbox('textbox').attr('readonly',false);  //设置输入框为禁用
			   $('#add_grantCode3').textbox('textbox').attr('readonly',false);  //设置输入框为禁用
			   $('#add_grantCode4').textbox('textbox').attr('readonly',false);  //设置输入框为禁用
			   
			   var vParentGrantCode= $('#add_parentGrantCode').combobox('getValue');
//			   if(vParentGrantCode.length==0){
//				   $("#add_grantCode1").textbox('setValue','00');
//				   $("#add_grantName").textbox('setValue','科研管理系统');
//				   $('#add_grantName').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
//				   $('#add_grantCode1').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
//				   $('#add_grantCode2').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
//				   $('#add_grantCode3').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
//				   $('#add_grantCode4').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
//			   }
			   
			   if(vParentGrantCode.length==2){
				   $("#add_grantCode1").textbox('setValue',vParentGrantCode);
				   $('#add_grantCode1').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
				   $('#add_grantCode3').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
				   $('#add_grantCode4').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
			   }
			   if(vParentGrantCode.length==4){
				   $("#add_grantCode1").textbox('setValue',vParentGrantCode.substr(0,2));
				   $("#add_grantCode2").textbox('setValue',vParentGrantCode.substr(2,2));
				   $('#add_grantCode1').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
				   $('#add_grantCode2').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
				   $('#add_grantCode4').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
			   }
			   if(vParentGrantCode.length==6){
				   $("#add_grantCode1").textbox('setValue',vParentGrantCode.substr(0,2));
				   $("#add_grantCode2").textbox('setValue',vParentGrantCode.substr(2,2));
				   $("#add_grantCode3").textbox('setValue',vParentGrantCode.substr(4,2));
				   $('#add_grantCode1').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
				   $('#add_grantCode2').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
				   $('#add_grantCode3').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
			   }
		　　  }
	});


     //权限编号必须只能输入数字
	$("#add_grantCode1").textbox('textbox').bind('keyup', function(e){
		$("#add_grantCode1").textbox('setValue', $(this).val().replace(/\D/g,''));
	});
	$("#add_grantCode2").textbox('textbox').bind('keyup', function(e){
	  $("#add_grantCode2").textbox('setValue', $(this).val().replace(/\D/g,''));
	  var grantCode2=$("#add_grantCode2").textbox('getValue');
	  if(grantCode2.length>2){
		  alert("只能输入2个字符");
		  $("#add_grantCode2").textbox('clear');
	  }
	});
	 $("#add_grantCode3").textbox('textbox').bind('keyup', function(e){
			
		  $("#add_grantCode3").textbox('setValue', $(this).val().replace(/\D/g,''));
		  var grantCode3=$("#add_grantCode3").textbox('getValue');
		  if(grantCode3.length>2){
			  alert("只能输入2个字符");
			  $("#add_grantCode3").textbox('clear');
		  }

		});
	 $("#add_grantCode4").textbox('textbox').bind('keyup', function(e){
		  $("#add_grantCode4").textbox('setValue', $(this).val().replace(/\D/g,''));
		  var grantCode4=$("#add_grantCode4").textbox('getValue');
		  if(grantCode4.length>3){
			  alert("只能输入3个字符");
			  $("#add_grantCode4").textbox('clear');
		  }

		});

     
	
	$("#btn_save").click(function(){
		
		$("#btn_save").linkbutton('disable');
			
		var grantName=$("#add_grantName").textbox('getValue');
		var ngrantName=grantName.replace(/^\s+/,'').replace(/\s+$/,'');
		  
		var vBelongSys= $('#add_belongSys').combobox('getValue');
		var vGrantLvl= $('#add_grantLvl').combobox('getValue');
		var vParentGrantCode= $('#add_parentGrantCode').combobox('getValue');
		if(vGrantLvl==1||vGrantLvl=="1"){
			vParentGrantCode="1";
		}
		  
		  var grantCode1=$("#add_grantCode1").textbox('getValue');
		  var grantCode2=$("#add_grantCode2").textbox('getValue');
		  var grantCode3=$("#add_grantCode3").textbox('getValue');
		  var grantCode4=$("#add_grantCode4").textbox('getValue');
		  var grantCode=grantCode1+grantCode2+grantCode3+grantCode4
		 
		  if(vBelongSys==null||vBelongSys==""){
			  alert("请选择所属系统");
			  $("#btn_save").linkbutton('enable');
			  return false;
		  }else if(vGrantLvl==null||vGrantLvl==""){
			  alert("请选择权限等级");
			  $("#btn_save").linkbutton('enable');
			  return false;
		  }else if(vParentGrantCode==null||vParentGrantCode==""){
			  alert("请选择父级权限编号");
			  $("#btn_save").linkbutton('enable');
			  return false;
		  }else{
			  var objId="#add_grantCode"+vGrantLvl
			  var gNumCode=$(objId).textbox('getValue');
			  if(grantCode.length==2||grantCode.length==4||grantCode.length==6||grantCode.length==9){
				  if(gNumCode==null||gNumCode==""){
					  alert("第"+vGrantLvl+"级权限不能为空");
					  $(objId).textbox().next('span').find('input').focus();
					  $("#btn_save").linkbutton('enable');
					  return false;
					  
				  }else{
					  saveHouMian(ngrantName);
					  
				  }
			  }else{
				  alert("权限编号命名不符合规则，必须位2位4位6位或9位，请重新添加");
				  $("#btn_save").linkbutton('enable');
				  $(str).textbox().next('span').find('input').focus();
				  
				  return false;
			  }
		  }
	
	});
	
	
	function saveHouMian(ngrantName){
		 if(ngrantName==null || ngrantName==""){
			   alert("权限名称必须写");
			   $('#add_grantName').textbox().next('span').find('input').focus();
			   $("#btn_save").linkbutton('enable');
				  return false;
		   }else if(ngrantName.length>50){
			   alert("权限名称不能超过50个字");
			   $('#add_grantName').textbox().next('span').find('input').focus();
			   $("#btn_save").linkbutton('enable');
				  return false;
			   
		   }else{
			 var url=path+"/grant/saveAuthGrant";
				var postData=$("#saveForm").serializeJSON();
				postData["belongSys"]=$("#add_belongSys").combobox('getText');
				$.post(url,postData,function(data){
					var mess=eval(data).mess;
					var total=eval(data).total
					 if(total==-1){
						alert("权限编号已存在，请重新输入！");
						$("#btn_save").linkbutton('enable');
						  return false;
					}else{
					       if ("succ"==mess){
						        alert("保存成功！");
						        $('#tbl_grant_detail').datagrid('load');
						        $("#btn_save").linkbutton('enable');
						        $('#dlg_save').dialog('close');
						        
					       }else{
						        alert(mess);
						        $("#btn_save").linkbutton('enable');
								  return false;
					            }
					}
					
				});
		 }
	}
	
	/*
	 * 删除
	 */
	 $("#btn_del").click(function(){
		    var row = $('#tbl_grant_detail').datagrid('getSelected');
		    $("#btn_del").linkbutton('disable');  //如果点击了修改，修改应变为不可点
		    var url=path+"/grant/delAuthGrant";  
		    var postData=$("#grant_delectForm").serializeJSON();
		    $.post(url,postData,function(data){
		    	if (row==null){
		    		alert("尚未选中任何记录！");
		    	}else{
		    		$("#del_grantId").val(row.grantId);
					$("#grant_btn_del").linkbutton('enable');  //设置修改按钮重新可点
					$("#grant_delect").dialog('open');
					$("#btn_del").linkbutton('enable'); 
		    	}
		    	$("#btn_del").linkbutton('enable');
		    });
	 });
	 $("#grant_btn_delect").click(function(){
		 var url=path+"/grant/delAuthGrant";
			var postData=$("#grant_delectForm").serializeJSON();
			$.post(url,postData,function(data){
			var mess=eval(data).mess;
		   if ("succ"==mess){
		    alert("删除成功！");
		    $('#tbl_grant_detail').datagrid('load');
		    $("#grant_btn_delect").linkbutton('enable');
		    $('#grant_delect').dialog('close');
		    
		   }else{
		    alert(mess);	
		   }
		  });
		 });
	 $("#grant_btn_cancelDel").click(function(){
		 $('#grant_delect').dialog('close');
		 });
	
	
})