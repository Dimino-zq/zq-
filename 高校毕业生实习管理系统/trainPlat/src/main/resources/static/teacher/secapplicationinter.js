$(document).ready(function(){
	//var path="/train";
	var path=getRootPath();
	button();			
	
	//按钮控制
	function button(){
		var teacherViewShow=$("#teacherViewShow").text();
		var adviserViewShow=$("#adviserViewShow").text();
		var deptViewShow=$("#deptViewShow").text();
		var staff=$("#staff").text();
		var enroll=$("#enroll").text();
		var teacherNo=$("#teacherNo").text();
		console.log(teacherViewShow);
		console.log(adviserViewShow);
		console.log(deptViewShow);
		console.log(enroll);
		
		console.log($("#staff").text());
		console.log(staff.indexOf("系主任"));
		$("#teachAll").attr("hidden","hidden"); 		
		$("#deptAll").attr("hidden","hidden");
		if(enroll==teacherNo){
			$("#teachAll").removeAttr("hidden"); 
			
		}else{
			$("#teachAll").attr("hidden","hidden");
		}
		
		if(teacherViewShow=="同意"){
			  $("#teachAll").attr("hidden","hidden"); 
		}else{
			
		}
		
      if(deptViewShow=="同意"){
      	 $("#deptAll").attr("hidden","hidden"); 
		}else{
			if(teacherViewShow=="同意"){
				 $("#deptAll").removeAttr("hidden"); 
		      }
		
		}
	
	}
		$("#dd").click(function(){
			var studentName = $("#studentName").text();
			$("#dialog_student").dialog("setTitle","查看"+studentName+"的附件内容");
			$("#dialog_student").dialog("open");
		});
		
		$("#btn_pass").click(function(){
			$("#dialog_student").dialog("close");
		});
	//老师同意
	$("#teachAgree").click(function(){	
		var secApplyId=$("#secApplyId").text();
		var applyId=$("#applyId").text();
		 var postData={"secApplyId":secApplyId,"teacherView":"同意","applyId":applyId};	
		 
         $.post((path+"/teacher/passTeacherSec"),postData,function (data) {
           if ("succ"==data)
           {
             $.messager.alert("提示","通过！","info");   //提示老师同意成功
             //隐藏老师同意拒绝按钮
             
             window.location.reload();
             
           }
           else 
           {
            $.messager.alert("提示","失败!","info");//提示老师同意失败
           }             
       });
	});
	//老师拒绝
    $("#teachRefuse").click(function(){   	
    	    var studentName = $("#studentName").text();
			$("#dialog_reason").dialog("setTitle","请输入对"+studentName+"的拒绝理由");
			$("#dialog_reason").dialog("open");
			$("#reason").next("span").find("textarea").focus();
			
			$("#btn_yes").click(function () {
					
				if ($("#reason").textbox('getText') == "")
				{
					$.messager.alert("提示","请输入您拒绝"+studentName+"的理由！","info");
					$("#reason").next("span").find("textarea").focus();
				}
	            else
	            {
	            	
	            	
	            	$.messager.confirm(' ', '是否确认驳回申请?', function(r){
	    				if (r){
	    					
	    					var secApplyId=$("#secApplyId").text();
		    	        var thisView = $("#reason").textbox('getValue');
		    	        var postData={"secApplyId":secApplyId,"teacherView":thisView};
			            $.post((path+"/teacher/refuseTeacherSec"),postData,function (data) {		           
			               if ("succ"==data)
			                  {				
			            	   
			            	   $.messager.alert("","该申请已驳回，点击退出页面","info" ,function(){
	   		            		   
			            		   
	   		            			$("#dialog_reason").dialog("close");		           	                  
	   		   		               //     $.messager.alert("提示","成功驳回！","info");	//提示老师拒绝成功           
	   		   		             	    javascript:history.back();
	   		   		            	 $("#back").click();
	   		   		           $("#back").click();
	   		   		           	$("#back").click();
	   		   		            	 console.log("老师");
	   		   		            	 	
	   		   		            	window.history.back(); 
	   		 					// exit action;
	   		   		            	 
	   		   		          	           	                  
			                      
			                   
	   		            		   	
	   		            		   
	   		            	   	});
			            	    
			                    
			                  }
			                else 
			                  { 
			                	$("#dialog_reason").dialog("close");
			                    $.messager.alert("提示","失败!","info");  //提示老师拒绝失败
			                  }          
			            });	
	    				}
	    			});
	            	
	            	
	            	
	    	       	         
	           }	       		    	   		    
	       });
      });
   /* //辅导员同意
    $("#adviserAgree").click(function(){	
    	var secApplyId=$("#secApplyId").text();
    	var applyId=$("#applyId").text();
		var postData={"secApplyId":secApplyId,"adviserView":"同意","applyId":applyId};	
        $.post((path+"/teacher/passTeacherSec"),postData,function (data) {
          if ("succ"==data)
          {
            $.messager.alert("提示","通过！","info");   //提示老师同意成功
            //隐藏老师同意拒绝按钮
            window.location.reload();
          }
          else 
          {
           $.messager.alert("提示","失败!","info");//提示老师同意失败
          }             
      });
	});
    //辅导员拒绝
    $("#adviserRefuse").click(function(){   	
	    var studentName = $("#studentName").text();
		$("#dialog_reason").dialog("setTitle","请输入对"+studentName+"的拒绝理由");
		$("#dialog_reason").dialog("open");
		$("#reason").next("span").find("textarea").focus();
		
		$("#btn_yes").click(function () {
			var postData={"adviserView":"不同意"};	
			if ($("#reason").textbox('getText') == "")
			{
				$.messager.alert("提示","请输入您拒绝"+studentName+"的理由！","info");
				$("#reason").next("span").find("textarea").focus();
			}
            else
            {
            	$.messager.confirm(' ', '是否确认驳回申请?', function(r){
    				if (r){var secApplyId=$("#secApplyId").text();
    		        var thisView = $("#reason").textbox('getValue');
    		        var postData={"secApplyId":secApplyId,"adviserView":thisView};
    	            $.post((path+"/teacher/refuseTeacherSec"),postData,function (data) {		           
    	               if ("succ"==data)
    	                  {						  
    	            	    $("#dialog_reason").dialog("close");		           	                  
    	                    $.messager.alert("提示","成功驳回！","info");	//提示老师拒绝成功     
    	                    $("#back").click();
    	                    javascript:history.back();
    	                  }
    	                else 
    	                  { 
    	                	$("#dialog_reason").dialog("close");
    	                    $.messager.alert("提示","失败!","info");  //提示老师拒绝失败
    	                  }          
    	            });	
    				}
    			});              
	          
           }	 
			
			
			
       });
  });*/
    
    //系主任同意
    $("#deptAgree").click(function(){		
			var secApplyId=$("#secApplyId").text();
			var applyId=$("#applyId").text();
			 var postData={"secApplyId":secApplyId,"deptView":"同意","applyId":applyId};	
       $.post((path+"/teacher/passDeptSec"),postData,function (data) {
         if ("succ"==data)
         {
           $.messager.alert("提示","通过！","info");   //提示老师同意成功
           //隐藏老师同意拒绝按钮
           window.location.reload();
           
           
           
           
         }
         else 
         {
          $.messager.alert("提示","失败!","info");//提示老师同意失败
         }             
     });
	});
   //系主任拒绝
   $("#deptRefuse").click(function(){   	
	    var studentName = $("#studentName").text();
		$("#dialog_reason").dialog("setTitle","请输入对"+studentName+"的拒绝理由");
		$("#dialog_reason").dialog("open");
		$("#reason").next("span").find("textarea").focus();
		
		$("#btn_yes").click(function () {
			
			
				
			if ($("#reason").textbox('getText') == "")
			{
				$.messager.alert("提示","请输入您拒绝"+studentName+"的理由！","info");
				$("#reason").next("span").find("textarea").focus();
			}
           else
           {
        	   
        	   $.messager.confirm(' ', '是否确认驳回申请?', function(r){
   				if (r){
   				 var secApplyId=$("#secApplyId").text();
   	   	        var thisView = $("#reason").textbox('getValue');
   	   	        var postData={"secApplyId":secApplyId,"deptView":thisView};
   		            $.post((path+"/teacher/refuseDeptSec"),postData,function (data) {		           
   		               if ("succ"==data)
   		                  {						 
   		            	  
   		            	   
   		            	 
		            	   $.messager.alert("","该申请已驳回，点击退出页面","info" ,function(){
   		            		   
		            		   
   		            			$("#dialog_reason").dialog("close");		           	                  
   		   		               //     $.messager.alert("提示","成功驳回！","info");	//提示老师拒绝成功           
   		   		             	    javascript:history.back();
   		   		            	 $("#back").click();
   		   		            	 	$("#back").click();
   		   		            	 		$("#back").click();
   		   		            	 console.log("系主任");
   		   		            	 	
   		   		            	window.history.back(); 
   		 					// exit action;
   		   		            	 
   		 		   	
   		            		   
   		            	   	});
   		            	
 	                  
   		                  }
   		                else 
   		                  { 
   		                	$("#dialog_reason").dialog("close");
   		                    $.messager.alert("提示","失败!","info");  //提示老师拒绝失败
   		                  }          
   		            });		 
   					 
   				}
   			});
                   
          }	
			
		      		    	   		    
      });
 });
	
	
});