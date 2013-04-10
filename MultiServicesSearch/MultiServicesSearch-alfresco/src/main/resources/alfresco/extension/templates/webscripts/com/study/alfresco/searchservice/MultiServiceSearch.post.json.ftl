{
 <#if links??>
 	<#list links as link>
 	   "${link_index + 1}" : "${link}"
 	</#list>
 <#elseif errorResult??>
	"error" : "The service encountered an error: ${errorResult}"
 </#if>
}