$(document).ready(function(){
//	target_registration_type();
//	bind_target_registration_type();
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
		
		$('.js-target-category1').val('');
		$('.js-target-category2').val('');
		$('.js-target-bonbu').val('');
		$('.js-target-branch').attr('checked', false);
		
		var ids = [],
			target_group_ids;
		$('.js-target-group-id:checked').each(function(i, el){
			ids.push($(el).val());
		});
		
		target_group_ids = $.toJSON(ids);
		
		$('.js-target-group-ids').val(target_group_ids);
		
		$.post(survey.context_path + '/target-groups/branches', {
			targetGroupIds: target_group_ids
		}, function(branchList){
			$('.js-target-branches').val($.toJSON(branchList));
		}, 'json');
	});
}

function check_target_group_ids(){
	_.forEach(survey.target_group_ids, function(id){
		$('.js-target-group-id[value='+id+']').attr('checked', true);
	});
}