$(document).ready(function(){
	bind_target_select();
	bind_set_branches();
});

function bind_target_select(){
	$('.js-category').on('change, click', '[name="targetCategory1"]', function(){
		if($('[name="targetCategory1"]').val() == '농지은행'){
			$('.js-target-category2').html($('#target-category2').html()).show();
		}else{
			$('.js-target-category2').html('').hide();
		}
		load_branch();
	});
	
	$('.js-category').on('change, click', '[name="targetCategory2"]', function(){
		load_branch();
	});
}

function load_branch(){
	var $select_el, category;
	if($('[name="targetCategory2"]').length > 0){
		$select_el = $('[name="targetCategory2"]'); 
	}else{
		$select_el = $('[name="targetCategory1"]');
	}
	
	category = $select_el.val();
	
	// 한글 깨짐 문제를 방지하려고 post로 보낸 것임.
	$.post(survey.context_path + '/customers/branch-list', {
		category: category
	}, function(html){
		$('.js-branches').html(html).show();
	});
}

function bind_set_branches(){
	$('.js-category').on('click, change', '.js-branch', function(){
		var branches = [];
		$('.js-branch:checked').each(function(index, branch){
			console.log($(branch).val());
			branches.push($(branch).val());
		});
		$('[name=targetBranches]').val($.toJSON(branches));
	});
}