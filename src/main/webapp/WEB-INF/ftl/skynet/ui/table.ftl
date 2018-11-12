<#--
表格标签：用于显示列表数据。
	value：列表数据，可以是Pagination也可以是List。
	class：table的class样式。默认"pn-ltable"。
	sytle：table的style样式。默认""。
	width：表格的宽度。默认100%。
-->
<#macro table id="" listAction="v_list.do" class="easyui-datagrid" style="" width="100%" toolbar="" title=""
	url="" method="POST" idField="" autoRowHeight="true" pagination="true" showFooter="false" showHeader="false" rownumbers="true"
	fitColumns="true" singleSelect="true" border="true" striped="true" nowrap="true" collapsible="false" selectOnCheck="" checkOnSelect=""
	dragSelection="" remoteSort="false" onDblClickRow="">
	<table id="${id}" class="${class}" style="${style}" width="${width}" cellpadding="0" border="0" <#t />
	data-options="<#if url!="">url:'${url}',</#if><#t />
					<#if title!="">title:'${title}',</#if><#t />
					<#if rownumbers!="">rownumbers:'${rownumbers}',</#if><#t />
					<#if method!="">method:'${method}',</#if><#t />
					<#if idField!="">idField:'${idField}',</#if><#t />
					<#if toolbar!="">toolbar:'#${toolbar}',</#if><#t />
					<#if autoRowHeight!="">autoRowHeight:${autoRowHeight},</#if><#t />
					<#if pagination!="">pagination:${pagination},</#if><#t />
					<#if showFooter!="">showFooter:${showFooter},</#if><#t />
					<#if showHeader!="">showHeader:${showHeader},</#if><#t />
					<#if fitColumns!="">fitColumns:${fitColumns},</#if><#t />
					<#if singleSelect!="">singleSelect:${singleSelect},</#if><#t />
					<#if selectOnCheck!="">selectOnCheck:${selectOnCheck},</#if><#t />
					<#if checkOnSelect!="">checkOnSelect:${checkOnSelect},</#if><#t />
					<#if border!="">border:${border},</#if><#t />
					<#if striped!="">striped:${striped},</#if><#t />
					<#if nowrap!="">nowrap:${nowrap},</#if><#t />
					<#if dragSelection!="">dragSelection:${dragSelection},</#if><#t />
					<#if remoteSort!="">remoteSort:${remoteSort},</#if><#t />
					<#if onDblClickRow!="">onDblClickRow:${onDblClickRow},</#if><#t />
					<#if collapsible!="">collapsible:${collapsible}</#if>"><#lt />
		<thead>
			<tr>
				<#nested />
			</tr>
		</thead>
	</table>
</#macro>