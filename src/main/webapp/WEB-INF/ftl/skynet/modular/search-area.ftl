<#macro searchArea id="searchArea" tableId="" width="100%">
	<div class="row" id="${id}">
		<#nested />
		<@p.button code="resource.search" id="${id}Button" />
	</div>
</#macro>