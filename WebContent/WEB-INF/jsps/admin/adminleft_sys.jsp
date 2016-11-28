<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 系统管理员菜单 -->
<script type="text/javascript">
	//$(function(){
	//    var menuId;
	//    var subMenuId;
	//    var tab = $("#tab").val();
	//    if(tab && tab.length > 0){
	//    	var menuIds = tab.split(",");
	//	    if(menuIds.length ==2){
	//	    	menuId = menuIds[0];
	//	    	subMenuId = menuIds[1];
	//	    }else{
	//	    	menuId = menuIds[0];
	//	    }
		    
	//	    if(subMenuId != null){
	//	    	$("#" + subMenuId).addClass("active");
	//	    	$("#" + menuId).addClass("open");
	//	    }else{
	//	    	$("#" + menuId).addClass("active");
	//	    }
	//    }
	//});
	
</script>
<style>
#sidebar ul li ul li{
text-indent:30px;}

</style>
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> 后台管理系统</a>
  <ul>
 
        <li class="submenu" id="m2"> <a href="javascript:void(0);"><i class="icon icon-th-list"></i> <span>公众号管理</span> </a>
      <ul style="display: block;">
      <!-- 
       <li id="msub22"><a href="<%=request.getContextPath()%>/admin/fx_fxAuditList.Q" id="auditMenu"> 待审核 </a></li>
       -->
       <li id="msub21"><a href="<%=request.getContextPath()%>/admin/fx_fxMember.Q">公众号管理</a></li>
      </ul>
    </li>
  </ul>
</div>
