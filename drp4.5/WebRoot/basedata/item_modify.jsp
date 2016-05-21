<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ page import="java.util.*" %>
<%@ page import="com.bjpowernode.drp.basedata.domain.*" %>
<%@ page import="com.bjpowernode.drp.util.datadict.domain.*" %>
<%

	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	Item item = (Item)request.getAttribute("item");
	List itemCategoryList = (List)request.getAttribute("itemCategoryList");
	List itemUnitList = (List)request.getAttribute("itemUnitList");
 %>
<html>
	<head>
		<base href="<%=basePath %>">
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>修改物料</title>
		<link rel="stylesheet" href="style/drp.css">
		<script src="script/client_validate.js"></script>
		<script type="text/javascript">
			function validateForm(form){
				
			}
		</script>
	</head>

	<body class="body1">
		<form name="itemForm" target="_self" id="itemForm" action="servlet/item/ModifyItemServlet" method="post" onsubmit="return validateForm(this)">
			<div align="center">
				<table width="95%" height="21" border="0" cellpadding="2"
					cellspacing="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="25">
					<tr>
						<td width="522" class="p1" height="25" nowrap>
							<img src="images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;物料维护&gt;&gt;修改</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								物料代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="itemNo" type="text" class="text1" id="itemNo"
								size="10" maxlength="10" readonly="true" value="<%=item.getItemNo() %>">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>物料名称:&nbsp;
							</div>
						</td>
						<td>
							<input name="itemName" type="text" class="text1" id="itemName"
								size="20" maxlength="20" value="<%=item.getItemName() %>">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								物料规格:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input name="spec" type="text" class="text1" id="spec"
									size="20" maxlength="20" value="<%=item.getSpec() %>">
							</label>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								物料型号:&nbsp;
							</div>
						</td>
						<td>
							<input name="pattern" type="text" class="text1" id="pattern"
								size="20" maxlength="20" value="<%=item.getPattern() %>">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>类别:&nbsp;
							</div>
						</td>
						<td>
							<select name="category" class="select1" id="category">
								<%
									for(int i=0;i<itemCategoryList.size();i++){
										ItemCategory ic = (ItemCategory)itemCategoryList.get(i);
										String selectdString = "";
										if(item.getItemCategory().getId().equals(ic.getId())){
											selectdString = "selected";
										}
								 %>
								 	<option value="<%=ic.getId() %>" <%=selectdString %>><%=ic.getName() %></option>
								 <%
								 	}
								  %>
							</select>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>计量单位:&nbsp;
							</div>
						</td>
						<td>
							<select name="unit" class="select1" id="unit">
								<%
									for(int i=0;i<itemUnitList.size();i++){
										ItemUnit iu = (ItemUnit)itemUnitList.get(i);
										String selectdString = "";
										if(item.getItemUnit().getId().equals(iu.getId())){
											selectdString = "selected";
										}
								 %>
								 	<option value="<%=iu.getId() %>" <%=selectdString %>><%=iu.getName() %></option>
								 <%
								 	}
								  %>
							</select>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnModify" class="button1" type="submit"
						id="btnModify" value="修改">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnModify" class="button1" type="button"
						id="btnModify" value="返回" onClick="history.go(-1)">
				</div>
			</div>
		</form>
	</body>
</html>
