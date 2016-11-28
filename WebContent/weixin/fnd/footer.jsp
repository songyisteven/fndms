﻿<%@ page language="java" pageEncoding="UTF-8"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<footer>
    <nav class="nav">
        <!-- 
        <a href="<%=request.getContextPath()%>/drug/index_toIndex.Q">
            <ul>
                <li class="iconfont icon-yongyao"></li>
                <li>找新药</li>
            </ul>
        </a>
        <a href="<%=request.getContextPath()%>/drug/index_getMoreDisease.Q">
            <ul>
                <li class="iconfont icon-huibaojilu"></li>
                <li>要推荐</li>
            </ul>
        </a>
        <a href="javascript:void(0);" onclick="contactDKF();return false;">
            <ul>
                <li class="iconfont icon-8"></li>
                <li>要咨询</li>
            </ul>
        </a>
      	 <a href="<%=request.getContextPath()%>/drug/user_userCenter.Q">
            <ul>
                <li class="iconfont icon-gerenzhongxin1"></li>
                <li>我的</li>
            </ul>
        </a>
         -->
    </nav>
</footer>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?ec82594d63e2a8ead0d208564bba4c0e";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<script>
(function (doc, win) {
var docEl = doc.documentElement,
resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
recalc = function () {
var clientWidth = docEl.clientWidth;
if (!clientWidth) return;
docEl.style.fontSize = 16 * (clientWidth / 320) + 'px';
};

if (!doc.addEventListener) return;
win.addEventListener(resizeEvt, recalc, false);
doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);
</script>
<style>
<!--
.dialog-class {margin: 10px auto 0 auto;}
.dialog-class .layui-layer-title{
	background:#f0f0f0; color:#7e147e; text-align:left; border:none;font-size:14px
}
.dialog-class .layui-layer-content{
	font-size:14px
}
.dialog-class .layui-layer-btn .layui-layer-btn0 .layui-layer-btn1{
	text-align:center;
	font-size:14px;
	margin: 10px auto 0 auto;
	padding: 2px 26px;
	background: #f3d5f5;
	color: #db99df;
	display: table;
	border-radius: 100px;
	border: none;
}
-->
</style>
<%-- <script src="<%=request.getContextPath()%>/weixin/drug/js/jquery-1.12.3.js"></script> --%>
<script src="<%=request.getContextPath()%>/weixin/drug/js/dialog/layer.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/weixin/js/wxjs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common/config.js"></script>
<script>
layer.config({
  skin: 'dialog-class'
});
	
function showDialog(){
	layer.confirm('您好，您已接入多客服，请点击左下角后输入文字或者语音，客服人员将及时与您取得联系。', {
		  title:'咨询提示',
		  closeBtn:0,
		  btn: ['转到咨询']
		}, function(){
		  // 回调处理写在这里
		  //layer.msg('关闭回调', {icon: 1});
			wx.closeWindow();	  
		}
	);
}
//联系多客服
function contactDKF(){
	layer.open({
		  content: '确认要咨询在线客服吗？'
		  ,btn: ['是的', '否' ]
		  ,yes: function(index, layero){
		    //按钮【按钮一】的回调
		    //http://drug.yin-teng.com/zysq/wx/wx_verify.Q 
		    $.ajax({
				url: "<%=request.getContextPath()%>/wx/wx_contactDKF.Q",
				dataType:'json',
				success:function(data){
					//alert(data.msg);
					if(data.msg == "ok"){
						showDialog();
					}else{
						layer.msg('多客服接入失败，请稍后重试。', {icon: 2});
					}
				}
			});
		    
		    layer.close(index);
		  },btn2: function(index, layero){
			  layer.close(index);
		  } 
		  ,cancel: function(){ 
		    //右上角关闭回调
		  }
		});
}


//微信是否初始化过,接入微信JS API
$(document).ready(function(){
	//初始化参数
	initWxParams();
});
/////////////////////////////////////////////////////////////////////////
</script>