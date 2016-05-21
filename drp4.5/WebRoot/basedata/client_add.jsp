<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ page import="java.util.*" %>
<%@ page import="com.bjpowernode.drp.util.*" %>
<%@ page import="com.bjpowernode.drp.basedata.domain.*" %>
<%@ page import="com.bjpowernode.drp.basedata.manager.*" %>
<%@ page import="com.bjpowernode.drp.util.datadict.manager.*" %>
<%@ page import="com.bjpowernode.drp.util.datadict.domain.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	int pid = Integer.parseInt(request.getParameter("pid"));
	List<ClientLevel> clientLevelList = DataDictManager.getInstance().findClientLevelList();
	
	String command = request.getParameter("command");
	if (Constants.ADD.equals(command)) {
		Client client = new Client();
		client.setPid(pid);
		client.setAddress(request.getParameter("address"));
		client.setBankAcctNo(request.getParameter("bankAcctNo"));
		ClientLevel cl = new ClientLevel();
		cl.setId(request.getParameter("clientLevel"));
		client.setClientLevel(cl);
		client.setContactTel(request.getParameter("contactTel"));
		client.setName(request.getParameter("clientName"));
		client.setClientId(request.getParameter("clientId"));
		client.setZipCode(request.getParameter("zipCode"));
		client.setIsLeaf(Constants.YES);
		client.setIsClient(Constants.YES);
		
		ClientManager.getInstance().addClientOrRegion(client);
		out.println("��ӷ����̳ɹ���");
	}
%>
<html>
	<head>
		<base href="<%=basePath %>">
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>��ӷ�����</title>
		<link rel="stylesheet" href="style/drp.css">
		<script src="script/client_validate.js"></script>
		<script type="text/javascript">
			function validateClientId(field) {
				if (trim(field.value) != "") {
					var xmlHttp = null;
					//��ʾ��ǰ���������ie,��ns,firefox
					if(window.XMLHttpRequest) {
						xmlHttp = new XMLHttpRequest();
					} else if (window.ActiveXObject) {
						xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
					}
					var url = "servlet/ClientIdValidateServlet?clientId=" + trim(field.value);
					xmlHttp.open("GET", url, true);
					xmlHttp.onreadystatechange=function() {
						if (xmlHttp.readyState == 4) {
							if (xmlHttp.status == 200) {
								if (trim(xmlHttp.responseText) != "") {
									document.getElementById("spanClientId").innerHTML = "<font color='red'>" + xmlHttp.responseText + "</font>" 
								}else {
									document.getElementById("spanClientId").innerHTML = "";
								}
							}else {
								alert("����ʧ�ܣ�������=��" + xmlHttp.status + "��");
							}
						}
					};
					xmlHttp.send(null);
				}else {
					document.getElementById("spanClientId").innerHTML = "<font color='red'>�����̴��벻��Ϊ��</font>";
					//document.getElementById("imgClientId").src="images/reset2.gif";
				}
			}
			
			function validateClientName(field){
				if(trim(field.value).length == 0){
					document.getElementById("spanClientName").innerHTML = "<font color='red'>���������Ʋ���Ϊ��</font>";
				}else{
					document.getElementById("spanClientName").innerHTML = "";
				}
					
			}
			
			
			function validateForm(form){
				if(document.getElementById("spanClientId").innerHTML != ""){
					alert("�����̴��벻��Ϊ��")��
					document.getElementById("clientId").focus();
					return false;
				}
				if(document.getElementById("spanClientName").innerHTML != ""){
					alert("���������Ʋ���Ϊ��")��
					document.getElementById("clientName").focus();
					return false;
				}
				return true;
			}
		</script>
	</head>

	<body class="body1">
		<form action="basedata/client_add.jsp" method="post" onsubmit="return validateForm(this)">
			<input type="hidden" name="pid" value="<%=pid %>">
			<input type="hidden" name="command" value="<%=Constants.ADD %>">
			<div align="center">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="8">
					<tr>
						<td width="522" class="p1" height="2" nowrap>
							<img src="images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>�������ݹ���&gt;&gt;������ά��&gt;&gt;��ӷ�����</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>�����̴���:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="clientId" type="text" class="text1" id="clientId"
								size="10" maxlength="10" onblur="validateClientId(this)">
								<span id="spanClientId"></span>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>����������:&nbsp;
							</div>
						</td>
						<td>
							<input name="clientName" type="text" class="text1"
								id="clientName" size="40" maxlength="40" onblur="validateClientName(this)">
								<span id="spanClientName"></span>
						</td>
					</tr>
					<tr>
						<td height="15">
							<div align="right">
								<font color="#FF0000">*</font>����������:&nbsp;
							</div>
						</td>
						<td>
							<select name="clientLevel" class="select1" id="clientLevel">
								<%
									for(int i=0;i<clientLevelList.size();i++){
										ClientLevel cl = clientLevelList.get(i);
									
								 %>
								 	<option value="<%=cl.getId() %>"><%=cl.getName() %></option>
								 <%
								 	}
								  %>
							</select>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								�����ʺ�:&nbsp;
							</div>
						</td>
						<td>
							<input name="bankAcctNo" type="text" class="text1"
								id="bankAcctNo" size="10" maxlength="10">
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
								id="contactTel" size="10" maxlength="10">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								��ַ:&nbsp;
							</div>
						</td>
						<td>
							<input name="address" type="text" class="text1" id="address"
								size="10" maxlength="10">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								�ʱ�:&nbsp;
							</div>
						</td>
						<td>
							<input name="zipCode" type="text" class="text1" id="zipCode"
								size="10" maxlength="10">
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="submit" id="btnAdd"
						value="���">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="����" onclick="history.go(-1)" />
				</div>
			</div>
		</form>
	</body>
</html>
