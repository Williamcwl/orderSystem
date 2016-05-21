<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ page import="com.bjpowernode.drp.util.*" %>
<%@ page import="com.bjpowernode.drp.basedata.domain.*" %>
<%@ page import="com.bjpowernode.drp.basedata.manager.*" %>
<%
	int pid = Integer.parseInt(request.getParameter("pid"));
	String command = request.getParameter("command");
	if(Constants.ADD.equals(command)){
		String name = request.getParameter("");
		Client region = new Client();
		region.setPid(pid);
		region.setName(request.getParameter("name"));
		region.setIsLeaf(Constants.NO);
		region.setIsClient(Constants.YES);
		ClientManager.getInstance().addClientOrRegion(region);
		out.println("添加区域成功");
	}
	

 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" href="../style/drp.css" />
		<script src="../script/client_validate.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />
		<title>添加区域节点</title>
		<script type="text/javascript">
			function validateForm(form){
				if(trim(form.name.value).length == 0){
					alert("区域名称不能为空");
					form.name.focus();
					return false;
				}
				return true;
			}
		</script>
	</head>

	<body class="body1">
		<form name="regionForm" method="post" action="client_node_add.jsp" onsubmit="return validateForm(this)">
			<input type="hidden" name="command" value="<%=Constants.ADD %>"/>
			<input type="hidden" name="pid" value="<%=pid %>"/>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				height="8">
				<tr>
					<td width="522" class="p1" height="2" nowrap="nowrap">
						<img src="../images/mark_arrow_03.gif" width="14" height="14" />
						&nbsp;
						<b>基础数据管理&gt;&gt;分销商维护&gt;&gt;添加区域节点</b>
					</td>
				</tr>
			</table>
			<hr width="97%" align="center" size="0" />
			<p></p>
			<p></p>
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="213">
						<div align="right">
							<font color="#FF0000">*</font>区域名称：
						</div>
					</td>
					<td width="410">
						<label>
							<input name="name" type="text" class="text1" id="name" />
						</label>
					</td>
				</tr>
			</table>
			<p></p>
			<label>
				<br />
			</label>
			<hr />
			<p align="center">
				<input name="btnAdd" class="button1" type="submit" value="添加" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="btnBack" class="button1" type="button" value="返回"
					onclick="history.go(-1)" />
			</p>
		</form>
	</body>
</html>
