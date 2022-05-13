$(document).ready(function() {
	var path = getRootPath();
	syslevel();
	length();
	
	//点击提交查询信息
    $("#btn_query").click(function(){
        var queryParams=$('#form_query').serializeJSON();
        $('#tbl_syslevel').datagrid('load',queryParams);
    });


	//打开添加对话框
    $("#btn_addDlg").click(function () {
    	$("#dlg_syslevel").dialog("setTitle","增加等级信息");
        $("#dlg_syslevel").dialog("open");
    });
	
	//关闭对话框
    $("#btn_close").click(function () {
        $("#dlg_syslevel").dialog("close");
        clearForm();
    });


	//点击删除按钮
    $("#btn_delDlg").click(function () {
        var row=$("#tbl_syslevel").datagrid("getSelected");
        if (null!=row)
        {
        	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){
        	    if (r)
        	    {
        	        var lvlId=row.lvlId;
        	        var postData={"lvlId":lvlId};
        	        $.post((path+"/syslevel/deleteLevel"),postData,function (data) {
        	            if ("succ"==data)
        	            {
        	            	$.messager.alert('提示','删除成功！','info',function(){
        	            	syslevel();
        	            	});
        	            }
        	            else
							$.messager.alert("提示",data,"info");
        	                
        	        });
        	    }
        	});
        }
        else
        {
			$.messager.alert("提示","请选中一行！","info");
           
        }
    });

	//打开修改对话框
    $("#btn_updDlg").click(function () {
    	var  row=$('#tbl_syslevel').datagrid("getSelected");
		if (row==null)
		{
			$.messager.alert("提示","请选中要修改的行！","info");
			
			return false;
		}
		else
		{
            $("#input_lvlId").val(row.lvlId);
			$("#input_lvlName").textbox("setValue",row.lvlName);
			$("#pictureimg").attr("src","data:image/jpeg;base64,"+row.lvlPicPath);
			$("#imgString").val(row.lvlPicPath);
		    $("#input_minScore").textbox("setValue",row.minScore);

		    $("#dlg_syslevel").dialog("setTitle","修改等级信息");
			$("#dlg_syslevel").dialog("open");
		}
	});
	
	//点击表单中保存--保存
	$("#btn_save").click(function() {
		var postData = $('#form_add').serializeJSON();
		if (postData.lvlName == "")
		{
			$.messager.alert("提示","请输入等级名称！","info",function (){
				$("#input_lvlName").next("span").find("input[type='text']").focus();
			});
		}
		else if (postData.lvlPicPath == "")
		{
			$.messager.alert("提示","请输入图片标志！","info",function (){
				$("#input_lvlPicPath").next("span").find("input[type='text']").focus();
			});
		}
		else if (postData.minScore == "")
		{
			$.messager.alert("提示","请选择最小分数！","info",function (){
				$("#input_minScore").next("span").find("input[type='text']").focus();
			});
		}
		else
		{
			var input_lvlId=$("#input_lvlId").val();
				if(input_lvlId==""){
					var url=path+"/syslevel/saveLevel";
		    		var postData=$('#form_add').serializeJSON();
					console.log(postData);
					//增加图片
				        var file = $("#uploadimg").next().find('input[type=file]')[0].files[0];
				        console.log(file);
				        if (file == null)
				        {
				        	$.messager.alert("提示",'错误，请选择文件',"info");
				        	return;
				        }
				        var fileName = file.name;
				        var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
				        if (file_typename == '.gif'||file_typename == '.jpg')
				        {
							console.log(file_typename);
				        //ajaxFileUpload(file);
							// var formData = new FormData();
						
							var formData = new FormData($('#form_add')[0]);
							console.log(formData);
						        /*formData.append("file", file);
								formData.append("saveform",postData);*/
						        $.ajax({
						           // url : url,
						            type : "post",
						            async : false,
									cache:false,
						            
						            processData : false,
						            contentType : false,
						            beforeSend : function() {
						                console.log("正在进行，请稍候");

						            },
						            dataType : "json"
						        });
                              $.post(url,postData,function (data) {
		        	            if ("succ"==data)
		        	            {
		        	            	$.messager.alert('提示','保存成功！','info',function(){
			                         
		        	            	 syslevel();
									 $("#dlg_syslevel").dialog("close");
										$('#uploadimg').filebox('clear');
									 	clearForm();
		        	            	});
		        	            }
		        	            else
									$.messager.alert("提示",data,"info");
		        	                
		        	        });
					
				}
				else
				        {
				        	$.messager.alert("提示","文件类型错误","info");
				        	$("#dlg_syslevel").dialog("close");
				        } 
				}
		else//点击表单中保存--修改
		{
			var url = path + "/syslevel/updLevel";
			var postData=$('#form_add').serializeJSON();
			var formData = new FormData($('#form_add')[0]);
		    console.log(formData);
	        /*formData.append("file", file);
			formData.append("saveform",postData);*/
	        $.ajax({
	            url : path+ "/syslevel/updLevel",
	            type : "post",
	            async : false,
				cache:false,
	            data : formData,
	            processData : false,
	            contentType : false,
	            beforeSend : function() {
	                console.log("正在进行，请稍候");
	            },						            
	             dataType : "json"
	          });
            $.post(url,postData,function (data) {
		        if ("succ"==data)
		      {
		          $.messager.alert('提示','修改成功！','info',function(){
			      
		          syslevel();
                  $("#dlg_syslevel").dialog("close");
		        });
		    }
		         else
				   $.messager.alert("提示",data,"info");
		        	                
		  });
					

			
			
			
		}
}
		
	});
			
	//重置对话框中表单的数据
	function clearForm(){
		$("#input_lvlId").val("");
		$("#pictureimg").attr("src","");
		$("#input_lvlName").textbox("setValue","");
		$("#input_lvlPicPath").filebox("setValue","");
		$("#input_minScore").textbox("setValue","");
		
	}
	
	    //上传文件函数,预览图片
   /* function ajaxFileUpload(file) {
        var formData = new FormData();
        formData.append("file", file);
        $.ajax({
            url : path+ "",
            type : "post",
            async : false,
			cache:false,
            data : formData,
            processData : false,
            contentType : false,
            beforeSend : function() {
                console.log("正在进行，请稍候");
            },
            dataType : "json"
        });
    }*/
	
	
	 //初始化数据表格
    function syslevel() {
        var url = path + "/syslevel/getAllLevel";
        $("#tbl_syslevel").datagrid({
        	loadMsg:"加载数据中......",
            url:url,
            border:false,
            striped:true,
            fit:true,
            rownumbers:true,
            autoRowHeight:false,
            singleSelect:true,
            fitColumns:true,
            columns:[[
            	{field: 'lvlName',title: '等级名称',width:435,fixed:true},
                {field: 'lvlPicPath',title: '等级标志',width:435,fixed:true,
                     formatter:function (value,row,index) {
						if(null==row.lvlPicPath || row.lvlPicPath == "")
							return "<div style='color:red'>无</div>";
						else
							return "<div><img style='height:18px;' src='data:image/gif;base64,"+row.lvlPicPath+"'></div>"
					}},
                {field: 'minScore',title: '最小分数',width:430,fixed:true},
            ]]
        });
	}
	
	 //长度校验
       function length(){
			  $('#input_lvlName').textbox('textbox').attr('maxlength',20) 
			  $('#input_minScore').numberbox('textbox').attr('maxlength',20)
			 
			  
		 
		
		}
	
});