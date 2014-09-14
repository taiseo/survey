$(document).ready(function(){
	bind_target_select();
});

function bind_target_select(){
	$('.js-category').on('change, click', '[name="대분류"]', function(){
		if($('[name="대분류"]').val() == '농지은행'){
			$('.js-소분류').html($('#소분류').html()).show();
		}else{
			$('.js-소분류').html('').hide();
		}
		load_branch();
	});
	
	$('.js-category').on('change, click', '[name="소분류"]', function(){
		load_branch();
	});
}

function load_branch(){
	var $select_el, category;
	if($('[name="소분류"]').length > 0){
		$select_el = $('[name="소분류"]'); 
	}else{
		$select_el = $('[name="대분류"]');
	}
	
	category = $select_el.val();
	
	// 한글 깨짐 문제를 방지하려고 post로 보낸 것임.
	$.post(survey.context_path + '/customers/branch-list', {
		category: category
	}, function(html){
		$('.js-지사명').html(html).show();
	});
}