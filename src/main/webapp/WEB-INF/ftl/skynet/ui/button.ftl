<#--
<input type="button"/>
-->
<#macro button
	value="" code="" url=""  dataTarget=""
	id="" name="" class="btn btn-primary" style="" size="" title="" disabled="" tabindex="" accesskey=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange="">
	
<button <#t/>
<#if dataTarget!="">data-toggle="modal" data-target="#${dataTarget}"</#if><#t/>
<#if value!=""> value="${value}"</#if><#t/>
<#if id!=""> id="${id}"</#if><#t/>
<#include "common-attributes.ftl"/><#rt/>
<#include "scripting-events.ftl"/><#rt/>
><#rt/>
<#nested /><#t/><#if code!=""><@s.m code/></#if>
</button>
</#macro>

<#--
<input type="submit"/>
-->

<#macro submit
	value="" code=""
	id="" name="" class="btn btn-default" style="" size="" title="" disabled="" tabindex="" accesskey=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<input type="submit"<#rt/>
<#if value!=""> value="${value}"<#elseif code!=""> value="<@s.m code/>"</#if><#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
 class="${class}"<#rt/>
<#include "common-attributes.ftl"/><#rt/>
<#include "scripting-events.ftl"/><#rt/>
/><#rt/>
</#macro>

<#--
<input type="reset"/>
-->
<#macro reset
	value="" code=""
	id="" name="" class="btn btn-default" style="" size="" title="" disabled="" tabindex="" accesskey=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<input type="reset"<#rt/>
<#if value!=""> value="${value}"<#elseif code!=""> value="<@s.m code/>"</#if><#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
 class="${class}"<#rt/>
<#include "common-attributes.ftl"/><#rt/>
<#include "scripting-events.ftl"/><#rt/>
/><#rt/>
</#macro>