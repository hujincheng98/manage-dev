<!--
	当前位置：
	list ： 当前位置的国际化标示
	eg：["golabl.index", "user.list", "user.add"]
-->
<#macro position list>
	<ol class="breadcrumb">
		<#if (list?size>0)>
			<#list list as code>
			<li class="active"><@s.m code /></li>
			</#list>
		</#if>
	</ol>
</#macro>