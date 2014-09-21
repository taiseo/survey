$(document).ready(function(){
	target_registration_type();
	bind_target_registration_type();
});





function bind_target_registration_type(){
	$('.js-survey-form').on('change', '[name=targetRegistrationType]', target_registration_type);
}

function target_registration_type(){
	var target_registration_type = $('[name=targetRegistrationType]:checked').val();
	$('.js-target-registration-type').each(function(index, el){
		$(el).hide();
		if($(el).data('target-registration-type') == target_registration_type){
			$(el).show();
		} 
	});
}
