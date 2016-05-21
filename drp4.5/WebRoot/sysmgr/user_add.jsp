<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ page import="com.bjpowernode.drp.sysmgr.domain.*" %> 
<%@ page import="com.bjpowernode.drp.sysmgr.manager.*" %>   
<%
	//request.setCharacterEncoding("GB18030");
	String command = request.getParameter("command");
	String userId = "";
	String userName = "";
	String contactTel = "";
	String email = "";
	//Thread.currentThread().sleep(3000);
	//if (command != null && command.equals("add")) {
	if ("add".equals(command)) {	
		if (UserManager.getInstance().findUserById(request.getParameter("userId")) == null) { 
			User user = new User();
			user.setUserId(request.getParameter("userId"));
			user.setUserName(request.getParameter("userName"));
			user.setPassword(request.getParameter("password"));
			user.setContactTel(request.getParameter("contactTel"));
			user.setEmail(request.getParameter("email"));
			
			UserManager.getInstance().addUser(user);
			out.println("添加用户成功！");
		}else {
			userId = request.getParameter("userId");
			userName = request.getParameter("userName");
			contactTel = request.getParameter("contactTel");
			email = request.getParameter("email");
			out.println("用户代码已经存在，代码=【" + request.getParameter("userId") + "】");		
		}
	}
%>    
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>添加用户</title>
		<link rel="stylesheet" href="../style/drp.css">
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
	function goBack() {
		window.self.location="user_maint.jsp"
	}
	
	function addUser() {
		var userIdField = document.getElementById("userId");
		//用户代码不能为空
		if (trim(userIdField.value) == "" ) {
			alert("用户代码不能为空！");
			userIdField.focus();
			return;
		}
		//用户代码至少四个字符
		if (trim(userIdField.value).length < 4) {
			alert("用户代码至少4个字符！");
			userIdField.focus();
			return;
		}
		
		//第一个字符必须是字母
		if (!(trim(userIdField.value).charAt(0) >='a' && trim(userIdField.value).charAt(0) <='z')) {
			alert("用户代码首字符必须为字母！");
			userIdField.focus();
			return;
		}
		
		//判断用户代码只能是数字或字母，为4~6位
		/*
		if (!(trim(userIdField.value).length >=4 && trim(userIdField.value).length <=6)) {
			alert("用户代码只能为4~6位！！");
			userIdField.focus();
			return;
		}
		for (var i=0; i<trim(userIdField.value).length; i++) {
			var c = trim(userIdField.value).charAt(i);
			if (!((c >= '0' && c <='9') || (c >='a' && c <='z') || (c >='A' && c <='Z'))) {
				alert("用户代码必须为数字和字母！");
				userIdField.focus();
				return;
			}
		}
		*/
		
		//采用正则表达式判断用户代码只能是数字或字母，为4~6位（中午作业）
		var re = new RegExp(/^[a-zA-Z0-9]{4,6}$/);
		if (!re.test(trim(userIdField.value))) {
			alert("用户代码必须为数字或字母,只能为4~6位！");
			userIdField.focus();
			return;
		}
		
		
		//用户名称必须输入，不能和用户代码不能为空一致（中午作业）
		if (trim(document.getElementById("userName").value).length == 0) {
			alert("用户名称不能为空！");
			document.getElementById("userName").focus();
			return;
		}
		
		//密码至少6位（中午作业）
		if (trim(document.getElementById("password").value).length < 6) {
			alert("密码至少6位！");
			document.getElementById("password").focus();
			return;
		}
		//如果联系电话不为空，进行判断，判断规则：都为数字，采用两种方式：1、采用正则，2、不采用正则（中午作业）
		var contactTelField = document.getElementById("contactTel");
		//alert("a" + trim(contactTelField.value) + "a");
		//不采用正则
		/*
		if (trim(contactTelField.value) != "") {
			
			for (var i=0; i<trim(contactTelField.value).length; i++) {
				var c = trim(contactTelField.value).charAt(i);
				if (!(c >= '0' && c <= '9')) {
					alert("电话号码不合法！");
					contactTelField.focus();
					return;
				}
			}
		}
		*/
		if (trim(contactTelField.value) != "") { 
			//采用正则
			re.compile(/^[0-9]*$/);
			if (!re.test(trim(contactTelField.value))) {
				alert("电话号码不合法！");
				contactTelField.focus();
				return;
			}	
		}
				
		//如果emial不能空，进行判断，判断规则：只要包含@即可，@最好不再最前面和最后面（中午作业）
		var emailField = document.getElementById("email");
		if (trim(emailField.value).length != 0) {
			var emailValue = trim(emailField.value);
			if ((emailValue.indexOf("@") == 0) || (emailValue.indexOf("@") == (emailValue.length - 1))) {
				alert("email地址不正确！");
				emailField.focus();
				return;				
			}
			if (emailValue.indexOf("@") < 0) {
				alert("email地址不正确！");
				emailField.focus();
				return;				
			}
		}
		
		//alert("a" + document.getElementById("spanUserId").innerHTML + "a");
		if(document.getElementById("spanUserId").innerHTML !=""){
			alert("用户代码已经存在");
			userIdField.focus();
			return;
		}
		
		/*
		document.getElementById("userForm").action="user_add.jsp";
		document.getElementById("userForm").method="post";
		document.getElementById("userForm").submit();
		*/
		
		//等同上面的写法
		with (document.getElementById("userForm")) {
			action="user_add.jsp";
			method="post";
			submit();
		}
	}
	
	function init() {
		document.getElementById("userId").focus();
	}
	
	function userIdOnKeyPress() {
		//alert(window.event.keyCode);
		if (!(event.keyCode >= 97 && event.keyCode <=122)) {
			event.keyCode = 0;
		}
	}
	
	function document.onkeydown() {
		//alert(window.event.keyCode);
		if (window.event.keyCode == 13 && window.event.srcElement.type != 'button') {
			window.event.keyCode = 9; 
		}
	}
	
	var xmlHttp;
	
	function validate(field) {
		if (trim(field.value).length != 0) {
			var xmlHttp = null;
			//表示当前浏览器不是ie,如ns,firefox
			if(window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			var url = "user_validate.jsp?userId=" + trim(field.value) + "&time=" + new Date().getTime();
			
			//设置请求方式为GET，设置请求的URL，设置为异步提交
			xmlHttp.open("GET", url, true);
			
			//将方法地址复制给onreadystatechange属性
			//类似于电话号码
			xmlHttp.onreadystatechange=function() {
				//Ajax引擎状态为成功
				if (xmlHttp.readyState == 4) {
					//HTTP协议状态为成功
					if (xmlHttp.status == 200) {
						if (trim(xmlHttp.responseText) != "") {
							//alert(xmlHttp.responseText);
							document.getElementById("spanUserId").innerHTML = "<font color='red'>" + xmlHttp.responseText + "</font>"
						}else {
							document.getElementById("spanUserId").innerHTML = "";
						}
					}else {
						alert("请求失败，错误码=" + xmlHttp.status);
					}
				}
			};
			
			//将设置信息发送到Ajax引擎
			xmlHttp.send(null);
		} else {
			document.getElementById("spanUserId").innerHTML = "";
		}	
	}
</script>
	</head>

	<body class="body1" onload="init()">
		<form name="userForm" target="_self" id="userForm">
			<input type="hidden" name="command" value="add">
			<div align="center">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>&nbsp;
							
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="25">
					<tr>
						<td width="522" class="p1" height="25" nowrap>
							<img src="../images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>系统管理&gt;&gt;用户维护&gt;&gt;添加</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>用户代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="userId" type="text" class="text1" id="userId"
								size="10" maxlength="10" onkeypress="userIdOnKeyPress()" value="<%=userId %>" onblur="validate(this)">
								<span id="spanUserId"></span>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>用户名称:&nbsp;
							</div>
						</td>
						<td>
							<input name="userName" type="text" class="text1" id="userName"
								size="20" maxlength="20" value="<%=userName %>">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>密码:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input name="password" type="password" class="text1"
									id="password" size="20" maxlength="20">
							</label>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								联系电话:&nbsp;
							</div>
						</td>
						<td>
							<input name="contactTel" type="text" class="text1"
								id="contactTel" size="20" maxlength="20" value="<%=contactTel %>">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								email:&nbsp;
							</div>
						</td>
						<td>
							<input name="email" type="text" class="text1" id="email"
								size="20" maxlength="20" value="<%=email %>">
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="button" id="btnAdd"
						value="添加" onClick="addUser()">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick="goBack()" />
				</div>
			</div>
		</form>
	</body>
</html>
