$(document).ready(function(){
	bind_name_by_part();
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
	var params = {
		branch: $('[name=branch]').val()
	}
	
	$.post(survey.context_path + '/history/search-by-branch', params, function(html){
		$('.js-search-result').html(html);
	});
}

function search_by_user(){
	var params = {
		part: $('[name=part]').val(),
		username: $('[name=username]').val()
	}
	
	$.post(survey.context_path + '/history/search-by-user', params, function(html){
		$('.js-search-result').html(html);
	});
}