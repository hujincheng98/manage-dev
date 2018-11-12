<#--
	placeholder : 默认显示的内容
-->
<#macro text code="" type="text"
	maxlength="" readonly="" value="" width="100%" height="32px" class="form-control" style="" 
	label="" noHeight="false" required="false" colspan="" help="" helpPosition="2" colon=":" hasColon="true"
	id="" name="" size="" title="" disabled="" tabindex="" accesskey=""
	vld="" equalTo="" maxlength="" minlength="" max="" min="" rname="" rvalue=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
	<#include "control.ftl"/><#rt/>
	<input 
	<#if type!=""> type="${type}"</#if><#rt/>
	<#if id!=""> id="${id}"</#if><#rt/>
	<#if code!=""> placeholder="<@s.m code/>"</#if><#rt/>
	<#if class!=""> class="${class}"</#if><#rt/>
	<#if maxlength!=""> maxlength="${maxlength}"</#if><#rt/>
	<#if max?string!=""> max="${max}"</#if><#rt/>
	<#if min?string!=""> min="${min}"</#if><#rt/>
	<#if readonly!=""> readonly="${readonly}"</#if><#rt/>
	<#if rname!=""> rname="${rname}"</#if><#rt/>
	<#if rvalue!=""> rvalue="${rvalue}"</#if><#rt/>
	<#if width!=""> width="${width}"</#if><#rt/>
	<#if height!=""> height="${height}"</#if><#rt/>
	<#if title != "" && title?string!=""> title="<@s.m '${title}'/>"</#if><#rt/>
	<#if value != "" && value?string!=""> value="${value?html}"</#if><#rt/>
	<#include "common-attributes.ftl"/><#rt/>
	<#include "scripting-events.ftl"/><#rt/>
	/><#rt/>
	<#include "control-close.ftl"/><#rt/>
</#macro>
