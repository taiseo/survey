$(document).ready(function(){
	$('[name=part]').change(function(){
		if($(this).val() == ''){
			$('[name=username] option').show();
		}else{
			$('[name=username] option').hide().blur();
			$('[name=username] option.js-part-' + $(this).val()).show();
		}
	});
});