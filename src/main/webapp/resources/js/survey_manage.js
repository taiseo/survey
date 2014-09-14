$(document).ready(function(){
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
		fillTargetBranches();
	});
}

function bind_set_branches(){
	$('.js-category').on('click, change', '.js-branch', fillTargetBranches);
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

function fillTargetBranches(){
	var branches = [];
	$('.js-branch:checked').each(function(index, branch){
		branches.push($(branch).val());
	});
	$('[name=targetBranches]').val($.toJSON(branches));
}