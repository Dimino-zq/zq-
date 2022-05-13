KindEditor.ready(function(K) {
	//获取上下文
	var path=getRootPath();
	/**
	 * 初始化富文本编辑器
	 */
	$(document).ready(function(){
        var options = {
                cssPath : '/css/index.css',
                filterMode : true,
                items : [
                    'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template',
                    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent',
                    'clearhtml', 'quickformat', 'selectall'
                ],
            	resizeType : 0,
            	height : document.body.clientHeight-58,
        };
        //实习单位简介编辑框
        var editor1 = K.create('#editor_id1', options);
        //实习工作情况简介编辑框
        var editor2 = K.create('#editor_id2', options);
        //实习总结简介编辑框
        var editor3 = K.create('#editor_id3', {
            cssPath : '/css/index.css',
            filterMode : true,
            items : [
                'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent',
                'clearhtml', 'quickformat', 'selectall'
            ],
        	resizeType : 0,
        	height : document.body.clientHeight-58,
        	afterFocus: function(){
                if (editor3.html() == '总结内容要求: 1.实习具体内容、过程及完成情况；2.实习的效果及主要收获(含知识与技能的运用及熟练程度)；3.存在不足与建议。') {
                	editor3.html('');
                    // 输入文字变为黑色
                	$("#editor_id3").prev().find(".ke-edit").find("iframe").contents().find(".ke-content").css("color","");
                }
        	},
        	afterBlur: function(){
        		editor3.sync();
        		//console.log(editor3.html());
                if (editor3.html() == '<br />' || editor3.html() == '') {
                	editor3.html('总结内容要求: 1.实习具体内容、过程及完成情况；2.实习的效果及主要收获(含知识与技能的运用及熟练程度)；3.存在不足与建议。');
                    //console.log($("#editor_id3").prev().find(".ke-edit").find("iframe").contents().find(".ke-content"));
                	$("#editor_id3").prev().find(".ke-edit").find("iframe").contents().find(".ke-content").css("color","#777");
                }
        	},
        });
        //窗口大小改变时自动调整编辑框的高度（360浏览器显示不正常）
        $(window).resize(function(){
        	editor1.edit.setHeight(document.body.clientHeight-100);
        	editor2.edit.setHeight(document.body.clientHeight-100);
        	editor3.edit.setHeight(document.body.clientHeight-100);
        });
 
        
        //初始化数据
        initData();

        //点击修改按钮
        $("#btn_updDlg").click(function(){
        	$("#btn_updDlg").css("display","none");
    		editor1.readonly(false);
    		editor2.readonly(false);
    		editor3.readonly(false);
        	$("#btn_addDlg").css("display","");
	    })
	    
	    //点击保存按钮
	    $("#btn_addDlg").click(function(){
	    	var postData = {
	    		"reportId":$("#input_reportId").val(),
	    		"comProfile":editor1.html(),
	    		"comworkfile":editor2.html(),
	    		"conclusion":editor3.html(),
	    	};
			if (postData)
			{
				if(postData.reportId!=null && postData.reportId!="")
					updateReport(postData);
				else
					createReport(postData);
			}
			else
				alert("postData is null!");
	    })
	    
	    //表单字段校验
		function checkForm(postData)
		{
        	console.log();
			if (postData.comProfile == "" || editor1.isEmpty()) {
				$.messager.alert("提示", "请填写实习单位简介！", "info", function() {
					$("#tabs_report").tabs("select",0);
					editor1.focus();
				});
			}
			else if (postData.comworkfile == "" || editor2.isEmpty()) {
				$.messager.alert("提示", "请填写实习工作情况简介！", "info", function() {
					$("#tabs_report").tabs("select",1);
					editor2.focus();
				});
			}
			else if (postData.conclusion == "" || editor3.isEmpty() || editor3.html() == "总结内容要求: 1.实习具体内容、过程及完成情况；2.实习的效果及主要收获(含知识与技能的运用及熟练程度)；3.存在不足与建议。") {
				$.messager.alert("提示", "请填写实习总结！", "info",  function() {
					$("#tabs_report").tabs("select",2);
					editor3.focus();
				});
			}
			else
				return true;
			return false;
		}
	    
    	//保存新增的实习报告
    	function createReport(postData) {
    		//检查表单数据是否符合要求
    		if (checkForm(postData)) {
    			var url = path + "/studentapp/savereport";
    			//post提交数据
    			$.post(url, postData, function (data) {
    				if (data != null) {
    					if (data.error != null) {
    						$.messager.alert("错误", data.error, "error");
    					} else {
    						$.messager.alert("提示", data.tip, "info", function () {
    							initData();
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
    	
    	//保存修改后的实习报告
    	function updateReport(postData) {
    		if (checkForm(postData)) {
    			var url = path + "/studentapp/updatereport";
    			$.post(url, postData, function (data) {
    				if (data != null) {
    					if (data.error != null)
    						$.messager.alert("错误", data.error, "error");
    					else {
    						$.messager.alert("提示", data.tip, "info", function () {
    							initData();
    						});
    					}
    				} else
    					$.messager.alert("错误", "未知错误！", "error");
    			});
    		}
    	}
	    
  	  //查看详情
  	  $("#btn_printDlg").click(function(){
  		 var reportId = $("#input_reportId").val();
  	     if (reportId==null){
  	      $.messager.alert("提示","你还未提交过报告书!","info");
  	      return false;
  	     }
  	     else
  	     {
  	        var url=path+"/studentapp/allinfo";
  	        //post跳转网页
  	        var temp_form = document.createElement("form");
  	        temp_form .action = url;
  	        temp_form .target = "_self";
  	        temp_form .method = "post";
  	        temp_form .style.display = "none"; 
  	        var opt = document.createElement("textarea");
  	        opt.name = "intreportId";
  	        opt.value = reportId;
  	        temp_form .appendChild(opt);
  	        document.body.appendChild(temp_form);
  	        temp_form .submit();
  	       }    
  	 	});
    	
	    //ajax加载实习报告书数据
        function initData() {
			$("#btn_addDlg").css("display","none");
			$("#btn_updDlg").css("display","none");
	        $.ajax({
	        	url : path+"/studentapp/gettrainreport",
	        	success : function(data){
	            	if(null!=data) {
	            		if(data.length!=0) {
	            			$("#input_reportId").val(data[0].reportId);
	                		editor1.html(data[0].comProfile);
	                		editor2.html(data[0].comworkfile);
	                		editor3.html(data[0].conclusion);
	                		editor1.readonly(true);
	                		editor2.readonly(true);
	                		editor3.readonly(true);
	            			if(null==data[0].teacherReportAppraisal) {
		                		$("#btn_updDlg").css("display","");
	            			}
	            		} else {
	            			//editor3.focus();
	            			editor3.html("总结内容要求: 1.实习具体内容、过程及完成情况；2.实习的效果及主要收获(含知识与技能的运用及熟练程度)；3.存在不足与建议。");
	                    	$("#editor_id3").prev().find(".ke-edit").find("iframe").contents().find(".ke-content").css("color","#777");
	            			$("#btn_addDlg").css("display","");
	            			$("#btn_printDlg").linkbutton("disable");
	            		}
	
	            	}
	        	}
	        });
	    }
    });

})