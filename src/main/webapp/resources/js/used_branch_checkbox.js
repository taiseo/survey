$(document).ready(function(){
	bind_target_select();
	bind_set_branches();
	attr_disabled_cat2();
	load_branch();
	fill_target_bonbus();
	bind_bonbu_select();
});

function bind_target_select(){
	
	$('.js-used-branch-checkbox-area').on('change', '.js-target-category1', function(){
		attr_disabled_cat2();
		load_branch();
	});
	
	$('.js-used-branch-checkbox-area').on('change', '.js-target-category2', function(){
		load_branch();
	});

	// 지사를 클릭할 때마다 대상 수를 세서 갱신
	$('.js-used-branch-checkbox-area').on('change', '.js-target-branch', write_target_count);
}

function load_branch(){
	var $select_el, category;
	if($('.js-target-category2').is(':disabled')){
		$select_el = $('.js-target-category1'); 
	}else{
		$select_el = $('.js-target-category2');
	}
	
	category = $select_el.val();
	
	// 한글 깨짐 문제를 방지하려고 post로 보낸 것임.
	$.post(survey.context_path + '/customers/branch-list', {
		category: category
	}, function(html){
		$('.js-target-branches-checkbox-area').html(html).show();
		check_seleted_branches();
		fill_target_branches();
		show_branch_by_bonbu();
		write_target_count();
	});
}

function bind_set_branches(){
	$('.js-used-branch-checkbox-area').on('click, change', '.js-target-branch', fill_target_branches);
}

function attr_disabled_cat2(){
	if($('.js-target-category1').val() == '농지은행'){
		$('.js-target-category2').attr('disabled', false);
		$('.js-target-category2-wrapper').show();
	}else{
		$('.js-target-category2').attr('disabled', true);
		$('.js-target-category2-wrapper').hide();
	}
}

function show_branches(){
	if($('.js-branch:checked').length > 0 ){
		$('.js-target-branches').show();
	}
}

function check_seleted_branches(){
	_.each(survey.target_branches, function(branch){
		$('.js-target-branch[value="' + branch + '"]').attr('checked', true);
	});
	
}

function fill_target_branches(){
	var branches = [];
	$('.js-target-branch:checked').each(function(index, branch){
		branches.push($(branch).val());
	});
	$('.js-target-branches').val($.toJSON(branches));
}

function write_target_count(){
	var params = {
		category1: $('.js-target-category1').val(),
		category2: $('.js-target-category2').val(),
		branches: $('.js-target-branches').val(),
		limit: ( $('.js-target-limit').val() || 30 )
	};
	
	if(params.targetBranches == '[]'){
		$('.js-target-count').html('');
		return;
	}
	
	$.post(survey.context_path + '/surveys/target-count', params, function(data){
		$('.js-target-count').html(data);
	});
}

function fill_target_bonbus(){
	$('.js-target-bonbu').html('').append('<option value="">전체 지역</option>');
	_.forEach(survey.branches, function(list, index){
		$('.js-target-bonbu').append('<option>' + index + '</option>');
	});
	$('.js-target-bonbu').val($('.js-target-bonbu').data('value'));
}

function bind_bonbu_select(){
	$('.js-target-bonbu').on('change', show_branch_by_bonbu);
}

function show_branch_by_bonbu(){
	var bonbu = $('.js-target-bonbu').val();
	
	if(bonbu == ''){
		
		// 전체 지역
		$('.js-target-branch').attr('disabled', false);
		$('.js-target-branches-checkbox-area label').show();
		return;
	}
	
	$('.js-target-branch').attr('disabled', true);
	$('.js-target-branches-checkbox-area label').hide();
	$('.js-target-branch').each(function(){
		if(_.indexOf(survey.branches[bonbu], $(this).val()) != -1){
			$(this).attr('disabled', false)
				.parent().show();
		}
	});
}
