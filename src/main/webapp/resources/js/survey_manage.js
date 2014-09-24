$(document).ready(function(){
	target_registration_type();
	bind_target_registration_type();
	bind_target_group_ids();
	check_target_group_ids();
	bind_target_el_show_hide();
	target_el_show_hide();
	bind_target_group_detail();
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
	$('.js-target-group-id').change(target_group_change_event);
	$('[name=targetRegistrationType]').change(function(){
		if($('[name=targetRegistrationType]:checked').val() == '캠페인 그룹 선택'){
			target_group_change_event();
		}
	});
}

function target_group_change_event(){
	$('.js-target-category1').val('');
	$('.js-target-category2').val('');
	$('.js-target-bonbu').val('');
	$('.js-target-branch').attr('checked', false);
	
	var target_group_ids = get_target_group_ids();
	
	$('.js-target-group-ids').val(target_group_ids);
	
	// targetGroup에 따라 branch 목록을 가져와서 hidden input에 세팅한다.
	$.post(survey.context_path + '/target-groups/branches', {
		targetGroupIds: target_group_ids
	}, function(branchList){
		$('.js-target-branches').val($.toJSON(branchList));
	}, 'json');
	
	// targetGroup에 따른 인원수를 세서 세팅한다.
	$.post(survey.context_path + '/target-groups/count', {
		targetGroupIds: target_group_ids,
		startDate: $('.js-target-start-date').val(),
		endDate: $('.js-target-end-date').val()
	}, function(html){
		$('.js-target-count').html(html);
	});
}

function get_target_group_ids(){
	var ids = [],
		target_group_ids;

	$('.js-target-group-id:checked').each(function(i, el){
		ids.push($(el).val());
	});
	
	return $.toJSON(ids);
}

function check_target_group_ids(){
	_.forEach(survey.target_group_ids, function(id){
		$('.js-target-group-id[value='+id+']').attr('checked', true);
	});
}

function bind_target_el_show_hide(){
	$('[name="targetRegistrationType"]').on('change', target_el_show_hide);
}

function target_el_show_hide(){
	var type = $('[name="targetRegistrationType"]:checked').val();
	if(type == '캠페인 그룹 선택' || type == 'CRM DB 추출'){
		$('.js-target-date').show();
		$('.js-target-count-tr').show();
	}else{
		$('.js-target-date').hide();
		$('.js-target-count-tr').hide();
	}
}

function bind_target_detail(){
	$('.js-target-count').on('click', '.js-target-detail', target_detail);
}

function target_detail(){
	var params = get_target_params();
	$.post(survey.context_path + '/surveys/target-detail', params, function(html){
		if($('#target-detail-modal').length == 0){
			$('body').append('<div id="target-detail-modal" class="modal  fade" tabindex="-1" role="dialog" aria-hidden="true"></div>');
		}
		$('#target-detail-modal')
			.html(html)
			.modal();
	});
	return false;
}

function bind_target_group_detail(){
	$('.js-target-count').on('click', '.js-target-group-detail', target_group_detail);
}

function target_group_detail(){
	var params = {
		targetGroupIds: get_target_group_ids(),
		startDate: ( $('.js-target-start-date').val() || null ),
		endDate: ( $('.js-target-end-date').val() || null )
	};
	$.post(survey.context_path + '/target-groups/detail', params, function(html){
		if($('#target-group-detail-modal').length == 0){
			$('body').append('<div id="target-group-detail-modal" class="modal  fade" tabindex="-1" role="dialog" aria-hidden="true"></div>');
		}
		$('#target-group-detail-modal')
			.html(html)
			.modal();
	});
	return false;
}
