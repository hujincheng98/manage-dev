<#macro buttonArea id="" width="100%">
	<div style="margin:10px 0"<#if id!="">id="${id}"</#if>>
		<#nested />
	</div>
</#macro>