<#--
表格列标签：展示数据列。
title string 列标题文本。 undefined 
field string 列字段名称。 undefined 
width number 列的宽度。如果没有定义，宽度将自动扩充以适应其内容。 undefined 
rowspan number 指明将占用多少行单元格（合并行）。 undefined 
colspan number 指明将占用多少列单元格（合并列）。 undefined 
align string 指明如何对齐列数据。可以使用的值有：'left','right','center'。 undefined 
halign string 指明如何对齐列标题。可以使用的值有：'left','right','center'。如果没有指定，则按照align属性进行对齐。（该属性自1.3.2版开始可用）  undefined 
sortable boolean 如果为true，则允许列使用排序。 undefined 
order string 默认排序数序，只能是'asc'或'desc'。（该属性自1.3.2版开始可用）  undefined 
resizable boolean 如果为true，允许列改变大小。 undefined 
hidden boolean 如果为true，则隐藏列。 undefined 
checkbox boolean 如果为true，则显示复选框。该复选框列固定宽度。 undefined 
formatter function 单元格formatter(格式化器)函数，带3个参数：
value：字段值。
rowData：行记录数据。
rowIndex: 行索引。 
-->
<#macro column field title="" code="" value="" rowData="" rowIndex="" width="80" rowspan="" colspan="" align="" halign="" 
	sortable="false" order="" resizable="false" hidden="false" checkbox="false" formatter="">
	
	<#if hidden=="false" && title="" && code=""><td>title and code all not assign!</td><#return></#if>
	
			<th data-options="<#if field!="">field:'${field}',</#if><#t />
							<#if width!="">width:'${width}',</#if><#t />
							<#if rowspan!="">rowspan:'${rowspan}',</#if><#t />
							<#if colspan!="">colspan:'${colspan}',</#if><#t />
							<#if align!="">align:'${align}',</#if><#t />
							<#if halign!="">halign:'${halign}',</#if><#t />
							<#if sortable!="">sortable:${sortable},</#if><#t />
							<#if order!="">order:'${order}',</#if><#t />
							<#if resizable!="">resizable:${resizable},</#if><#t />
							<#if checkbox!="">checkbox:${checkbox},</#if><#t />
							<#if formatter!="">formatter:'${formatter}',</#if><#t />
							<#if value!="">value:'${value}',</#if><#t />
							<#if rowData!="">rowData:'${rowData}',</#if><#t />
							<#if rowIndex!="">rowIndex:'${rowIndex}'</#if><#t />
							<#if hidden!="">hidden:${hidden}</#if><#t />"><#t />
			<#if title!="">${title}<#else><@s.mt code=code text=code/></#if></th><#t />
</#macro>