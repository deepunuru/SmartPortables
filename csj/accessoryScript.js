$(document).ready(function() {
	$('#productType').change(function(event) {
		var var1 = $("select#productType").val();
		$.get('TypeServlet', { typeVal : var1 }, function(response) {
		
		var select1 = $('#accessory1');
		select1.find('option').remove();
		$.each(response, function(index, value) {
		$('<option>').val(value).text(value).appendTo(select1);
			});
		var select2 = $('#accessory2');
		select2.find('option').remove();
		$.each(response, function(index, value) {
		$('<option>').val(value).text(value).appendTo(select2);
			});
		var select3 = $('#accessory3');
		select3.find('option').remove();
		$.each(response, function(index, value) {
		$('<option>').val(value).text(value).appendTo(select3);
			});
		var select4 = $('#accessory4');
		select4.find('option').remove();
		$.each(response, function(index, value) {
		$('<option>').val(value).text(value).appendTo(select4);
			});
		var select5 = $('#accessory5');
		select5.find('option').remove();
		$.each(response, function(index, value) {
		$('<option>').val(value).text(value).appendTo(select5);
			});
		var select6 = $('#accessory6');
		select6.find('option').remove();
		$.each(response, function(index, value) {
		$('<option>').val(value).text(value).appendTo(select6);
			});
		var select7 = $('#accessory7');
		select7.find('option').remove();
		$.each(response, function(index, value) {
		$('<option>').val(value).text(value).appendTo(select7);
			});
		});
	});
});