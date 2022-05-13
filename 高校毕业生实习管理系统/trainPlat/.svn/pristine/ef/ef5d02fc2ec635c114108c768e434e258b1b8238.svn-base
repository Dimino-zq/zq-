$(document).ready(function(){
	//获取上下文
	var path=getRootPath();
	
	var departCycle = null;		//存储当前用户所处的实训周期
	getCycle();			//获取实训周期
	//初始化表单
	clearForm();
	//加载数据表格
	initTable();
	//长度校验
	length();
	/**
	 * 创建申请表功能
	 */
	//打开承诺对话框
	$("#btn_addDlg").click(function(){
		//初始化承诺对话框
		$('#dialog_promise').dialog({
			title: '承诺书',
			width: 500,
			height: 240,
			closable:false,
			closed: false,
			resizable:false,
			constrain:true,
			modal: true,
			buttons:[{
				text:'同意',
				handler:function(){
					$('#dialog_promise').dialog("close");
					//打开创建对话框
					openDlg();
				}
			},{
				text:'不同意',
				handler:function(){
					$('#dialog_promise').dialog("close");
				}
			}]
		});
	})
	
	//关闭创建对话框
	$("#btn_close").click(function(){
		$("#dlg_stuApplication").dialog("close");
		clearForm();
	})
	//保存创建的申请表
	function createApplication(postData) {
		//检查表单数据是否符合要求
		if (checkForm(postData)) {
			var url = path + "/studentapp/saveapplication";
			//post提交数据
			$.post(url, postData, function (data) {
				if (data != null) {
					if (data.error != null) {
						$.messager.alert("错误", data.error, "error");
					} else {
						$.messager.alert("提示", data.tip, "info", function () {
							$("#dlg_stuApplication").dialog("close");
							clearForm();
							initTable();
						});
					}
				} else {
					$.messager.alert("错误", "未知错误！", "error");
				}
			});
		} else {
			return;
		}
	}
	
	/**
	 * 查看申请表功能，跳转页面，html形式显示申请表表格
	 */
	$("#btn_allInfo").click(function(){
		var row = $("#tbl_stuApplication").datagrid("getSelected");
		    if (row==null){
		    	$.messager.alert("提示","请选中一行数据!","info");
		    	return false;
		    	}
		    else
		    {
				var applicationId=row.applyId;
				var url=path+"/studentapp/gotohtmlapplication";
				//post跳转网页
				var temp_form = document.createElement("form");
				temp_form .action = url;
				temp_form .target = "_self";
				temp_form .method = "post";
				temp_form .style.display = "none"; 
				var opt = document.createElement("textarea");
				opt.name = "applicationId";
				opt.value = applicationId;
				temp_form .appendChild(opt);
				document.body.appendChild(temp_form);
				temp_form .submit();
		    }
		});
		
	
	
	/**
	 * 修改申请表功能
	 */
	//打开修改对话框
	$("#btn_updDlg").click(function(){
		var row = $("#tbl_stuApplication").datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中一行数据!","info");
			return false;
		}
		else
		{
			$("#input_applyId").val(row.applyId);
			$("#input_insurance").combobox("setValue",row.insurance);
			if (row.company)
				$("#input_company").combobox("setValue",row.company.companyId);
			$("#input_surcomjob").textbox("setValue",row.surcomjob);
			$("#input_surcomcontent").textbox("setValue",row.surcomcontent);
			if(row.applydate>=departCycle.planStartTime)
				$("#input_applydate").datebox("setValue",row.applydate);
			var minDate = null;
			var maxDate = null;
			var lsDate = new Date(row.endDate);
        	$('#input_endDate').datebox('calendar').calendar({
        	    validator: function(date){
        	    	//结束时间应该至少满足实习持续周数要求
        	        var d1 = new Date(row.applydate);
        	        d1.setDate(d1.getDate()+departCycle.weeks*7-1);//开始时间+持续天数
        	        var d2 = new Date(departCycle.planEndTime);
        			minDate = d1;
        			maxDate = d2;
        	        return d1<=date && date<=d2;
        	    }
        	});
        	if(minDate<=lsDate && lsDate<=maxDate)
				$("#input_endDate").datebox("setValue",row.endDate);
        	else
            	$('#input_endDate').datebox('calendar').calendar('moveTo', new Date(departCycle.planEndTime));
			$("#input_teacherName").combobox("setValue",row.teacherName);
			$("#input_teacherNo").val(row.teacherNo);
			$("#input_phoneOrQQ").textbox("setValue",row.phoneOrQQ);
			$("#input_adress").textbox("setValue",row.adress);
			openDlg();
		}
	});

	//保存修改后的申请表
	function updateApplication(postData) {
		if (checkForm(postData)) {
			var url = path + "/studentapp/updateapplication";
			$.post(url, postData, function (data) {
				if (data != null) {
					if (data.error != null)
						$.messager.alert("错误", data.error, "error");
					else {
						$.messager.alert("提示", data.tip, "info", function () {
							$("#dlg_stuApplication").dialog("close");
							clearForm();
							initTable();
						});
					}
				} else
					$.messager.alert("错误", "未知错误！", "error");
			});
		}
	}
	
	/**
	 * 删除未提交的申请表功能
	 */
	//点击删除按钮
	$("#btn_delDlg").click(function () {
		var row = $("#tbl_stuApplication").datagrid("getSelected");
		//检查是否选中数据
		if (row == null) {
			$.messager.alert("提示", "请选中一行数据!", "info");
		} else {
			$.messager.confirm("确认", "您确认想要删除记录吗？", function (r) {
				if (r) {
					var applyId = row.applyId;
					var postData = {"applicationId": applyId};
					var url = path + "/studentapp/deleteapplication";
					$.post(url, postData, function (data) {
						if (null != data) {
							if (data.error != null) {
								$.messager.alert("错误", data.error, "error");
							}
							else {
								$.messager.alert("提示", data.tip, "info", function () {
									initTable();
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

	
	/**
	 * 提交申请表，等待审批
	 */
	//点击提交按钮
	$("#btn_subDlg").click(function () {
		var row = $("#tbl_stuApplication").datagrid("getSelected");
		if (row == null) {
			$.messager.alert("提示", "请选中一行数据!", "info");
			return false;
		} else if (row.filePath == null || row.filePath == "") {
			$.messager.alert("提示", "您还未提交附件！!", "info");
			return false;
		} else {
			$.messager.confirm("确认", "您确认要提交申请吗？一旦提交，在审批结束之前您将无法进行任何操作！", function (r) {
				if (r) {
					var applyId = row.applyId;
					var postData = {"applicationId": applyId};
					var url = path + "/studentapp/submitapplication";
					$.post(url, postData, function (data) {
						if (null != data) {
							if (data.error != null) {
								$.messager.alert("错误", data.error, "error");
							} else {
								$.messager.alert("提示", data.tip, "info", function () {
									initTable();
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

	//重置对话框表单函数
	function clearForm(){
		$("#input_applyId").val("");
		$("#input_company").combobox("setValue","");
		$("#input_surcomjob").textbox("setValue","");
		$("#input_insurance").combobox("setValue","");
		$("#input_surcomcontent").textbox("setValue","");
		$("#input_adress").textbox("setValue","");
		$("#input_applydate").datebox("setValue","");
		$("#input_endDate").datebox("setValue","");
		$("#input_teacherName").combobox("setValue","");
		//$("#input_teacherNo").textbox("setValue","");
		$("#input_phoneOrQQ").textbox("setValue","");
		$("#input_status").combobox("setValue","");
	}
	
	//查看图片对话框
	/*$("#tbl_stuApplication").datagrid({
			onClickCell:function(index, field, value){
						    if (field=="filePath"){
						    	if(value==null||value==""){
						    		$.messager.alert("提示", "您还未提交附件！!", "info");
						    		return false;
						    	}else{
						    		//$("#dialog_a").dialog("open");
				$.messager.alert("附件","<img src='/train/static/apply/2001/1001/application/companyView.jpg'>","");

		    		}
		    	}
		    },
		
		
	})*/
	
	
	//初始化数据表格
    function initTable() {
        var url = path + "/studentapp/getapplication";
        $("#tbl_stuApplication").datagrid({
        	loadMsg:"加载数据中......",
            url:url,
            border:false,
            striped:true,
            fit:true,
            autoRowHeight:false,
            singleSelect:true,
            fitColumns:true,
            columns:[[
				{field: "comName",title: "实习单位",width:150,
					formatter:function (value,row,index) {
						if (row.company)
							return row.company.comName;
					}},
                {field: "surcomjob",title: "实习岗位",width:150},
                //{field: "insurance",title: "保险情况",width:150},
                {field: "surcomcontent",title: "实习内容",width:150},
                {field: "applydate",title: "开始日期",width: 150},
                {field: "endDate",title: "结束日期",width:150},
				{field: "teacherName",title: "指导老师",width:150},
				{field: "filePath",title: "附件",width:150,
					formatter:function (value,row,index) {
						if(null==row.filePath || row.filePath == "")
							return "<div style='color:red'>无附件</div>";
						else
							return  "<a href='#' file='"+row.filePath+"'>查看</a>"; 
					}},
				{field: "statusVal",title: "状态",width:150}
            ]], 
            onLoadSuccess:function(data){
            	//初始化按钮
            	if(data.rows.length == 0)
            	{
            		$("#btn_addDlg").linkbutton("enable");
            		$("#btn_delDlg").linkbutton("disable");
            		$("#btn_allInfo").linkbutton("disable");
            		$("#btn_photo").linkbutton("disable");
            		$("#btn_updDlg").linkbutton("disable");
            		$("#btn_subDlg").linkbutton("disable");
					$("#btn_recordDlg").linkbutton("disable");
            	}
            	else
            	{
            		$("#tbl_stuApplication").datagrid("selectRow",0);
            		$("#btn_addDlg").linkbutton("disable");
            		$("#btn_allInfo").linkbutton("enable");
            		if(data.rows[0].status == "BApprove")
            		{
            			if (data.rows[0].filePath !=null && data.rows[0].filePath !="")
							$("#btn_subDlg").linkbutton("enable");
            			else
							$("#btn_subDlg").linkbutton("disable");
                		$("#btn_delDlg").linkbutton("enable");
                		$("#btn_updDlg").linkbutton("enable");
						$("#btn_photo").linkbutton("enable");
						$("#btn_recordDlg").linkbutton("enable");

            		}
            		else if (data.rows[0].status == "FApprove")
					{
						$("#btn_delDlg").linkbutton("enable");
						$("#btn_updDlg").linkbutton("enable");
						$("#btn_photo").linkbutton("enable");
						$("#btn_subDlg").linkbutton("disable");
						$("#btn_recordDlg").linkbutton("enable");
					}
            		else
            		{
						$("#btn_photo").linkbutton("disable");
                		$("#btn_delDlg").linkbutton("disable");
                		$("#btn_updDlg").linkbutton("disable");
                		$("#btn_subDlg").linkbutton("disable");
						$("#btn_recordDlg").linkbutton("enable");
            		}
                	$("a[file]").click(function() {
                		var filePath = $(this).attr("file");
						var url=path+"/getRemoteImgFile?filePath="+filePath
                		$("#perview").attr("src",url);
                		$("#dialog_photo").dialog({
                			top:20,
                			title:data.rows[0].student.studentName+"的附件",
                			minHeight:200,
                			maxHeight:document.body.clientHeight-30,
                			onClose: function() {
                				$("#perview").attr("src","");
                			}
                			//content:"<img alt='附件' src='"+path+filePath+"' style='width:650px;'>"
                		});
        				//$("#dialog_photo").dialog("resize",{height: document.body.clientHeight-30});
                		$("#dialog_photo").dialog("open");
                	});
            	}
            }
        });
    };

	//打开申请表对话框函数
	function openDlg(){
		//当实习周期不存在时，禁用实习时间框
		if(null == departCycle) {
    		$("#input_applydate").datebox("disable");
    		$("#input_endDate").datebox("disable");
		}
		//复选款控制保存按钮是否有效
		if ($('#check_agreement').checkbox('options').checked)
			$("#btn_save").linkbutton("enable");
		else
			$("#btn_save").linkbutton("disable");
		$("#dlg_stuApplication").dialog("open");
	}
	
	//点击保存按钮
	$("#btn_save").click(function(){
		var postData=$("#form_stuApplication").serializeJSON();
		if (postData)
		{
			if(postData.applyId!=null && postData.applyId!="")
				updateApplication(postData);
			else
				createApplication(postData);
		}
		else
			alert("postData is null!");
	});
	
	
	
	//表单字段校验
	function checkForm(postData)
	{
		if (postData.insurance == "")
			$("#input_insurance").next("span").find("a").click();
		else if (postData.companyId == "")
			$("#input_company").next("span").find("a").click();
		else if (postData.surcomjob == "")
			$("#input_surcomjob").next("span").find("input[type='text']").focus();
		else if (postData.surcomcontent == "")
			$("#input_surcomcontent").next("span").find("input[type='text']").focus();
		else if (postData.applydate == "") {
			$("#input_applydate").next("span").find("a").click();
			$.messager.alert("错误", "请选择实习开始时间！", "error");
		} else if (postData.endDate == "") {
			$("#input_endDate").next("span").find("a").click();
			$.messager.alert("错误", "请选择实习结束时间！", "error");
		} else if (postData.teacherNo == "" || postData.teacherName == "")
			$("#input_teacherName").next("span").find("input[type='text']").focus();
		else if (postData.phoneOrQQ == "")
			$("#input_phoneOrQQ").next("span").find("input[type='text']").focus();
		else if(!(postData.phoneOrQQ && /[1][3,4,5,7,8][0-9]{9}$/.test(postData.phoneOrQQ)))
			$.messager.alert("提示","请输入正确的手机号码","info");
		else if (postData.phoneOrQQ.length != 11)
            $.messager.alert("提示","请输入11位的手机号码","info");
        else if (postData.adress == "")
			$("#input_adress").next("span").find("input[type='text']").focus();
		else
			return true;
		return false;
	}
	
	//保险情况下拉列表框
	$("#input_insurance").combobox({
		value:"五险一金",
		url:path+"/studentapp/getinsurance",
    	editable:false,
    	textField: "dictValue",
    	valueField: "dictKey",
		onLoadError: function(){
			console.log("加载保险数据失败！");
		}
	});
	

	//指导老师下拉框
	$("#input_teacherName").combobox({
		url:path+"/studentapp/getteachername",
    	editable:false,
    	textField: "staffName",
    	valueField: "staffName",
		onSelect:function (record) {
			$("#input_teacherNo").val(record.userCode);
		},
		onLoadError: function(){
			console.log("加载指导老师数据失败！");
		}
    });

	//实习单位下拉框
	$("#input_company").combobox({
		url:path+"/syscompany/getAllSysCompanyForStudent",
		editable:false,
		textField: "comName",
		valueField: "companyId",
		onLoadError: function(){
			console.log("加载实习单位数据失败！");
		}
	});

	//上传附件
	$("#btn_photo").click(function () {
      $("#dialog_importphoto").dialog("open");
	});

	$("#btn_mod").click(function () {
		var form = $("#form_importphoto")[0];
		var file = $(form).find('input[type=file]')[0].files[0];
		if(file == null) {
			$.messager.alert("错误", "请先选择一个文件！", "error");
		}
		var fileName = file.name;
		console.log(fileName);
		var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
		if (file_typename.toLowerCase() == '.jpg') {
			phoFileUpload(form, "/studentapp/uploadfile", "dialog_importphoto");
		} else {
			$.messager.alert("错误", "请选择一个.jpg格式的文件！", "error");
		}

	})
	
	/**
	 * 上传鉴定表
	 */
	$("#btn_AppraisalFromFirm").click(function () {
      $("#dialog_AppraisalFromFirm").dialog("open");
	});

	$("#btn_mod1").click(function () {
		var form = $("#form_AppraisalFromFirm")[0];
		var file = $(form).find('input[type=file]')[0].files[0];
		if(file == null) {
			$.messager.alert("错误", "请先选择一个文件！", "error");
		}
		var fileName = file.name;
		console.log(fileName);
		var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
		if (file_typename.toLowerCase() == '.jpg') {
			phoFileUpload(form, "/studentapp/uploadAppraisalFromFirm", "dialog_AppraisalFromFirm");
		} else {
			$.messager.alert("错误", "请选择一个.jpg格式的文件！", "error");
		}
	})
	
	//上传
	function phoFileUpload(form, uploadPath, dialogId) {
		var formData = new FormData(form);
        var applicationId = $("#tbl_stuApplication").datagrid("getSelected").applyId;
        formData.append("applicationId",applicationId);
        $.ajax({
            url : path + uploadPath,
            type : "post",
            async : false,
            cache:false,
            data : formData,
            processData : false,
            contentType : false,
			beforeSend : function() {
				$.messager.progress({
				 	text:"上传中..."
				});
			},
            success : function(e) {
				$.messager.progress("close");
                if (null != e.error) {
					$.messager.alert("错误", e.error, "error");
                    
                } else {
					$.messager.alert("提示", e.tip, "info", function () {
						$("#"+dialogId).dialog("close");
						//initTable();
						location.reload();
					});
                }
            },
			error : function(e) {
				$.messager.progress("close");
				$.messager.alert("错误", "未知错误！", "error");
			}
        });
    }

    //必读项复选框
	$("#check_agreement").checkbox({
		value: 'on',
		checked: false,
		onChange:function (checked) {
			if (checked)
				$("#btn_save").linkbutton("enable");
			else
				$("#btn_save").linkbutton("disable");
		}
	});

	//审批记录功能
	$("#btn_recordDlg").click(function () {
		//初始化审批记录对话框
		$('#dialog_checkRecord').dialog({
			title: '审批记录',
			width: 560,
			height: 300,
			closable:true,
			closed: false,
			resizable:false,
			constrain:true,
			modal: true
		});
		//初始化审批记录表
		var applicationId = $("#tbl_stuApplication").datagrid("getSelected").applyId;//选中记录的Id
		$("#tbl_checkRecord").datagrid({
			loadMsg:"正在读取审批记录...",
			url:path+"/studentapp/getreviewlog",
			border:false,
			nowrap: false,
			striped:true,
			//fit:true,
			autoRowHeight:false,
			singleSelect:true,
			fitColumns:true,
			queryParams: {applicationId: applicationId},
			columns:[[
				{field: "userName",title: "用户",width:90},
				{field: "action",title: "操作",width:80},
				{field: "chgStatus",title: "状态",width:120},
				{field: "memo",title: "备注",width:290
					//当备注内容过多时控制显示控制
					// formatter: function (value, row, index) {
					// 	if (row)
					// 		if (row.memo.length>4)
					// 			return row.memo.substring(0,4)+"...";
					// }
				},
			]],
			//高亮显示新增的审批记录
			// rowStyler: function(index,row){
			// 	if (row)
			// 		if (row.readStatus == "未读" && row.userType == "teacher"){
			// 			return 'color:red;';
			// 		}
			// },
			onLoadSuccess:function(data){
				//默认选择第一行
				if(data.rows.length != 0)
					$("#tbl_checkRecord").datagrid("selectRow",0);
			}
		});
	});
	
	//初始化开始时间框
	$("#input_applydate").datebox({
	    onSelect: function(startDate){
	    	//console.log(startDate.getFullYear() +"-"+ (startDate.getMonth()+1) +"-"+ startDate.getDate());
	    	//$("#input_endDate").datebox("enable");
	    	//选择开始时间后先清除结束时间（重选结束时间），防止时间控制导致重新打开修改框时无法设置数据
	    	$("#input_endDate").datebox("setValue","");
        	//结束时间的选择范围，结束时间应该大于开始时间
        	$('#input_endDate').datebox('calendar').calendar({
        	    validator: function(date){
        	    	//结束时间应该至少满足实习持续周数要求
        	        var d1 = new Date(startDate);
        	        d1.setDate(d1.getDate()+departCycle.weeks*7-1);//开始时间+持续天数
        	        var d2 = new Date(departCycle.planEndTime);
        	        return d1<=date && date<=d2;
        	    }
        	});
        	//定位结束时间到日历对应的页，默认周期结束时间
        	$('#input_endDate').datebox('calendar').calendar('moveTo', new Date(departCycle.planEndTime));
	    }
	});

	//获取实训周期
	function getCycle() {
		$.ajax({
			url:path+"/studentapp/getcycle",
            type : "post",
            async : false,
            cache:true,
            success: function (e){
            	if(null != e && "" != e) {
            		console.log(e);
                	departCycle = e;
                	$('#help').tooltip("update","实习日期应在"+departCycle.planStartTime+"和"+departCycle.planEndTime+"范围内，且实习时间不少于"+departCycle.weeks+"周！");		//提示实习申请时间的填写范围
                	$('#help').attr("deptCycle",true);
                	/**
                	 * 设置实习时间的选择范围
                	 */
                	//开始时间的选择范围
                	$('#input_applydate').datebox('calendar').calendar({
                	    validator: function(date){
                	    	var d1 = new Date(departCycle.planStartTime);
                	    	d1.setDate(d1.getDate()-1);
                	    	//结束时间-持续天数，能选择的开始时间的最大值，防止出现实习时间不够持续时间的情况
                	        var d2 = new Date(departCycle.planEndTime);
                	        d2.setDate(d2.getDate()-departCycle.weeks*7+1);
                	        return d1<=date && date<=d2;
                	    }
                	});
                	//定位开始时间到日历对应的页，默认周期开启时间
                	$('#input_applydate').datebox('calendar').calendar('moveTo', new Date(departCycle.planStartTime));
                	//结束时间控制在选择开始时间后执行
            	} else {
            		//$("#help").css("display","inline");
            		$('#help').tooltip("update","实训周期尚未开启！请重新登录后重试！");		//实习周期不存在时提示
            		$('#help').attr("deptCycle",false);
            		$.messager.alert("错误", "无法获取实训周期信息，请重新登录后再尝试！", "error");
            	}
            },
            fail: function(){
            	$('#help').attr("deptCycle",false);
            	$('#help').tooltip("update","无法获取实训周期信息！请重新登录后再尝试！");
            	$.messager.alert("错误", "无法获取实训周期信息，请重新登录后再尝试！", "error");
            }
		});
	}
	
	//验证框的验证规则
    $.extend($.fn.validatebox.defaults.rules, {
        minLength: {
            validator: function(value, param){
                return value.length >= param[0];
            },
            message: 'Please enter at least {0} characters.'
        }
    });

	//长度校验
	function length(){
		      $('#input_surcomjob').textbox('textbox').attr('maxlength',50) 
			  $('#input_surcomcontent').textbox('textbox').attr('maxlength',100)
			  $('#input_phoneOrQQ').numberbox('textbox').attr('maxlength',11)
			  $('#input_adress').textbox('textbox').attr('maxlength',80) 
			  
			
		}
	
	
		

})
