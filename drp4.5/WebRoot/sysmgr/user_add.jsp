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
			out.println("����û��ɹ���");
		}else {
			userId = request.getParameter("userId");
			userName = request.getParameter("userName");
			contactTel = request.getParameter("contactTel");
			email = request.getParameter("email");
			out.println("�û������Ѿ����ڣ�����=��" + request.getParameter("userId") + "��");		
		}
	}
%>    
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>����û�</title>
		<link rel="stylesheet" href="../style/drp.css">
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
	function goBack() {
		window.self.location="user_maint.jsp"
	}
	
	function addUser() {
		var userIdField = document.getElementById("userId");
		//�û����벻��Ϊ��
		if (trim(userIdField.value) == "" ) {
			alert("�û����벻��Ϊ�գ�");
			userIdField.focus();
			return;
		}
		//�û����������ĸ��ַ�
		if (trim(userIdField.value).length < 4) {
			alert("�û���������4���ַ���");
			userIdField.focus();
			return;
		}
		
		//��һ���ַ���������ĸ
		if (!(trim(userIdField.value).charAt(0) >='a' && trim(userIdField.value).charAt(0) <='z')) {
			alert("�û��������ַ�����Ϊ��ĸ��");
			userIdField.focus();
			return;
		}
		
		//�ж��û�����ֻ�������ֻ���ĸ��Ϊ4~6λ
		/*
		if (!(trim(userIdField.value).length >=4 && trim(userIdField.value).length <=6)) {
			alert("�û�����ֻ��Ϊ4~6λ����");
			userIdField.focus();
			return;
		}
		for (var i=0; i<trim(userIdField.value).length; i++) {
			var c = trim(userIdField.value).charAt(i);
			if (!((c >= '0' && c <='9') || (c >='a' && c <='z') || (c >='A' && c <='Z'))) {
				alert("�û��������Ϊ���ֺ���ĸ��");
				userIdField.focus();
				return;
			}
		}
		*/
		
		//����������ʽ�ж��û�����ֻ�������ֻ���ĸ��Ϊ4~6λ��������ҵ��
		var re = new RegExp(/^[a-zA-Z0-9]{4,6}$/);
		if (!re.test(trim(userIdField.value))) {
			alert("�û��������Ϊ���ֻ���ĸ,ֻ��Ϊ4~6λ��");
			userIdField.focus();
			return;
		}
		
		
		//�û����Ʊ������룬���ܺ��û����벻��Ϊ��һ�£�������ҵ��
		if (trim(document.getElementById("userName").value).length == 0) {
			alert("�û����Ʋ���Ϊ�գ�");
			document.getElementById("userName").focus();
			return;
		}
		
		//��������6λ��������ҵ��
		if (trim(document.getElementById("password").value).length < 6) {
			alert("��������6λ��");
			document.getElementById("password").focus();
			return;
		}
		//�����ϵ�绰��Ϊ�գ������жϣ��жϹ��򣺶�Ϊ���֣��������ַ�ʽ��1����������2������������������ҵ��
		var contactTelField = document.getElementById("contactTel");
		//alert("a" + trim(contactTelField.value) + "a");
		//����������
		/*
		if (trim(contactTelField.value) != "") {
			
			for (var i=0; i<trim(contactTelField.value).length; i++) {
				var c = trim(contactTelField.value).charAt(i);
				if (!(c >= '0' && c <= '9')) {
					alert("�绰���벻�Ϸ���");
					contactTelField.focus();
					return;
				}
			}
		}
		*/
		if (trim(contactTelField.value) != "") { 
			//��������
			re.compile(/^[0-9]*$/);
			if (!re.test(trim(contactTelField.value))) {
				alert("�绰���벻�Ϸ���");
				contactTelField.focus();
				return;
			}	
		}
				
		//���emial���ܿգ������жϣ��жϹ���ֻҪ����@���ɣ�@��ò�����ǰ�������棨������ҵ��
		var emailField = document.getElementById("email");
		if (trim(emailField.value).length != 0) {
			var emailValue = trim(emailField.value);
			if ((emailValue.indexOf("@") == 0) || (emailValue.indexOf("@") == (emailValue.length - 1))) {
				alert("email��ַ����ȷ��");
				emailField.focus();
				return;				
			}
			if (emailValue.indexOf("@") < 0) {
				alert("email��ַ����ȷ��");
				emailField.focus();
				return;				
			}
		}
		
		//alert("a" + document.getElementById("spanUserId").innerHTML + "a");
		if(document.getElementById("spanUserId").innerHTML !=""){
			alert("�û������Ѿ�����");
			userIdField.focus();
			return;
		}
		
		/*
		document.getElementById("userForm").action="user_add.jsp";
		document.getElementById("userForm").method="post";
		document.getElementById("userForm").submit();
		*/
		
		//��ͬ�����д��
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
			//��ʾ��ǰ���������ie,��ns,firefox
			if(window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			var url = "user_validate.jsp?userId=" + trim(field.value) + "&time=" + new Date().getTime();
			
			//��������ʽΪGET�����������URL������Ϊ�첽�ύ
			xmlHttp.open("GET", url, true);
			
			//��������ַ���Ƹ�onreadystatechange����
			//�����ڵ绰����
			xmlHttp.onreadystatechange=function() {
				//Ajax����״̬Ϊ�ɹ�
				if (xmlHttp.readyState == 4) {
					//HTTPЭ��״̬Ϊ�ɹ�
					if (xmlHttp.status == 200) {
						if (trim(xmlHttp.responseText) != "") {
							//alert(xmlHttp.responseText);
							document.getElementById("spanUserId").innerHTML = "<font color='red'>" + xmlHttp.responseText + "</font>"
						}else {
							document.getElementById("spanUserId").innerHTML = "";
						}
					}else {
						alert("����ʧ�ܣ�������=" + xmlHttp.status);
					}
				}
			};
			
			//��������Ϣ���͵�Ajax����
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
							<b>ϵͳ����&gt;&gt;�û�ά��&gt;&gt;���</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>�û�����:&nbsp;
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
								<font color="#FF0000">*</font>�û�����:&nbsp;
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
								<font color="#FF0000">*</font>����:&nbsp;
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
								��ϵ�绰:&nbsp;
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
						value="���" onClick="addUser()">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="����" onClick="goBack()" />
				</div>
			</div>
		</form>
	</body>
</html>
