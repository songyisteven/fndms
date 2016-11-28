<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsps/admin/adminleft_sys.jsp"%>


<script>
	//30秒查一次
	
	 //$(document).ready(function(){
	//	setInterval(getMsg, 30000);
	//});
	
	//function getMsg(){
	//	$.ajax({
	//		type : "POST",
	//		url : "<%=request.getContextPath()%>/admin/fx_getMsg.Q",
	//		dataType : "json",
	//		success : function(data) {
	//			 if(data.auditCnt){
	//			 	$("#auditMenu").html("待审核&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"label label-important\">"+ data.auditCnt +"</span>");
	//			 }
	//		}
	//	});
	//}
</script>