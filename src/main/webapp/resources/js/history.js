$(document).ready(function(){
	bind_name_by_part();
	print_bonbu_select_el();
	print_branch_select_el();
	bind_branch_select_el();
});

function bind_name_by_part(){
	$('[name=part]').change(function(){
		if($(this).val() == ''){
			$('[name=username] option').show();
		}else{
			$('[name=username]').val("");
			$('[name=username] option').hide();
			$('[name=username] option.js-part-' + $(this).val()).show();
			$('[name=username] option.js-all').show();
			
		}
	});
}

function search_by_branch(){
	var branches = [],
		branch = $('[name=branch]').val(),
		bonbu = $('#bonbu').val(),
		branches_string;
	
	if(bonbu == '' && branch == ''){
		// 전체
		_.each(survey.branches, function(branch){
			branches = branches.concat(branch);
		});
	}else  if(bonbu != '' && branch == ''){
		// 본부만 선택
		branches = survey.branches[bonbu];
	}else{
		// 지사까지 선택
		branches.push(branch);
	}
	
	branches_string = $.toJSON(branches);
	
	var params = {
		startDate: $('[name=startDate]').val(),
		endDate: $('[name=endDate]').val(),
		branches: branches_string
	}
	
	$.post(survey.context_path + '/history/search-by-branch', params, function(html){
		$('.js-search-result').html(html);
	});
}

function search_by_user(){
	var params = {
		startDate: $('[name=startDate]').val(),
		endDate: $('[name=endDate]').val(),
		part: $('[name=part]').val(),
		username: $('[name=username]').val()
	}
	
	$.post(survey.context_path + '/history/search-by-user', params, function(html){
		$('.js-search-result').html(html);
	});
}

function print_bonbu_select_el(){
	$('#bonbu').append('<option value="">전체</option>');
	_.each(survey.branches, function(branch, index, list){
		var option = document.createElement("option");
		$('#bonbu').append('<option>' + index + '</option>');
	});
}

function bind_branch_select_el(){
	$('.js-search-by-branch').on('change', '#bonbu', print_branch_select_el);
}

function print_branch_select_el(){
	$('[name=branch]')
		.html('')
		.append('<option value="">전체</option>');
	var bonbu = $('#bonbu').val();
	if(bonbu == ''){
		_.each(survey.branches, function(el){
			_.each(el, function(branch){
				$('[name=branch]').append('<option>' + branch + '</option>');
			});
		});
	}else{
		_.each(survey.branches[bonbu], function(branch){
			$('[name=branch]').append('<option>' + branch + '</option>');
		});
	}
}