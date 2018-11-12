<#macro win title id="">
		<div class="modal fade" id="winModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">${title}</h4>
		      </div>
		      <div class="modal-body">
		      		<#nested />
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><@s.m code="global.cancel" /></button>
		        <button type="button" class="btn btn-primary" id="saveBtn"><@s.m code="global.save" /></button>
		      </div>
		    </div>
		  </div>
		</div>
		
		<script type="text/javascript">
		$(function(){
	   		$("#saveBtn").click(function(){
		   		var jsonData = $(".modal-body").toJson();
		   		$.ajax({
					url : "save.json",
					data : JSON.stringify(jsonData),
					cache : false,
					contentType : "application/json",
					type : "POST",
					success : function(json) {
						$("#winModal").modal('hide');
						$(".modal-body").clearValue();
						alert(json.msg);
						$("#table").bootstrapTable("refresh");
					}
				});
	   		});
	   		
	   		$("#editBtn").click(function(){
		   		var data = $("#table").bootstrapTable("getSelections");
				if (data.length == 0)
				{
					alert("警告！请先选择要编辑的数据！");
					return false;
				}
				if (data.length > 1)
				{
					alert("警告！请选择一条数据进行编辑！");
					return false;
				}
		   		var jsonData = data[0];
		   		$(".modal-body").fromJson(jsonData);
		   		$("#winModal").modal('show');
	   		});
	   	});
	   	</script>
</#macro>