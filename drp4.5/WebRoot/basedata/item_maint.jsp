<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ page import="java.util.*" %>
<%@ page import="com.bjpowernode.drp.util.*" %>
<%@ page import="com.bjpowernode.drp.basedata.domain.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	String itemNoOrName=request.getParameter("itemNoOrName") == null ? "" : request.getParameter("itemNoOrName");
	PageModel pageModel = (PageModel)request.getAttribute("pageModel");

 %>

<html>
	<head>
		<base href="<%=basePath %>">
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>����ά��</title>
		<link rel="stylesheet" href="style/drp.css">
		<script src="script/windows.js"></script>
		<script type="text/javascript">
	function addItem() {
		//window.self.location = "item_add.html";
		window.self.location = "<%=basePath %>servlet/item/ShowAddItemServlet";
	}
	
	function modifyItem() {
		var selectFlags  = document.getElementsByName("selectFlag");
		var count = 0;
		var j = 0;
		for (var i=0; i<selectFlags.length; i++) {
			if (selectFlags[i].checked) {
			    j = i;
				count++;
			}
		}
		if (count == 0) {
			alert("��ѡ����Ҫ�޸ĵ����ϣ�");
			return;
		}
		if (count > 1) {
			alert("һ��ֻ���޸�һ�����ϣ�");
			return;
		}
		window.self.location = "<%=basePath%>servlet/item/ShowModifyItemServlet?itemNo=" + selectFlags[j].value;
	}
	
/* 	function deleteItem() {
		
	} */
	
	function topPage() {
		window.self.location="<%=basePath %>servlet/item/SearchItemServlet?pageNo=<%=pageModel.getTopPageNo() %>&itemNoOrName=<%=request.getParameter("itemNoOrName") %>";
	}
	
	function previousPage() {
		window.self.location="<%=basePath %>servlet/item/SearchItemServlet?pageNo=<%=pageModel.getPreviousPageNo() %>&itemNoOrName=<%=request.getParameter("itemNoOrName") %>";
	}
	
	function nextPage() {
		window.self.location="<%=basePath %>servlet/item/SearchItemServlet?pageNo=<%=pageModel.getNextPageNo() %>&itemNoOrName=<%=request.getParameter("itemNoOrName") %>";
	}
	
	function bottomPage() {
		window.self.location="<%=basePath %>servlet/item/SearchItemServlet?pageNo=<%=pageModel.getBottomPageNo() %>&itemNoOrName=<%=request.getParameter("itemNoOrName") %>";
	}
	
	function checkAll() {
	
	}
	
	function uploadPic4Item() {
		var selectFlags  = document.getElementsByName("selectFlag");
		var count = 0;
		var j = 0;
		for (var i=0; i<selectFlags.length; i++) {
			if (selectFlags[i].checked) {
			    j = i;
				count++;
			}
		}
		if (count == 0) {
			alert("��ѡ����Ҫ�ϴ�ͼƬ�����ϣ�");
			return;
		}
		if (count > 1) {
			alert("һ��ֻ��Ϊһ�������ϴ�ͼƬ��");
			return;
		}
		window.self.location = "<%=basePath%>servlet/item/ShowItemUploadServlet?itemNo=" + selectFlags[j].value;
	}
	
	function validateForm(from){
		if(window.confirm("�Ƿ�ȷ��ɾ��?")){
			return true;
		}
		return false;
	}
</script>
	</head>

	<body class="body1">

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
							<img src="images/mark_arrow_02.gif" alt="��" width="14"
								height="14">
							&nbsp;
							<b>�������ݹ���&gt;&gt;����ά��</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<form name="itemForm" action="servlet/item/SearchItemServlet">
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="17%" height="29">
							<div align="left">
								���ϴ���/����:
							</div>
						</td>
						<td width="57%">
							<input name="itemNoOrName" type="text" class="text1"
								id="itemNoOrName" size="50" maxlength="50" value="<%=itemNoOrName %>">
						</td>
						<td width="26%">
							<div align="left">
								<input name="btnQuery" type="submit" class="button1"
									id="btnQuery" value="��ѯ">
							</div>
						</td>
					</tr>
					<tr>
						<td height="16">
							<div align="right"></div>
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							<div align="right"></div>
						</td>
					</tr>
				</table>
				</form>
			</div>
			<form action="servlet/item/DelItemServlet" onsubmit="return validateForm(this)">
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				class="rd1" align="center">
				<tr>
					<td nowrap height="10" class="p2">
						������Ϣ
					</td>
					<td nowrap height="10" class="p3">
						&nbsp;<font color="red"><%=request.getParameter("errorMessage") == null ? "": request.getParameter("errorMessage") %></font>
					</td>
				</tr>
			</table>
			
			<table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					<td width="35" class="rd6">
						<input type="checkbox" name="ifAll" onClick="checkAll()">
					</td>
					<td width="155" class="rd6">
						���ϴ���
					</td>
					<td width="155" class="rd6">
						��������
					</td>
					<td width="155" class="rd6">
						���Ϲ��
					</td>
					<td width="155" class="rd6">
						�����ͺ�
					</td>
					<td width="138" class="rd6">
						���
					</td>
					<td width="101" class="rd6">
						������λ
					</td>
				</tr>
				<%
					List itemList =pageModel.getList();
					for(Iterator iter=itemList.iterator();iter.hasNext();){
						Item item = (Item)iter.next();
				 %>
				
				<tr>

					<td class="rd8">
						<input type="checkbox" name="selectFlag" class="checkbox1" value="<%=item.getItemNo() %>">
					</td>
					<td class="rd8">
						<a href="#"
							onClick="window.open('item_detail.html', '������ϸ��Ϣ', 'width=400, height=400, scrollbars=no')"><%=item.getItemNo() %></a>
					</td>
					<td class="rd8">
						<%=item.getItemName() %>
					</td>
					<td class="rd8">
						<%=item.getSpec() %>
					</td>
					<td class="rd8">
						<%=item.getPattern() %>
					</td>
					<td class="rd8">
						<%=item.getItemCategory().getName() %>
					</td>
					<td class="rd8">
						<%=item.getItemUnit().getName() %>
					</td>
				</tr>
				<%
					}
				 %>
			</table>
			<table width="95%" height="30" border="0" align="center"
				cellpadding="0" cellspacing="0" class="rd1">
				<tr>
					<td nowrap class="rd19" height="2" width="36%">
						<div align="left">
							<font color="#FFFFFF">&nbsp;��&nbsp<%=pageModel.getTotalPages() %>&nbspҳ</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#FFFFFF">��ǰ��</font>&nbsp
							<font color="#FF0000"><%=pageModel.getPageNo() %></font>&nbsp
							<font color="#FFFFFF">ҳ</font>
						</div>
					</td>
					<td nowrap class="rd19" width="64%">
						<div align="right">
							<input name="btnTopPage" class="button1" type="button"
								id="btnTopPage" value="|&lt;&lt; " title="��ҳ"
								onClick="topPage()">
							<input name="btnPreviousPage" class="button1" type="button"
								id="btnPreviousPage" value=" &lt;  " title="��ҳ"
								onClick="previousPage()">
							<input name="btnNextPage" class="button1" type="button"
								id="btnNextPage" value="  &gt; " title="��ҳ" onClick="nextPage()">
							<input name="btnBottomPage" class="button1" type="button"
								id="btnBottomPage" value=" &gt;&gt;|" title="βҳ"
								onClick="bottomPage()">
							<input name="btnAdd" type="button" class="button1" id="btnAdd"
								value="���" onClick="addItem()">
							<input name="btnDelete" class="button1" type="submit"
								id="btnDelete" value="ɾ��">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="�޸�" onClick="modifyItem()">
							<input name="btnUpload" class="button1" type="button"
								id="btnUpload" value="�ϴ�ͼƬ" onClick="uploadPic4Item()">
						</div>
					</td>
				</tr>
			</table>
			</from>
	</body>
</html>
