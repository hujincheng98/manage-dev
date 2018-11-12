<#--
root：树对象。
	treeName：节点名称；
	id：节点id，使用复选框时需要使用；
	treeUrl：节点链接（${base+node.treeUrl!}${suffix}）；
	isLeaf：是否叶子节点，主要用于动态加载树，为NULL则检查是否有子节点；
	child：子节点。
treeId：树的ID。在一个页面有多个树时，需要使用。
target：目标。链接打开的目标frame。
showDeep：展开深度。0不展开。
isCheckBox：是否是复选框。
checkBoxName：复选框的名称。
valueContent：值集合，指示复选框是否选中。
isUrl：是否有链接。
suffix：链接后缀。
-->
<#macro treeMenu>
	<#if menus?size gt 0>
		var _menus = {"menus":[
		<#list menus as menu>
			<@resList menu=menu hasChildren=((menu_index?if_exists+1) != menus?size) />
		</#list>
		]};
	</#if>
</#macro>


<#macro resList menu hasChildren>
	<#if menu.resType == 'MENU' && menu.isEnabled>
		{"menuid":"${menu.resId}","icon":"${menu.resIcon}","menuname":"${menu.resName}", "url":"${base}${menu.resUrl}",<#t />
		<#if menu.childrenMenus?size gt 0>
			"menus":[
				<#list menu.children as res>
					<@childList menu=res hasChildren=((res_index?if_exists+1) != menu.childrenMenus?size) />
				</#list>
			]}<#if hasChildren>,</#if>
		<#else>
			}<#if hasChildren>,</#if>
		</#if>
	</#if>
</#macro>

<#macro childList menu hasChildren>
	<#if menu.resType == 'MENU' && menu.isEnabled>
		{"menuid":"${menu.resId}","icon":"${menu.resIcon}","menuname":"${menu.resName}", "url":"${base}${menu.resUrl}"<#t />
		<#if menu.childrenMenus?size gt 0>
			,"child":[
				<#list menu.children as res>
					<@childList menu=res hasChildren=((res_index?if_exists+1) != menu.childrenMenus?size) />
				</#list>
			]}<#if hasChildren>,</#if>
		<#else>
			}<#if hasChildren>,</#if>
		</#if>
	</#if>
</#macro>