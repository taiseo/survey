$(document).ready(function(){
	target_registration_type();
	bind_target_registration_type();
	bind_target_select();
	bind_set_branches();
	attr_disabled_cat2();
	load_branch();
});

function bind_target_select(){
	
	$('.js-category').on('change, click', '[name="targetCategory1"]', function(){
		attr_disabled_cat2();
		load_branch();
	});
	
	$('.js-category').on('change, click', '[name="targetCategory2"]', function(){
		load_branch();
	});

	// 지사를 클릭할 때마다 대상 수를 세서 갱신
	$('.js-category').on('change, click', '.js-branch', write_target_count);
}

function load_branch(){
	var $select_el, category;
	if($('[name="targetCategory2"]').is(':disabled')){
		$select_el = $('[name="targetCategory1"]'); 
	}else{
		$select_el = $('[name="targetCategory2"]');
	}
	
	category = $select_el.val();
	
	// 한글 깨짐 문제를 방지하려고 post로 보낸 것임.
	$.post(survey.context_path + '/customers/branch-list', {
		category: category
	}, function(html){
		$('.js-target-branches').html(html).show();
		check_seleted_branches();
		fill_target_branches();
		write_target_count();
	});
}

function bind_set_branches(){
	$('.js-category').on('click, change', '.js-branch', fill_target_branches);
}

function attr_disabled_cat2(){
	if($('[name="targetCategory1"]').val() == '농지은행'){
		$('[name="targetCategory2"]').attr('disabled', false);
		$('.js-target-category2').show();
	}else{
		$('[name="targetCategory2"]').attr('disabled', true);
		$('.js-target-category2').hide();
	}
}

function show_branches(){
	if($('.js-branch:checked').length > 0 ){
		$('.js-target-branches').show();
	}
}

function check_seleted_branches(){
	_.each(survey.selected_branches, function(branch){
		$('.js-branch[value="' + branch + '"]').attr('checked', true);
	});
	
}

function fill_target_branches(){
	var branches = [];
	$('.js-branch:checked').each(function(index, branch){
		branches.push($(branch).val());
	});
	$('[name=targetBranches]').val($.toJSON(branches));
}

function write_target_count(){
	var params = {
		targetCategory1: $('[name=targetCategory1]').val(),
		targetCategory2: $('[name=targetCategory2]').val(),
		targetBranches: $('[name=targetBranches]').val(),
		limit: ( $('#limit').val() || 30 )
	};
	
	if(params.targetBranches == '[]'){
		$('.js-target-count').html('');
		return;
	}
	
	$.post(survey.context_path + '/surveys/target-count', params, function(data){
		$('.js-target-count').html(data);
	});
}

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
