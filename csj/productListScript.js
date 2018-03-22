$(document).ready(function() {
	$('#productType').change(function(event) {
		var var1 = $("select#productType").val();
		$.get('ProductListServlet', { typeVal : var1 }, function(response) {
		
		var select = $('#productName');
		select.find('option').remove();
		$.each(response, function(index, value) {
		$('<option>').val(value).text(value).appendTo(select);
			});
		});
	});
});