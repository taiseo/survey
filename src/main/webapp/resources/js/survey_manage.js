$(document).ready(function(){
	target_registration_type();
	bind_target_registration_type();
	bind_target_group_ids();
	check_target_group_ids();
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

function bind_target_group_ids(){
	$('.js-target-group-id').change(function(){
		var ids = [];
		$('.js-target-group-id:checked').each(function(i, el){
			ids.push($(el).val());
		});
		$('.js-target-group-ids').val($.toJSON(ids));
	});
}

function check_target_group_ids(){
	_.forEach(survey.target_group_ids, function(id){
		$('.js-target-group-id[value='+id+']').attr('checked', true);
	});
}